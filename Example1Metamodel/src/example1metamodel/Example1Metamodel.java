package example1metamodel;

import thespian.semanticmodel.MultiAgentSystem;
import thespian.semanticmodel.fsm.FSM;
import thespian.semanticmodel.organization.OrganizationType;
import thespian.semanticmodel.organization.Competence;
import thespian.semanticmodel.organization.Role;
import thespian.semanticmodel.player.PlayerType;
import thespian.semanticmodel.player.Responsibility;
import thespian.semanticmodel.protocol.Message;
import thespian.semanticmodel.protocol.Message.MessageType;
import thespian.semanticmodel.protocol.Party;
import thespian.semanticmodel.protocol.Protocol;

/**
 * @author Lukáš Kúdela
 * @since 2012-01-10
 * @version %I% %G%
 */
public class Example1Metamodel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MultiAgentSystem model = createModel();
        model.generate("C:\\DATA\\projects\\MAS\\MetaMAS");
    }
    
    // ----- PRIVATE -----
    
    private static MultiAgentSystem createModel() {
        MultiAgentSystem example1Mas = new MultiAgentSystem("Example1FunctionInvocation", "example1");
        
        // ---------- Protocols ----------
        
        example1Mas.addProtocol(createInvokeFunctionProtocol());
      
        // ---------- Organizations ----------
        
        OrganizationType invokeFunctionOrganizationType = createInvokeFunctionOrganizationType();
        example1Mas.addOrganizationType(invokeFunctionOrganizationType);
        example1Mas.addOrganization(invokeFunctionOrganizationType.createOrganization("invokeFunction_Organization"));  
        
        // ---------- Players ----------
        
        PlayerType blankPlayerType = createBlankPlayerType();
        example1Mas.addPlayerType(blankPlayerType);
        example1Mas.addPlayer(blankPlayerType.createPlayer("player1"));
        
        PlayerType factorialComputerPlayerType = createFactorialComputerPlayerType();
        example1Mas.addPlayerType(factorialComputerPlayerType); 
        example1Mas.addPlayer(factorialComputerPlayerType.createPlayer("player2"));
        
        return example1Mas;
    }
    
    // ---------- Protocols ----------

    private static Protocol createInvokeFunctionProtocol() {
        Protocol invokeFunctionProtocol = new Protocol("InvokeFunctionProtocol");
        
        // ---------- Parties ---------- 
        
        // The initiator party.
        Party initiatorParty = new Party("InvokeFunction_InitiatorParty");
        initiatorParty.setFSM(createInvokeFunctionInitiatorFSM());
        invokeFunctionProtocol.setInitiatorParty(initiatorParty);
        
        // The responder party.
        Party responderParty = new Party("InvokeFunction_ResponderParty");
        responderParty.setFSM(createInvokeFunctionResponderFMS());
        invokeFunctionProtocol.setResponderParty(responderParty);
        
        // ---------- Messages ----------
        
        invokeFunctionProtocol.addMessage(new Message("RequestMessage", MessageType.TextMessage));
        invokeFunctionProtocol.addMessage(new Message("ReplyMessage", MessageType.TextMessage));
        
        return invokeFunctionProtocol;
    }
    
    private static FSM createInvokeFunctionInitiatorFSM() {
        throw new UnsupportedOperationException("Not yet implemented");       
    }
    
    private static FSM createInvokeFunctionResponderFMS() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    // ---------- Organizations ----------

    private static OrganizationType createInvokeFunctionOrganizationType() {
        OrganizationType invokeFunctionOrganizationType = new OrganizationType("InvokeFunction_Organization");
        
        // ---------- Roles ----------
        invokeFunctionOrganizationType.addRole(createInvokerRole());
        invokeFunctionOrganizationType.addRole(createExecutorRole());
        
        return invokeFunctionOrganizationType;
    }
    
    private static Role createInvokerRole() {
        Role invokerRole = new Role("Invoker_Role");
        
        // The 'Invoke function' competence.
        Competence invokeFunctionCompetence = new Competence("InvokeFunction_Competence",
            Competence.CompetenceType.Synchronous, "Integer", "Integer");
        invokeFunctionCompetence.setFSM(createInvokeFunctionCompetenceFSM());
        invokerRole.addCompetence(invokeFunctionCompetence);
        
        return invokerRole;
    }
    
    private static FSM createInvokeFunctionCompetenceFSM() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private static Role createExecutorRole() {
        Role executorRole = new Role("Executor_Role");
        return executorRole;
    }
    
    // ---------- Players ----------
    
    private static PlayerType createBlankPlayerType() {
        PlayerType blankPlayerType = new PlayerType("Blank_Player");        
        return blankPlayerType;
    } 
    
    private static PlayerType createFactorialComputerPlayerType() {
        PlayerType factorialComputerPlayerType = new PlayerType("FactorialComputer_Player");
        
        // The 'Execute function' responsibility.
        Responsibility executeFunctionResponsibility = new Responsibility("InvokeFunction_Responsibility",
            Responsibility.ResponsibilityType.Asynchronous, "Integer", "Integer");
        factorialComputerPlayerType.addResponsibility(executeFunctionResponsibility);
        
        return factorialComputerPlayerType;
    }
}
