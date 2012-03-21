package thespian4jade.proto.organizationprotocol.publisheventprotocol;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.organization.Organization_PublishEvent_InitiatorParty;
import thespian4jade.core.player.Player_PublishEvent_ResponderParty;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.Protocol;
import thespian4jade.proto.ResponderParty;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-19
 * @version %I% %G%
 */
public class PublishEventProtocol extends Protocol {
 
    // <editor-fold defaultstate="collapsed" desc="Fields">
   
    private static PublishEventProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private PublishEventProtocol() {
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public static PublishEventProtocol getInstance() {
        if (singleton == null) {
            singleton = new PublishEventProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates an initiator party.
     * @param arguments the initiator party's contructor arguments
     * @returns an initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object... arguments) {
        String event = (String)arguments[0];
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
