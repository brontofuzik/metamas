package example2.organizations.expressionevaluation.evaluator;

import example2.organizations.expressionevaluation.adder.Adder_Role;
import example2.protocols.Protocols;
import thespian4jade.proto.ProtocolRegistry_StaticClass;

/**
 * The 'Evaluate addition' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class EvaluateAddition_InitiatorParty extends EvaluateBinaryOperation_InitiatorParty {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateAddition_InitiatorParty() {
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.EVALUATE_ADDITION_PROTOCOL));
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    protected String getBinaryOperatorRoleName() {
        return Adder_Role.NAME;
    }
    
    // </editor-fold>
}
