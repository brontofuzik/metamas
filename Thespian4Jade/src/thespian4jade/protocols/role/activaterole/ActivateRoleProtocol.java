package thespian4jade.protocols.role.activaterole;

import jade.lang.acl.ACLMessage;
import thespian4jade.core.organization.Role_ActivateRole_ResponderParty;
import thespian4jade.core.player.Player_ActivateRole_InitiatorParty;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.behaviours.parties.ResponderParty;

/**
 * The 'Activate role' protocol.
 * Design pattern: Singleton, Role: Singleton
 * Design pattern: Abstract factory, Role: Concrete factory
 * @author Lukáš Kúdela
 * @since 2011-10-29
 * @version %I% %G%
 */
public class ActivateRoleProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the ActivateRoleProtocol class.
     * The 'Activate role' protocol is initiated by an ACL message with the REQUEST
     * performative.
     */
    public ActivateRoleProtocol() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates a new 'Activate role' protocol initiator party.
     * @param arguments the 'Activate role' protocol initiator party's contructor
     * arguments:
     *     1) name of the role
     * @returns a new 'Activate role' protocol initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object... arguments) {
        String roleName = (String)arguments[0];
        return new Player_ActivateRole_InitiatorParty(roleName);
    }

    /**
     * Creates a new 'Activate role' protocol responder party.
     * @param message the ACL message to which the 'Activate role' protocol
     * responder party responds
     * @returns a new 'Activate role' protocol responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Role_ActivateRole_ResponderParty(message);
    }
    
    // </editor-fold>
}
