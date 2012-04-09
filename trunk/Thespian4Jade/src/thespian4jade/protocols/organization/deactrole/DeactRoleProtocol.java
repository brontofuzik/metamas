package thespian4jade.protocols.organization.deactrole;

import jade.lang.acl.ACLMessage;
import thespian4jade.core.organization.Organization_DeactRole_ResponderParty;
import thespian4jade.core.player.Player_DeactRole_InitiatorParty;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.behaviours.parties.ResponderParty;

/**
 * The 'Deact role' protocol.
 * Design pattern: Singleton, Role: Singleton
 * Design pattern: Abstract factory, Role: Concrete factory
 * @author Lukáš Kúdela
 * @since 2011-10-24
 * @version %I% %G%
 */
public class DeactRoleProtocol extends Protocol {   
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the DeactRoleProtocol class.
     * The 'Deact role' protocol is initiated by an ACL message with the REQUEST
     * performative.
     */
    public DeactRoleProtocol() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates a new 'Deact role' protocol initiator party.
     * @param arguments the 'Deact role' protocol initiator party's contructor
     * arguments:
     *     1) name of the organization, and
     *     2) name of the role
     * @returns a new 'Deact role' protocol initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object... arguments) {
        String organizationName = (String)arguments[0];
        String roleName = (String)arguments[1];
        return new Player_DeactRole_InitiatorParty(organizationName, roleName);
    }
    
    /**
     * Creates a new 'Deact role' protocol responder party.
     * @param message the ACL message to which the 'Deact role' protocol
     * responder party responds
     * @returns a new 'Deact role' protocol responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Organization_DeactRole_ResponderParty(message);
    }
    
    // </editor-fold>
}
