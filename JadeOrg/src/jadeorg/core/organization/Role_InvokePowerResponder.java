package jadeorg.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.core.organization.power.Power;
import jadeorg.proto.Initialize;
import jadeorg.proto.ResponderParty;
import jadeorg.proto.SendSuccessOrFailure;
import jadeorg.proto.SingleReceiverState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerProtocol;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerRequestMessage;
import jadeorg.proto.roleprotocol.invokepowerprotocol.PowerArgumentMessage;
import jadeorg.proto.roleprotocol.invokepowerprotocol.ArgumentRequestMessage;
import jadeorg.proto.roleprotocol.invokepowerprotocol.PowerResultMessage;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * An 'Invoke power' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Role_InvokePowerResponder<TArgument extends Serializable,
    TResult extends Serializable> extends ResponderParty<Role> {
 
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    
    /**
     * The player; more precisely its AID.
     */
    private AID player;
    
    /**
     * The name of the power.
     */
    private String powerName;
    
    /**
     * The 'Receive power argument' state.
     */
    private State receivePowerArgument;
    
    /**
     * The power state.
     */
    private Power power;
    
    /**
     * The 'Send power result' state.
     */
    private State sendPowerResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Role_InvokePowerResponder class.
     * @param aclMessage the received ACL message
     */
    public Role_InvokePowerResponder(ACLMessage aclMessage) {
        super(InvokePowerProtocol.getInstance(), aclMessage);
        
        player = getACLMessage().getSender();
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the finite state machine.
     */
    private void buildFSM() {
        // ----- States -----
        State initialize = new MyInitialize();
        State receiveInvokePowerRequest = new ReceiveInvokePowerRequest();
        State sendPowerArgumentRequest = new SendPowerArgumentRequest();
        receivePowerArgument = new ReceivePowerArgument();
        sendPowerResult = new SendPowerResult();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------
        
        // Register states.
        registerFirstState(initialize);
        
        registerState(receiveInvokePowerRequest);      
        registerState(sendPowerArgumentRequest);
        registerState(receivePowerArgument);
        registerState(sendPowerResult);
        
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register transitions.
        initialize.registerTransition(MyInitialize.OK, receiveInvokePowerRequest);
        initialize.registerTransition(MyInitialize.FAIL, failureEnd);
        
        receiveInvokePowerRequest.registerDefaultTransition(sendPowerArgumentRequest);
        
        sendPowerArgumentRequest.registerTransition(SendPowerArgumentRequest.SUCCESS, receivePowerArgument);
        sendPowerArgumentRequest.registerTransition(SendPowerArgumentRequest.FAILURE, failureEnd);
        
        sendPowerResult.registerTransition(SendPowerResult.SUCCESS, successEnd);
        sendPowerResult.registerTransition(SendPowerResult.FAILURE, failureEnd);
    }
    
    /**
     * Selects a power specified by its name.
     * @param powerName the name of the power to select.
     */
    private void selectPower(String powerName) {
        //System.out.println("----- ADDING POWER: " + powerName + " -----");
        power = createPower(powerName);
        
        // Register the power-related states.
        registerState(power);
        
        // Register the power-related transitions.
        receivePowerArgument.registerDefaultTransition(power);
        power.registerDefaultTransition(sendPowerResult);
        //System.out.println("----- POWER ADDED -----");
    }
    
    /**
     * Create a power specified by its name.
     * @param powerName the name of the power
     * @return the power
     */
    private Power createPower(String powerName) {
        //System.out.println("----- CREATING POWER: " + powerName + " -----");
        Class powerClass = getMyAgent().powers.get(powerName);
        //System.out.println("----- POWER CLASS: " + powerClass + " -----");
        
        // Get the power constructor.
        Constructor powerConstructor = null;
        try {
            powerConstructor = powerClass.getConstructor();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
                
        // Instantiate the power.
        Power power = null;
        try {
            power = (Power)powerConstructor.newInstance();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }        
        //System.out.println("----- POWER CREATED -----");
        
        return power;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyInitialize extends Initialize {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int initialize() {
            getMyAgent().logInfo(String.format(
                "Responding to the 'Invoke power' protocol (id = %1$s).",
                getACLMessage().getConversationId()));
        
            if (player.equals(getMyAgent().playerAID)) {
                // The sender player is enacting this role.
                return OK;
            } else {
                // The sender player is not enacting this role.
                // TODO
                return FAIL;
            }
        }      
    
        // </editor-fold>
    }
    
    private class ReceiveInvokePowerRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            InvokePowerRequestMessage message = new InvokePowerRequestMessage();
            message.parseACLMessage(getACLMessage());
            
            powerName = message.getPower();                       
            selectPower(powerName);
        }
        
        // </editor-fold>
    }
    
    private class SendPowerArgumentRequest
        extends SendSuccessOrFailure<ArgumentRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Send power argument request.");
        }

        @Override
        protected int onManager() {
            return SUCCESS;
        }
        
        @Override
        protected ArgumentRequestMessage prepareMessage() {
            ArgumentRequestMessage message = new ArgumentRequestMessage();
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Power argument request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceivePowerArgument
        extends SingleReceiverState<PowerArgumentMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceivePowerArgument() {
            super(new PowerArgumentMessage.Factory());
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
            getMyAgent().logInfo("Receiving power argument.");
        }
        
        @Override
        protected void handleMessage(PowerArgumentMessage message) {
            power.setArgument(message.getArgument());
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Power argument received.");
        }
        
        // </editor-fold>
    }
    
    private class SendPowerResult
        extends SendSuccessOrFailure<PowerResultMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending power result.");
        }

        @Override
        protected int onManager() {
            return SUCCESS;
        }
        
        @Override
        protected PowerResultMessage prepareMessage() {
            PowerResultMessage message = new PowerResultMessage();
            message.setResult(power.getResult());
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Power result sent.");
        }
        
        // </editor-fold>
    }
    
    private class SuccessEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo("The 'Invoke power' responder party succeeded.");
        }
        
        // </editor-fold>
    }
    
    private class FailureEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo("The 'Invoke power' responder party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
