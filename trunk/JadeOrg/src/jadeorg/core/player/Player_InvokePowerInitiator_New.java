package jadeorg.core.player;

import jade.core.AID;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.ReceiveSuccessOrFailure;
import jadeorg.proto.SimpleState;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerProtocol;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerRequestMessage;
import jadeorg.proto.roleprotocol.invokepowerprotocol.PowerArgumentMessage;
import jadeorg.proto.roleprotocol.invokepowerprotocol.PowerArgumentRequestMessage;
import jadeorg.proto.roleprotocol.invokepowerprotocol.PowerResultMessage;

/**
 * An 'Invoke power' protocol initiator party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Player_InvokePowerInitiator_New extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "invoke-power-initiator-new";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID roleAID;
    
    private String powerName;
    
    private Object powerArgument;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Player_InvokePowerInitiator_New(String powerName, Object powerArgument) {
        super(NAME);
        
        this.powerName = powerName;
        this.powerArgument = powerArgument;
        
        registerStatesAndTransitions();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @Override
    public Protocol getProtocol() {
        return InvokePowerProtocol.getInstance();
    }
    
    // ----- PRIVATE -----
    
    private Player getMyPlayer() {
        return (Player)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void registerStatesAndTransitions() {
        // ----- States -----
        State sendInvokePowerRequest = new SendInvokePowerRequest();
        State receivePowerArgumentRequest = new ReceivePowerArgumentRequest();
        State sendPowerArgument = new SendPowerArgument();
        State receivePowerResult = new ReceivePowerResult();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------
        
        // Register the states.
        registerFirstState(sendInvokePowerRequest);
        registerState(receivePowerArgumentRequest);
        registerState(sendPowerArgument);
        registerState(receivePowerResult);
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register the transitions.
        sendInvokePowerRequest.registerDefaultTransition(receivePowerArgumentRequest);
        
        receivePowerArgumentRequest.registerTransition(ReceivePowerArgumentRequest.SUCCESS, sendPowerArgument);
        receivePowerArgumentRequest.registerTransition(ReceivePowerArgumentRequest.FAILURE, failureEnd);
        
        sendPowerArgument.registerDefaultTransition(receivePowerResult);
        
        receivePowerResult.registerTransition(ReceivePowerResult.SUCCESS, successEnd);       
        receivePowerResult.registerTransition(ReceivePowerResult.FAILURE, failureEnd);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class SendInvokePowerRequest extends SingleSenderState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-invoke-request";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendInvokePowerRequest() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Sending invoke power request.");
        }
        
        @Override
        protected void onSingleSender() {
            InvokePowerRequestMessage message = new InvokePowerRequestMessage();
            message.setPower(powerName);
            
            send(message, roleAID);
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Invoke power requets sent.");
        }
        
        // </editor-fold>    
    }
    
    private class ReceivePowerArgumentRequest extends ReceiveSuccessOrFailure {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-power-argument-request";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceivePowerArgumentRequest() {
            super(NAME, roleAID);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Receiving power argument request.");
        }
        
        @Override
        protected int onSuccessReceiver() {
            PowerArgumentRequestMessage message = new PowerArgumentRequestMessage();
            boolean messageReceived = receive(message, roleAID);
            
            if (messageReceived) {
                return InnerReceiverState.RECEIVED;
            } else {
                return InnerReceiverState.NOT_RECEIVED;
            }
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Power argument request received.");
        }
        
        // </editor-fold>
    }
    
    private class SendPowerArgument extends SingleSenderState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-power-argument";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendPowerArgument() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
       
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Sending power argument inform.");
        }
        
        @Override
        protected void onSingleSender() {
            PowerArgumentMessage message = new PowerArgumentMessage();
            message.setArgument(powerArgument);
            
            send(message, roleAID);
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Power argument inform sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceivePowerResult extends ReceiveSuccessOrFailure {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-power-result";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceivePowerResult() {
            super(NAME, roleAID);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Receiving power result inform.");
        }
        
        @Override
        protected int onSuccessReceiver() {
            PowerResultMessage message = new PowerResultMessage();
            boolean messageReceived = receive(message, roleAID);
            
            if (messageReceived) {
                getMyPlayer().knowledgeBase.getActiveRole()
                    .savePowerResult(powerName, message.getResult());
                return InnerReceiverState.RECEIVED;
            } else {
                return InnerReceiverState.NOT_RECEIVED;
            }
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Power result inform received.");
        }
        
        // </editor-fold>
    }
    
    private class SuccessEnd extends SimpleState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "success-end";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SuccessEnd() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo("The 'Invoke power' initiator party succeeded.");
        }
        
        // </editor-fold>
    }
    
    private class FailureEnd extends SimpleState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "failure-end";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        FailureEnd() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo("The 'Invoke power' initiator party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
