package example3.organizations.expressionevaluation.evaluator;

import example3.organizations.expressionevaluation.divisor.Divider_Role;
import example3.protocols.evaluatedivision.EvaluateDivisionProtocol;

/**
 * The 'Evaluate division' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2012-03-014
 * @version %I% %G%
 */
public class EvaluateDivision_InitiatorParty extends EvaluateBinaryOperation_InitiatorParty {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateDivision_InitiatorParty() {
        super(EvaluateDivisionProtocol.getInstance());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters
    
    @Override
    protected String getBinaryEvaluatorRoleName() {
        return Divider_Role.NAME;
    }
    
    // </editor-fold>
}
