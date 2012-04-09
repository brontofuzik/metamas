package thespian4jade.protocols.role.invokeresponsibility;

import jade.lang.acl.ACLMessage;
import thespian4jade.core.organization.Role_InvokeResponsibility_InitiatorParty;
import thespian4jade.core.player.Player_InvokeResponsibility_ResponderParty;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.behaviours.parties.ResponderParty;
import java.io.Serializable;

/**
 * The 'Invoke responsibility' protocol.
 * Design pattern: Singleton, Role: Singleton
 * @author Lukáš Kúdela
 * @since 2011-11-16
 * @version %I% %G%
 */
public class InvokeResponsibilityProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the InvokeResponsibilityProtocol class.
     * The 'Invoke responsibility' protocol is initiated by an ACL message
     * with the REQUEST performative.
     */
    public InvokeResponsibilityProtocol() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates a new 'Invoke responsibility' protocol initiator party.
     * @param arguments the 'Invoke responsibility' protocol initiator party's
     * contructor arguments:
     *     1) name of the rsponsibility, and
     *     2) responsibility argument
     * @returns a new 'Invoke responsibility' protocol initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object... arguments) {
        String responsibilityName = (String)arguments[0];
        Serializable argument = (Serializable)arguments[1];
        return new Role_InvokeResponsibility_InitiatorParty(responsibilityName, argument);
    }

    /**
     * Creates a new 'Invoke responsibility' protocol responder party.
     * @param message the ACL message to which the 'Invoke responsibility'
     * protocol responder party responds
     * @returns a new 'Invoke responsibility' protocol responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Player_InvokeResponsibility_ResponderParty(message);
    }
    
    // </editor-fold>   
}
