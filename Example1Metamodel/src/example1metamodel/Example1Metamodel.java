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
        
        PlayerType demoPlayerType = createDemoPlayerType();
        example1Mas.addPlayerType(demoPlayerType);
        example1Mas.addPlayer(demoPlayerType.createPlayer("demo1_Player"));
        example1Mas.addPlayer(demoPlayerType.createPlayer("demo2_Player"));
        
        return example1Mas;
    }
    
    // ---------- Protocols ----------

    private static Protocol createInvokeFunctionProtocol() {
        Protocol invokeFunctionProtocol = new Protocol("InvokeFunctionProtocol");
        
        // ---------- Parties ---------- 
        
        // The 'Invoke function' initiator party.
        Party invokeFunctionProtocolInitiatorParty = new Party("InvokeFunction_InitiatorParty");
        invokeFunctionProtocolInitiatorParty.setFSM(createInvokeFunctionInitiatorFSM());
        invokeFunctionProtocol.setInitiatorParty(invokeFunctionProtocolInitiatorParty);
        
        // The 'Invoke function' responder party.
        Party invokeFunctionResponderParty = new Party("InvokeFunction_ResponderParty");
        invokeFunctionResponderParty.setFSM(createInvokeFunctionResponderFMS());
        invokeFunctionProtocol.setResponderParty(invokeFunctionResponderParty);
        
        // ---------- Messages ----------
        
        invokeFunctionProtocol.addMessage(new Message("RequestMessage", MessageType.TextMessage));
        invokeFunctionProtocol.addMessage(new Message("ReplyMessage", MessageType.TextMessage));
        
        return invokeFunctionProtocol;
    }
    
    private static FSM createInvokeFunctionInitiatorFSM() {
         // TODO Implement.
        throw new UnsupportedOperationException("Not yet implemented");       
    }
    
    private static FSM createInvokeFunctionResponderFMS() {
        // TODO Implement.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    // ---------- Organizations ----------

    private static OrganizationType createInvokeFunctionOrganizationType() {
        OrganizationType invokeFunctionOrganizationType = new OrganizationType("InvokeFunction_Organization");
        
        // ---------- Roles ----------
        invokeFunctionOrganizationType.addRole(createInvokerRole());
        invokeFunctionOrganizationType.addRole(createExecuterRole());
        
        return invokeFunctionOrganizationType;
    }
    
    private static Role createInvokerRole() {
        Role invokerRole = new Role("Invoker_Role");
        
        // The 'Invoke function' competence.
        Competence invokeFunctionCompetence = new Competence("InvokeFunction_Competence",
            Competence.CompetenceType.FSMCompetence, "Integer", "Integer");
        invokeFunctionCompetence.setFSM(createInvokeFunctionCompetenceFSM());
        invokerRole.addCompetence(invokeFunctionCompetence);
        
        return invokerRole;
    }
    
    private static FSM createInvokeFunctionCompetenceFSM() {
        // TODO Implement.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private static Role createExecuterRole() {
        Role executerRole = new Role("Executer_Role");
        return executerRole;
    }
    
    // ---------- Players ----------
    
    private static PlayerType createDemoPlayerType() {
        PlayerType demoPlayerType = new PlayerType("Demo_Player");
        
        // The 'Execute function' responsibility.
        Responsibility executeFunctionResponsibility = new Responsibility("InvokeFunction_Responsibility",
            Responsibility.ResponsibilityType.OneShotResponsibility, "Integer", "Integer");
        demoPlayerType.addResponsibility(executeFunctionResponsibility);
        
        return demoPlayerType;
    } 
}
