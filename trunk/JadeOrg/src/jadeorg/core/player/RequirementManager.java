package jadeorg.core.player;

import jade.core.AID;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.proto.ActiveState;
import jadeorg.proto.PassiveState;
import jadeorg.proto.State;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

/**
 * A requirement manager.
 * @author Lukáš Kúdela
 * @since 2011-11-11
 * @version %I% %G%
 */
public class RequirementManager extends FSMBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Map<String, Requirement> requirements = new Hashtable<String, Requirement>();
    
    private Requirement currentRequirement;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public RequirementManager() {
        registerStatesAndTransitions();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public boolean containsRequirement(String requirementName) {
        return requirements.containsKey(requirementName);
    }
    
    public void addRequirement(Requirement requirement) {
        String requirementName = requirement.toBehaviour().getBehaviourName();
        requirements.put(requirementName, requirement);
        
        // Register the state.
        registerState(requirement.toBehaviour(), requirementName);
        
        // Register the transitions.
        registerTransition("send-request-param", requirementName, requirement.hashCode());
        registerDefaultTransition(requirementName, "send-result");
    }
    
    protected void invokeRequirement(String requirementName, AID roleAID) {
        if (containsRequirement(requirementName)) {
            currentRequirement = getRequirement(requirementName);
            currentRequirement.setRoleAID(roleAID);
            reset();
        }
    }
    
    // ---------- PRIVATE ----------
    
    private static MessageTemplate createMessageTemplate(String protocol, int[] performatives, AID senderAID) {
        // Match protocol.
        MessageTemplate messageTemplate = MessageTemplate.MatchProtocol(protocol);
        
        // Match peformatives.
        MessageTemplate performativeTemplate = null;
        for (int performative : performatives) {
            performativeTemplate = MessageTemplate.or(
                performativeTemplate,
                MessageTemplate.MatchPerformative(performative));
        }
        messageTemplate = messageTemplate.and(
            messageTemplate,
            messageTemplate);
        
        // Match sender AID.
        messageTemplate = MessageTemplate.and(
            messageTemplate,
            MessageTemplate.MatchSender(senderAID));
        return messageTemplate;
    }
    
    private void registerStatesAndTransitions() {
        // ----- States -----
        State sendRequestParam = new SendRequestParam();
        State receiveParam = new ReceiveParam();
        State sendResult = new SendResult();
        State end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(sendRequestParam.toBehaviour(), sendRequestParam.getName());
        registerState(receiveParam.toBehaviour(), receiveParam.getName());
        registerLastState(sendResult.toBehaviour(), sendResult.getName());
        registerLastState(end.toBehaviour(), end.getName());
        
        // Register the transitions.
        registerDefaultTransition(sendRequestParam.getName(), receiveParam.getName());
        registerTransition(receiveParam.getName(), end.getName(), 1);
        registerDefaultTransition(sendResult.getName(), end.getName());
    }
    
    private Requirement getRequirement(String requirementName) {
        return requirements.get(requirementName);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    class SendRequestParam extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-request-param";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendRequestParam() {
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
            MessageTemplate messageTemplate = createMessageTemplate(
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
    
    class SendResult extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-result";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendResult() {
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
