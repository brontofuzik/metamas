package example1metamodel;

import metamas.semanticmodel.MultiAgentSystem;
import metamas.semanticmodel.fsm.FSM;
import metamas.semanticmodel.organization.Organization;
import metamas.semanticmodel.organization.OrganizationClass;
import metamas.semanticmodel.organization.Power;
import metamas.semanticmodel.organization.RoleClass;
import metamas.semanticmodel.player.Player;
import metamas.semanticmodel.player.PlayerClass;
import metamas.semanticmodel.player.Requirement;
import metamas.semanticmodel.protocol.Message;
import metamas.semanticmodel.protocol.Message.MessageType;
import metamas.semanticmodel.protocol.Protocol;

/**
 * @author Lukáš Kúdela
 * @since 2012-01-10
 * @version %I% %G%
 */
public class Demo1Metamodel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MultiAgentSystem model = createModel();
        model.generate("C:\\DATA\\projects\\MAS\\MetaMAS");
    }
    
    // ----- PRIVATE -----
    
    private static MultiAgentSystem createModel() {
        MultiAgentSystem demo1Mas = new MultiAgentSystem("Demo1", "demo1");
        
        // ---------- Protocols ----------
        
        // The 'Calculate factorial' protocol.
        Protocol calculateFactorialProtocol = new Protocol("CalculateFactorialProtocol");
        demo1Mas.addProtocol(calculateFactorialProtocol);
        calculateFactorialProtocol.addMessage(new Message("RequestMessage", MessageType.TextMessage));
        calculateFactorialProtocol.addMessage(new Message("ReplyMessage", MessageType.TextMessage));
        
        // ---------- Organizations ----------
        
        // The 'Demo' organization class.
        OrganizationClass demoOrganizationClass = new OrganizationClass("Demo_Organization");
        demo1Mas.addOrganizationClass(demoOrganizationClass);
             
        // The 'demo' organization.
        Organization demoOrganization = demoOrganizationClass.createOrganization("demo_Organization");
        demo1Mas.addOrganization(demoOrganization);
        
        // ---------- Roles ----------
        
        // The 'Asker' role class.
        RoleClass askerRoleClass = new RoleClass("Asker_Role");
        demoOrganizationClass.addRole(askerRoleClass);
        FSM askerRoleFSM = new FSM();
        // TODO
        askerRoleClass.setFSM(askerRoleFSM);
        
        // The 'Calculate factorial' power.
        Power calculateFactorialPower = new Power("CalculateFactorial_Power",
            Power.PowerType.FSMPower, "Integer", "Integer");
        FSM calculateFactorialPowerFSM = new FSM();
        // TODO
        calculateFactorialPower.setFSM(calculateFactorialPowerFSM);
        
        // The 'Answerer' role.
        RoleClass answererRoleClass = new RoleClass("Answerer_Role");
        demoOrganizationClass.addRole(answererRoleClass);
        FSM answererRoleFSM = new FSM();
        // TODO
        answererRoleClass.setFSM(answererRoleFSM);
        
        // ---------- Players ----------
        
        // The 'Demo' player class.
        PlayerClass demoPlayerClass = new PlayerClass("Demo_Player");
        demo1Mas.addPlayerClass(demoPlayerClass);
        
        // The 'Calcualate factorial' requirement.
        Requirement calculateFactorialRequirement = new Requirement("CalculateFactorial_Requirement",
            Requirement.RequirementType.OneShotRequirement, "Integer", "Integer");
        demoPlayerClass.addRequirement(calculateFactorialRequirement);
        
        // The 'demo1' player.
        Player demo1Player = demoPlayerClass.createPlayer("demo1_Player");
        demo1Mas.addPlayer(demo1Player);
        
        // The 'demo2' player.
        Player demo2Player = demoPlayerClass.createPlayer("demo2_Player");
        demo1Mas.addPlayer(demo2Player);
        
        return demo1Mas;
    }
}
