package example2.organizations.expressionevaluation.evaluator;

import example2.organizations.expressionevaluation.multiplier.Multiplier_Role;
import example2.protocols.Protocols;
import thespian4jade.proto.ProtocolRegistry_StaticClass;

/**
 * The 'Evaluate multiplication' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2012-03-04
 * @version %I% %G%
 */
public class EvaluateMultiplication_InitiatorParty extends EvaluateBinaryOperation_InitiatorParty {
 
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateMultiplication_InitiatorParty() {
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.EVALUATE_MULTIPLICATION_PROTOCOL));
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    protected String getBinaryOperatorRoleName() {
        return Multiplier_Role.NAME;
    }
    
    // </editor-fold>
}
