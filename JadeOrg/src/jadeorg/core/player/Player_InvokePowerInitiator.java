package jadeorg.core.player;

import jade.core.AID;
import jadeorg.proto.Initialize;
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
import java.io.Serializable;

/**
 * An 'Invoke power' protocol initiator party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Player_InvokePowerInitiator<TArgument extends Serializable,
    TResult extends Serializable> extends InitiatorParty<Player> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The role; more precisely its AID.
     */
    private AID role;
    
    /**
     * The name of the power.
     */
    private String powerName;
    
    /**
     * The power argument.
     */
    private TArgument powerArgument;
    
    /**
     * The power result.
     */
    private TResult powerResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initialize a new instance of the Player_InvokePowerInitiator class.
     * @param powerName the name of the power
     * @param powerArgument the power argument
     */
    public Player_InvokePowerInitiator(String powerName, TArgument powerArgument) {
        super(InvokePowerProtocol.getInstance());
        // ----- Preconditions -----
        assert powerName != null && !powerName.isEmpty();
        // -------------------------

        this.powerName = powerName;
        this.powerArgument = powerArgument;
        
        buildFSM();
    }
    
    /**
     * Initializes a new instance of the Player_InvokePowerInitiator class.
     * @param powerName the name of the power.
     */
    public Player_InvokePowerInitiator(String powerName) {
        this(powerName, null);
    }
 
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    /**
     * Sets the power argument.
     * @param powerArgument the power argument
     */
    public void setPowerArgument(TArgument powerArgument) {
        this.powerArgument = powerArgument;
    }
    
    /**
     * Gets the power rsult.
     * @return the power result
     */
    public TResult getPowerResult() {
        return powerResult;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the finite state machine, i. e. registers the states and transitions.
     */
    private void buildFSM() {
        // ----- States -----
        State initialize = new MyInitialize();
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
        initialize.registerTransition(MyInitialize.OK, sendInvokePowerRequest);
        initialize.registerTransition(MyInitialize.FAIL, failureEnd);
        
        sendInvokePowerRequest.registerDefaultTransition(receivePowerArgumentRequest);
        
        receivePowerArgumentRequest.registerTransition(ReceivePowerArgumentRequest.SUCCESS, sendPowerArgument);
        receivePowerArgumentRequest.registerTransition(ReceivePowerArgumentRequest.FAILURE, failureEnd);
        
        sendPowerArgument.registerDefaultTransition(receivePowerResult);
        
        receivePowerResult.registerTransition(ReceivePowerResult.SUCCESS, successEnd);       
        receivePowerResult.registerTransition(ReceivePowerResult.FAILURE, failureEnd);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyInitialize extends Initialize {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int initialize() {
            getMyAgent().logInfo(String.format(
                "Initiating the 'Invoke power' (%1$s) protocol.",
                powerName));

            if (getMyAgent().knowledgeBase.canInvokePower(powerName)) {
                // The player can invoke the power.
                role = getMyAgent().knowledgeBase.getActiveRole().getRoleAID();
                return OK;
            } else {
                // The player can not invoke the power.
                String message = String.format(
                    "I cannot invoke the power '%1$s'.",
                    powerName);
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    private class SendInvokePowerRequest
        extends SingleSenderState<InvokePowerRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { role };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending invoke power request.");
        }
        
        @Override
        protected InvokePowerRequestMessage prepareMessage() {
            InvokePowerRequestMessage message = new InvokePowerRequestMessage();
            message.setPower(powerName);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Invoke power requets sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceivePowerArgumentRequest
        extends ReceiveSuccessOrFailure<ArgumentRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceivePowerArgumentRequest() {
            super(new ArgumentRequestMessage.Factory());
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
            getMyAgent().logInfo("Receiving power argument request.");
        }
        
        @Override
        protected void onExit() {
            getMyAgent().logInfo("Power argument request received.");
        }
        
        // </editor-fold>
    }
    
    private class SendPowerArgument
        extends SingleSenderState<PowerArgumentMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { role };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
       
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending power argument.");
        }
        
        @Override
        protected PowerArgumentMessage prepareMessage() {
            PowerArgumentMessage<TArgument> message = new PowerArgumentMessage<TArgument>();
            message.setArgument(powerArgument);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Power argument sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceivePowerResult
        extends ReceiveSuccessOrFailure<PowerResultMessage<TResult>> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceivePowerResult() {
            super(new PowerResultMessage.Factory<TResult>());
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
            getMyAgent().logInfo("Receiving power result.");
        }
        
        @Override
        protected void handleSuccessMessage(PowerResultMessage<TResult> message) {
            powerResult = message.getResult();
            getMyAgent().knowledgeBase.getActiveRole()
                .savePowerResult(powerName, message.getResult());
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Power result received.");
        }
        
        // </editor-fold>
    }
    
    private class SuccessEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo("The 'Invoke power' initiator party succeeded.");
        }
        
        // </editor-fold>
    }
    
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo("The 'Invoke power' initiator party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
