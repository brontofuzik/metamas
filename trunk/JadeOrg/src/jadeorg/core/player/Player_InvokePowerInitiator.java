package jadeorg.core.player;

import jade.core.AID;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.ReceiveSuccessOrFailure;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerProtocol;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerRequestMessage;
import jadeorg.proto.roleprotocol.invokepowerprotocol.PowerArgumentMessage;
import jadeorg.proto.roleprotocol.invokepowerprotocol.ArgumentRequestMessage;
import jadeorg.proto.roleprotocol.invokepowerprotocol.PowerResultMessage;

/**
 * An 'Invoke power' protocol initiator party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Player_InvokePowerInitiator extends InitiatorParty {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID roleAID;
    
    private String powerName;
    
    private Object powerArgument;
    
    private Object powerResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Player_InvokePowerInitiator(String powerName, Object powerArgument) {
        super(InvokePowerProtocol.getInstance());
        // ----- Preconditions -----
        assert powerName != null && !powerName.isEmpty();
        // -------------------------

        this.powerName = powerName;
        this.powerArgument = powerArgument;
        
        buildFSM();
    }
    
    public Player_InvokePowerInitiator(String powerName) {
        this(powerName, null);
    }
 
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public void setPowerArgument(Object powerArgument) {
        this.powerArgument = powerArgument;
    }
    
    public Object getPowerResult() {
        return powerResult;
    }
    
    // ----- PRIVATE -----
    
    private Player getMyPlayer() {
        return (Player)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- States -----
        State initialize = new Initialize();
        State sendInvokePowerRequest = new SendInvokePowerRequest();
        State receivePowerArgumentRequest = new ReceivePowerArgumentRequest();
        State sendPowerArgument = new SendPowerArgument();
        State receivePowerResult = new ReceivePowerResult();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        
        registerState(sendInvokePowerRequest);
        registerState(receivePowerArgumentRequest);
        registerState(sendPowerArgument);
        registerState(receivePowerResult);
        
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register the transitions.
        initialize.registerTransition(Initialize.OK, sendInvokePowerRequest);
        initialize.registerTransition(Initialize.FAIL, failureEnd);
        
        sendInvokePowerRequest.registerDefaultTransition(receivePowerArgumentRequest);
        
        receivePowerArgumentRequest.registerTransition(ReceivePowerArgumentRequest.SUCCESS, sendPowerArgument);
        receivePowerArgumentRequest.registerTransition(ReceivePowerArgumentRequest.FAILURE, failureEnd);
        
        sendPowerArgument.registerDefaultTransition(receivePowerResult);
        
        receivePowerResult.registerTransition(ReceivePowerResult.SUCCESS, successEnd);       
        receivePowerResult.registerTransition(ReceivePowerResult.FAILURE, failureEnd);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class Initialize extends OneShotBehaviourState {

        public static final int OK = 0;
        public static final int FAIL = 1;
        
        private int exitValue;
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo(String.format(
                "Initiating the 'Invoke power' (%1$s) protocol.",
                powerName));

            if (getMyPlayer().knowledgeBase.canInvokePower(powerName)) {
                // The player can invoke the power.
                roleAID = getMyPlayer().knowledgeBase.getActiveRole().getRoleAID();
                exitValue = OK;
            } else {
                // The player can not invoke the power.
                String message = String.format(
                    "I cannot invoke the power '%1$s'.",
                    powerName);
                exitValue = FAIL;
            }
        }
        
        @Override
        public int onEnd() {
            return exitValue;
        }
        
        // </editor-fold>
    }
    
    private class SendInvokePowerRequest extends SingleSenderState {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getReceiverAID() {
            return roleAID;
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
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getSenderAID() {
            return roleAID;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Receiving power argument request.");
        }
        
        @Override
        protected int onSuccessReceiver() {
            ArgumentRequestMessage message = new ArgumentRequestMessage();
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
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getReceiverAID() {
            return roleAID;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
       
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Sending power argument.");
        }
        
        @Override
        protected void onSingleSender() {
            PowerArgumentMessage message = new PowerArgumentMessage();
            message.setArgument(powerArgument);
            
            send(message, roleAID);
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Power argument sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceivePowerResult extends ReceiveSuccessOrFailure {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getSenderAID() {
            return roleAID;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Receiving power result.");
        }
        
        @Override
        protected int onSuccessReceiver() {
            PowerResultMessage message = new PowerResultMessage();
            boolean messageReceived = receive(message, roleAID);
            
            if (messageReceived) {
                powerResult = message.getResult();
                getMyPlayer().knowledgeBase.getActiveRole()
                    .savePowerResult(powerName, message.getResult());
                return InnerReceiverState.RECEIVED;
            } else {
                return InnerReceiverState.NOT_RECEIVED;
            }
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Power result received.");
        }
        
        // </editor-fold>
    }
    
    private class SuccessEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo("The 'Invoke power' initiator party succeeded.");
        }
        
        // </editor-fold>
    }
    
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo("The 'Invoke power' initiator party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
