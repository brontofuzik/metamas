package thespian4jade.proto.organizationprotocol.raiseeventprotocol;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.organization.Organization_RaiseEvent_InitiatorParty;
import thespian4jade.core.player.Player_RaiseEvent_ResponderParty;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.Protocol;
import thespian4jade.proto.ResponderParty;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-19
 * @version %I% %G%
 */
public class RaiseEventProtocol extends Protocol {
 
    // <editor-fold defaultstate="collapsed" desc="Fields">
   
    private static RaiseEventProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private RaiseEventProtocol() {
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public static RaiseEventProtocol getInstance() {
        if (singleton == null) {
            singleton = new RaiseEventProtocol();
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
        return new Organization_RaiseEvent_InitiatorParty(event, argument);
    }

    /**
     * Creates a responder party.
     * @param message the ACL message
     * @returns a responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Player_RaiseEvent_ResponderParty(message);
    }
    
    // </editor-fold>
}
