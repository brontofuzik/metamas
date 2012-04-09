package thespian4jade.proto.roleprotocol.invokecompetenceprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.core.organization.Role_InvokeCompetence_ResponderParty;
import thespian4jade.core.player.Player_InvokeCompetence_InitiatorParty;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.Protocol;
import thespian4jade.proto.ResponderParty;
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
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The singleton instance of this protocol. */
    private static InvokeCompetenceProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the singleton instance of this protocol.
     * @return the singleton instance of this protocol
     */
    public static InvokeCompetenceProtocol getInstance() {
        if (singleton == null) {
            singleton = new InvokeCompetenceProtocol();
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
        String competenceName = (String)arguments[0];
        Serializable argument = (Serializable)arguments[1];
        return new Player_InvokeCompetence_InitiatorParty(competenceName, argument);
    }

    /**
     * Creates a responder party.
     * @param message the ACL message
     * @returns a responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Role_InvokeCompetence_ResponderParty(message);
    }
    
    // </editor-fold>
}