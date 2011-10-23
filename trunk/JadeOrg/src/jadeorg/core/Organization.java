package jadeorg.core;

import jade.wrapper.StaleProxyException;
import jadeorg.proto.State;
import jadeorg.proto.Protocol;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jadeorg.proto.enactprotocol.EnactProtocol;
import jadeorg.proto.enactprotocol.RefuseMessage;
import jadeorg.proto.organizationprotocol.OrganizationMessage;
import jadeorg.proto.enactprotocol.RequirementsMessage;
import jadeorg.proto.enactprotocol.RoleAIDMessage;
import jadeorg.proto.organizationprotocol.OrganizationProtocol;
import jadeorg.proto.ActiveState;
import jadeorg.proto.PassiveState;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Map;

/**
 * An organization agent.
 * @author Lukáš Kúdela (2011-10-16)
 * @version 0.1
 */
public abstract class Organization extends Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String ENACT_ACTION = "enact";
        
    private static final String DEACT_ACTION = "deact";
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The organization roles. */
    private Map<String, Class> roles = new Hashtable<String, Class>();
    
    /** The enacted roles. */
    private Map<String, Role> enactedRoles = new Hashtable<String, Role>();
    
    /** The requirements. */
    private Map<String, String[]> requirements = new Hashtable<String, String[]>();
    
    /** The DF agent description. */
    private DFAgentDescription agentDescription;
    
    /** The role manager. */
    private RoleManager roleManager;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void setup() {
        initialize();
        registerWithDF();
    }
    
    // ---------- PROTECTED ----------
    
    protected void addRole(Class roleClass) {
        String roleName = roleClass.getName();       
        roles.put(roleName, roleClass);
        
        registerRoleWithDF(roleName);
    }
    
    // ---------- PRIVATE ----------
    
    private void initialize() {
        initializeState();
        initializeBehaviour();
    }

    private void initializeState() {
    }

    private void initializeBehaviour() {
        addBehaviour(new OrganizationManagerBehaviour());
    }

    /** Registers this organization with the DF. */
    private void registerWithDF() {
        agentDescription = new OrganizationAgentDescription(getAID());
        
        try {
            DFService.register(this, agentDescription);
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
    }
    
    /** Register a role with the DF. */
    private void registerRoleWithDF(String roleName) {
        RoleServiceDescription serviceDescription = new RoleServiceDescription(roleName);
        agentDescription.addServices(serviceDescription);
        
        try {
            DFService.modify(this, agentDescription);
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
        
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
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * An organization manager behaviour.
     */
    private class OrganizationManagerBehaviour extends CyclicBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Receive the message.
            MessageTemplate organizationMessageTemplate = OrganizationProtocol.getInstance()
                .getTemplate(OrganizationMessage.class);
            ACLMessage message = myAgent.receive(organizationMessageTemplate);
            if (message != null) {
                // Parse the ACL message.
                OrganizationMessage organizationMessage = (OrganizationMessage)OrganizationProtocol
                    .getInstance().parse(OrganizationMessage.class, message);
                   
                switch (organizationMessage.getAction()) {
                    case ENACT_ACTION:
                        enactRole(organizationMessage.getRole(), organizationMessage.getPlayer());
                        break;                     
                    case DEACT_ACTION:
                        deactRole(organizationMessage.getRole(), organizationMessage.getPlayer());
                        break;                     
                    default:
                        sendNotUnderstood(organizationMessage.getPlayer());
                        break;
                }
            } else {
                block();
            }
        }
        
        // ---------- PRIVATE ----------
        
        /** Enacts a role.
         * @param roleName the name of the role
         * @param player the player
         */
        private void enactRole(String roleName, AID player) {
            if (roles.containsKey(roleName)) {
                addBehaviour(new EnactProtocolResponder(roleName, player));
            }
        }

        /** Deacts a role.
         * @param roleName the name of the role
         * @param player the player
         */
        private void deactRole(String roleName, AID player) {
            addBehaviour(new DeactProtocolResponder(roleName, player));
        }
                
        // </editor-fold>
    }
    
    /**
     * An 'Enact' protocol responder.
     */
    private class EnactProtocolResponder extends Protocol {
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private State receiveEnactRequest = new ReceiveEnactRequest();
        private State sendRequirementsInform = new SendRequirementsInform();
        private State sendFailure = new SendFailure();
        private State receiveRequirementsInform = new ReceiveRequirementsInform();
        private State sendRoleAID = new SendRoleAID();
        private State end = new End();
        
        private String roleName;
        
        private AID player;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public EnactProtocolResponder(String roleName, AID player) {
            // ----- Preconditions -----
            assert !roleName.isEmpty();
            assert player != null;
            // -------------------------
            
            this.roleName = roleName;
            this.player = player;
            
            registerStates();       
            registerTransitions();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Registers the states.
         */
        private void registerStates() {
            registerState(new ReceiveEnactRequest());
            registerState(new SendRequirementsInform());
            registerState(new SendFailure());
            registerState(new ReceiveRequirementsInform());
            registerState(new SendRoleAID());
            registerState(new End());
        }

        /**
         * Registers the transitions.
         */
        private void registerTransitions() {
            registerTransition(receiveEnactRequest, sendRequirementsInform, PassiveState.Event.SUCCESS);
            registerTransition(receiveEnactRequest, sendFailure, PassiveState.Event.FAILURE);
            
            registerDefaultTransition(sendRequirementsInform, receiveRequirementsInform);
            
            registerTransition(receiveRequirementsInform, sendRoleAID, PassiveState.Event.SUCCESS);
            registerTransition(receiveRequirementsInform, end, PassiveState.Event.FAILURE);   
            
            registerDefaultTransition(sendRoleAID, end);
            
            registerDefaultTransition(sendFailure, end);
        }
        
//        private void registerTransitions() {     
//            receiveEnactRequest.registerTransition(0, sendRequirementsInform);
//            receiveEnactRequest.registerTransition(1, sendFailure);
//            
//            sendRequirementsInform.registerDefaultTransition(receiveRequirementsInform);
//            
//            receiveRequirementsInform.registerTransition(2, receiveRequirementsInform);
//            receiveRequirementsInform.registerTransition(0, sendRoleAID);
//            receiveRequirementsInform.registerTransition(1, sendFailure);   
//            
//            sendRoleAID.registerDefaultTransition(end);
//            
//            sendFailure.registerDefaultTransition(end);
//        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Classes">
        
        /**
         * A state in which the 'Enact' message is received.
         */
        private class ReceiveEnactRequest extends PassiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "receive-enact-request";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
            }

            @Override
            public String getName() {
                return NAME;
            }
            
            // </editor-fold>
        }
        
        /**
         * A state in which the 'Requirements' message is send.
         */
        private class SendRequirementsInform extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "send-requirements-inform";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                // Create the 'Requirements' message.
                RequirementsMessage requirementsMessage = new RequirementsMessage()
                    .setPlayer(player)
                    .setRequirements(requirements.get(roleName));
                
                // Generate the ACL message.
                ACLMessage aclMessage = EnactProtocol.getInstance()
                    .generate(RequirementsMessage.class, requirementsMessage);
                
                // Send the message.
                myAgent.send(aclMessage);
            }

            @Override
            public String getName() {
                return NAME;
            }
            
            // </editor-fold>
        }
        
        /**
         * A state in which the 'Failure' message is send.
         */
        private class SendFailure extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Fields">
            
            private static final String NAME = "send-failure";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                // Create the 'Refuse' message.
                RefuseMessage refuseMesage = new RefuseMessage()
                    .setPlayer(player);
      
                // Generate the ACL message.
                ACLMessage aclMessage = EnactProtocol.getInstance()
                    .generate(RefuseMessage.class, refuseMesage);
                
                myAgent.send(aclMessage);
            }

            @Override
            public String getName() {
                return NAME;
            }
            
            // </editor-fold>
        }
        
        /**
         * A state in which the 'Requirements' message is received.
         */
        private class ReceiveRequirementsInform extends PassiveState {

            // <editor-fold defaultstate="collapsed" desc="Fields">
            
            private static final String NAME = "receive-requirements-inform";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                // Receive the message.
                MessageTemplate messageTemplate = MessageTemplate.MatchProtocol(EnactProtocol.getInstance().getName());
                ACLMessage message = myAgent.receive(messageTemplate);
                if (message != null) {
                    if (message.getPerformative() == ACLMessage.AGREE) {
                        
                    } else if (message.getPerformative() == ACLMessage.REFUSE) {
                    } else {
                        sendNotUnderstood(player);
                    }
                } else {
                    block();
                }
            }

            @Override
            public String getName() {
                return NAME;
            }
        
            // </editor-fold>
        }
        
        /** A state in which the 'Role AID' message is send. */
        private class SendRoleAID extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "send-role-aid";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                Role role = createRoleAgent(roleName, roleName);
                startRoleAgent(role);
                
                enactedRoles.put(roleName + ":" + player.getName(), role);
                roleManager.updatePlayer_Enact(roleName, player);
                
                // Create the 'RoleAID' message.
                RoleAIDMessage roleAIDMessage = new RoleAIDMessage()
                    .setPlayer(player)
                    .setRoleAID(role.getAID());
                
                // Generate the ACL message.
                ACLMessage aclMessage = EnactProtocol.getInstance()
                    .generate(RoleAIDMessage.class, roleAIDMessage);
                        
                // Send the ACL message.
                myAgent.send(aclMessage);
            }
            
            @Override
            public String getName() {
                return NAME;
            }
            
            // ---------- PRIVATE ----------

            /**
             * Create a role agent.
             * @param roleClassName the name of the role agent class.
             * @param roleName the name of the role agent instance.
             * @return the role agent.
             */
            private Role createRoleAgent(String roleClassName, String roleName) {
                Class roleClass = roles.get(roleClassName);
                Constructor roleConstructor = null;
                try {
                    roleConstructor = roleClass.getConstructor();
                } catch (NoSuchMethodException ex) {
                    ex.printStackTrace();
                } catch (SecurityException ex) {
                    ex.printStackTrace();
                }
                Role role = null;
                try {
                    role = (Role)roleConstructor.newInstance();
                } catch (InstantiationException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                }
                role.setRoleName(roleName);
                role.setOrganization((Organization)myAgent);
                return role;
            }
            
            private void startRoleAgent(Role role) {
                AgentController agentController = null;
                try {
                    agentController = getContainerController().acceptNewAgent(roleName, role);
                    agentController.start();
                } catch (StaleProxyException ex) {
                    ex.printStackTrace();
                }             
            }
            
            // </editor-fold>    
        }
        
        /** */
        public class End extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "end";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
            }

            @Override
            public String getName() {
                return NAME;
            }
            
            // </editor-fold>
        }
        
        // </editor-fold>
    }
    
    /**
     * A 'Deact' protocol responder.
     */
    private class DeactProtocolResponder extends FSMBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private String roleName;
        
        private AID player;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public DeactProtocolResponder(String roleName, AID player) {
            // ----- Preconditions -----
            assert !roleName.isEmpty();
            assert player != null;
            // -------------------------
            
            this.roleName = roleName;
            this.player = player;
        }
        
        // </editor-fold>
    }
    
    /**
     * An organization agent description.
     */
    private class OrganizationAgentDescription extends DFAgentDescription {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public OrganizationAgentDescription(AID organization) {
            super();
            setName(organization);
        }
        
        // </editor-fold>
    }
    
    /**
     * A role service description.
     */
    private class RoleServiceDescription extends ServiceDescription {
        
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
    
    // </editor-fold>
}