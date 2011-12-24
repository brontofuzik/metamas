package jadeorg.core.organization;

import jadeorg.proto.MultiReceiverState;
import jadeorg.proto.MultiSenderState;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.SimpleState;
import jadeorg.proto.SingleReceiverState;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.ArgumentRequestMessage;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.MeetRequirementProtocol;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.RequirementRequestMessage;

/**
 * A 'Meet requirement' protocol initiator party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-22
 * @version %I% %G%
 */
public class Role_MeetRequirementInitiator_New extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "meet-requirement-initiator-new";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Object argument;
    
    private Object result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Role_MeetRequirementInitiator_New() {
        super(NAME);
        registerStatesAndtransitions();
    }
    
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
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public Protocol getProtocol() {
        return MeetRequirementProtocol.getInstance();
    }
        
    // ----- PACKAGE -----    
    
    Power getMyPower() {
        return (Power)getParent();
    }
    
    void setArgument(Object argument) {
        this.argument = argument;
    }
    
    Object getResult() {
        return result;
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
            getMyPower().getMyRole().logInfo("Sending requirement request.");
        }
        
        @Override
        protected void onSender() {
            RequirementRequestMessage message = new RequirementRequestMessage();
            message.setRequirement(getParent().getBehaviourName());
            
            send(message, getMyPower().getPlayerAID());
        }

        @Override
        protected void onExit() {
            getMyPower().getMyRole().logInfo("Requirement request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveArgumentRequest extends SingleReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-argument-request";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveArgumentRequest() {
            super(NAME, null); // TODO playerAID
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPower().getMyRole().logInfo("Receiving argument request.");
        }
        
        @Override
        protected int onReceiver() {
            ArgumentRequestMessage message = new ArgumentRequestMessage();
            boolean messageReceived = receive(message, getMyPower().getPlayerAID());
            
            if (messageReceived) {
                return InnerReceiverState.RECEIVED;
            } else {
                return InnerReceiverState.NOT_RECEIVED;
            }
        }

        @Override
        protected void onExit() {
            getMyPower().getMyRole().logInfo("Argument request received.");
        }
        
        // </editor-fold>
    }
    
    private class SendRequirementArgument extends MultiSenderState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-requirement-argument";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendRequirementArgument() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPower().getMyRole().logInfo("Sending requirement argument.");
        }
        
        @Override
        protected int onManager() {
            // TODO Implement.
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected void onExit() {
            getMyPower().getMyRole().logInfo("Requirement argument sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveRequirementResult extends MultiReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-requirement-result";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveRequirementResult() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPower().getMyRole().logInfo("Receiving requirement result.");
        }

        @Override
        protected void onExit() {
            getMyPower().getMyRole().logInfo("Requirement result received.");
        }
        
        // </editor-fold>
    }
    
    private class End extends SimpleState {

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
