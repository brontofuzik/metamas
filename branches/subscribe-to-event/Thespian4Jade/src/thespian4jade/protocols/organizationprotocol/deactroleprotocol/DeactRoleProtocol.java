package thespian4jade.protocols.organizationprotocol.deactroleprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.core.organization.Organization_DeactRole_ResponderParty;
import thespian4jade.core.player.Player_DeactRole_InitiatorParty;
import thespian4jade.protocols.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.protocols.ResponderParty;

/**
 * The 'Deact role' protocol.
 * Design pattern: Singleton, Role: Singleton
 * Design pattern: Abstract factory, Role: Concrete factory
 * @author Lukáš Kúdela
 * @since 2011-10-24
 * @version %I% %G%
 */
public class DeactRoleProtocol extends Protocol {   
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates an initiator party.
     * @param arguments the initiator party's contructor arguments
     * @returns an initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object... arguments) {
        String organizationName = (String)arguments[0];
        String roleName = (String)arguments[1];
        return new Player_DeactRole_InitiatorParty(organizationName, roleName);
    }
    
    /**
     * Creates a responder party.
     * @param message the ACL message
     * @returns a responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Organization_DeactRole_ResponderParty(message);
    }
    
    // </editor-fold>
}
