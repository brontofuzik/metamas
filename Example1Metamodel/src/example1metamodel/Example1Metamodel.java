package example1metamodel;

import thespian.semanticmodel.MultiAgentSystem;
import thespian.semanticmodel.fsm.FSM;
import thespian.semanticmodel.organization.Organization;
import thespian.semanticmodel.organization.OrganizationClass;
import thespian.semanticmodel.organization.Competence;
import thespian.semanticmodel.organization.RoleClass;
import thespian.semanticmodel.player.Player;
import thespian.semanticmodel.player.PlayerClass;
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
        MultiAgentSystem example1Mas = new MultiAgentSystem("Example1", "example1");
        
        // ---------- Protocols ----------
        
        // The 'Invoke function' protocol.
        Protocol invokeFunctionProtocol = new Protocol("InvokeFunctionProtocol");
        example1Mas.addProtocol(invokeFunctionProtocol);
        invokeFunctionProtocol.addMessage(new Message("RequestMessage", MessageType.TextMessage));
        invokeFunctionProtocol.addMessage(new Message("ReplyMessage", MessageType.TextMessage));
        
        // The 'Invoke function' initiator party.
        Party invokeFunctionProtocolInitiatorParty = new Party("InvokeFunction_InitiatorParty");
        invokeFunctionProtocolInitiatorParty.setFSM(createInvokeFunctionInitiatorFSM());
        invokeFunctionProtocol.setInitiatorParty(invokeFunctionProtocolInitiatorParty);
        
        // The 'Invoke function' responder party.
        Party invokeFunctionResponderParty = new Party("InvokeFunctionResponder");
        invokeFunctionResponderParty.setFSM(createInvokeFunctionResponderFMS());
        invokeFunctionProtocol.setResponderParty(invokeFunctionResponderParty);
      
        // ---------- Organizations ----------
        
        // The 'Invoke function' organization class.
        OrganizationClass invokeFunctionOrganizationClass = new OrganizationClass("Demo_Organization");
        example1Mas.addOrganizationClass(invokeFunctionOrganizationClass);
             
        // The 'invoke function' organization.
        Organization invokeFunctionOrganization = invokeFunctionOrganizationClass.createOrganization("demo_Organization");
        example1Mas.addOrganization(invokeFunctionOrganization);
        
        // ---------- Roles ----------
        
        // The 'Invoker' role class.
        RoleClass invokerRoleClass = new RoleClass("Invoker_Role");
        invokeFunctionOrganizationClass.addRole(invokerRoleClass);
        
        // The 'Invoke function' competence.
        Competence invokeFunctionCompetence = new Competence("InvokeFunction_Competence",
            Competence.CompetenceType.FSMCompetence, "Integer", "Integer");
        invokeFunctionCompetence.setFSM(createInvokeFunctionCompetenceFSM());
        invokerRoleClass.addCompetence(invokeFunctionCompetence);
        
        // The 'Executer' role.
        RoleClass executerRoleClass = new RoleClass("Executer_Role");
        invokeFunctionOrganizationClass.addRole(executerRoleClass);
        
        // ---------- Players ----------
        
        // The 'Demo' player class.
        PlayerClass demoPlayerClass = new PlayerClass("Demo_Player");
        example1Mas.addPlayerClass(demoPlayerClass);
        
        // The 'Execute function' responsibility.
        Responsibility executeFunctionResponsibility = new Responsibility("InvokeFunction_Responsibility",
            Responsibility.ResponsibilityType.OneShotResponsibility, "Integer", "Integer");
        demoPlayerClass.addResponsibility(executeFunctionResponsibility);
        
        // The 'demo1' player.
        Player demo1Player = demoPlayerClass.createPlayer("demo1_Player");
        example1Mas.addPlayer(demo1Player);
        
        // The 'demo2' player.
        Player demo2Player = demoPlayerClass.createPlayer("demo2_Player");
        example1Mas.addPlayer(demo2Player);
        
        return example1Mas;
    }

    private static FSM createInvokeFunctionCompetenceFSM() {
        // TODO Implement.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private static FSM createInvokeFunctionInitiatorFSM() {
         // TODO Implement.
        throw new UnsupportedOperationException("Not yet implemented");       
    }
    
    private static FSM createInvokeFunctionResponderFMS() {
        // TODO Implement.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
