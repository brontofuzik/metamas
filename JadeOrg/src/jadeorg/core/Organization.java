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
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * An organization agent.
 * @author Lukáš Kúdela (2011-10-16)
 */
public class Organization extends Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Map<String, Role> roles = new Hashtable<String, Role>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void setup() {
        initialize();
        registerWithDF();
    }
    
    // ---------- PRIVATE ----------
    
    private void initialize() {
        initializeState();
        initializeBehaviour();
    }

    private void initializeState() {
    }

    private void initializeBehaviour() {
        addBehaviour(new OrganizationMainBehaviour());
    }

    private void registerWithDF() {
        DFAgentDescription agentDescription = new DFAgentDescription();
        agentDescription.setName(getAID());
        try {
            DFService.register(this, agentDescription);
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
    }
    
    /** Enacts a role.
     * @param roleName the name of the role
     * @param player the player
     */
    private void enactRole(String roleName, AID player) {
        if (roles.containsKey(roleName)) {
            addBehaviour(new EnactProtocolResponder(roleName, player));
        }
    }

    /** Deacts a role.
     * @param roleName the name of the role
     * @param player the player
     */
    private void deactRole(String roleName, AID player) {
        addBehaviour(new DeactProtocolResponder(roleName, player));
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
    
    /**
     * An organization behaviour.
     */
    private class OrganizationMainBehaviour extends CyclicBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private final String ORGANIZATION_PROTOCOL = "ORGANIZATION_PROTOCOL";
        
        private final String ENACT_ACTION = "enact";
        
        private final String DEACT_ACTION = "deact";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private MessageTemplate organizationMessageTemplate = MessageTemplate.and(
            MessageTemplate.MatchProtocol(ORGANIZATION_PROTOCOL),
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            ACLMessage message = myAgent.receive(organizationMessageTemplate);
            if (message != null) {
                OrganizationMessage orgMessage = new OrganizationMessage(message);
                if (orgMessage.getAction().equals(ENACT_ACTION)) {
                    // Enact action requested.
                    enactRole(orgMessage.getRole(), orgMessage.getPlayer());
                } else if (orgMessage.getAction().equals(DEACT_ACTION)) {
                    // Deact action requested.
                    deactRole(orgMessage.getRole(), orgMessage.getPlayer());
                } else {
                    // Unknown action requested.
                    sendNotUnderstood(message.getSender());
                }
            } else {
                block();
            }
        }
                
        // </editor-fold>
    }
    
    /**
     * An enact protocol responder.
     */
    private class EnactProtocolResponder extends FSMBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private String roleName;
        
        private AID player;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public EnactProtocolResponder(String roleName, AID player) {
            // ----- Preconditions -----
            assert !roleName.isEmpty();
            assert player != null;
            // -------------------------
            
            this.roleName = roleName;
            this.player = player;                  
        }
        
        // </editor-fold>
    }
    
    /**
     * A deact protocol responder.
     */
    private class DeactProtocolResponder extends FSMBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private String roleName;
        
        private AID player;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public DeactProtocolResponder(String roleName, AID player) {
            // ----- Preconditions -----
            assert !roleName.isEmpty();
            assert player != null;
            // -------------------------
            
            this.roleName = roleName;
            this.player = player;
        }
        
        // </editor-fold>
    }
    
    /**
     * An organization message tokenizer.
     */
    private class OrganizationMessage {
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private String action = "";
        
        private String role = "";
        
        private AID player;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public OrganizationMessage(ACLMessage message) {
            StringTokenizer tokenizer = new StringTokenizer(message.getContent());
            
            // Get the action.
            if (tokenizer.hasMoreTokens()) {
                action = tokenizer.nextToken();
            }
            
            // Get the role.
            if (tokenizer.hasMoreTokens()) {
                role = tokenizer.nextToken();
            }
            
            // Get the player.
            player = message.getSender();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
       public String getAction() {
            return action;
        }
        
        public String getRole() {
            return role;
        }
        
        public AID getPlayer() {
            return player;
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}