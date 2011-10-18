package jadeorg.core;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * A role agent.
 * @author Lukáš Kúdela (2011-10-16)
 */
public class Role extends Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private final String ROLE_PROTOCOL = "role-protocol";
    
    private final String ACTIVATE_ACTION = "activate";
    
    private final String DEACTIVATE_ACTION = "deactivate";
    
    private final String INVOKE_POWER_ACTION = "invoke-power";
    
    private final String INVOKE_RESPONSIBILITY_ACTION = "invoke-responsibility";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private MessageTemplate roleMessageTemplate = MessageTemplate.and(
            MessageTemplate.MatchProtocol(ROLE_PROTOCOL),
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
    
    private PlayerInfo playerInfo;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void setup() {
        initialize();
        registerWithDF();
    }
    
    // ---------- PRIVATE ----------
    
    private void initialize() {
        initializeState();
        initialieBehaviour();
    }
    
    private void initializeState() {       
    }

    private void initialieBehaviour() {
        addBehaviour(new RoleManagerBehaviour());
    }
    
    private void registerWithDF() {
        try {
            DFAgentDescription agentDescription = new DFAgentDescription();
            agentDescription.setName(getAID());
            DFService.register(this, agentDescription);
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Sends a NOT_UNDERSTOOD message.
     * @param receiver the receiver.
     */
    private void sendNotUnderstood(AID receiver) {
        ACLMessage message = new ACLMessage(ACLMessage.NOT_UNDERSTOOD);
        message.addReceiver(receiver);
        send(message);
    }
     
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /** A role manager behaviour. */
    private class RoleManagerBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            ACLMessage message = myAgent.receive(roleMessageTemplate);
            if (message != null) {
                // A 'Role' message received.
                RoleMessage roleMessage = new RoleMessage(message);
                switch (roleMessage.getAction()) {
                    case ACTIVATE_ACTION:
                        // 'Activate' action requested.
                        activateRole(roleMessage.getPlayer());
                        break;
                        
                    case DEACTIVATE_ACTION:
                        // 'Deactivate' action requested.
                        deactivateRole(roleMessage.getPlayer());
                        break;
                    
                    case INVOKE_POWER_ACTION:
                        break;
                        
                    case INVOKE_RESPONSIBILITY_ACTION:
                        break;
                        
                    default:
                        // Unknown action requested.
                        sendNotUnderstood(message.getSender());
                        break;
                }
            } else {
                // No 'Role' message received.
                block();
            }
        }
        
        // ---------- PRIVATE ----------

        private void activateRole(AID player) {
            if (player.equals(playerInfo.getAID())) {
                addBehaviour(new ActivateProtocolResponder());
            } else {
                // You are not enacting this role.
            }
        }

        private void deactivateRole(AID player) {
            if (player.equals(playerInfo.getAID())) {
                addBehaviour(new DeactivateProtocolResponder());
            } else {
                // You are not enacting this role.
            }
        }
    }
    
    /** An 'Activate' protocol responder behaviour. */
    private class ActivateProtocolResponder extends FSMBehaviour {
    }
    
    /** A 'Deactivate' protocol responder behaviour. */
    private class DeactivateProtocolResponder extends FSMBehaviour {
    }
    
    private class RoleMessage {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private String action;
        
        private AID player;

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        private RoleMessage(ACLMessage message) {
            
            // Get the player.
            player = message.getSender();
        }
                
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        public String getAction() {
            return action;
        }
        
        public AID getPlayer() {
            return player;
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
