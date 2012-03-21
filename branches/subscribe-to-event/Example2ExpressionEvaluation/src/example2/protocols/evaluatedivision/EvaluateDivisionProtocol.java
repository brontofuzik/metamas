package example2.protocols.evaluatedivision;

import example2.organizations.expressionevaluation.divisor.EvaluateDivision_ResponderParty;
import example2.organizations.expressionevaluation.evaluator.EvaluateDivision_InitiatorParty;
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
