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
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private MessageTemplate roleMessageTemplate = MessageTemplate.and(
            MessageTemplate.MatchProtocol(ROLE_PROTOCOL),
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
    
    private PlayerInfo playerInfo;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    void setRoleName(String roleName) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    void setOrganization(Organization organization) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
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
    
    private void invokePower(AID player, String power, List<String> args) {
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
     * A role manager behaviour.
     */
    private class RoleManagerBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            ACLMessage message = myAgent.receive(roleMessageTemplate);
            if (message != null) {
                RoleMessage roleMessage = new RoleMessage(message);
                roleMessage.process();
            } else {
                block();
            }
        }
    }
    
    /**
     * An 'Activate' protocol responder behaviour.
     */
    private class ActivateProtocolResponder extends FSMBehaviour {
    }
    
    /**
     * A 'Deactivate' protocol responder behaviour.
     */
    private class DeactivateProtocolResponder extends FSMBehaviour {
    }
    
    /**
     * A message send to a Role agent from a Player agent.
     */
    private class RoleMessage {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private String action;
        
        private String power;
        
        private List<String> args = new ArrayList<String>();
        
        private AID player;

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public RoleMessage(ACLMessage message) {
            StringTokenizer tokenizer = new StringTokenizer(message.getContent());
            
            // Get the action.
            if (tokenizer.hasMoreTokens()) {
                action = tokenizer.nextToken();
            }
            
            switch (action) {
                case ACTIVATE_ACTION:
                    parseActivateMessage(tokenizer);
                    break;
                case DEACTIVATE_ACTION:
                    parseDeactivateMessage(tokenizer);
                    break;
                case INVOKE_POWER_ACTION:
                    parseInvokePowerMessage(tokenizer);
                    break;
                default:
                    assert false;
            }
            
            // Get the player.
            player = message.getSender();
        }
                
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        public String getAction() {
            return action;
        }
        
        public String getPower() {
            return power;
        }
        
        public AID getPlayer() {
            return player;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        public void process() {
            switch (action) {
                case ACTIVATE_ACTION:
                    activateRole(player);
                    break;
                case DEACTIVATE_ACTION:
                    deactivateRole(player);
                    break;
                case INVOKE_POWER_ACTION:
                    invokePower(player, power, args);
                default:
                    sendNotUnderstood(player);
                    break;
            }
        }
        
        private void parseActivateMessage(StringTokenizer tokenizer) {
        }

        private void parseDeactivateMessage(StringTokenizer tokenizer) {
        }
        
        private void parseInvokePowerMessage(StringTokenizer tokenizer) {
            // Get the power.
            if (tokenizer.hasMoreTokens()) {
                power = tokenizer.nextToken();
            }
            
            // Get the arguments.
            while (tokenizer.hasMoreTokens()) {
                args.add(tokenizer.nextToken());
            }
        }
        
        // </editor-fold>
    }
  
    // </editor-fold>
}
