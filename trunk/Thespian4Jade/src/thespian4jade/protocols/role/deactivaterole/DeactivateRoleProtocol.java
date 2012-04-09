package thespian4jade.protocols.role.deactivaterole;

import jade.lang.acl.ACLMessage;
import thespian4jade.core.organization.Role_DeactivateRole_ResponderParty;
import thespian4jade.core.player.Player_DeactivateRole_InitiatorParty;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.behaviours.parties.ResponderParty;

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

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the DeactivateRoleProtocol class.
     * The 'Deactivate role' protocol is initiated by an ACL message with the REQUEST
     * performative.
     */
    public DeactivateRoleProtocol() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates a new 'Deactivate role' protocol initiator party.
     * @param arguments the 'Deactivate role' protocol initiator party's contructor
     * arguments:
     *     1) name of the role
     * @returns a new 'Deactivate role' protocol initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object... arguments) {
        String roleName = (String)arguments[0];
        return new Player_DeactivateRole_InitiatorParty(roleName);
    }

    /**
     * Creates a new 'Deactivate role' protocol responder party.
     * @param message the ACL message to which the 'Deactivate role' protocol
     * responder party responds
     * @returns a new 'Deactivate role' protocol responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Role_DeactivateRole_ResponderParty(message);
    }
    
    // </editor-fold>
}
