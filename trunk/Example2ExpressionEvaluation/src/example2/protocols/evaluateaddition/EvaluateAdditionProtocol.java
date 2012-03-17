package example2.protocols.evaluateaddition;

import example2.organizations.expressionevaluation.adder.EvaluateAddition_ResponderParty;
import example2.organizations.expressionevaluation.evaluator.EvaluateAddition_InitiatorParty;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.Protocol;
import thespian4jade.proto.ResponderParty;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateAdditionProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static EvaluateAdditionProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public static EvaluateAdditionProtocol getInstance() {
        if (singleton == null) {
            singleton = new EvaluateAdditionProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public InitiatorParty createInitiatorParty(Object[] arguments) {
        return new EvaluateAddition_InitiatorParty();
    }

    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new EvaluateAddition_ResponderParty(message);
    }
    
    // </editor-fold>
}
