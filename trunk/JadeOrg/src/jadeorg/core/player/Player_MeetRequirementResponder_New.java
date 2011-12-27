package jadeorg.core.player;

import jade.core.AID;
import jadeorg.proto.OuterReceiverState;
import jadeorg.proto.OuterSenderState;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.ReceiveSuccessOrFailure;
import jadeorg.proto.SendSuccessOrFailure;
import jadeorg.proto.SimpleState;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.ArgumentInformMessage;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.ArgumentRequestMessage;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.MeetRequirementProtocol;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.ResultInformMessage;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

/**
 * A 'Meet requirement' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Player_MeetRequirementResponder_New extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "meet-requirement-responder-new";
    
    // </editor-fold>
     
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Map<String, Requirement> requirements = new Hashtable<String, Requirement>();
    
    private Requirement currentRequirement;
    
    private State receiveRequirementArgument;
    
    private State sendRequirementResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Player_MeetRequirementResponder_New() {
        super(NAME);
        registerStatesAndTransitions();
    }
    
    private void registerStatesAndTransitions() {        
        // ----- States -----
        State sendArgumentRequest = new SendArgumentRequest();       
        receiveRequirementArgument = new ReceiveRequirementArgument();
        sendRequirementResult = new SendRequirementResult();
        State end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(sendArgumentRequest);
        registerState(receiveRequirementArgument);
        registerState(sendRequirementResult);
        registerLastState(end);
        
        // Register the transitions.     
        sendArgumentRequest.registerDefaultTransition(receiveRequirementArgument);
        
        receiveRequirementArgument.registerTransition(1, end);
        
        sendRequirementResult.registerDefaultTransition(end);      
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @Override
    public Protocol getProtocol() {
        return MeetRequirementProtocol.getInstance();
    }
    
    // ----- PRIVATE -----
    
    private Player getMyPlayer() {
        return (Player)myAgent;
    }
    
    private AID getRoleAID() {
        return currentRequirement != null ? currentRequirement.getRoleAID() : null;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    boolean containsRequirement(String requirementName) {
        return requirements.containsKey(requirementName);
    }
    
    void selectRequirement(String requirementName, AID roleAID) {
        if (containsRequirement(requirementName)) {
            currentRequirement = getRequirement(requirementName);
            currentRequirement.setRoleAID(roleAID);
            reset();
        }
    }
    
    // ----- PROTECTED -----
    
    protected void addRequirement(Requirement requirement) {
        requirements.put(requirement.getName(), requirement);
        
        // Register the state.
        registerState(requirement);
        
        // Register the transitions.
        receiveRequirementArgument.registerTransition(requirement.hashCode(), requirement);
        requirement.registerDefaultTransition(sendRequirementResult);
    }
    
    // ---------- PRIVATE ----------
    
    private Requirement getRequirement(String requirementName) {
        return requirements.get(requirementName);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class SendArgumentRequest extends SingleSenderState {
    
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-argument-request";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendArgumentRequest() {
            super(NAME);
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Sending argument request.");
        }
        
        @Override
        protected void onSingleSender() {
            ArgumentRequestMessage message = new ArgumentRequestMessage();

            send(message, getRoleAID());
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Argument request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveRequirementArgument extends ReceiveSuccessOrFailure {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-requirement-argument";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
       
        ReceiveRequirementArgument() {
            // TODO
            super(NAME, getRoleAID());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Receiving argument.");
        }
        
        @Override
        protected int onSuccessReceiver() {
            ArgumentInformMessage message = new ArgumentInformMessage();
            boolean messageReceived = receive(message, getRoleAID());

            if (messageReceived) {
                currentRequirement.setArgument(message.getArgument());
                return InnerReceiverState.RECEIVED;
            } else {
                return InnerReceiverState.NOT_RECEIVED;
            }
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Argument received.");
        }
        
        // </editor-fold>
    }
    
    private class SendRequirementResult extends SendSuccessOrFailure {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-requirement-result";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendRequirementResult() {
            super(NAME, getRoleAID());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Sending requirement result.");
        }
        
        @Override
        protected int onManager() {
            return currentRequirement.getResult() instanceof Serializable ?
                SUCCESS :
                FAILURE;
        }
        
        @Override
        protected void onSuccessSender() {
            // Create the 'Result inform' message.
            ResultInformMessage message = new ResultInformMessage();
            message.setResult(currentRequirement.getResult());

            // Send the message.
            send(message, getRoleAID());

            currentRequirement.reset();
            getParent().reset();  
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Requirement result sent.");
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
            // Do nothing.
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}