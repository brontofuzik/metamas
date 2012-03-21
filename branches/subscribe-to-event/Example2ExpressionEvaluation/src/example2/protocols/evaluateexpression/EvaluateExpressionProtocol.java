package example2.protocols.evaluateexpression;

import example2.organizations.expressionevaluation.EvaluateExpression_InitiatorParty;
import example2.organizations.expressionevaluation.evaluator.EvaluateExpression_ResponderParty;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.Protocol;
import thespian4jade.proto.ResponderParty;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateExpressionProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public InitiatorParty createInitiatorParty(Object[] arguments) {
        return new EvaluateExpression_InitiatorParty();
    }

    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new EvaluateExpression_ResponderParty(message);
    }
    
    // </editor-fold>
}
