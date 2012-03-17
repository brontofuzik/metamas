package example3.protocols.evaluatedivision;

import example3.organizations.expressionevaluation.divisor.EvaluateDivision_ResponderParty;
import example3.organizations.expressionevaluation.evaluator.EvaluateDivision_InitiatorParty;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.Protocol;
import thespian4jade.proto.ResponderParty;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateDivisionProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static EvaluateDivisionProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public static EvaluateDivisionProtocol getInstance() {
        if (singleton == null) {
            singleton = new EvaluateDivisionProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    public InitiatorParty createInitiatorParty(Object[] arguments) {
        return new EvaluateDivision_InitiatorParty();
    }

    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new EvaluateDivision_ResponderParty(message);
    }
    
    // </editor-fold>  
}
