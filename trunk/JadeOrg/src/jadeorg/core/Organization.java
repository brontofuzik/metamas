package jadeorg.core;

import jade.wrapper.StaleProxyException;
import jadeorg.proto.Protocol;
import jadeorg.proto.State;
import jadeorg.proto.Party;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jadeorg.lang.ACLMessageWrapper;
import jadeorg.lang.Message;
import jadeorg.proto.enactprotocol.EnactProtocol;
import jadeorg.proto.enactprotocol.RefuseMessage;
import jadeorg.proto.enactprotocol.RequirementsMessage;
import jadeorg.proto.enactprotocol.RoleAIDMessage;
import jadeorg.proto.organizationprotocol.OrganizationProtocol;
import jadeorg.proto.ActiveState;
import jadeorg.proto.PassiveState;
import jadeorg.proto.deactprotocol.DeactProtocol;
import jadeorg.proto.deactprotocol.FailureMessage;
import jadeorg.proto.organizationprotocol.DeactRequestMessage;
import jadeorg.proto.organizationprotocol.EnactRequestMessage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Map;

/**
 * An organization agent.
 * @author Lukáš Kúdela
 * @since 2011-10-16
 * @version %I% %G%
 */
public abstract class Organization extends Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String ENACT_ACTION = "enact";
        
    private static final String DEACT_ACTION = "deact";
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The organization roles. */
    private Map<String, Class> roles = new Hashtable<String, Class>();
    
    /** The requirements. */
    private Map<String, String[]> requirements = new Hashtable<String, String[]>();
    
    /** The DF agent description. */
    private DFAgentDescription agentDescription;
    
    /** The knowledge base. */
    private OrganizationKnowledgeBase knowledgeBase = new OrganizationKnowledgeBase();
    
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
        addBehaviour(new OrganizationManager());
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
    
    /** Enacts a role.
     * @param roleName the name of the role
     * @param player the player
     */
    // TODO Move the precondition assertions to the 'Enact' protocol responder behaviour.
    private void enactRole(String roleName, AID player) {
        // If the role is defined for this organization ...
        if (roles.containsKey(roleName)) {

            // ... and it is currently not enacted by any player ...
            if (!knowledgeBase.isRoleEnacted(roleName)) {

                // ... respond according to the 'Enact' protocol.
                addBehaviour(new EnactProtocolResponder(roleName, player));
            }          
        }
    }
    
    /** Deacts a role.
     * @param roleName the name of the role
     * @param player the player
     */
    // TODO Move the precondition assertions to the 'Deact' protocol responder beahviour.
    private void deactRole(String roleName, AID player) {
        // If the role is defined for this organization ...
        if (roles.containsKey(roleName)) {

            // ... and it is currently enacted by the player.
            if (knowledgeBase.isRoleEnactedByPlayer(roleName, player)) {

                // ... respond according to the the 'Deact' protocol.
                addBehaviour(new DeactProtocolResponder(roleName, player));
            }
        }      
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * An organization manager behaviour.
     */
    private class OrganizationManager extends Party {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected Protocol getProtocol() {
            return OrganizationProtocol.getInstance();
        }
                
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Classes">
        
        private class ReceiveOrganizationRequest extends PassiveState {
           
            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "receive-organization-request";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            ReceiveOrganizationRequest() {
                super(NAME);
            }
            
            // </editor-fold>
                 
            // <editor-fold defaultstate="collapsed" desc="Methods">

            @Override
            public void action() {
                // Receive the 'Organization' message.
                Message organizationMessage = receive(null, null);
                if (organizationMessage != null) {
                    if (organizationMessage instanceof EnactRequestMessage) {
                        EnactRequestMessage enactRequestMessage = (EnactRequestMessage)organizationMessage;
                        enactRole(enactRequestMessage.getRoleName(), enactRequestMessage.getSenderPlayer());
                    } else if (organizationMessage instanceof DeactRequestMessage) {
                        DeactRequestMessage deactRequestMessage = (DeactRequestMessage)organizationMessage;
                        deactRole(deactRequestMessage.getRoleName(), deactRequestMessage.getSenderPlayer());
                    } else {
                        // TODO Send not understood.
                        assert false;
                    }
                } else {
                    block();
                }
            }
            
            // </editor-fold>
        }
                
        // </editor-fold>
    }
    
    /**
     * An 'Enact' protocol responder.
     */
    private class EnactProtocolResponder extends Party {
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private String roleName;
        
        private AID playerAID;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public EnactProtocolResponder(String roleName, AID player) {
            // ----- Preconditions -----
            assert !roleName.isEmpty();
            assert player != null;
            // -------------------------
            
            this.roleName = roleName;
            this.playerAID = player;
            
            registerStatesAndTransitions();       
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected Protocol getProtocol() {
            return EnactProtocol.getInstance();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Registers the states and transitions.
         */
        private void registerStatesAndTransitions() {
            // ----- States -----
            State receiveEnactRequest = new ReceiveEnactRequest();
            State sendRequirementsInform = new SendRequirementsInform();
            State sendFailure = new SendRefuse();
            State receiveRequirementsInform = new ReceiveRequirementsInform();
            State sendRoleAID = new SendRoleAID();
            State end = new End();
            // ------------------
        
            // Register the states.
            registerFirstState(new ReceiveEnactRequest());
            registerState(new SendRequirementsInform());
            registerState(new SendRefuse());
            registerState(new ReceiveRequirementsInform());
            registerState(new SendRoleAID());
            registerLastState(new End());
            
            // Register the transitions (OLD).
            registerTransition(receiveEnactRequest, sendRequirementsInform, PassiveState.Event.SUCCESS);
            registerTransition(receiveEnactRequest, sendFailure, PassiveState.Event.FAILURE);
            
            registerDefaultTransition(sendRequirementsInform, receiveRequirementsInform);
            
            registerTransition(receiveRequirementsInform, sendRoleAID, PassiveState.Event.SUCCESS);
            registerTransition(receiveRequirementsInform, end, PassiveState.Event.FAILURE);   
            
            registerDefaultTransition(sendRoleAID, end);
            
            registerDefaultTransition(sendFailure, end);
            
//            // Register the transitions (NEW).
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
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Classes">
        
        /**
         * The state in which the 'Enact' message is received.
         */
        private class ReceiveEnactRequest extends PassiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "receive-enact-request";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            ReceiveEnactRequest() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
            }
            
            // </editor-fold>
        }
        
        /**
         * The state in which the 'Requirements' message is send.
         */
        private class SendRequirementsInform extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "send-requirements-inform";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            SendRequirementsInform() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                // Create the 'Requirements' message.
                RequirementsMessage requirementsMessage = new RequirementsMessage();
                requirementsMessage.setReceiverPlayer(playerAID);
                requirementsMessage.setRequirements(requirements.get(roleName));
                
                // Send the 'Requirements' message.
                send(RequirementsMessage.class, requirementsMessage);
            }
            
            // </editor-fold>
        }
        
        /**
         * The state in which the 'Refuse' message is send.
         */
        private class SendRefuse extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "send-failure";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            SendRefuse() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                // Create the 'Refuse' message.
                RefuseMessage refuseMessage = new RefuseMessage();
                refuseMessage.setReceiverPlayer(playerAID);
      
                // Set the 'Refuse' message.
                send(RefuseMessage.class, refuseMessage);
            }
            
            // </editor-fold>
        }
        
        /**
         * The state in which the 'Requirements' message is received.
         */
        private class ReceiveRequirementsInform extends PassiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "receive-requirements-inform";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            ReceiveRequirementsInform() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                // Receive the ACL message.
                ACLMessageWrapper aclMessageWrapper = (ACLMessageWrapper)receive(ACLMessageWrapper.class, playerAID);                                     
                if (aclMessageWrapper != null) {
                    if (aclMessageWrapper.getWrappedACLMessage().getPerformative() == ACLMessage.AGREE) {
                        // TODO
                    } else if (aclMessageWrapper.getWrappedACLMessage().getPerformative() == ACLMessage.REFUSE) {
                        // TODO
                    } else {
                        sendNotUnderstood(playerAID);
                    }
                } else {
                    block();
                }
            }
        
            // </editor-fold>
        }
        
        /**
         * The state in which the 'Role AID' message is send.
         */
        private class SendRoleAID extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "send-role-aid";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            SendRoleAID() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                Role role = createRoleAgent(roleName, roleName);
                startRoleAgent(role);
                
                knowledgeBase.updateRoleIsEnacted(role, playerAID);
                
                // Create the 'RoleAID' message.
                RoleAIDMessage roleAIDMessage = new RoleAIDMessage();
                roleAIDMessage.setReceiverPlayer(playerAID);
                roleAIDMessage.setRoleAID(role.getAID());
                
                // Send the 'RoleAID' message.
                send(RoleAIDMessage.class, roleAIDMessage);
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
        
        /**
         * The ending state.
         */
        public class End extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "end";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            End() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
            }
            
            // </editor-fold>
        }
        
        // </editor-fold>
    }
    
    /**
     * A 'Deact' protocol responder.
     */
    private class DeactProtocolResponder extends Party {
        
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
            
            registerStatesAndTransitions();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
       @Override
        protected Protocol getProtocol() {
            return DeactProtocol.getInstance();
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        /**
         * Registers the transitions and transitions.
         */
        private void registerStatesAndTransitions() {
            State receiveDeactRequest = new ReceiveDeactRequest();
            State sendDeactInformation = new SendDeactInformation();
            State sendDeactFailure = new SendDeactFailure();
            State end = new End();
        
            // Register the states.
            registerFirstState(receiveDeactRequest);
            registerState(sendDeactInformation);
            registerState(sendDeactFailure);
            registerLastState(end);
            
            // Register the transisions (OLD).
            registerTransition(receiveDeactRequest, sendDeactInformation, PassiveState.Event.SUCCESS);
            registerTransition(receiveDeactRequest, sendDeactFailure, PassiveState.Event.FAILURE);
            
            registerDefaultTransition(sendDeactInformation, end);
            
            registerDefaultTransition(sendDeactFailure, end);
            
//            // Register the transisions (NEW).
//            receiveDeactRequest.registerTransition(PassiveState.Event.SUCCESS, sendDeactInformation);
//            receiveDeactRequest.registerTransition(PassiveState.Event.FAILURE, sendDeactFailure);
//            
//            sendDeactInformation.registerDefaultTransition(end);
//            
//            sendDeactFailure.registerDefaultTransition(end);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Classes">
        
        /**
         * The 'Receive deact request' active state.
         */
        class ReceiveDeactRequest extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "receive-deact-request";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            ReceiveDeactRequest() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
            // </editor-fold>
        }
        
        /**
         * The 'Send deact information' active state.
         */
        class SendDeactInformation extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "send-deact-information";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            SendDeactInformation() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
            // </editor-fold>
        }
        
        /**
         * The 'Send deact failure' active state.
         */
        class SendDeactFailure extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "send-deact-failure";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            SendDeactFailure() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                // Create the 'Failure' message.
                FailureMessage failureMessage = new FailureMessage();
                failureMessage.setReceiverPlayer(player);
                
                send(FailureMessage.class, failureMessage);
            }
            
            // </editor-fold>
        }
        
        /**
         * The 'End' active state.
         */
        class End extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "end";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            End() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
            }
            
            // </editor-fold>
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