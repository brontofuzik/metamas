package jadeorg.core.organization;

import jade.core.AID;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.ReceiveSuccessOrFailure;
import jadeorg.proto.SendSuccessOrFailure;
import jadeorg.proto.SingleReceiverState;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.RequirementArgumentMessage;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.ArgumentRequestMessage;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.MeetRequirementProtocol;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.RequirementRequestMessage;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.RequirementResultMessage;
import java.io.Serializable;

/**
 * A 'Meet requirement' protocol initiator party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-22
 * @version %I% %G%
 */
public class Role_MeetRequirementInitiator extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "meet-requirement-initiator";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID playerAID;
    
    private String requirementName;
    
    private Object requirementArgument;
    
    private Object requirementResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Role_MeetRequirementInitiator(String requirementName) {
        super(NAME);
        // ----- Preconditions -----
        assert requirementName != null && !requirementName.isEmpty();
        // -------------------------
        
        setProtocolId(new Integer(hashCode()).toString());
        this.requirementName = requirementName;
        
        registerStatesAndtransitions();
    }
    
    public Role_MeetRequirementInitiator(String requirementName, Object requirementArgument) {
        this(requirementName);
        
        this.requirementArgument = requirementArgument;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public Protocol getProtocol() {
        return MeetRequirementProtocol.getInstance();
    }
    
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
    
    private void registerStatesAndtransitions() {
        // ----- States -----
        State sendRequirementRequest = new SendRequirementRequest();
        State receiveArgumentRequest = new ReceiveArgumentRequest();
        State sendRequirementArgument = new SendRequirementArgument();
        State receiveRequirementResult = new ReceiveRequirementResult();
        State end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(sendRequirementRequest);
        
        registerState(receiveArgumentRequest);
        registerState(sendRequirementArgument);
        registerState(receiveRequirementResult);
        
        registerLastState(end);
        
        // Regster the transitions.
        sendRequirementRequest.registerDefaultTransition(receiveArgumentRequest);
        
        receiveArgumentRequest.registerDefaultTransition(sendRequirementArgument);
        
        sendRequirementArgument.registerDefaultTransition(receiveRequirementResult);
        
        receiveRequirementResult.registerDefaultTransition(end);            
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class SendRequirementRequest extends SingleSenderState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-requirement-request";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendRequirementRequest() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Sending requirement request.");
        }
        
        @Override
        protected void onSingleSender() {
            RequirementRequestMessage message = new RequirementRequestMessage();
            message.setRequirement(requirementName);
            
            send(message, playerAID);
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Requirement request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveArgumentRequest extends SingleReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-argument-request";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveArgumentRequest() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Receiving argument request.");
        }
        
        @Override
        protected int onSingleReceiver() {
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

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-requirement-argument";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendRequirementArgument() {
            super(NAME, playerAID);
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

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-requirement-result";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveRequirementResult() {
            super(NAME, playerAID);
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
    
    private class End extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "end";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        End() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // TODO Implement.
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
