package metamas.semanticmodel.protocol;

import java.util.HashMap;
import java.util.Map;
import metamas.utilities.Assert;

/**
 * An interaction protocol.
 * @author Lukáš Kúdela
 */
public class Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The name of the interaction protocol. */
    private String name;
    
    private Map<String, Party> parties = new HashMap<String, Party>();
    
    private Party initiator;
    
    private Map<String, Message> messages = new HashMap<String, Message>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Protocol(String name, Map<String, Party> parties, Party initiator) {
        Assert.isNotEmpty(name, "name");
        Assert.isNotNull(parties, "parties");
        Assert.isNotNull(initiator, "initiator");
                
        this.name = name;
        this.parties = parties;
        this.initiator = initiator;
    }

    public Protocol(String name) {
        Assert.isNotEmpty(name, "name");
        
        this.name = name;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
   
    /**
     * Gets the name.
     * @return The name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the initiator.
     * @return The initiator.
     */
    public Party getInitiator() {
        return initiator;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void addParty(Party party) {
        Assert.isNotNull(party, "party");
        
        parties.put(party.getName(), party);
    }
    
    public void addMessage(Message message) {
        Assert.isNotNull(message, "message");
        
        messages.put(message.getName(), message);
    }
    
    // </editor-fold>
}
