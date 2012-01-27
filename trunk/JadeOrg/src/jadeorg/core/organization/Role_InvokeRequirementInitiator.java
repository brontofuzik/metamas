package jadeorg.core.organization;

import jade.core.AID;
import jadeorg.lang.Message;
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
public class Role_InvokeRequirementInitiator<TArgument extends Serializable,
    TResult extends Serializable> extends InitiatorParty {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The player; more precisely its AID.
     */
    private AID player;
    
    /**
     * The name of the requirement.
     */
    private String requirementName;
    
    /**
     * The (serializable) requirement argument.
     */
    private TArgument requirementArgument;
    
    /**
     * The serializable requirement argument.
     */
    private TResult requirementResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    
    
    /**
     * Initializes a new instance of the Role_InvokeRequirementInitiator class.
     * @param requirementName the name of the requirement
     */
    public Role_InvokeRequirementInitiator(String requirementName) {
        super(InvokeRequirementProtocol.getInstance());
        // ----- Preconditions -----
        assert requirementName != null && !requirementName.isEmpty();
        // -------------------------
        
        this.requirementName = requirementName;
        
        buildFSM();
    }
    
    // TODO Make this constructor the default one.
    public Role_InvokeRequirementInitiator(String requirementName, TArgument requirementArgument) {
        this(requirementName);
        
        this.requirementArgument = requirementArgument;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Sets the requirement argument.
     * @param requirementArgument the requirement argument
     */
    public void setRequirementArgument(TArgument requirementArgument) {
        this.requirementArgument = requirementArgument;
    }
    
    /**
     * Gets the requirement result.
     * @return the requirement result
     */
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
                player = getMyRole().playerAID;
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
    
    private class SendRequirementRequest
        extends SingleSenderState<InvokeRequirementRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Sending requirement request.");
        }
        
        @Override
        protected InvokeRequirementRequestMessage prepareMessage() {
            InvokeRequirementRequestMessage message = new InvokeRequirementRequestMessage();
            message.setRequirement(requirementName);
            return message;
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Requirement request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveRequirementArgumentRequest
        extends ReceiveSuccessOrFailure<ArgumentRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveRequirementArgumentRequest() {
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
            getMyRole().logInfo("Receiving argument request.");
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Argument request received.");
        }
        
        // </editor-fold>
    }
    
    private class SendRequirementArgument
        extends SendSuccessOrFailure<RequirementArgumentMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { player };
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
        protected RequirementArgumentMessage prepareMessage() {
            RequirementArgumentMessage message = new RequirementArgumentMessage();
            message.setArgument(requirementArgument);
            return message;
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Requirement argument sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveRequirementResult
        extends ReceiveSuccessOrFailure<RequirementResultMessage<TResult>> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveRequirementResult() {
            super(new RequirementResultMessage.Factory<TResult>());
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
            getMyRole().logInfo("Receiving requirement result.");
        }
        
        /**
         * Handles the received 'Requirement result' message.
         * @param message the received 'Requirement result' message
         */
        @Override
        protected void handleSuccessMessage(RequirementResultMessage<TResult> message) {
            requirementResult = message.getResult();
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
