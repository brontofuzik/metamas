package example2.organizations.expressionevaluation.subtractor;

import example2.organizations.expressionevaluation.EvaluateBinaryOperation_ResponderParty;
import example2.protocols.Protocols;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.ProtocolRegistry_StaticClass;

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
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.EVALUATE_SUBTRACTION_PROTOCOL), message);
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