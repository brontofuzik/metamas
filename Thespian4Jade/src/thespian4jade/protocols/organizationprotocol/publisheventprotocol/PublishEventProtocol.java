package thespian4jade.protocols.organizationprotocol.publisheventprotocol;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.Event;
import thespian4jade.core.organization.Organization_PublishEvent_InitiatorParty;
import thespian4jade.core.player.Player_PublishEvent_ResponderParty;
import thespian4jade.protocols.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.protocols.ResponderParty;

/**
 * The 'Publish event' protocol.
 * @author Lukáš Kúdela
 * @since 2012-03-19
 * @version %I% %G%
 */
public class PublishEventProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates an initiator party.
     * @param arguments the initiator party's contructor arguments
     * @returns an initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object... arguments) {
        Event event = (Event)arguments[0];
        String argument = (String)arguments[1];
        AID playerToExclude = (AID)arguments[2];
        return new Organization_PublishEvent_InitiatorParty(event, argument, playerToExclude);
    }

    /**
     * Creates a responder party.
     * @param message the ACL message
     * @returns a responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Player_PublishEvent_ResponderParty(message);
    }
    
    // </editor-fold>
}
