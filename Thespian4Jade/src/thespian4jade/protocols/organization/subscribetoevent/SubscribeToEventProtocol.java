package thespian4jade.protocols.organization.subscribetoevent;

import jade.lang.acl.ACLMessage;
import thespian4jade.core.Event;
import thespian4jade.core.organization.Organization_SubscribeToEvent_ResponderParty;
import thespian4jade.core.player.Player_SubscribeToEventEvent_InitiatorParty;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.behaviours.parties.ResponderParty;

/**
 * The 'Subscribe to event' protocol.
 * @author Lukáš Kúdela
 * @since 2012-03-21
 * @version %I% %G%
 */
public class SubscribeToEventProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the SubscribeToEventProtocol class.
     * The 'Subscribe to event' protocol is initiated by an ACL message with the REQUEST
     * performative.
     */
    public SubscribeToEventProtocol() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates a new 'Subscribe to event' protocol initiator party.
     * @param arguments the 'Subscribe to event' protocol initiator party's contructor
     * arguments:
     *     1) name of the organization,
     *     2) event, and
     *     3) event handler
     * @returns a new 'Subscribe to event' protocol initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object... arguments) {
        String organizationName = (String)arguments[0];
        Event event = (Event)arguments[1];
        Class eventHandlerClass = (Class)arguments[2];
        return new Player_SubscribeToEventEvent_InitiatorParty(organizationName, event, eventHandlerClass);
    }

    /**
     * Creates a new 'Subscribe to event' protocol responder party.
     * @param message the ACL message to which the 'Subscribe to event' protocol
     * responder party responds
     * @returns a new 'Subscribe to event' protocol responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Organization_SubscribeToEvent_ResponderParty(message);
    }
    
    // </editor-fold>    
}
