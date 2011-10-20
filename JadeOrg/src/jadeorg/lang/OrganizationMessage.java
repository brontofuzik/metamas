package jadeorg.lang;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.util.StringTokenizer;

/**
 * An organization message parser/generator.
 * An organization message is a message send from a Player agent to an Organization agent
 * containing a request to enact/deact a certain role.
 * @author Lukáš Kúdela (2011-10-20)
 * @version 0.1
 */
public class OrganizationMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID player;
    
    private String action = "";

    private String role = "";

    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    // Parser
    public OrganizationMessage(ACLMessage message) {
        // Parse the player.
        player = message.getSender();
        
        StringTokenizer tokenizer = new StringTokenizer(message.getContent());

        // Parse the action.
        if (tokenizer.hasMoreTokens()) {
            action = tokenizer.nextToken();
        }

        // Parse the role.
        if (tokenizer.hasMoreTokens()) {
            role = tokenizer.nextToken();
        }
    }
    
    // Generator
    public OrganizationMessage() {
    }
        
    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    // Parser
    public AID getPlayer() {
        return player;
    }
    
    // Parser
    public String getAction() {
        return action;
    }
    
    // Generator
    public OrganizationMessage setAction(String action) {
        this.action = action;
        return this;
    }

    // Parser
    public String getRole() {
        return role;
    }
    
    // Generator
    public OrganizationMessage setRole(String role) {
        this.role = role;
        return this;
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    // Generator
    public ACLMessage getMessage() {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.setContent(getContent());
        return message;
    }
    
    private String getContent() {
        return "";
    }
    
    // </editor-fold>
}
