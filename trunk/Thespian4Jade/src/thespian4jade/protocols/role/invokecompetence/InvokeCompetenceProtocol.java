package thespian4jade.protocols.role.invokecompetence;

import jade.lang.acl.ACLMessage;
import thespian4jade.core.organization.Role_InvokeCompetence_ResponderParty;
import thespian4jade.core.player.Player_InvokeCompetence_InitiatorParty;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.behaviours.parties.ResponderParty;
import java.io.Serializable;

/**
 * The 'Invoke competence' protocol.
 * Design pattern: Singleton, Role: Singleton
 * Design pattern: Abstract factory, Role: Concrete factory
 * @author Lukáš Kúdela
 * @since 2011-11-30
 * @version %I% %G%
 */
public class InvokeCompetenceProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the InvokeCompetenceProtocol class.
     * The 'Invoke competence' protocol is initiated by an ACL message
     * with the REQUEST performative.
     */
    public InvokeCompetenceProtocol() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates a new 'Invoke competence' protocol initiator party.
     * @param arguments the 'Invoke competence' protocol initiator party's
     * contructor arguments:
     *     1) name of the competence, and
     *     2) competence argument
     * @returns a new 'Invoke competence' protocol initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object... arguments) {
        String competenceName = (String)arguments[0];
        Serializable argument = (Serializable)arguments[1];
        return new Player_InvokeCompetence_InitiatorParty(competenceName, argument);
    }

    /**
     * Creates a new 'Invoke competence' protocol responder party.
     * @param message the ACL message to which the 'Invoke competence' protocol
     * responder party responds
     * @returns a new 'Invoke competence' protocol responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Role_InvokeCompetence_ResponderParty(message);
    }
    
    // </editor-fold>
}
