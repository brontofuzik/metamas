package jadeorg.lang;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * A requirements message parser/generator.
 * A requirements message is a message send from an Organization agent to a Player agent
 * containing information about requirements to enact a certain role.
 * @author Lukáš Kúdela (2011-10-20)
 */
public class RequirementsMessage {
    
    private static final String ENACT_PROTOCOL = "enact-protocol";
    
    // <editor-fold defaultstate="collapsed" desc="Fields">

    private AID player;
    
    private String[] requirements;

    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    // Parser
    public RequirementsMessage(ACLMessage message) {
        // Parse the player.
        player = message.getSender();
        
        // Parse the requirements.
    }

    // Generator
    public RequirementsMessage() {
    }

    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    // Generator
    public RequirementsMessage setPlayer(AID player) {
        this.player = player;
        return this;
    }
    
    /**
     * Gets the requirements.
     * @return the requirements
     */
    // Parser
    public String[] getRequirements() {
        return Arrays.copyOf(requirements, requirements.length);
    }
    
    /**
     * Sets the requirements.
     * @requirements the requirements
     */
    //Generator
    public RequirementsMessage setRequirements(String[] requirements) {
        this.requirements = Arrays.copyOf(requirements, requirements.length);
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    // Generator
    public ACLMessage getMessage() {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setProtocol(ENACT_PROTOCOL);
        message.addReceiver(player);
        message.setContent(getContent());
        return message;
    }
    
    // Generator
    private String getContent() {
        // Generate the requirements.
        return "requirements: ";
    }
    
    // </editor-fold>
}
