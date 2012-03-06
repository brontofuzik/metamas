package jadeorg.core.player;

import jade.lang.acl.ACLMessage;
import jadeorg.core.player.requirement.Requirement;
import jade.core.AID;
import jadeorg.lang.Message;
import jadeorg.proto.Initialize;
import jadeorg.proto.ResponderParty;
import jadeorg.proto.SendSuccessOrFailure;
import jadeorg.proto.SingleReceiverState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.roleprotocol.invokerequirementprotocol.RequirementArgumentMessage;
import jadeorg.proto.roleprotocol.invokerequirementprotocol.ArgumentRequestMessage;
import jadeorg.proto.roleprotocol.invokerequirementprotocol.InvokeRequirementProtocol;
import jadeorg.proto.roleprotocol.invokerequirementprotocol.InvokeRequirementRequestMessage;
import jadeorg.proto.roleprotocol.invokerequirementprotocol.RequirementResultMessage;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * A 'Invoke requirement' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Player_InvokeRequirementResponder<TArgument extends Serializable,
    TResult extends Serializable> extends ResponderParty<Player> {
     
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The role; more precisely, its AID.
     */
    private AID role;
    
    /**
     * The name of the requirement.
     */
    private String requirementName;
    
    /**
     * The 'Receive requirement argument' state.
     */
    private State receiveRequirementArgument;
    
    /**
     * The requirement state.
     */
    private Requirement<TArgument, TResult> requirement;
    
    /**
     * The 'Send requirement result' state.
     */
    private State sendRequirementResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Player_InvokeRequirementResponder class.
     * @param aclMessage the ACL message
     */
    public Player_InvokeRequirementResponder(ACLMessage aclMessage) {
        super(InvokeRequirementProtocol.getInstance(), aclMessage);

        role = getACLMessage().getSender();
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the finite state machine, i. e. registers the states and transitions.
     */
    private void buildFSM() {        
         // ----- States -----
        State initialize = new MyInitialize();
        State receiveInvokeRequirementRequest = new ReceiveInvokeRequirementRequest();
        State sendRequirementArgumentRequest = new SendRequirementArgumentRequest();
        receiveRequirementArgument = new ReceiveRequirementArgument();
        sendRequirementResult = new SendRequirementResult();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------
        
        // Register states.
        registerFirstState(initialize);
        
        registerState(receiveInvokeRequirementRequest);    
        registerState(sendRequirementArgumentRequest);
        registerState(receiveRequirementArgument);
        registerState(sendRequirementResult);
        
        registerLastState(successEnd);     
        registerLastState(failureEnd);
        
        // Register transitions.
        initialize.registerTransition(MyInitialize.OK, receiveInvokeRequirementRequest);
        initialize.registerTransition(MyInitialize.FAIL, failureEnd);
        
        receiveInvokeRequirementRequest.registerDefaultTransition(sendRequirementArgumentRequest);
        
        sendRequirementArgumentRequest.registerTransition(SendRequirementArgumentRequest.SUCCESS, receiveRequirementArgument);
        sendRequirementArgumentRequest.registerTransition(SendRequirementArgumentRequest.FAILURE, failureEnd);
        
        sendRequirementResult.registerTransition(SendRequirementResult.SUCCESS, successEnd);
        sendRequirementResult.registerTransition(SendRequirementResult.FAILURE, failureEnd);
    }
    
    /**
     * Selets a requirement specified by its name
     * @param requirementName the name of the requirement to select
     */
    private void selectRequirement(String requirementName) {
        requirement = createRequirement(requirementName);
        
        // Register the requirement-related states.
        registerState(requirement);
        
        // Register the requirement-related transitions.
        receiveRequirementArgument.registerDefaultTransition(requirement);
        requirement.registerDefaultTransition(sendRequirementResult);
    }
    
    /**
     * Creates a requirement specified by its name
     * @param requirementName the name of the requirement
     * @return the requirement
     */
    private Requirement createRequirement(String requirementName) {
        System.out.println("----- REQUIREMENT NAME: " + requirementName + " -----");
        
        Class requirementClass = getMyAgent().responsibilities.get(requirementName);
        System.out.println("----- REQUIREMENT CLASS: " + requirementClass + " -----");
        
        // Get the requirement constructor.
        Constructor requirementConstructor = null;
        try {
            requirementConstructor = requirementClass.getConstructor();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        System.out.println("----- REQUIREMENT CONSTRUCTOR: " + requirementConstructor + " -----");
        
        // Instantiate the requirement.
        Requirement requirement = null;
        try {
            requirement = (Requirement)requirementConstructor.newInstance();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }        
        System.out.println("----- REQUIREMENT: " + requirement + " -----");
        
        return requirement;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyInitialize extends Initialize {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public int initialize() {
            getMyAgent().logInfo(String.format(
                "Responding to the 'Invoke requirement' protocol (id = %1$s).",
                getACLMessage().getConversationId()));
        
            if (role.equals(getMyAgent().knowledgeBase.getActiveRole().getRoleAID())) {
                // The sender role is the active role.
                return OK;
            } else {
                // The sender role is not the active role.
                // TODO
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    private class ReceiveInvokeRequirementRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            InvokeRequirementRequestMessage message = new InvokeRequirementRequestMessage();
            message.parseACLMessage(getACLMessage());
            
            requirementName = message.getRequirement();         
            selectRequirement(requirementName);
        }
        
        // </editor-fold>
    }
    
    private class SendRequirementArgumentRequest
        extends SendSuccessOrFailure<ArgumentRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { role };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Send requirement argument request.");
        }

        @Override
        protected int onManager() {
            return SUCCESS;
        }
        
        @Override
        protected ArgumentRequestMessage prepareMessage() {
            ArgumentRequestMessage message = new ArgumentRequestMessage();
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Requirement argument request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveRequirementArgument
        extends SingleReceiverState<RequirementArgumentMessage<TArgument>> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveRequirementArgument() {
            super(new RequirementArgumentMessage.Factory<TArgument>());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { role };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving requirement argument.");
        }
        
        /**
         * Handles the received 'Requirement argument' message.
         * @param message the received 'Requirement argument' message
         */
        @Override
        protected void handleMessage(RequirementArgumentMessage<TArgument> message) {
            requirement.setArgument(message.getArgument());
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Requirement argument received.");
        }
        
        // </editor-fold>
    }
    
    private class SendRequirementResult
        extends SendSuccessOrFailure<RequirementResultMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { role };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending requirement result.");
        }

        @Override
        protected int onManager() {
            return SUCCESS;
        }
        
        @Override
        protected RequirementResultMessage prepareMessage() {
            RequirementResultMessage message = new RequirementResultMessage();
            message.setResult(requirement.getResult());
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Requirement result sent.");
        }
        
        // </editor-fold>
    }
    
    private class SuccessEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo("The 'Invoke requirement' responder party succeeded.");
        }
        
        // </editor-fold>
    }
    
    private class FailureEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo("The 'Invoke requirement' responder party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
