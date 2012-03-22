package thespian4jade.proto.organizationprotocol.subscribetoeventprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.core.Event;
import thespian4jade.core.organization.Organization_SubscribeToEvent_ResponderParty;
import thespian4jade.core.player.Player_SubscribeToEventEvent_InitiatorParty;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.Protocol;
import thespian4jade.proto.ResponderParty;

/**
 * The 'Subscribe to event' protocol.
 * @author Lukáš Kúdela
 * @since 2012-03-21
 * @version %I% %G%
 */
public class SubscribeToEventProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates an initiator party.
     * @param arguments the initiator party's contructor arguments
     * @returns an initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object... arguments) {
        String organizationName = (String)arguments[0];
        Event event = (Event)arguments[1];
        Class eventHandlerClass = (Class)arguments[2];
        return new Player_SubscribeToEventEvent_InitiatorParty(organizationName, event, eventHandlerClass);
    }

    /**
     * Creates a responder party.
     * @param message the ACL message
     * @returns a responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Organization_SubscribeToEvent_ResponderParty(message);
    }
    
    // </editor-fold>    
}
