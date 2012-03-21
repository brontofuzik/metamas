package thespian4jade.proto.roleprotocol.deactivateroleprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.core.organization.Role_DeactivateRole_ResponderParty;
import thespian4jade.core.player.Player_DeactivateRole_InitiatorParty;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.Protocol;
import thespian4jade.proto.ResponderParty;

/**
 * The 'Deactivate role' protocol.
 * Design pattern: Singleton, Role: Singleton
 * Design pattern: Abstract factory, Role: Concrete factory
 * DP: Singleton - Singleton
 * @author Lukáš Kúdela
 * @since 2011-10-29
 * @version %I% %G%
 */
public class DeactivateRoleProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates an initiator party.
     * @param arguments the initiator party's contructor arguments
     * @returns an initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object... arguments) {
        String roleName = (String)arguments[0];
        return new Player_DeactivateRole_InitiatorParty(roleName);
    }

    /**
     * Creates a responder party.
     * @param message the ACL message
     * @returns a responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Role_DeactivateRole_ResponderParty(message);
    }
    
    // </editor-fold>
}
