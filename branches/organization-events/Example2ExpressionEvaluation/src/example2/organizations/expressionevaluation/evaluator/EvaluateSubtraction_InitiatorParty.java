package example2.organizations.expressionevaluation.evaluator;

import example2.organizations.expressionevaluation.subtractor.Subtractor_Role;
import example2.protocols.evaluatesubtraction.EvaluateSubtractionProtocol;

/**
 * The 'Evaluate subtraction' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2012-03-04
 * @version %I% %G%
 */
public class EvaluateSubtraction_InitiatorParty extends EvaluateBinaryOperation_InitiatorParty {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateSubtraction_InitiatorParty() {
        super(EvaluateSubtractionProtocol.getInstance());
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    @Override
    protected String getBinaryEvaluatorRoleName() {
        return Subtractor_Role.NAME;
    }
    
    // </editor-fold>
}
