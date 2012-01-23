package jadeorg.core.organization;

import jade.core.AID;
import jadeorg.proto.Initialize;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.ReceiveSuccessOrFailure;
import jadeorg.proto.SendSuccessOrFailure;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.roleprotocol.invokerequirementprotocol.RequirementArgumentMessage;
import jadeorg.proto.roleprotocol.invokerequirementprotocol.ArgumentRequestMessage;
import jadeorg.proto.roleprotocol.invokerequirementprotocol.InvokeRequirementProtocol;
import jadeorg.proto.roleprotocol.invokerequirementprotocol.InvokeRequirementRequestMessage;
import jadeorg.proto.roleprotocol.invokerequirementprotocol.RequirementResultMessage;
import java.io.Serializable;

/**
 * A 'Invoke requirement' protocol initiator party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-22
 * @version %I% %G%
 */
public class Role_InvokeRequirementInitiator extends InitiatorParty {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID playerAID;
    
    private String requirementName;
    
    private Object requirementArgument;
    
    private Object requirementResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Role_InvokeRequirementInitiator(String requirementName) {
        super(InvokeRequirementProtocol.getInstance());
        // ----- Preconditions -----
        assert requirementName != null && !requirementName.isEmpty();
        // -------------------------
        
        this.requirementName = requirementName;
        
        buildFSM();
    }
    
    public Role_InvokeRequirementInitiator(String requirementName, Object requirementArgument) {
        this(requirementName);
        
        this.requirementArgument = requirementArgument;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public void setRequirementArgument(Object requirementArgument) {
        this.requirementArgument = requirementArgument;
    }
    
    public Object getRequirementResult() {
        return requirementResult;
    }
    
    // ----- PRIVATE -----
    
    private Role getMyRole() {
        return (Role)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- States -----
        State initialize = new MyInitialize();
        State sendRequirementRequest = new SendRequirementRequest();
        State receiveRequirementArgumentRequest = new ReceiveRequirementArgumentRequest();
        State sendRequirementArgument = new SendRequirementArgument();
        State receiveRequirementResult = new ReceiveRequirementResult();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        
        registerState(sendRequirementRequest);   
        registerState(receiveRequirementArgumentRequest);
        registerState(sendRequirementArgument);
        registerState(receiveRequirementResult);
        
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Regster the transitions.
        initialize.registerTransition(MyInitialize.OK, sendRequirementRequest);
        initialize.registerTransition(MyInitialize.FAIL, failureEnd);
        
        sendRequirementRequest.registerDefaultTransition(receiveRequirementArgumentRequest);
        
        receiveRequirementArgumentRequest.registerTransition(ReceiveRequirementArgumentRequest.SUCCESS, sendRequirementArgument);
        receiveRequirementArgumentRequest.registerTransition(ReceiveRequirementArgumentRequest.FAILURE, failureEnd);
        
        sendRequirementArgument.registerDefaultTransition(receiveRequirementResult);
        
        receiveRequirementResult.registerTransition(ReceiveRequirementResult.SUCCESS, successEnd);
        receiveRequirementResult.registerTransition(ReceiveRequirementResult.FAILURE, failureEnd);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyInitialize extends Initialize {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int initialize() {
            getMyRole().logInfo(String.format(
                "Initiating the 'Invoke requirement' (%1$s) protocol.",
                requirementName));

            if (true) {
                // The role can invoke the requirement.
                playerAID = getMyRole().playerAID;
                return OK;
            } else {
                // The role can not invoke the requirement.
                String message = String.format(
                    "I cannot invoke the requirement '%1$s'.",
                    requirementName);
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    private class SendRequirementRequest extends SingleSenderState {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { playerAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Sending requirement request.");
        }
        
        @Override
        protected void onSingleSender() {
            InvokeRequirementRequestMessage message = new InvokeRequirementRequestMessage();
            message.setRequirement(requirementName);
            
            send(message, playerAID);
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Requirement request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveRequirementArgumentRequest extends ReceiveSuccessOrFailure {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { playerAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Receiving argument request.");
        }
        
        @Override
        protected int onSuccessReceiver() {
            ArgumentRequestMessage message = new ArgumentRequestMessage();
            boolean messageReceived = receive(message, playerAID);
            
            if (messageReceived) {
                return InnerReceiverState.RECEIVED;
            } else {
                return InnerReceiverState.NOT_RECEIVED;
            }
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Argument request received.");
        }
        
        // </editor-fold>
    }
    
    private class SendRequirementArgument extends SendSuccessOrFailure {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { playerAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Sending requirement argument.");
        }
        
        @Override
        protected int onManager() {
            if (requirementArgument != null && requirementArgument instanceof Serializable) {
                return SUCCESS;
            } else {
                return FAILURE;
            }
        }
        
        @Override
        protected void onSuccessSender() {
            RequirementArgumentMessage message = new RequirementArgumentMessage();
            message.setArgument(requirementArgument);

            send(message, playerAID);
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Requirement argument sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveRequirementResult extends ReceiveSuccessOrFailure {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { playerAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Receiving requirement result.");
        }
        
        @Override
        protected int onSuccessReceiver() {
            RequirementResultMessage message = new RequirementResultMessage();
            boolean messageReceived = receive(message, playerAID);

            if (messageReceived) {
                requirementResult = message.getResult();
                return InnerReceiverState.RECEIVED;
            } else {
                requirementResult = null;
                return InnerReceiverState.NOT_RECEIVED;
            }
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Requirement result received.");
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
            getMyRole().logInfo("The 'Invoke requirement' initiator party suceeded.");
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
            getMyRole().logInfo("The 'Invoke requirement' initiator party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
