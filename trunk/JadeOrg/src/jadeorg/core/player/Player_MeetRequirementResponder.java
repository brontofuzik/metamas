package jadeorg.core.player;

import jadeorg.core.player.requirement.Requirement;
import jade.core.AID;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.ReceiveSuccessOrFailure;
import jadeorg.proto.SendSuccessOrFailure;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.RequirementArgumentMessage;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.ArgumentRequestMessage;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.MeetRequirementProtocol;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.RequirementResultMessage;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

/**
 * A 'Meet requirement' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Player_MeetRequirementResponder extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "meet-requirement-responder";
    
    // </editor-fold>
     
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID roleAID;
    
    private Map<String, Requirement> requirements = new Hashtable<String, Requirement>();
    
    private Requirement currentRequirement;
    
    private State receiveRequirementArgument;
    
    private State sendRequirementResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Player_MeetRequirementResponder() {
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
    
    // ----- PACKAGE -----
    
    void setRoleAID(AID roleAID) {
        this.roleAID = roleAID;
    }  
    
    // ----- PRIVATE -----
    
    private Player getMyPlayer() {
        return (Player)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    boolean containsRequirement(String requirementName) {
        return requirements.containsKey(requirementName);
    }
    
    void selectRequirement(String requirementName) {
        if (containsRequirement(requirementName)) {
            currentRequirement = getRequirement(requirementName);
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

            send(message, roleAID);
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
            super(NAME, roleAID);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Receiving argument.");
        }
        
        @Override
        protected int onSuccessReceiver() {
            RequirementArgumentMessage message = new RequirementArgumentMessage();
            boolean messageReceived = receive(message, roleAID);

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
            super(NAME, roleAID);
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
            RequirementResultMessage message = new RequirementResultMessage();
            message.setResult(currentRequirement.getResult());

            // Send the message.
            send(message, roleAID);
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Requirement result sent.");
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
            // Do nothing.
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
