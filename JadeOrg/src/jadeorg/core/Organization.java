package jadeorg.core;

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
import jadeorg.lang.OrganizationMessage;
import jadeorg.lang.RequirementsMessage;
import jadeorg.proto.ActiveState;
import jadeorg.proto.PassiveState;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * An organization agent.
 * @author Lukáš Kúdela (2011-10-16)
 * @version 0.1
 */
public abstract class Organization extends Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
    private static final String ORGANIZATION_PROTOCOL = "organiation-protocol";
    
    private static final String ENACT_PROTOCOL = "enact-protocol";
    
    private static final String ENACT_ACTION = "enact";
    
    private static final String DEACT_PROTOCOL = "deact-protocol";
        
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
    
    /** The player manager. */
    private PlayerManager playerManager;
    
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
             
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private MessageTemplate organizationMessageTemplate = MessageTemplate.and(
            MessageTemplate.MatchProtocol(ORGANIZATION_PROTOCOL),
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Receive the message.
            ACLMessage message = myAgent.receive(organizationMessageTemplate);
            if (message != null) {
                // Parse the message.
                OrganizationMessage messageParser = new OrganizationMessage(message);
                switch (messageParser.getAction()) {
                    case ENACT_ACTION:
                        enactRole(messageParser.getRole(), messageParser.getPlayer());
                        break;                     
                    case DEACT_ACTION:
                        deactRole(messageParser.getRole(), messageParser.getPlayer());
                        break;                     
                    default:
                        sendNotUnderstood(message.getSender());
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
            registerTransition(receiveEnactRequest, sendRequirementsInform, 0);
            registerTransition(receiveEnactRequest, sendFailure, 1);
            
            registerDefaultTransition(sendRequirementsInform, receiveRequirementsInform);
            
            registerTransition(receiveRequirementsInform, receiveRequirementsInform, 2);
            registerTransition(receiveRequirementsInform, sendRoleAID, 0);
            registerTransition(receiveRequirementsInform, end, 1);   
            
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

            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
            }

            @Override
            public String getName() {
                return "receive-enact-request";
            }
            
            // </editor-fold>
        }
        
        /**
         * A state in which the 'Requirements' message is send.
         */
        private class SendRequirementsInform extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {              
                // Generate the message.
                ACLMessage message = new RequirementsMessage()
                    .setPlayer(player)
                    .setRequirements(requirements.get(roleName))
                .getMessage();
                
                // Send the message.
                myAgent.send(message);
            }

            @Override
            public String getName() {
                return "send-requirements-inform";
            }
            
            // </editor-fold>
        }
        
        /**
         * A state in which the 'Failure' message is send.
         */
        private class SendFailure extends ActiveState {

            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getName() {
                return "send-failure";
            }
        }
        
        /**
         * A state in which the 'Requirements' message is received.
         */
        private class ReceiveRequirementsInform extends PassiveState {

            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getName() {
                return "receive-requirements-inform";
            }
        
        }
        
        /** A state in which the 'Role AID' message is send. */
        private class SendRoleAID extends ActiveState {

            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getName() {
                return "send-role-aid";
            }
            
        }
        
        /** */
        public class End extends ActiveState {

            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getName() {
                return "end";
            }
            
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