package thespian4jade.core.player;

import jade.lang.acl.ACLMessage;
import thespian4jade.core.player.responsibility.Responsibility;
import jade.core.AID;
import thespian4jade.lang.Message;
import thespian4jade.proto.Initialize;
import thespian4jade.proto.ResponderParty;
import thespian4jade.proto.SendSuccessOrFailure;
import thespian4jade.proto.SingleReceiverState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.State;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.ResponsibilityArgumentMessage;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.ArgumentRequestMessage;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.InvokeResponsibilityProtocol;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.InvokeResponsibilityRequestMessage;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.ResponsibilityResultMessage;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * A 'Invoke responsibility' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Player_InvokeResponsibility_ResponderParty<TArgument extends Serializable,
    TResult extends Serializable> extends ResponderParty<Player> {
     
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The role; more precisely, its AID.
     */
    private AID role;
    
    /**
     * The name of the responsibility.
     */
    private String responsibilityName;
    
    /**
     * The 'Receive responsibility argument' state.
     */
    private State receiveResponsibilityArgument;
    
    /**
     * The responsibility state.
     */
    private Responsibility<TArgument, TResult> responsibility;
    
    /**
     * The 'Send responsibility result' state.
     */
    private State sendResponsibilityResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Player_InvokeResponsibilityResponder class.
     * @param aclMessage the ACL message
     */
    public Player_InvokeResponsibility_ResponderParty(ACLMessage aclMessage) {
        super(InvokeResponsibilityProtocol.getInstance(), aclMessage);

        role = getACLMessage().getSender();
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the party FSM.
     */
    private void buildFSM() {        
         // ----- States -----
        State initialize = new MyInitialize();
        State receiveInvokeResponsibilityRequest = new ReceiveInvokeResponsibilityRequest();
        State sendResponsibilityArgumentRequest = new SendResponsibilityArgumentRequest();
        receiveResponsibilityArgument = new ReceiveResponsibilityArgument();
        sendResponsibilityResult = new SendResponsibilityResult();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------
        
        // Register states.
        registerFirstState(initialize);        
        registerState(receiveInvokeResponsibilityRequest);    
        registerState(sendResponsibilityArgumentRequest);
        registerState(receiveResponsibilityArgument);
        registerState(sendResponsibilityResult);      
        registerLastState(successEnd);     
        registerLastState(failureEnd);
        
        // Register transitions.
        initialize.registerTransition(MyInitialize.OK, receiveInvokeResponsibilityRequest);
        initialize.registerTransition(MyInitialize.FAIL, failureEnd);       
        receiveInvokeResponsibilityRequest.registerDefaultTransition(sendResponsibilityArgumentRequest);      
        sendResponsibilityArgumentRequest.registerTransition(SendResponsibilityArgumentRequest.SUCCESS, receiveResponsibilityArgument);
        sendResponsibilityArgumentRequest.registerTransition(SendResponsibilityArgumentRequest.FAILURE, failureEnd);       
        sendResponsibilityResult.registerTransition(SendResponsibilityResult.SUCCESS, successEnd);
        sendResponsibilityResult.registerTransition(SendResponsibilityResult.FAILURE, failureEnd);
    }
    
    /**
     * Selets a responsibility specified by its name
     * @param responsibilityName the name of the responsibility to select
     */
    private void selectResponsibility(String responsibilityName) {
        responsibility = createResponsibility(responsibilityName);
        
        // Register the responsibility-related states.
        registerState(responsibility);
        
        // Register the responsibility-related transitions.
        receiveResponsibilityArgument.registerDefaultTransition(responsibility);
        responsibility.registerDefaultTransition(sendResponsibilityResult);
    }
    
    /**
     * Creates a responsibility specified by its name
     * @param responsibilityName the name of the responsibility
     * @return the responsibility
     */
    private Responsibility createResponsibility(String responsibilityName) {
        //System.out.println("----- RESPONSIBILITY NAME: " + responsibilityName + " -----");
        
        // TODO (priority: high) Move the following to the calling method.
        Class responsibilityClass = getMyAgent().responsibilities.get(responsibilityName);
        //System.out.println("----- RESPONSIBILITY CLASS: " + responsibilityClass + " -----");
        
        // Get the responsibility constructor.
        Constructor responsibilityConstructor = null;
        try {
            responsibilityConstructor = responsibilityClass.getConstructor();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        //System.out.println("----- RESPONSIBILITY CONSTRUCTOR: " + responsibilityConstructor + " -----");
        
        // Instantiate the responsibility.
        Responsibility responsibility = null;
        try {
            responsibility = (Responsibility)responsibilityConstructor.newInstance();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }        
        //System.out.println("----- RESPONSIBILITY: " + responsibility + " -----");
        
        return responsibility;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyInitialize extends Initialize {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public int initialize() {
            getMyAgent().logInfo(String.format(
                "Responding to the 'Invoke responsibility' protocol (id = %1$s).",
                getACLMessage().getConversationId()));
        
            if (role.equals(getMyAgent().knowledgeBase.getActiveRole().getRoleAID())) {
                // The sender role is the active role.
                return OK;
            } else {
                // The sender role is not the active role.
                // TODO (priority: low) Send a message to the role exaplaining
                // that a responsibility cannot be invoked by a non-activated role.
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    private class ReceiveInvokeResponsibilityRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            InvokeResponsibilityRequestMessage message = new InvokeResponsibilityRequestMessage();
            message.parseACLMessage(getACLMessage());
            
            responsibilityName = message.getResponsibility();         
            selectResponsibility(responsibilityName);
        }
        
        // </editor-fold>
    }
    
    private class SendResponsibilityArgumentRequest
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
            getMyAgent().logInfo("Send responsibility argument request.");
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
            getMyAgent().logInfo("Responsibility argument request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveResponsibilityArgument
        extends SingleReceiverState<ResponsibilityArgumentMessage<TArgument>> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveResponsibilityArgument() {
            super(new ResponsibilityArgumentMessage.Factory<TArgument>());
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
            getMyAgent().logInfo("Receiving responsibility argument.");
        }
        
        /**
         * Handles the received 'Responsibility argument' message.
         * @param message the received 'Responsibility argument' message
         */
        @Override
        protected void handleMessage(ResponsibilityArgumentMessage<TArgument> message) {
            responsibility.setArgument(message.getArgument());
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibility argument received.");
        }
        
        // </editor-fold>
    }
    
    private class SendResponsibilityResult
        extends SendSuccessOrFailure<ResponsibilityResultMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { role };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending responsibility result.");
        }

        @Override
        protected int onManager() {
            return SUCCESS;
        }
        
        @Override
        protected ResponsibilityResultMessage prepareMessage() {
            ResponsibilityResultMessage message = new ResponsibilityResultMessage();
            message.setResult(responsibility.getResult());
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibility result sent.");
        }
        
        // </editor-fold>
    }
    
    private class SuccessEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo("The 'Invoke responsibility' responder party succeeded.");
        }
        
        // </editor-fold>
    }
    
    private class FailureEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo("The 'Invoke responsibility' responder party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
