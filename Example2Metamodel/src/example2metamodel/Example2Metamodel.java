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
        example2Mas.addOrganizationType(createEvaluateExpressionOrganizationType());
        example2Mas.addOrganization(evaluateExpressionOrganizationType.createOrganization("evaluateExpression_Organization"));

        // ---------- Players ----------
        
        PlayerType calculatorPlayerType = createCalculatorPlayerType();
        example2Mas.addPlayerType(calculatorPlayerType);
        example2Mas.addPlayer(calculatorPlayerType.createPlayer("calculator1_Player"));
        example2Mas.addPlayer(calculatorPlayerType.createPlayer("calculator2_Player"));
        example2Mas.addPlayer(calculatorPlayerType.createPlayer("calculator3_Player"));
        example2Mas.addPlayer(calculatorPlayerType.createPlayer("calculator4_Player"));
        example2Mas.addPlayer(calculatorPlayerType.createPlayer("calculator5_Player"));
        
        return example2Mas;
    }

    // ---------- Protocols ----------
    
    private static Protocol createEvaluateExpressionProtocol() {
        Protocol evaluateExpressionProtocol = new Protocol("EvaluateExpressionProtocol");

        // ---------- Parties ----------
        
        // The 'Evaluate expression' initiator party.
        Party invokeFunctionProtocolInitiatorParty = new Party("InvokeFunction_InitiatorParty");
        invokeFunctionProtocolInitiatorParty.setFSM(createEvaluateExpressionInitiatorFSM());
        evaluateExpressionProtocol.setInitiatorParty(invokeFunctionProtocolInitiatorParty);

        // The 'Evaluate expression' responder party.
        Party invokeFunctionResponderParty = new Party("InvokeFunctionResponder");
        invokeFunctionResponderParty.setFSM(createEvaluateExpressionResponderFSM());
        evaluateExpressionProtocol.setResponderParty(invokeFunctionResponderParty);

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
                Competence.CompetenceType.FSMCompetence, "String", "Integer");
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

    private static PlayerType createCalculatorPlayerType() {
        PlayerType calculatorPlayerType = new PlayerType("Calculator_Player");
        
        // The 'Add' responsibility.
        Responsibility addResponsibility = new Responsibility("Add_Responsibility",
                Responsibility.ResponsibilityType.OneShotResponsibility, "OperandPair", "Integer");
        calculatorPlayerType.addResponsibility(addResponsibility);
        
        // The 'Subtract' responsibility.
        Responsibility subtractResponsibility = new Responsibility("Subtract_Responsibility",
                Responsibility.ResponsibilityType.OneShotResponsibility, "OperandPair", "Integer");
        calculatorPlayerType.addResponsibility(subtractResponsibility);
        
        // The 'Multiply' responsibility.
        Responsibility multiplyResponsibility = new Responsibility("Multiply_Responsibility",
                Responsibility.ResponsibilityType.OneShotResponsibility, "OperandPair", "Integer");
        calculatorPlayerType.addResponsibility(multiplyResponsibility);
        
        // The 'Divide' responsibility.
        Responsibility divideResponsibility = new Responsibility("Divide_Responsibility",
                Responsibility.ResponsibilityType.OneShotResponsibility, "OperandPair", "Integer");
        calculatorPlayerType.addResponsibility(divideResponsibility);
        
        return calculatorPlayerType;
    }
}
