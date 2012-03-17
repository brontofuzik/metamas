package example3.organizations.expressionevaluation.multiplier;

import example3.organizations.expressionevaluation.EvaluateBinaryOperation_ResponderParty;
import example3.protocols.evaluatemultiplication.EvaluateMultiplicationProtocol;
import jade.lang.acl.ACLMessage;

/**
 * The 'Evaluate multiplication' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2012-03-04
 * @version %I% %G%
 */
public class EvaluateMultiplication_ResponderParty
    extends EvaluateBinaryOperation_ResponderParty {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateMultiplication_ResponderParty(ACLMessage message) {
        super(EvaluateMultiplicationProtocol.getInstance(), message);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected InvokeResponsibility_EvaluateBinaryOperation createInvokeResponsibility_EvaluateBinaryOperation() {
        return new InvokeResponsibility_EvaluateMultiplication();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class InvokeResponsibility_EvaluateMultiplication
        extends InvokeResponsibility_EvaluateBinaryOperation {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        InvokeResponsibility_EvaluateMultiplication() {
            super("Multiply_Responsibility");
        }
        
        // </editor-fold>       
    }
    
    // </editor-fold>
}
