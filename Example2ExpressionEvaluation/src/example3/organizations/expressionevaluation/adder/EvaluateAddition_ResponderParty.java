package example3.organizations.expressionevaluation.adder;

import example3.organizations.expressionevaluation.EvaluateBinaryOperation_ResponderParty;
import example3.protocols.evaluateaddition.EvaluateAdditionProtocol;
import jade.lang.acl.ACLMessage;

/**
 * The 'Evaluate addition' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateAddition_ResponderParty
    extends EvaluateBinaryOperation_ResponderParty {
  
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateAddition_ResponderParty(ACLMessage message) {
        super(EvaluateAdditionProtocol.getInstance(), message);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods"> 
    
    @Override
    protected InvokeResponsibility_EvaluateBinaryOperation createInvokeResponsibility_EvaluateBinaryOperation() {
        return new InvokeResponsibility_EvaluateAddition();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class InvokeResponsibility_EvaluateAddition
        extends InvokeResponsibility_EvaluateBinaryOperation {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        InvokeResponsibility_EvaluateAddition() {
            super("Add_Responsibility");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
