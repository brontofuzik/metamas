package jadeorg.core.player;

import jade.core.AID;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.proto.ActiveState;
import jadeorg.proto.PassiveState;
import jadeorg.proto.State;
import jadeorg.utils.MessageTemplateBuilder;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

/**
 * A 'Meet requirement responder' (FSM) behaviour.
 * @author Lukáš Kúdela
 * @since 2011-11-11
 * @version %I% %G%
 */
public class MeetRequirementResponder extends FSMBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Map<String, Requirement> requirements = new Hashtable<String, Requirement>();
    
    private Requirement currentRequirement;
    
    private State sendArgumentRequest;
    
    private State sendRequirementResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public MeetRequirementResponder() {
        initializeFSM();
    }
    
    private void initializeFSM() {
        // ----- States -----
        sendArgumentRequest = new SendArgumentRequest();
        State receiveParam = new ReceiveParam();
        sendRequirementResult = new SendRequirementResult();
        State end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(sendArgumentRequest, sendArgumentRequest.getName());
        registerState(receiveParam, receiveParam.getName());
        registerLastState(sendRequirementResult, sendRequirementResult.getName());
        registerLastState(end, end.getName());
        
        // Register the transitions.
        registerDefaultTransition(sendArgumentRequest.getName(), receiveParam.getName());
        registerTransition(receiveParam.getName(), end.getName(), 1);
        registerDefaultTransition(sendRequirementResult.getName(), end.getName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public boolean containsRequirement(String requirementName) {
        return requirements.containsKey(requirementName);
    }
    
    public void addRequirement(Requirement requirement) {
        requirements.put(requirement.getName(), requirement);
        
        // Register the state.
        registerState(requirement, requirement.getName());
        
        // Register the transitions.
        registerTransition(sendArgumentRequest.getName(), requirement.getName(), requirement.hashCode());
        registerDefaultTransition(requirement.getName(), sendRequirementResult.getName());
    }
    
    protected void invokeRequirement(String requirementName, AID roleAID) {
        if (containsRequirement(requirementName)) {
            currentRequirement = getRequirement(requirementName);
            currentRequirement.setRoleAID(roleAID);
            reset();
        }
    }
    
    // ---------- PRIVATE ----------
    

    
    private Requirement getRequirement(String requirementName) {
        return requirements.get(requirementName);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    class SendArgumentRequest extends ActiveState {

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
        public void action() {
            // TODO Rework.
            ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
            aclMessage.setProtocol("requirement-protcol");
            aclMessage.setContent("param");
            aclMessage.addReceiver(currentRequirement.getRoleAID());
            
            // Send the ACL message.
            myAgent.send(aclMessage);
        }
        
        // </editor-fold>
    }
    
    class ReceiveParam extends PassiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-param";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveParam() {
            super(NAME);
        }
        
        // </editor-fold>
        
        @Override
        public void action() {
            MessageTemplate messageTemplate = MessageTemplateBuilder.createMessageTemplate(
                    "requirement-protocol",
                    new int[] { ACLMessage.INFORM, ACLMessage.FAILURE },
                    currentRequirement.getRoleAID());

            // Receive the ACL message.
            ACLMessage aclMessage = myAgent.receive(messageTemplate);
            if (aclMessage != null) {
                switch (aclMessage.getPerformative()) {
                    case ACLMessage.INFORM:
                        try {
                            currentRequirement.setArgument(aclMessage.getContentObject());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        // TODO Rework.
                        //setExitValue(currentRequirement.hashCode());
                        break;
                    case ACLMessage.FAILURE:
                        // TODO Rework.
                        //setExitValue(currentRequirement.hashCode());
                        break;
                }
            } else {
                block();
            }
        }
    }
    
    class SendRequirementResult extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-requirement-result";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendRequirementResult() {
            super(NAME);
        }
        
        // </editor-fold>
        
        @Override
        public void action() {
            // TODO Rework.    
            ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
            aclMessage.setProtocol("requirement-protocol");
            aclMessage.addReceiver(currentRequirement.getRoleAID());
            try {
                aclMessage.setContentObject((Serializable)currentRequirement.getResult());
            } catch (Exception ex) {
                aclMessage.setPerformative(ACLMessage.FAILURE);
                aclMessage.setContent(ex.toString());
            }
            
            // Send the ACL message.
            myAgent.send(aclMessage);

            currentRequirement.reset();
            getParent().reset();
        }
    }
    
    class End extends ActiveState {

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
