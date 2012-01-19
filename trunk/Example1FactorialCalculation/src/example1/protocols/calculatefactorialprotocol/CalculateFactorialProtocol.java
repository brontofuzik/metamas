package example1.protocols.calculatefactorialprotocol;

import example1.organizations.demo.answerer.Answerer_CalculateFactorialResponder;
import example1.organizations.demo.asker.Asker_CalculateFactorialInitiator;
import jade.lang.acl.ACLMessage;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.Protocol;
import jadeorg.proto.ResponderParty;

/**
 * The 'Calculate factorial' protocol.
 * Design pattern: Abstract factory, Role: Concrete factory
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public class CalculateFactorialProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static CalculateFactorialProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public static CalculateFactorialProtocol getInstance() {
        if (singleton == null) {
            singleton = new CalculateFactorialProtocol();
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
    public InitiatorParty createInitiatorParty(Object[] arguments) {
        return new Asker_CalculateFactorialInitiator();
    }

    /**
     * Creates a responder party.
     * @param message the ACL message
     * @returns a responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Answerer_CalculateFactorialResponder(message);
    }
    
    // </editor-fold> 
}
