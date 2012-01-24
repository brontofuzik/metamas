package jadeorg.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.core.organization.power.Power;
import jadeorg.lang.Message;
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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * An 'Invoke power' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Role_InvokePowerResponder extends ResponderParty {
 
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID playerAID;
    
    private String powerName;
    
    private State receivePowerArgument;
    
    private Power power;
    
    private State sendPowerResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Role_InvokePowerResponder(ACLMessage aclMessage) {
        super(InvokePowerProtocol.getInstance(), aclMessage);
        
        playerAID = getACLMessage().getSender();
        
        buildFSM();
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    private Role getMyRole() {
        return (Role)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
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
    
    private Power createPower(String powerName) {
        //System.out.println("----- CREATING POWER: " + powerName + " -----");
        Class powerClass = getMyRole().powers.get(powerName);
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
            getMyRole().logInfo(String.format(
                "Responding to the 'Invoke power' protocol (id = %1$s).",
                getACLMessage().getConversationId()));
        
            if (playerAID.equals(getMyRole().playerAID)) {
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
    
    private class SendPowerArgumentRequest extends SendSuccessOrFailure {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { playerAID };
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
        protected Message prepareMessage() {
            ArgumentRequestMessage message = new ArgumentRequestMessage();
            return message;
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Power argument request sent.");
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
            return new AID[] { playerAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Receiving power argument.");
        }
        
        @Override
        protected void handleMessage(PowerArgumentMessage message) {
            power.setArgument(message.getArgument());
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Power argument received.");
        }
        
        // </editor-fold>
    }
    
    private class SendPowerResult extends SendSuccessOrFailure {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { playerAID };
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
        protected Message prepareMessage() {
            PowerResultMessage message = new PowerResultMessage();
            message.setResult(power.getResult());
            return message;
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
