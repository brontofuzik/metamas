package thespian.semanticmodel.protocol;

import java.util.HashMap;
import java.util.Map;
import thespian.utilities.Assert;

/**
 * A protocol.
 * @author Lukáš Kúdela
 * @since 2012-01-12
 * @version %I% %G%
 */
public class Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The name of the interaction protocol.
     */
    private String name;
    
    /**
     * The messages exchanged in this protocol.
     */
    private Map<String, Message> messages = new HashMap<String, Message>();
    
    /**
     * The initiator party.
     */
    private Party initiatorParty;
    
    /**
     * The responder party.
     */
    private Party responderParty;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Protocol(String name) {
        // ----- Preconditions -----
        Assert.isNotEmpty(name, "name");
        // -------------------------        
        
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
     * Gets the initiator party.
     * @return the initiator party
     */
    public Party getInitiatorParty() {
        return initiatorParty;
    }
    
    /**
     * Sets the initiator party.
     * @param initiatorParty the initiator party
     * @return this protocol (Fluent interface)
     */
    public Protocol setInitiatorParty(Party initiatorParty) {
        this.initiatorParty = initiatorParty;
        return this;
    }
    
    /**
     * Gets the responder party.
     * @return the responder party
     */
    public Party getResponderParty() {
        return responderParty;
    }
    
    /**
     * Sets the responder party.
     * @param responderParty the responder party
     * @return this protocol (Fluent interface)
     */
    public Protocol setResponderParty(Party responderParty) {
        this.responderParty = responderParty;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void addMessage(Message message) {
        // ----- Preconditions -----
        Assert.isNotNull(message, "message");
        // -------------------------
        
        messages.put(message.getName(), message);
    }
    
    // </editor-fold>
}
