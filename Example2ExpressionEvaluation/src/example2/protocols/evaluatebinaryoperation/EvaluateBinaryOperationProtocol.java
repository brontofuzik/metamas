package example2.protocols.evaluatebinaryoperation;

import example2.organizations.expressionevaluation.EvaluateBinaryOperation_ResponderParty;
import example2.organizations.expressionevaluation.evaluator.EvaluateBinaryOperation_InitiatorParty;
import example2.organizations.expressionevaluation.evaluator.Operation;
import jade.lang.acl.ACLMessage;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.behaviours.parties.ResponderParty;

/**
 * The 'Evaluate binary operation' protocol. 
 * @author Lukáš Kúdela
 * @since 2012-03-24
 * @version %I% %G%
 */  
public class EvaluateBinaryOperationProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EvaluateBinaryOperationProtocol class.
     */
    public EvaluateBinaryOperationProtocol() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates a new 'Evaluate binary operation' protocol initiator party.
     * @param arguments the 'Evaluate binary operation' protocol initiator party's contructor
     * arguments:
     *     1) binary operation
     * @returns a new 'Evaluate binary operation' protocol initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object... arguments) {
        Operation operation = (Operation)arguments[0];
        return new EvaluateBinaryOperation_InitiatorParty(operation);
    }

    /**
     * Creates a new 'Evaluate binary operation' protocol responder party.
     * @param message the ACL message to which the 'Evaluate binary operation' protocol
     * responder party responds
     * @returns a new 'Evaluate binary operation' protocol responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new EvaluateBinaryOperation_ResponderParty(message);
    }
    
    // </editor-fold>
}
