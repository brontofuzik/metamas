package example3.organizations.expressionevaluation.subtractor;

import example3.organizations.expressionevaluation.EvaluateBinaryOperation_ResponderParty;
import example3.protocols.evaluatesubtraction.EvaluateSubtractionProtocol;
import jade.lang.acl.ACLMessage;

/**
 * The 'Evaluate subtraction' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateSubtraction_ResponderParty
    extends EvaluateBinaryOperation_ResponderParty {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateSubtraction_ResponderParty(ACLMessage message) {
        super(EvaluateSubtractionProtocol.getInstance(), message);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected InvokeResponsibility_EvaluateBinaryOperation createInvokeResponsibility_EvaluateBinaryOperation() {
        return new InvokeResponsibility_EvaluateSubtraction();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class InvokeResponsibility_EvaluateSubtraction
        extends InvokeResponsibility_EvaluateBinaryOperation {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        InvokeResponsibility_EvaluateSubtraction() {
            super("Subtract_Responsibility");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}