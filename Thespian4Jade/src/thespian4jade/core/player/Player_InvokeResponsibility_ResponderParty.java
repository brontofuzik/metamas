package thespian4jade.core.player;

import jade.lang.acl.ACLMessage;
import thespian4jade.core.player.responsibility.IResponsibility;
import jade.core.AID;
import thespian4jade.behaviours.states.special.ExitValueState;
import thespian4jade.behaviours.parties.ResponderParty;
import thespian4jade.behaviours.states.sender.SendSuccessOrFailure;
import thespian4jade.behaviours.states.receiver.SingleReceiverState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;
import thespian4jade.protocols.role.invokeresponsibility.ResponsibilityArgumentMessage;
import thespian4jade.protocols.role.invokeresponsibility.ArgumentRequestMessage;
import thespian4jade.protocols.role.invokeresponsibility.InvokeResponsibilityRequestMessage;
import thespian4jade.protocols.role.invokeresponsibility.ResponsibilityResultMessage;
import java.io.Serializable;
import thespian4jade.behaviours.states.special.StateWrapperState;
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
     * @param aclMessage the ACL message
     */
    public Player_InvokeResponsibility_ResponderParty(ACLMessage aclMessage) {
        super(ProtocolRegistry.getProtocol(Protocols.INVOKE_RESPONSIBILITY_PROTOCOL), aclMessage);

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
        IState initialize = new Initialize();
        IState receiveInvokeResponsibilityRequest = new ReceiveInvokeResponsibilityRequest();
        IState selectResponsibility = new SelectResponsibility();
        IState sendResponsibilityArgumentRequest = new SendResponsibilityArgumentRequest();
        receiveResponsibilityArgument = new ReceiveResponsibilityArgument();
        sendResponsibilityResult = new SendResponsibilityResult();
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();
        // ------------------
        
        // Register states.
        registerFirstState(initialize);        
        registerState(receiveInvokeResponsibilityRequest);
        registerState(selectResponsibility);
        registerState(sendResponsibilityArgumentRequest);
        registerState(receiveResponsibilityArgument);
        registerState(sendResponsibilityResult);      
        registerLastState(successEnd);     
        registerLastState(failureEnd);
        
        // Register transitions.
        initialize.registerTransition(Initialize.OK, receiveInvokeResponsibilityRequest);
        initialize.registerTransition(Initialize.FAIL, failureEnd);
        receiveInvokeResponsibilityRequest.registerDefaultTransition(selectResponsibility);
        selectResponsibility.registerTransition(SelectResponsibility.RESPONSIBILITY_EXISTS, sendResponsibilityArgumentRequest);
        selectResponsibility.registerTransition(SelectResponsibility.RESPONSIBILITY_DOES_NOT_EXIST, failureEnd);
        sendResponsibilityArgumentRequest.registerDefaultTransition(receiveResponsibilityArgument);
        sendResponsibilityResult.registerDefaultTransition(successEnd);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Initialize' (exit value) state.
     */
    private class Initialize extends ExitValueState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit values -----
        public static final int OK = 1;
        public static final int FAIL = 2;
        // -----------------------
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public int doAction() {
            // LOG
            getMyAgent().logInfo(String.format(
                "Responding to the 'Invoke responsibility' protocol (id = %1$s).",
                getProtocolId()));
        
            if (role.equals(getMyAgent().knowledgeBase.query().getActiveRole().getAID())) {
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
    
    /**
     * The 'Receive invoke responsibility request' (one-shot) state.
     */
    private class ReceiveInvokeResponsibilityRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            InvokeResponsibilityRequestMessage message = new InvokeResponsibilityRequestMessage();
            message.parseACLMessage(getACLMessage());
            
            responsibilityName = message.getResponsibility();         
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Select responsbility' (exit value) state.
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
    
    private class ResponsibilityWrapperState
        extends StateWrapperState<IResponsibility<TArgument, TResult>> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ResponsibilityWrapperState(IResponsibility responsibility) {
            super(responsibility);
        } 

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void setWrappedStateArgument(IResponsibility<TArgument, TResult> responsibility) {
            responsibility.setArgument(argument);
        }

        @Override
        protected void getWrappedStateResult(IResponsibility<TArgument, TResult> responsibility) {
            result = responsibility.getResult();
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
     * The 'Success end' (one-shot) state.
     */
    private class SuccessEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Invoke responsibility' protocol (id = %1$s) responder party succeeded.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Failure end' (one-shot) state.
     */
    private class FailureEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Invoke competence' protocol (id = %1$s) responder party failed.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
