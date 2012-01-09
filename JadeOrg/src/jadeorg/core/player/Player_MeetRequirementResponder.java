package jadeorg.core.player;

import jade.lang.acl.ACLMessage;
import jadeorg.core.player.requirement.Requirement;
import jade.core.AID;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.SendSuccessOrFailure;
import jadeorg.proto.SingleReceiverState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.RequirementArgumentMessage;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.ArgumentRequestMessage;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.MeetRequirementProtocol;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.MeetRequirementRequestMessage;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.RequirementResultMessage;
import java.util.Hashtable;
import java.util.Map;

/**
 * A 'Meet requirement' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Player_MeetRequirementResponder extends Party {
     
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private ACLMessage aclMessage;
    
    private AID roleAID;
    
    private Map<String, Requirement> requirements = new Hashtable<String, Requirement>();
    
    private Requirement currentRequirement;
    
    private State selectRequirement;
    
    private State sendRequirementResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Player_MeetRequirementResponder() {
        buildFSM();
    }
    
    private void buildFSM() {        
         // ----- States -----
        State receiveMeetRequirementRequest = new ReceiveMeetRequirementRequest();
        State sendRequirementArgumentRequest = new SendRequirementArgumentRequest();
        State receiveRequirementArgument = new ReceiveRequirementArgument();
        selectRequirement = new SelectRequirement();
        sendRequirementResult = new SendRequirementResult();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------
        
        // Register states.
        registerFirstState(receiveMeetRequirementRequest);
        
        registerState(sendRequirementArgumentRequest);
        registerState(receiveRequirementArgument);
        registerState(selectRequirement);
        registerState(sendRequirementResult);
        
        registerLastState(successEnd);     
        registerLastState(failureEnd);
        
        // Register transitions.
        receiveMeetRequirementRequest.registerDefaultTransition(sendRequirementArgumentRequest);
        
        sendRequirementArgumentRequest.registerTransition(SendRequirementArgumentRequest.SUCCESS, receiveRequirementArgument);
        sendRequirementArgumentRequest.registerTransition(SendRequirementArgumentRequest.FAILURE, failureEnd);
        
        receiveRequirementArgument.registerDefaultTransition(selectRequirement);
        
        sendRequirementResult.registerTransition(SendRequirementResult.SUCCESS, successEnd);
        sendRequirementResult.registerTransition(SendRequirementResult.FAILURE, failureEnd);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @Override
    public Protocol getProtocol() {
        return MeetRequirementProtocol.getInstance();
    }
    
    // ----- PACKAGE -----
    
    void setMessage(ACLMessage aclMessage) {
        // ----- Preconditions -----
        assert aclMessage != null;
        // -------------------------
        
        this.aclMessage = aclMessage;
    }
    
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
        }
    }
    
    // ----- PROTECTED -----
    
    protected void addRequirement(Requirement requirement) {
        requirements.put(requirement.getName(), requirement);
        
        // Register the requirement-relates states.
        registerState(requirement);
        
        // Register the requirement-related transitions.
        selectRequirement.registerTransition(requirement.hashCode(), requirement);
        requirement.registerDefaultTransition(sendRequirementResult);
    }
    
    // ---------- PRIVATE ----------
    
    private Requirement getRequirement(String requirementName) {
        return requirements.get(requirementName);
    }

    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
     private class ReceiveMeetRequirementRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            MeetRequirementRequestMessage message = new MeetRequirementRequestMessage();
            message.parseContent(aclMessage.getContent());
            
            setProtocolId(aclMessage.getConversationId());
            roleAID = aclMessage.getSender();
            selectRequirement(message.getRequirement());
        }
        
        // </editor-fold>
    }
    
    private class SendRequirementArgumentRequest extends SendSuccessOrFailure {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendRequirementArgumentRequest() {
            super(roleAID);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Send requirement argument request.");
        }

        @Override
        protected int onManager() {
            return SUCCESS;
        }
        
        @Override
        protected void onSuccessSender() {
            ArgumentRequestMessage message = new ArgumentRequestMessage();
            
            send(message, roleAID);
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Requirement argument request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveRequirementArgument extends SingleReceiverState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Receiving requirement argument.");
        }
        
        @Override
        protected int onSingleReceiver() {
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
            getMyPlayer().logInfo("Requirement argument received.");
        }
        
        // </editor-fold>
    }
    
    private class SelectRequirement extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo("Selecting requirement.");
        }
        
        @Override
        public int onEnd() {
            return currentRequirement.hashCode();
        }
        
        // </editor-fold>
    }
    
    private class SendRequirementResult extends SendSuccessOrFailure {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendRequirementResult() {
            super(roleAID);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Sending requirement result.");
        }

        @Override
        protected int onManager() {
            return SUCCESS;
        }
        
        @Override
        protected void onSuccessSender() {
            RequirementResultMessage message = new RequirementResultMessage();
            message.setResult(currentRequirement.getResult());
            
            send(message, roleAID);
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Requirement result sent.");
        }
        
        // </editor-fold>
    }
    
    private class SuccessEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo("The 'Meet requirement' responder party succeeded.");
        }
        
        // </editor-fold>
    }
    
    private class FailureEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo("The 'Meet requirement' responder party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
