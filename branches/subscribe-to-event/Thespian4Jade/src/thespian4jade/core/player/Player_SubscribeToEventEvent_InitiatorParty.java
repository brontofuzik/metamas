package thespian4jade.core.player;

import jade.core.AID;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.ProtocolRegistry;
import thespian4jade.proto.organizationprotocol.subscribetoeventprotocol.SubscribeToEventProtocol;

/**
 * The 'Subscribe to event' protocol initiator party.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public class Player_SubscribeToEventEvent_InitiatorParty
    extends InitiatorParty<Player> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The organization to subscribe to; more precisely its AID.
     * The responder party of this protocol.
     */
    private AID organization;
    
    /**
     * The name of the organization to subscribe to.
     */
    private final String organizationName;
    
    /**
     * The event to subscribe to.
     */
    private final String event;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Player_SubscribeToEventEvent_InitiatorParty class.
     * Creates a new 'Subscribe to event' protocol initiator party.
     * @param organizationName the name of the organization to subscribe to
     * @param event the event to subscribe to
     */
    public Player_SubscribeToEventEvent_InitiatorParty(String organizationName, String event) {
        super(ProtocolRegistry.getProtocol(ProtocolRegistry.SUBSCRIBE_TO_EVENT_PROTOCOL));
        
        this.organizationName = organizationName;
        this.event = event;
        
        buildFSM();
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Buidls the party FSM.
     */
    private void buildFSM() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    // </editor-fold>
}
