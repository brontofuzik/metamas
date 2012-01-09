package jadeorg.core.player;

import jade.lang.acl.ACLMessage;
import jadeorg.core.player.requirement.Requirement;
import jade.core.AID;
import jadeorg.proto.Protocol;
import jadeorg.proto.ResponderParty;
import jadeorg.proto.SendSuccessOrFailure;
import jadeorg.proto.SingleReceiverState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.RequirementArgumentMessage;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.ArgumentRequestMessage;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.MeetRequirementProtocol;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.MeetRequirementRequestMessage;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.RequirementResultMessage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * A 'Meet requirement' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Player_MeetRequirementResponder extends ResponderParty {
     
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private ACLMessage aclMessage;
    
    private AID roleAID;
    
    private String requirementName;
    
    private State receiveRequirementArgument;
    
    private Requirement requirement;
    
    private State sendRequirementResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Player_MeetRequirementResponder(ACLMessage aclMessage) {
        super(aclMessage);
        
        this.aclMessage = aclMessage;
        this.roleAID = aclMessage.getSender();
        
        buildFSM();
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
    
    private void buildFSM() {        
         // ----- States -----
        State receiveMeetRequirementRequest = new ReceiveMeetRequirementRequest();
        State sendRequirementArgumentRequest = new SendRequirementArgumentRequest();
        receiveRequirementArgument = new ReceiveRequirementArgument();
        sendRequirementResult = new SendRequirementResult();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------
        
        // Register states.
        registerFirstState(receiveMeetRequirementRequest);
        
        registerState(sendRequirementArgumentRequest);
        registerState(receiveRequirementArgument);
        registerState(sendRequirementResult);
        
        registerLastState(successEnd);     
        registerLastState(failureEnd);
        
        // Register transitions.
        receiveMeetRequirementRequest.registerDefaultTransition(sendRequirementArgumentRequest);
        
        sendRequirementArgumentRequest.registerTransition(SendRequirementArgumentRequest.SUCCESS, receiveRequirementArgument);
        sendRequirementArgumentRequest.registerTransition(SendRequirementArgumentRequest.FAILURE, failureEnd);
        
        sendRequirementResult.registerTransition(SendRequirementResult.SUCCESS, successEnd);
        sendRequirementResult.registerTransition(SendRequirementResult.FAILURE, failureEnd);
    }
    
    private void selectRequirement(String requirementName) {
        //System.out.println("----- ADDING REQUIREMENT: " + requirementName + " -----");
        requirement = createRequirement(requirementName);
        
        // Register the requirement-related states.
        registerState(requirement);
        
        // Register the requirement-related transitions.
        receiveRequirementArgument.registerDefaultTransition(requirement);
        requirement.registerDefaultTransition(sendRequirementResult);
        //System.out.println("----- REQUIREMENT ADDED -----");
    }
    
    private Requirement createRequirement(String requirementName) {
        //System.out.println("----- CREATING REQUIREMENT: " + requirementName + " -----");
        Class requirementClass = getMyPlayer().requirements.get(requirementName);
        
        // Get the requirement constructor.
        Constructor requirementConstructor = null;
        try {
            requirementConstructor = requirementClass.getConstructor();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        
        // Instantiate the requirement.
        Requirement requirement = null;
        try {
            requirement = (Requirement)requirementConstructor.newInstance();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }        
        //System.out.println("----- REQUIREMENT CREATED -----");
        
        return requirement;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
     private class ReceiveMeetRequirementRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            MeetRequirementRequestMessage message = new MeetRequirementRequestMessage();
            message.parseACLMessage(aclMessage);
            
            requirementName = message.getRequirement();         
            selectRequirement(requirementName);
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
                requirement.setArgument(message.getArgument());
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
            message.setResult(requirement.getResult());
            
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
