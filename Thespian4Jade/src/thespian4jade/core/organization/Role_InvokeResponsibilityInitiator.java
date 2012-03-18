package thespian4jade.core.organization;

import jade.core.AID;
import thespian4jade.core.player.Player;
import thespian4jade.proto.Initialize;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.ReceiveSuccessOrFailure;
import thespian4jade.proto.SendSuccessOrFailure;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.State;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.ResponsibilityArgumentMessage;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.ArgumentRequestMessage;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.InvokeResponsibilityProtocol;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.InvokeResponsibilityRequestMessage;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.ResponsibilityResultMessage;
import java.io.Serializable;

/**
 * A 'Invoke responsibility' protocol initiator party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-22
 * @version %I% %G%
 */
public class Role_InvokeResponsibilityInitiator<TArgument extends Serializable,
    TResult extends Serializable> extends InitiatorParty<Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The player; more precisely its AID.
     */
    private AID player;
    
    /**
     * The name of the responsibility.
     */
    private String responsibilityName;
    
    /**
     * The (serializable) responsibility argument.
     */
    private TArgument responsibilityArgument;
    
    /**
     * The serializable responsibility argument.
     */
    private TResult responsibilityResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    
    
    /**
     * Initializes a new instance of the Role_InvokeResponsibilityInitiator class.
     * @param responsibilityName the name of the responsibility
     */
    public Role_InvokeResponsibilityInitiator(String responsibilityName) {
        super(InvokeResponsibilityProtocol.getInstance());
        // ----- Preconditions -----
        assert responsibilityName != null && !responsibilityName.isEmpty();
        // -------------------------
        
        this.responsibilityName = responsibilityName;
        
        buildFSM();
    }
    
    // TODO Make this constructor the default one.
    public Role_InvokeResponsibilityInitiator(String responsibilityName, TArgument responsibilityArgument) {
        this(responsibilityName);
        
        this.responsibilityArgument = responsibilityArgument;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Sets the responsibility argument.
     * @param responsibilityArgument the responsibility argument
     */
    public void setResponsibilityArgument(TArgument responsibilityArgument) {
        this.responsibilityArgument = responsibilityArgument;
    }
    
    /**
     * Gets the responsibility result.
     * @return the responsibility result
     */
    public Object getResponsibilityResult() {
        return responsibilityResult;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- States -----
        State initialize = new MyInitialize();
        State sendResponsibilityRequest = new SendResponsibilityRequest();
        State receiveResponsibilityArgumentRequest = new ReceiveResponsibilityArgumentRequest();
        State sendResponsibilityArgument = new SendResponsibilityArgument();
        State receiveResponsibilityResult = new ReceiveResponsibilityResult();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        
        registerState(sendResponsibilityRequest);   
        registerState(receiveResponsibilityArgumentRequest);
        registerState(sendResponsibilityArgument);
        registerState(receiveResponsibilityResult);
        
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Regster the transitions.
        initialize.registerTransition(MyInitialize.OK, sendResponsibilityRequest);
        initialize.registerTransition(MyInitialize.FAIL, failureEnd);
        
        sendResponsibilityRequest.registerDefaultTransition(receiveResponsibilityArgumentRequest);
        
        receiveResponsibilityArgumentRequest.registerTransition(ReceiveResponsibilityArgumentRequest.SUCCESS, sendResponsibilityArgument);
        receiveResponsibilityArgumentRequest.registerTransition(ReceiveResponsibilityArgumentRequest.FAILURE, failureEnd);
        
        sendResponsibilityArgument.registerDefaultTransition(receiveResponsibilityResult);
        
        receiveResponsibilityResult.registerTransition(ReceiveResponsibilityResult.SUCCESS, successEnd);
        receiveResponsibilityResult.registerTransition(ReceiveResponsibilityResult.FAILURE, failureEnd);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyInitialize extends Initialize {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int initialize() {
            getMyAgent().logInfo(String.format(
                "Initiating the 'Invoke responsibility' (%1$s) protocol.",
                responsibilityName));

            if (true) {
                // The role can invoke the responsibility.
                player = getMyAgent().playerAID;
                return OK;
            } else {
                // The role can not invoke the responsibility.
                String message = String.format(
                    "I cannot invoke the responsibility '%1$s'.",
                    responsibilityName);
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    private class SendResponsibilityRequest
        extends SingleSenderState<InvokeResponsibilityRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending responsibility request.");
        }
        
        @Override
        protected InvokeResponsibilityRequestMessage prepareMessage() {
            InvokeResponsibilityRequestMessage message = new InvokeResponsibilityRequestMessage();
            message.setResponsibility(responsibilityName);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibility request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveResponsibilityArgumentRequest
        extends ReceiveSuccessOrFailure<ArgumentRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveResponsibilityArgumentRequest() {
            super(new ArgumentRequestMessage.Factory());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving argument request.");
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Argument request received.");
        }
        
        // </editor-fold>
    }
    
    private class SendResponsibilityArgument
        extends SendSuccessOrFailure<ResponsibilityArgumentMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending responsibility argument.");
        }
        
        @Override
        protected int onManager() {
            if (responsibilityArgument != null && responsibilityArgument instanceof Serializable) {
                return SUCCESS;
            } else {
                return FAILURE;
            }
        }
        
        @Override
        protected ResponsibilityArgumentMessage prepareMessage() {
            ResponsibilityArgumentMessage message = new ResponsibilityArgumentMessage();
            message.setArgument(responsibilityArgument);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibility argument sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveResponsibilityResult
        extends ReceiveSuccessOrFailure<ResponsibilityResultMessage<TResult>> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveResponsibilityResult() {
            super(new ResponsibilityResultMessage.Factory<TResult>());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving responsibility result.");
        }
        
        /**
         * Handles the received 'Responsibility result' message.
         * @param message the received 'Responsibility result' message
         */
        @Override
        protected void handleSuccessMessage(ResponsibilityResultMessage<TResult> message) {
            responsibilityResult = message.getResult();
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibility result received.");
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
            getMyAgent().logInfo("The 'Invoke responsibility' initiator party suceeded.");
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
            getMyAgent().logInfo("The 'Invoke responsibility' initiator party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
