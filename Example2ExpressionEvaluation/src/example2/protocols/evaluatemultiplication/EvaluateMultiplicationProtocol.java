package example2.protocols.evaluatemultiplication;

import example2.organizations.expressionevaluation.evaluator.EvaluateMultiplication_InitiatorParty;
import example2.organizations.expressionevaluation.multiplier.EvaluateMultiplication_ResponderParty;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.Protocol;
import thespian4jade.proto.ResponderParty;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateMultiplicationProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public InitiatorParty createInitiatorParty(Object[] arguments) {
        return new EvaluateMultiplication_InitiatorParty();
    }

    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new EvaluateMultiplication_ResponderParty(message);
    }
    
    // </editor-fold>
}
