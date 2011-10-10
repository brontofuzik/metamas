package demoorg;

import metamas.semanticmodel.Agent;
import metamas.semanticmodel.AgentClass;
import metamas.semanticmodel.Group;
import metamas.semanticmodel.GroupClass;
import metamas.semanticmodel.OrganizationClass;
import metamas.semanticmodel.Role;
import metamas.semanticmodel.interactionprotocol.ActiveState;
import metamas.semanticmodel.interactionprotocol.InteractionProtocol;
import metamas.semanticmodel.interactionprotocol.Message;
import metamas.semanticmodel.interactionprotocol.Party;
import metamas.semanticmodel.interactionprotocol.PassiveState;
import metamas.semanticmodel.interactionprotocol.State;

/**
 *
 * @author Lukáš Kúdela
 */
public class DemoOrg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //
        // Create the organization class.
        //
        
        OrganizationClass worldOrganizationClass = new OrganizationClass("World [organization class]");
        
        //
        // Create the group classes.
        //
        
        GroupClass terroristOrganizationGroupClass = CreateTerroristOrganizationGroupClass();
        GroupClass weaponsCartelGroupClass = CreateWeaponsCartelGroupClass();
        GroupClass westernSocietyGroupClass = CreateWesternSocietyGroupClass();
        
        //
        // Create the groups.
        //
        
        Group terroristOranizationGroup = terroristOrganizationGroupClass.createGroup("Terrorist organization [group]");
        Group weaponsCartelGroup = weaponsCartelGroupClass.createGroup("Weapons cartel [group]");
        Group westerSocietyGroup = westernSocietyGroupClass.createGroup("Wester society [group]");
        
        //
        // Create the agent classes.
        //
        
        AgentClass humanAgentClass = new AgentClass("Human [agent class]");
        
        //
        // Create the agents.
        //
        
        Agent agentA = humanAgentClass.createAgent("Agent A [agent]");
        Agent agentB = humanAgentClass.createAgent("Agent B [agent]");
        Agent agentC = humanAgentClass.createAgent("Agent C [agent]");
        Agent agentD = humanAgentClass.createAgent("Agent D [agent]");
        Agent agentE = humanAgentClass.createAgent("Agent E [agent]");
        
        //
        // Assign the agents to the groups.
        //
        
        // Agent A
        terroristOranizationGroup.addAgent("Operative [position]", agentA);
        weaponsCartelGroup.addAgent("Customer [position]", agentA);
        westerSocietyGroup.addAgent("Student [position]", agentA);
        
        // Agent B
        weaponsCartelGroup.addAgent("Negotiator [position]", agentB);
        
        // Agent C
        weaponsCartelGroup.addAgent("Supplier [position]", agentC);
        
        // Agent D
        westerSocietyGroup.addAgent("Citizen [position]", agentD);
        
        // Agent E
        terroristOranizationGroup.addAgent("Ringleader [position]", agentE);
    }

    /**
     * Creates the "Terrorist organization" group class.
     * @return The "Terrorist organization" group class.
     */
    private static GroupClass CreateTerroristOrganizationGroupClass() {
        GroupClass terroristOrganizationGroupClass = new GroupClass("Terrorist organization [group class]");
        
        //
        // Create the roles.
        //
        
        Role operativeRole = new Role("Operative [role]");
        Role ringleaderRole = new Role("Ringleader [role]");
        
        //
        // Create the positions.
        //
            
        terroristOrganizationGroupClass.addMultiPosition("Operative [position]", operativeRole);
        terroristOrganizationGroupClass.addSinglePosition("Ringleader [position]", ringleaderRole);
        
        return terroristOrganizationGroupClass;
    }
    
    /**
     * Creates the "Weapons cartel" group class.
     * @return The "Weapons cartel" group class.
     */
    private static GroupClass CreateWeaponsCartelGroupClass() {
        GroupClass weaponsCartelGroupClass = new GroupClass("Weapons cartel [group class]");
        
        //
        // Create the roles.
        //
       
        Role customerRole = new Role("Customer [role]");   
        Role supplierRole = new Role("Supplier [role]");        
        Role negotiatorRole = new Role("Negotiator [role]");
        
        //
        // Create the positions.
        //
        
        weaponsCartelGroupClass.addMultiPosition("Customer [position]", customerRole);
        weaponsCartelGroupClass.addMultiPosition("Supplier [position]", supplierRole);
        weaponsCartelGroupClass.addSinglePosition("Negotiator [position]", negotiatorRole);
        
        //
        // Create the interaction protocols.
        //
        
        InteractionProtocol weaponsProcurementNegotiation =
            CreateWeaponsProcurementNegotiationIP(weaponsCartelGroupClass);
        weaponsCartelGroupClass.addInteractionProtocol(weaponsProcurementNegotiation);
        
        return weaponsCartelGroupClass;
    }
    
    /**
     * Creates the "Weapons procurement negotiation" interaction protocol.
     * @return The "Weapons procurement negotiation" interaction protocol.
     */
    private static InteractionProtocol CreateWeaponsProcurementNegotiationIP(GroupClass weaponsCartelGroupClass) {
        InteractionProtocol weaponsProcurementNegotiation = new InteractionProtocol("Weapons procurement ngotiation [IP]");
        
        //
        // Create parties.
        //
        
        Party customer = new Party("Customer [party]", weaponsCartelGroupClass.getRole("Customer [role]"));
        weaponsProcurementNegotiation.addParty(customer);
        
        Party negotiator = new Party("Negotiator [party]", weaponsCartelGroupClass.getRole("Negotiator [role]"));
        weaponsProcurementNegotiation.addParty(negotiator);
        
        Party supplier = new Party("Supplier [party]", weaponsCartelGroupClass.getRole("Supplier [role]"));
        weaponsProcurementNegotiation.addParty(supplier);
        
        //
        // Create messages.
        //
        
        Message requestGuns = new Message("Request guns [message]");
        Message acceptRequest = new Message("Request accepted [message]");
        Message denyRequest = new Message("Request denied [message]");
        Message payMoney = new Message("Pay money [message]");
        Message orderGuns = new Message("Order guns [message]");
        Message deliverGuns = new Message("Deliver guns [message]");
        
        //
        // Create the Customer party states and transitions.
        //
        
        // States
        State start = new ActiveState("Start [active state]");
        State waitingForReply = new PassiveState("Waiting for reply [passive state]");
        State requestAccepted = new ActiveState("Request accepted [active state]");
        State waitingForGuns = new PassiveState("Waiting for guns [passive state]");
        State finish = new ActiveState("Finish [active state]");

        // Transitions
        start.addTransition(requestGuns, waitingForReply);
        waitingForReply.addTransition(acceptRequest, requestAccepted);
        waitingForReply.addTransition(denyRequest, finish);
        requestAccepted.addTransition(payMoney, waitingForGuns);
        waitingForGuns.addTransition(deliverGuns, finish);
        
        // Start state
        customer.setStartState(start);
        
        //
        // Create the Negotiator party states and transitions.
        //
        
        // States
        start = new PassiveState("Start [passive state]");
        State gunsRequested = new ActiveState("Guns requested [active state]");
        State waitingForMoney = new PassiveState("Waiting for moeny [passive state]");
        State moneyReceived = new ActiveState("Money received [active state]");
        finish = new ActiveState("Finish [active state]");
        
        // Transitions
        start.addTransition(requestGuns, gunsRequested);
        gunsRequested.addTransition(acceptRequest, waitingForMoney);
        gunsRequested.addTransition(denyRequest, finish);
        waitingForMoney.addTransition(payMoney, moneyReceived);
        moneyReceived.addTransition(orderGuns, finish);
                
        // Start state
        negotiator.setStartState(start);
        
        //
        // Create the Supplier party states and transitions.
        //
        
        // States
        start = new PassiveState("Start [passive state]");
        State gunsOrdered = new ActiveState("Guns ordered [active state]");
        finish = new ActiveState("Finish [active state]");
        
        // Transitions
        start.addTransition(orderGuns, gunsOrdered);
        gunsOrdered.addTransition(deliverGuns, finish);
        
        // Start state
        supplier.setStartState(start);
        
        return weaponsProcurementNegotiation;
    }
    
    /**
     * Creates "the Western society" group class.
     * @return "The western society" group class.
     */
    private static GroupClass CreateWesternSocietyGroupClass() {
        GroupClass westernSocietyGroupClass = new GroupClass("Western society [group class]");
        
        //
        // Create the roles.
        //
        
        Role citizenRole = new Role("Citizen [role]");
        Role studentRole = new Role("Student [role]");
        
        //
        // Create the positions.
        //
        
        westernSocietyGroupClass.addMultiPosition("Citizen [position]", citizenRole);
        westernSocietyGroupClass.addMultiPosition("Student [position]", studentRole);
        
        return westernSocietyGroupClass;
    }
}
