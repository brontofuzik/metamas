package thespian4jade.protocols.organization.publishevent;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.Event;
import thespian4jade.core.organization.Organization_PublishEvent_InitiatorParty;
import thespian4jade.core.player.Player_PublishEvent_ResponderParty;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.behaviours.parties.ResponderParty;

/**
 * The 'Publish event' protocol.
 * @author Lukáš Kúdela
 * @since 2012-03-19
 * @version %I% %G%
 */
public class PublishEventProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the PublishEventProtocol class.
     * The 'Publish event' protocol is initiated by an ACL message with the INFORM
     * performative.
     */
    public PublishEventProtocol() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates a new 'Publish event' protocol initiator party.
     * @param arguments the 'Publish event' protocol initiator party's contructor
     * arguments:
     *     1) name of the organization,
     *     2) event argument, and
     *     3) player to exclude (more precisely, its AID)
     * @returns a new 'Publish event' protocol initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object... arguments) {
        Event event = (Event)arguments[0];
        String argument = (String)arguments[1];
        AID playerToExclude = (AID)arguments[2];
        return new Organization_PublishEvent_InitiatorParty(event, argument, playerToExclude);
    }

    /**
     * Creates a new 'Publish event' protocol responder party.
     * @param message the ACL message to which the 'Publish event' protocol
     * responder party responds
     * @returns a new 'Publish event' protocol responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Player_PublishEvent_ResponderParty(message);
    }
    
    // </editor-fold>
}
