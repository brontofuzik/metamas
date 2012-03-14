package example3.organizations.expressionevaluation.evaluator;

import example3.protocols.evaluateexpression.EvaluateExpressionProtocol;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.ResponderParty;

/**
 * The 'Evaluate expression' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateExpression_ResponderParty extends ResponderParty<Evaluator_Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateExpression_ResponderParty(ACLMessage message) {
        super(EvaluateExpressionProtocol.getInstance(), message);
    }
    
    // </editor-fold>    
}
