package example2.organizations.expressionevaluation.evaluator;

import example2.organizations.expressionevaluation.divisor.Divider_Role;
import example2.protocols.Protocols;
import thespian4jade.proto.ProtocolRegistry_StaticClass;

/**
 * The 'Evaluate division' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2012-03-014
 * @version %I% %G%
 */
public class EvaluateDivision_InitiatorParty extends EvaluateBinaryOperation_InitiatorParty {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateDivision_InitiatorParty() {
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.EVALUATE_DIVISION_PROTOCOL));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters
    
    @Override
    protected String getBinaryOperatorRoleName() {
        return Divider_Role.NAME;
    }
    
    // </editor-fold>
}
