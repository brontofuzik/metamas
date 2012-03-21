package example2metamodel;

import thespian.semanticmodel.MultiAgentSystem;
import thespian.semanticmodel.fsm.FSM;
import thespian.semanticmodel.organization.Competence;
import thespian.semanticmodel.organization.OrganizationType;
import thespian.semanticmodel.organization.Role;
import thespian.semanticmodel.player.PlayerType;
import thespian.semanticmodel.player.Responsibility;
import thespian.semanticmodel.protocol.Message;
import thespian.semanticmodel.protocol.Message.MessageType;
import thespian.semanticmodel.protocol.Party;
import thespian.semanticmodel.protocol.Protocol;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-17
 * @version %I% %G%
 */
public class Example2Metamodel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MultiAgentSystem model = createModel();
        model.generate("C:\\DATA\\projects\\MAS\\MetaMAS");
    }

    // ----- PRIVATE -----
    
    private static MultiAgentSystem createModel() {
        MultiAgentSystem example2Mas = new MultiAgentSystem("Example2ExpressionEvaluation", "example2");

        // ---------- Protocols ----------
        
        example2Mas.addProtocol(createEvaluateExpressionProtocol());
        example2Mas.addProtocol(createEvaluateAdditionProtocol());
        example2Mas.addProtocol(createEvaluateSubtractionProtocol());
        example2Mas.addProtocol(createEvaluateMultiplicationProtocol());
        example2Mas.addProtocol(createEvaluateDivisionProtocol());

        // ---------- Organizations ----------
        
        OrganizationType evaluateExpressionOrganizationType = createEvaluateExpressionOrganizationType();        
        example2Mas.addOrganizationType(evaluateExpressionOrganizationType);
        example2Mas.addOrganization(evaluateExpressionOrganizationType.createOrganization("evaluateExpression_Organization"));

        // ---------- Players ----------
        
        PlayerType player1PlayerType = createPlayer1PlayerType();
        example2Mas.addPlayerType(player1PlayerType);
        example2Mas.addPlayer(player1PlayerType.createPlayer("player1"));
        
        PlayerType player2PlayerType = createPlayer2PlayerType();
        example2Mas.addPlayerType(player2PlayerType);
        example2Mas.addPlayer(player2PlayerType.createPlayer("player2"));
        
        PlayerType player3PlayerType = createPlayer3PlayerType();
        example2Mas.addPlayerType(player3PlayerType);
        example2Mas.addPlayer(player3PlayerType.createPlayer("player3"));
        
        PlayerType player4PlayerType = createPlayer4PlayerType();
        example2Mas.addPlayerType(player4PlayerType);
        example2Mas.addPlayer(player4PlayerType.createPlayer("player4"));
        
        PlayerType player5PlayerType = createPlayer5PlayerType();
        example2Mas.addPlayerType(player5PlayerType);
        example2Mas.addPlayer(player5PlayerType.createPlayer("player5"));
        
        return example2Mas;
    }

    // ---------- Protocols ----------
    
    private static Protocol createEvaluateExpressionProtocol() {
        Protocol evaluateExpressionProtocol = new Protocol("EvaluateExpressionProtocol");

        // ---------- Parties ----------
        
        // The initiator party.
        Party initiatorParty = new Party("InvokeFunction_InitiatorParty");
        initiatorParty.setFSM(createEvaluateExpressionInitiatorFSM());
        evaluateExpressionProtocol.setInitiatorParty(initiatorParty);

        // The responder party.
        Party responderParty = new Party("InvokeFunction_ResponderParty");
        responderParty.setFSM(createEvaluateExpressionResponderFSM());
        evaluateExpressionProtocol.setResponderParty(responderParty);

        // ---------- Messages ----------
        
        evaluateExpressionProtocol.addMessage(new Message("EvaluateExpressionRequestMessage", MessageType.TextMessage));
        evaluateExpressionProtocol.addMessage(new Message("EvaluateExpressionReplyMessage", MessageType.BinaryMessage));
        
        return evaluateExpressionProtocol;
    }
    
    private static FSM createEvaluateExpressionInitiatorFSM() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private static FSM createEvaluateExpressionResponderFSM() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private static Protocol createEvaluateAdditionProtocol() {
        throw new UnsupportedOperationException("Not yet implemented");        
    }
    
    private static Protocol createEvaluateSubtractionProtocol() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private static Protocol createEvaluateMultiplicationProtocol() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private static Protocol createEvaluateDivisionProtocol() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // ---------- Organizations ----------
    
    private static OrganizationType createEvaluateExpressionOrganizationType() {
        OrganizationType evaluateExpressionOrganizationType = new OrganizationType("EvaluateExpression_Organization");
        
        // ---------- Roles ----------
        
        evaluateExpressionOrganizationType.addRole(createEvaluatorRole());
        evaluateExpressionOrganizationType.addRole(createAdderRole());
        evaluateExpressionOrganizationType.addRole(createSubtractorRole());
        evaluateExpressionOrganizationType.addRole(createMultiplierRole());
        evaluateExpressionOrganizationType.addRole(createDivisorRole());
        
        return evaluateExpressionOrganizationType;
    }
    
    private static Role createEvaluatorRole() {
        Role evaluatorRole = new Role("Evaluator_Role");
        
        // The 'Evaluate' competence.
        Competence evaluateCompetence = new Competence("Evaluate_Competence",
                Competence.CompetenceType.Synchronous, "String", "Integer");
        evaluateCompetence.setFSM(createEvaluateCompetenceFSM());
        evaluatorRole.addCompetence(evaluateCompetence);
        
        return evaluatorRole;
    }
    
    private static FSM createEvaluateCompetenceFSM() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private static Role createAdderRole() {
        Role adderRole = new Role("Adder_Role");
        return adderRole;
    }
    
    private static Role createSubtractorRole() {
        Role subtractorRole = new Role("Subtractor_Role");
        return subtractorRole;
    }
        
    private static Role createMultiplierRole() {
        Role multiplierRole = new Role("Multiplier_Role");
        return multiplierRole;
    }
            
    private static Role createDivisorRole() {
        Role divisorRole = new Role("Divisor_Role");
        return divisorRole;
    }
    
    // ---------- Players ----------

    private static PlayerType createPlayer1PlayerType() {
        PlayerType player1PlayerType = new PlayerType("Player1");
        return player1PlayerType;
    }
    
    private static PlayerType createPlayer2PlayerType() {
        PlayerType player2PlayerType = new PlayerType("Player2");

        // The 'Add' responsibility.
        Responsibility addResponsibility = new Responsibility("Add_Responsibility",
        Responsibility.ResponsibilityType.Asynchronous, "OperandPair", "Integer");
        player2PlayerType.addResponsibility(addResponsibility);
        
        return player2PlayerType;
    }
    
    private static PlayerType createPlayer3PlayerType() {
        PlayerType player3PlayerType = new PlayerType("Player3");
        
        // The 'Subtract' responsibility.
        Responsibility subtractResponsibility = new Responsibility("Subtract_Responsibility",
        Responsibility.ResponsibilityType.Asynchronous, "OperandPair", "Integer");
        player3PlayerType.addResponsibility(subtractResponsibility);
        
        return player3PlayerType;
    }
        
    private static PlayerType createPlayer4PlayerType() {
        PlayerType player4PlayerType = new PlayerType("Player4");
        
        // The 'Multiply' responsibility.
        Responsibility multiplyResponsibility = new Responsibility("Multiply_Responsibility",
        Responsibility.ResponsibilityType.Asynchronous, "OperandPair", "Integer");
        player4PlayerType.addResponsibility(multiplyResponsibility);
        
        return player4PlayerType;
    }
            
    private static PlayerType createPlayer5PlayerType() {
        PlayerType player5PlayerType = new PlayerType("Player5");
        
        // The 'Divide' responsibility.
        Responsibility divideResponsibility = new Responsibility("Divide_Responsibility",
        Responsibility.ResponsibilityType.Asynchronous, "OperandPair", "Integer");
        player5PlayerType.addResponsibility(divideResponsibility);
        
        return player5PlayerType;
    }
}
