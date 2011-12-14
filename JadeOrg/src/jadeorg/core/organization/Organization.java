package jadeorg.core.organization;

import jadeorg.core.organization.kb.OrganizationKnowledgeBase;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.Logger;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRequestMessage;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRequestMessage;
import jadeorg.util.ManagerBehaviour;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;

/**
 * An organization agent.
 * @author Lukáš Kúdela
 * @since 2011-10-16
 * @version %I% %G%
 */
public abstract class Organization extends Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The organization roles. */
    public Map<String, Class> roles = new Hashtable<String, Class>();
    
    /** The requirements. */
    public Map<String, String[]> requirements = new Hashtable<String, String[]>();
    
    /** The DF agent description. */
    private DFAgentDescription agentDescription;
    
    /** The knowledge base. */
    public OrganizationKnowledgeBase knowledgeBase = new OrganizationKnowledgeBase();

    private Logger logger;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Organization() {
        logger = jade.util.Logger.getMyLogger(this.getClass().getName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        addBehaviours();
        
        // TAG YellowPages
        //registerWithYellowPages();
    }

    /**
     * Adds a role.
     * @param roleClass the role class
     * @param requirements the role requirements
     */
    protected void addRole(Class roleClass, String[] requirements) {
        // ----- Preconditions -----
        if (roleClass == null) {
            throw new IllegalArgumentException("roleClass");
        }
        if (requirements == null) {
            throw new IllegalArgumentException("requirements");
        }
        // -------------------------
        
        String roleName = roleClass.getSimpleName();
        roles.put(roleName, roleClass);
        this.requirements.put(roleName, requirements);
        
        // TAG YellowPages
        //registerRoleWithDF(roleName);
        
        logInfo(String.format("Role (%1$s) added.", roleName));
    }
    
    /**
     * Adds a role.
     * @param roleClass the role class 
     */
    protected void addRole(Class roleClass) {        
        addRole(roleClass, new String[0]);
    }
    
    /**
     * Logs a message.
     * @param level the level
     * @param message the message
     */
    public void log(Level level, String message) {
        if (logger.isLoggable(level)) {
            logger.log(level, String.format("%1$s: %2$s", getLocalName(), message));
        }
    }
    
    /**
     * Logs an INFO-level message.
     * @param message the INFO-level message
     */
    public void logInfo(String message) {
        log(Level.INFO, message);
    }

    // ---------- PRIVATE ----------
    
    private void addBehaviours() {
        addBehaviour(new OrganizationManager());
        logInfo("Behaviours addded.");
    }

    // TAG YellowPages
    /**
     * Registers this organization with the Yellow pages.
     */
    private void registerWithYellowPages() {
        agentDescription = createOrganizationDescription();

        try {
            DFService.register(this, agentDescription);
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
        logInfo("Registered with the Yellow Pages.");
    }

    // TAG YellowPages
    private OrganizationAgentDescription createOrganizationDescription() {
        return new OrganizationAgentDescription(getAID());
    }

    // TAG YellowPages
    /**
     * Register a role with the Yellow pages.
     */
    private void registerRoleWithYellowPages(String roleName) {
        agentDescription.addServices(createRoleDescription(roleName));

        try {
            DFService.modify(this, agentDescription);
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
        logInfo(String.format("Role (%1$) registered with the Yellow Pages.", roleName));
    }

    // TAG YellowPages
    private RoleServiceDescription createRoleDescription(String roleName) {
        return new RoleServiceDescription(roleName);
    }

    /**
     * Sends a NOT_UNDERSTOOD message.
     * @param receiver the receiver.
     */
    private void sendNotUnderstood(AID receiver) {
        ACLMessage message = new ACLMessage(ACLMessage.NOT_UNDERSTOOD);
        message.addReceiver(receiver);
        send(message);
    }

    /**
     * Enacts a role.
     * @param player the player
     */
    private void enactRoleResponder(AID player) {
        addBehaviour(new Organization_EnactRoleResponder_New(player));
    }

    /**
     * Deacts a role.
     * @param player the player
     */
    // TODO Move the precondition assertions to the 'Deact' protocol responder beahviour.
    private void deactRoleResponder(AID player) {

    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * An organization manager behaviour.
     */
    private class OrganizationManager extends ManagerBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        OrganizationManager() {
            addHandler(new EnactRoleHandler());
            addHandler(new DeactRoleHandler());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Classes">
        
        private class EnactRoleHandler extends HandlerBehaviour {

            @Override
            public void action() {
                MessageTemplate enactRequestTemplate = EnactRoleProtocol.getInstance()
                    .getTemplate(EnactRequestMessage.class);
                ACLMessage enactRequestMessage = receive(enactRequestTemplate);
                if (enactRequestMessage != null) {
                    putBack(enactRequestMessage);
                    enactRoleResponder(enactRequestMessage.getSender());
                }
            }
        }
        
        private class DeactRoleHandler extends HandlerBehaviour {

            @Override
            public void action() {
                MessageTemplate deactRequestTemplate = DeactRoleProtocol.getInstance()
                    .getTemplate(DeactRequestMessage.class);
                ACLMessage deactRequestMessage = receive(deactRequestTemplate);
                if (deactRequestMessage != null) {
                    putBack(deactRequestMessage);
                    deactRoleResponder(deactRequestMessage.getSender());
                }
            }
        }
       
        // </editor-fold>
    }
    
    // TAG YellowPages
    /**
     * An organization agent description.
     */
    private static class OrganizationAgentDescription extends DFAgentDescription {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public OrganizationAgentDescription(AID organization) {
            super();
            setName(organization);
        }
        
        // </editor-fold>
    }

    // TAG YellowPages
    /**
     * A role service description.
     */
    private static class RoleServiceDescription extends ServiceDescription {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String ROLE_SERVICE_TYPE = "role";

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public RoleServiceDescription(String roleName) {
            super();
            setType(ROLE_SERVICE_TYPE);
            setName(roleName);
        }
        
        // </editor-fold>
    }
}