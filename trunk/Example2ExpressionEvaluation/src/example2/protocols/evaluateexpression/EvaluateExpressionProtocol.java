package example2.protocols.evaluateexpression;

import example2.organizations.expressionevaluation.EvaluateExpression_InitiatorParty;
import example2.organizations.expressionevaluation.evaluator.EvaluateExpression_ResponderParty;
import jade.lang.acl.ACLMessage;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.behaviours.parties.ResponderParty;

/**
 * The 'Evaluate expression' protocol.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateExpressionProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EvaluateExpressionProtocol class.
     */
    public EvaluateExpressionProtocol() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates a new 'Evaluate expression' protocol initiator party.
     * @param arguments the 'Evaluate expression' protocol initiator party's contructor
     * arguments:
     *     - none
     * @returns a new 'EEvaluate expression' protocol initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object[] arguments) {
        return new EvaluateExpression_InitiatorParty();
    }

    /**
     * Creates a new 'Evaluate expression' protocol responder party.
     * @param message the ACL message to which the 'Evaluate expression' protocol
     * responder party responds
     * @returns a new 'Evaluate expression' protocol responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new EvaluateExpression_ResponderParty(message);
    }
    
    // </editor-fold>
}
