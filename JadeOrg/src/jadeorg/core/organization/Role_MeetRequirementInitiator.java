package jadeorg.core.organization;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.proto_old.ActiveState;
import jadeorg.proto_new.Party;
import jadeorg.proto_old.PassiveState;
import jadeorg.proto_new.Protocol;
import jadeorg.proto_old.State;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.MeetRequirementProtocol;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.RequirementRequestMessage;
import jadeorg.util.MessageTemplateBuilder;
import java.io.IOException;
import java.io.Serializable;

/**
 * A 'Meet requirement' protocol initiator.
 * @author Lukáš Kúdela
 * @since 2011-11-13
 * @version %I% %G%
 */
public class Role_MeetRequirementInitiator extends Party {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "meet-requirement-initiator";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Object argument;
    
    private Object result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Role_MeetRequirementInitiator(String requirement) {
        super(NAME);
        initializeFSM();
    }
    
    private void initializeFSM() {
//        // ----- States -----
//        State sendRequirementRequest = new SendRequirementRequest();
//        State receiveArgumentRequest = new ReceiveArgumentRequest();
//        State receiveResult = new ReceiveResult();
//        State end = new End();
//        // ------------------
//        
//        // Register states.
//        registerFirstState(sendRequirementRequest);
//        registerState(receiveArgumentRequest);
//        registerState(receiveResult);
//        registerLastState(end);
//        
//        // Regster transitions.
//        sendRequirementRequest.registerDefaultTransition(receiveArgumentRequest);
//        receiveArgumentRequest.registerDefaultTransition(receiveResult);
//        receiveResult.registerDefaultTransition(end);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    Power getMyPower() {
        return (Power)getParent();
    }
    
    void setArgument(Object argument) {
        this.argument = argument;
    }
    
    Object getResult() {
        return result;
    }
    
    // </editor-fold>   

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public Protocol getProtocol() {
        // TODO Implement.
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
     
    private class SendRequirementRequest extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-requirement-request";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendRequirementRequest() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
//            RequirementRequestMessage requirementRequestMessage = new RequirementRequestMessage();
//            requirementRequestMessage.setReceiverPlayer(getMyPower().getPlayerAID());
//            requirementRequestMessage.setRequirement(getParent().getBehaviourName());
//
//            send(RequirementRequestMessage.class, requirementRequestMessage);
        }
        
        // </editor-fold>
    }
    
    private class ReceiveArgumentRequest extends PassiveState {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private static final String NAME = "receive-argument-request";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveArgumentRequest() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // TODO Use custom message class.
            MessageTemplate messageTemplate = MessageTemplateBuilder.createMessageTemplate(
                    MeetRequirementProtocol.getInstance().getName(),
                    new int[] { ACLMessage.REQUEST },
                    getMyPower().getPlayerAID());           
            ACLMessage aclMessage = myAgent.receive(messageTemplate);
            if (aclMessage != null) {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.setProtocol(MeetRequirementProtocol.getInstance().getName());
                msg.addReceiver(getMyPower().getPlayerAID());
                if (argument != null) {
                    try {
                        msg.setContentObject((Serializable)argument);
                    } catch (IOException ex) {
                        msg.setPerformative(ACLMessage.FAILURE);
                    }
                } else {
                    msg.setPerformative(ACLMessage.FAILURE);
                }
                myAgent.send(msg);
            } else {
                block();
            }
        }
        
        // </editor-fold>
    }
    
    private class ReceiveResult extends PassiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-result";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveResult() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // TODO Use custom message class.
            MessageTemplate messageTemplate = MessageTemplateBuilder.createMessageTemplate(
                    MeetRequirementProtocol.getInstance().getName(),
                    new int[] { ACLMessage.INFORM, ACLMessage.FAILURE },
                    getMyPower().getPlayerAID());
            
            ACLMessage aclMessage = myAgent.receive(messageTemplate);
            if (aclMessage != null) {
                if (aclMessage.getPerformative() == ACLMessage.INFORM) {
                    try {
                        result = aclMessage.getContentObject();
                    } catch (Exception ex) {
                        result = null;
                    }
                    setExitValue(State.Event.FAILURE);
                } else if (aclMessage.getPerformative() == ACLMessage.FAILURE) {
                    result = null;
                    setExitValue(State.Event.FAILURE);
                }
            } else {
                block();
            }
        }
        
        // </editor-fold>
    }
    
    private class End extends ActiveState {

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
