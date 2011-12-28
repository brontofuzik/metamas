package jadeorg.core.organization;

import jade.core.AID;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.SendSuccessOrFailure;
import jadeorg.proto.SimpleState;
import jadeorg.proto.SingleReceiverState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerProtocol;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerRequestMessage;
import jadeorg.proto.roleprotocol.invokepowerprotocol.PowerArgumentMessage;
import jadeorg.proto.roleprotocol.invokepowerprotocol.PowerArgumentRequestMessage;
import jadeorg.proto.roleprotocol.invokepowerprotocol.PowerResultMessage;
import java.util.Hashtable;
import java.util.Map;

/**
 * An 'Invoke power' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Role_InvokePowerResponder_New extends Party {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">

    private static final String NAME = "invoke-power-responder-new";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Map<String, Power> powers = new Hashtable<String, Power>();
    
    private Power currentPower;
    
    private State selectPower;
    
    private State sendPowerResult;
    
    private AID playerAID;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Role_InvokePowerResponder_New() {
        super(NAME);   
        registerStatesAndTransitions();
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @Override
    public Protocol getProtocol() {
        return InvokePowerProtocol.getInstance();
    }
    
    // ----- PACKAGE -----
    
    void setPlayerAID(AID playerAID) {
        // ----- Preconditions -----
        assert playerAID != null;
        // -------------------------
        
        this.playerAID = playerAID;
    }
    
    // ----- PRIVATE -----
    
    private Role getMyRole() {
        return (Role)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    boolean containsPower(String powerName) {
        return powers.containsKey(powerName);
    }
    
    void selectPower(String powerName) {
        if (containsPower(powerName)) {
            currentPower = getPower(powerName);
        }
    }
    
    // ----- PROTECTED -----
    
    protected void addPower(Power power) {    
        power.buildFSM();
        
        powers.put(power.getName(), power);
        
        // Register the power-related state.
        registerState(power, power.getName());
        
        // Register the power-related transitions.
        registerTransition(selectPower.getName(), power.getName(), power.hashCode());
        registerDefaultTransition(power.getName(), sendPowerResult.getName());
    }
    
    // ----- PRIVATE -----
    
    private void registerStatesAndTransitions() {
        // ----- States -----
        State receiveInvokePowerRequest = new ReceiveInvokePowerRequest();
        State sendPowerArgumentRequest = new SendPowerArgumentRequest();
        State receivePowerArgument = new ReceivePowerArgument();
        selectPower = new SelectPower();
        sendPowerResult = new SendPowerResult();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------
        
        // Register states.
        registerFirstState(receiveInvokePowerRequest);
        
        registerState(sendPowerArgumentRequest);
        registerState(receivePowerArgument);
        registerState(selectPower);
        registerState(sendPowerResult);
        
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register transitions.
        receiveInvokePowerRequest.registerDefaultTransition(sendPowerArgumentRequest);
        
        sendPowerArgumentRequest.registerTransition(SendPowerArgumentRequest.SUCCESS, receivePowerArgument);
        sendPowerArgumentRequest.registerTransition(SendPowerArgumentRequest.FAILURE, failureEnd);
        
        receivePowerArgument.registerDefaultTransition(selectPower);
        
        sendPowerResult.registerTransition(SendPowerResult.SUCCESS, successEnd);
        sendPowerResult.registerTransition(SendPowerResult.FAILURE, failureEnd);
    }
    
    private Power getPower(String powerName) {
        return powers.get(powerName);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class ReceiveInvokePowerRequest extends SingleReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-invoke-power-request";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveInvokePowerRequest() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyRole().logInfo("Receiving invoke power request.");
        }
        
        @Override
        protected int onSingleReceiver() {
            InvokePowerRequestMessage message = new InvokePowerRequestMessage();
            boolean messageReceived = receive(message, playerAID);
            
            if (messageReceived) {
                selectPower(message.getPower());
                return InnerReceiverState.RECEIVED;
            } else {
                return InnerReceiverState.NOT_RECEIVED;
            }
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Invoke power request received.");
        }
        
        // </editor-fold>`
    }
    
    private class SendPowerArgumentRequest extends SendSuccessOrFailure {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-power-argument-request";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendPowerArgumentRequest() {
            super(NAME, playerAID);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyRole().logInfo("Send power argument request.");
        }

        @Override
        protected int onManager() {
            return SUCCESS;
        }
        
        @Override
        protected void onSuccessSender() {
            PowerArgumentRequestMessage message = new PowerArgumentRequestMessage();
            
            send(message, playerAID);
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Power argument request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceivePowerArgument extends SingleReceiverState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-power-argument";
        
        // </editor-fold>
     
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceivePowerArgument() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Receiving power argument.");
        }
        
        @Override
        protected int onSingleReceiver() {
            PowerArgumentMessage message = new PowerArgumentMessage();
            boolean messageReceived = receive(message, playerAID);
            
            if (messageReceived) {
                currentPower.setArgument(message.getArgument());
                return InnerReceiverState.RECEIVED;
            } else {
                return InnerReceiverState.NOT_RECEIVED;
            }
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Power argument received.");
        }
        
        // </editor-fold>
    }
    
    private class SelectPower extends SimpleState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "select-power";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SelectPower() {
            super(NAME);
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Do nothing.
        }
        
        @Override
        public int onEnd() {
            return currentPower.hashCode();
        }
        
        // </editor-fold>
    }
    
    private class SendPowerResult extends SendSuccessOrFailure {
          
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-power-result";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendPowerResult() {
            super(NAME, playerAID);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Sending power result.");
        }

        @Override
        protected int onManager() {
            return SUCCESS;
        }
        
        @Override
        protected void onSuccessSender() {
            PowerResultMessage message = new PowerResultMessage();
            message.setResult(currentPower.getResult());
            
            send(message, playerAID);
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Power result sent.");
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
            // TODO Implement.
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
            // TODO Implement.
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
