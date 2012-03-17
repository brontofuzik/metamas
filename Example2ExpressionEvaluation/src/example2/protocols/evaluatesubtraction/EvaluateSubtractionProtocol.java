package example2.protocols.evaluatesubtraction;

import example2.organizations.expressionevaluation.evaluator.EvaluateSubtraction_InitiatorParty;
import example2.organizations.expressionevaluation.subtractor.EvaluateSubtraction_ResponderParty;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.Protocol;
import thespian4jade.proto.ResponderParty;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateSubtractionProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static EvaluateSubtractionProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public static EvaluateSubtractionProtocol getInstance() {
        if (singleton == null) {
            singleton = new EvaluateSubtractionProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public InitiatorParty createInitiatorParty(Object[] arguments) {
        return new EvaluateSubtraction_InitiatorParty();
    }

    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new EvaluateSubtraction_ResponderParty(message);
    }
    
    // </editor-fold>
}
