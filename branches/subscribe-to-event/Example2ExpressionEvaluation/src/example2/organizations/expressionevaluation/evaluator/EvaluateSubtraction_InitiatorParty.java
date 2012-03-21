package example2.organizations.expressionevaluation.evaluator;

import example2.organizations.expressionevaluation.subtractor.Subtractor_Role;
import example2.protocols.Protocols;
import thespian4jade.proto.ProtocolRegistry_StaticClass;

/**
 * The 'Evaluate subtraction' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2012-03-04
 * @version %I% %G%
 */
public class EvaluateSubtraction_InitiatorParty extends EvaluateBinaryOperation_InitiatorParty {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateSubtraction_InitiatorParty() {
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.EVALUATE_SUBTRACTION_PROTOCOL));
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    @Override
    protected String getBinaryOperatorRoleName() {
        return Subtractor_Role.NAME;
    }
    
    // </editor-fold>
}
