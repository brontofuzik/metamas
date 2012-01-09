package jadeorg.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.core.organization.power.Power;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.SendSuccessOrFailure;
import jadeorg.proto.SingleReceiverState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerProtocol;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerRequestMessage;
import jadeorg.proto.roleprotocol.invokepowerprotocol.PowerArgumentMessage;
import jadeorg.proto.roleprotocol.invokepowerprotocol.ArgumentRequestMessage;
import jadeorg.proto.roleprotocol.invokepowerprotocol.PowerResultMessage;
import java.util.Hashtable;
import java.util.Map;

/**
 * An 'Invoke power' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Role_InvokePowerResponder extends Party {
 
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private ACLMessage aclMessage;
    
    private AID playerAID;
    
    private Map<String, Power> powers = new Hashtable<String, Power>();
    
    private Power currentPower;
    
    private State selectPower;
    
    private State sendPowerResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Role_InvokePowerResponder() {     
        buildFSM();
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @Override
    public Protocol getProtocol() {
        return InvokePowerProtocol.getInstance();
    }
    
    // ----- PACKAGE -----
    
    void setMessage(ACLMessage aclMessage) {
        // ----- Preconditions -----
        assert aclMessage != null;
        // -------------------------
          
        this.aclMessage = aclMessage;
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
        powers.put(power.getName(), power);
        
        // Register the power-related state.
        registerState(power);
        
        // Register the power-related transitions.
        selectPower.registerTransition(power.hashCode(), power);
        power.registerDefaultTransition(sendPowerResult);
    }
    
    // ----- PRIVATE -----
    
    private void buildFSM() {
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
    
    private class ReceiveInvokePowerRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            InvokePowerRequestMessage message = new InvokePowerRequestMessage();
            message.parseContent(aclMessage.getContent());
            
            setProtocolId(aclMessage.getConversationId());
            playerAID = aclMessage.getSender();
            selectPower(message.getPower());
        }
        
        // </editor-fold>
    }
    
    private class SendPowerArgumentRequest extends SendSuccessOrFailure {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendPowerArgumentRequest() {
            super(playerAID);
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
            ArgumentRequestMessage message = new ArgumentRequestMessage();
            
            send(message, playerAID);
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Power argument request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceivePowerArgument extends SingleReceiverState {
        
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
    
    private class SelectPower extends OneShotBehaviourState {

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
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendPowerResult() {
            super(playerAID);
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
    
    private class SuccessEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyRole().logInfo("The 'Invoke power' responder party succeeded.");
        }
        
        // </editor-fold>
    }
    
    private class FailureEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyRole().logInfo("The 'Invoke power' responder party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
