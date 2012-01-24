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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * A 'Invoke requirement' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Player_InvokeRequirementResponder extends ResponderParty {
     
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID roleAID;
    
    private String requirementName;
    
    private State receiveRequirementArgument;
    
    private Requirement requirement;
    
    private State sendRequirementResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Player_InvokeRequirementResponder(ACLMessage aclMessage) {
        super(InvokeRequirementProtocol.getInstance(), aclMessage);

        roleAID = getACLMessage().getSender();
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    private Player getMyPlayer() {
        return (Player)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
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
    
    private void selectRequirement(String requirementName) {
        //System.out.println("----- ADDING REQUIREMENT: " + requirementName + " -----");
        requirement = createRequirement(requirementName);
        
        // Register the requirement-related states.
        registerState(requirement);
        
        // Register the requirement-related transitions.
        receiveRequirementArgument.registerDefaultTransition(requirement);
        requirement.registerDefaultTransition(sendRequirementResult);
        //System.out.println("----- REQUIREMENT ADDED -----");
    }
    
    private Requirement createRequirement(String requirementName) {
        //System.out.println("----- CREATING REQUIREMENT: " + requirementName + " -----");
        Class requirementClass = getMyPlayer().requirements.get(requirementName);
        
        // Get the requirement constructor.
        Constructor requirementConstructor = null;
        try {
            requirementConstructor = requirementClass.getConstructor();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        
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
        //System.out.println("----- REQUIREMENT CREATED -----");
        
        return requirement;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyInitialize extends Initialize {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public int initialize() {
            getMyPlayer().logInfo(String.format(
                "Responding to the 'Invoke requirement' protocol (id = %1$s).",
                getACLMessage().getConversationId()));
        
            if (roleAID.equals(getMyPlayer().knowledgeBase.getActiveRole().getRoleAID())) {
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
    
    private class SendRequirementArgumentRequest extends SendSuccessOrFailure {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { roleAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Send requirement argument request.");
        }

        @Override
        protected int onManager() {
            return SUCCESS;
        }
        
        @Override
        protected Message prepareMessage() {
            ArgumentRequestMessage message = new ArgumentRequestMessage();
            return message;
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Requirement argument request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveRequirementArgument
        extends SingleReceiverState<RequirementArgumentMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { roleAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Receiving requirement argument.");
        }
        
        @Override
        protected RequirementArgumentMessage createEmptyMessage() {
            return new RequirementArgumentMessage();
        }
        
        @Override
        protected void handleMessage(RequirementArgumentMessage message) {
            requirement.setArgument(message.getArgument());
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Requirement argument received.");
        }
        
        // </editor-fold>
    }
    
    private class SendRequirementResult extends SendSuccessOrFailure {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { roleAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Sending requirement result.");
        }

        @Override
        protected int onManager() {
            return SUCCESS;
        }
        
        @Override
        protected Message prepareMessage() {
            RequirementResultMessage message = new RequirementResultMessage();
            message.setResult(requirement.getResult());
            return message;
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Requirement result sent.");
        }
        
        // </editor-fold>
    }
    
    private class SuccessEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo("The 'Invoke requirement' responder party succeeded.");
        }
        
        // </editor-fold>
    }
    
    private class FailureEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo("The 'Invoke requirement' responder party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
