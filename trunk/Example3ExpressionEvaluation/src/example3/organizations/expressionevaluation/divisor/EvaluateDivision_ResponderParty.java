package example3.organizations.expressionevaluation.divisor;

import example3.organizations.expressionevaluation.EvaluateBinaryOperation_ResponderParty;
import example3.protocols.evaluatedivision.EvaluateDivisionProtocol;
import jade.lang.acl.ACLMessage;

/**
 * The 'Evaluate division' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateDivision_ResponderParty extends EvaluateBinaryOperation_ResponderParty {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateDivision_ResponderParty(ACLMessage message) {
        super(EvaluateDivisionProtocol.getInstance(), message);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected InvokeResponsibility_EvaluateBinaryOperation createInvokeResponsibility_EvaluateBinaryOperation() {
        return new InvokeResponsibility_EvaluateDivision();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class InvokeResponsibility_EvaluateDivision
        extends InvokeResponsibility_EvaluateBinaryOperation {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        InvokeResponsibility_EvaluateDivision() {
            super("Divide_Responsibility");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
