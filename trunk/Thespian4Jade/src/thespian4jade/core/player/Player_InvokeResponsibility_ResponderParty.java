package thespian4jade.core.player;

import jade.lang.acl.ACLMessage;
import jade.core.AID;
import java.io.Serializable;
import thespian4jade.behaviours.states.special.ExitValueState;
import thespian4jade.behaviours.parties.ResponderParty;
import thespian4jade.behaviours.states.sender.SendSuccessOrFailure;
import thespian4jade.behaviours.states.receiver.SingleReceiverState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;
import thespian4jade.core.player.responsibility.IResponsibility;
import thespian4jade.protocols.role.invokeresponsibility.ResponsibilityArgumentMessage;
import thespian4jade.protocols.role.invokeresponsibility.ArgumentRequestMessage;
import thespian4jade.protocols.role.invokeresponsibility.InvokeResponsibilityRequestMessage;
import thespian4jade.protocols.role.invokeresponsibility.ResponsibilityResultMessage;
import thespian4jade.behaviours.states.special.WrapperState;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;
import thespian4jade.utililites.ClassHelper;

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
     * The role requesting the responsibility invocation; more precisely its AID.
     * The initiator party.
     */
    private AID role;
    
    /**
     * The name of the responsibility.
     */
    private String responsibilityName;
    
    /**
     * The responsibility argument.
     */
    private TArgument argument;
    
    /**
     * The responsibility result.
     */
    private TResult result;
    
    /**
     * The 'Receive responsibility argument' state.
     */
    private IState receiveResponsibilityArgument;
    
    /**
     * The 'Send responsibility result' state.
     */
    private IState sendResponsibilityResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Player_InvokeResponsibility_ResponderParty class.
     * @param message the ACL message intiating the protocol
     */
    public Player_InvokeResponsibility_ResponderParty(ACLMessage message) {
        super(ProtocolRegistry.getProtocol(Protocols.INVOKE_RESPONSIBILITY_PROTOCOL), message);
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the party FSM.
     */
    private void buildFSM() {        
         // ----- States -----
        IState initialize = new Initialize();
        IState receiveInvokeResponsibilityRequest = new ReceiveInvokeResponsibilityRequest();
        IState selectResponsibility = new SelectResponsibility();
        IState sendResponsibilityArgumentRequest = new SendResponsibilityArgumentRequest();
        receiveResponsibilityArgument = new ReceiveResponsibilityArgument();
        sendResponsibilityResult = new SendResponsibilityResult();
        IState responsibilityInvoked = new ResponsibilityInvoked();
        IState responsibilityNotInvoked = new ResponsibilityNotInvoked();
        // ------------------
        
        // Register states.
        registerFirstState(initialize);        
        registerState(receiveInvokeResponsibilityRequest);
        registerState(selectResponsibility);
        registerState(sendResponsibilityArgumentRequest);
        registerState(receiveResponsibilityArgument);
        registerState(sendResponsibilityResult);      
        registerLastState(responsibilityInvoked);     
        registerLastState(responsibilityNotInvoked);
        
        // Register transitions.
        initialize.registerDefaultTransition(receiveInvokeResponsibilityRequest);
        receiveInvokeResponsibilityRequest.registerTransition(ReceiveInvokeResponsibilityRequest.ROLE_IS_ACTIVE, selectResponsibility);
        receiveInvokeResponsibilityRequest.registerTransition(ReceiveInvokeResponsibilityRequest.ROLE_IS_NOT_ACTIVE, responsibilityNotInvoked);
        selectResponsibility.registerTransition(SelectResponsibility.RESPONSIBILITY_EXISTS, sendResponsibilityArgumentRequest);
        selectResponsibility.registerTransition(SelectResponsibility.RESPONSIBILITY_DOES_NOT_EXIST, responsibilityNotInvoked);
        sendResponsibilityArgumentRequest.registerDefaultTransition(receiveResponsibilityArgument);
        sendResponsibilityResult.registerDefaultTransition(responsibilityInvoked);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Initialize' initial (one-shot) state.
     * A state in which the party is initialized.
     */
    private class Initialize extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Invoke responsibility' protocol (id = %1$s) initiator party started.",
                getProtocolId()));
        
            role = getACLMessage().getSender();
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive invoke responsibility request' (single receiver) state.
     * A state in which the 'Invoke responsibility request' is received.
     */
    private class ReceiveInvokeResponsibilityRequest extends ExitValueState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit values -----
        static final int ROLE_IS_ACTIVE = 1;
        static final int ROLE_IS_NOT_ACTIVE = 2;
        // -----------------------
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected int doAction() {
            if (role.equals(getMyAgent().knowledgeBase.query().getActivePosition().getAID())) {
                // The sender role is the active role.
                InvokeResponsibilityRequestMessage message = new InvokeResponsibilityRequestMessage();
                message.parseACLMessage(getACLMessage());   
                responsibilityName = message.getResponsibility();    
                return ROLE_IS_ACTIVE;
            } else {
                // The sender role is not the active role.
                // TODO (priority: low) Send a message to the role exaplaining
                // that a responsibility cannot be invoked by a non-activated role.
                return ROLE_IS_NOT_ACTIVE;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Select responsibility' (exit value) state.
     * A state in which the responsibility is selected.
     */
    private class SelectResponsibility extends ExitValueState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit values -----
        static final int RESPONSIBILITY_EXISTS = 1;
        static final int RESPONSIBILITY_DOES_NOT_EXIST = 2;
        // -----------------------
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected int doAction() {
            Class responsibilityClass = getMyAgent().responsibilities.get(responsibilityName);
            if (responsibilityClass != null) {
                // The responsibility exsits.
                IResponsibility responsibility = ClassHelper.instantiateClass(responsibilityClass);
                ResponsibilityWrapperState responsibilityWrapper = new ResponsibilityWrapperState(responsibility);
        
                // Register the responsibility-related states.
                registerState(responsibilityWrapper);
        
                // Register the responsibility-related transitions.
                receiveResponsibilityArgument.registerDefaultTransition(responsibilityWrapper);
                responsibilityWrapper.registerDefaultTransition(sendResponsibilityResult);
                return RESPONSIBILITY_EXISTS;
            }   else {
                // The responsibility does not exist.
                return RESPONSIBILITY_DOES_NOT_EXIST;
            }
        }
    
        // </editor-fold>
    }
    
    /**
     * The 'Send responsibility argument request' (send-success-or-failure) state.
     * A state in which a 'Competence argument request' message is sent
     * in case of success, or a FAILURE message. 
     */
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
            // LOG
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
            // LOG
            getMyAgent().logInfo("Responsibility argument request sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive responsibility argument' (single receiver) state.
     * A stat in which the responsibility argument is received.
     */
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
            // LOG
            getMyAgent().logInfo("Receiving responsibility argument.");
        }
        
        /**
         * Handles the received 'Responsibility argument' message.
         * @param message the received 'Responsibility argument' message
         */
        @Override
        protected void handleMessage(ResponsibilityArgumentMessage<TArgument> message) {
            argument = message.getArgument();
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibility argument received.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Responsibility wrapper' (wrapper) state.
     * A state in which the responsibility is executed.
     */
    private class ResponsibilityWrapperState
        extends WrapperState<IResponsibility<TArgument, TResult>> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ResponsibilityWrapperState(IResponsibility responsibility) {
            super(responsibility);
        } 

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void doActionBefore(IResponsibility<TArgument, TResult> responsibility) {
            responsibility.setArgument(argument);
        }

        @Override
        protected void doActionAfter(IResponsibility<TArgument, TResult> responsibility) {
            result = responsibility.getResult();
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send responsibility result' (send-success-or-failure) state.
     * A state in which either a responsibility result is sent in case of success,
     * or a FAILURE message.
     */
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
            // LOG
            getMyAgent().logInfo("Sending responsibility result.");
        }

        @Override
        protected int onManager() {
            return SUCCESS;
        }
        
        @Override
        protected ResponsibilityResultMessage prepareMessage() {
            ResponsibilityResultMessage message = new ResponsibilityResultMessage();
            message.setResult(result);
            return message;
        }

        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Responsibility result sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Responsibility invoked' final (one-shot) state.
     */
    private class ResponsibilityInvoked extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Invoke responsibility' protocol (id = %1$s) responder party ended; responsibility was invoked.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Responsibility not invoked' final (one-shot) state.
     */
    private class ResponsibilityNotInvoked extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Invoke competence' protocol (id = %1$s) responder party ended; responsibility was not invoked.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
