package jadeorg.core.organization;

import jadeorg.core.organization.kb.OrganizationKnowledgeBase;
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
import jade.lang.acl.MessageTemplate;
import jade.util.Logger;
import jade.wrapper.AgentController;
import jadeorg.lang.ACLMessageWrapper;
import jadeorg.proto.organizationprotocol.enactprotocol.EnactRoleProtocol;
import jadeorg.proto.organizationprotocol.enactprotocol.RequirementsInformMessage;
import jadeorg.proto.organizationprotocol.enactprotocol.RoleAIDMessage;
import jadeorg.proto.ActiveState;
import jadeorg.proto.PassiveState;
import jadeorg.proto.organizationprotocol.deactprotocol.DeactRoleProtocol;
import jadeorg.proto.organizationprotocol.deactprotocol.FailureMessage;
import jadeorg.proto.organizationprotocol.deactprotocol.DeactRequestMessage;
import jadeorg.proto.organizationprotocol.enactprotocol.EnactRequestMessage;
import jadeorg.util.ManagerBehaviour;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
    private Map<String, Class> roles = new Hashtable<String, Class>();
    
    /** The requirements. */
    private Map<String, String[]> requirements = new Hashtable<String, String[]>();
    
    /** The DF agent description. */
    private DFAgentDescription agentDescription;
    
    /** The knowledge base. */
    private OrganizationKnowledgeBase knowledgeBase = new OrganizationKnowledgeBase();

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
    protected void log(Level level, String message) {
        if (logger.isLoggable(level)) {
            logger.log(level, String.format("%1$s: %2$s", getLocalName(), message));
        }
    }
    
    /**
     * Logs an INFO-level message.
     * @param message the INFO-level message
     */
    protected void logInfo(String message) {
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
        addBehaviour(new EnactProtocolResponder(player));
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

    /**
     * An 'Enact' protocol responder.
     */
    private class EnactProtocolResponder extends Party {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "enact-protocol-responder";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private AID playerAID;
        
        private String roleName;

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public EnactProtocolResponder(AID player) {
            super(NAME);
            // ----- Preconditions -----
            assert player != null;
            // -------------------------

            this.playerAID = player;

            registerStatesAndTransitions();
        }

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected Protocol getProtocol() {
            return EnactRoleProtocol.getInstance();
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
            State sendFailure = new SendFailure();
            State receiveRequirementsReply = new ReceiveRequirementsReply();
            State sendRoleAID = new SendRoleAID();
            State successEnd = new SuccessEnd();
            State failureEnd = new FailureEnd();
            // ------------------

            // Register the states.
            registerFirstState(receiveEnactRequest);
            registerState(sendRequirementsInform);
            registerState(sendFailure);
            registerState(receiveRequirementsReply);
            registerState(sendRoleAID);
            registerLastState(successEnd);

            // Register the transitions (OLD).
            registerTransition(receiveEnactRequest, sendRequirementsInform, PassiveState.Event.SUCCESS);
            registerTransition(receiveEnactRequest, sendFailure, PassiveState.Event.FAILURE);

            registerDefaultTransition(sendRequirementsInform, receiveRequirementsReply);

            registerTransition(receiveRequirementsReply, sendRoleAID, PassiveState.Event.SUCCESS);
            registerTransition(receiveRequirementsReply, failureEnd, PassiveState.Event.FAILURE);

            registerDefaultTransition(sendRoleAID, successEnd);

            registerDefaultTransition(sendFailure, failureEnd);

//            // Register the transitions (NEW).
//            receiveEnactRequest.registerTransition(0, sendRequirementsInform);
//            receiveEnactRequest.registerTransition(1, sendFailure);
//            
//            sendRequirementsInform.registerDefaultTransition(receiveRequirementsInform);
//            
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
                logInfo("Receiving enact request.");
                
                EnactRequestMessage enactRequestMessage = (EnactRequestMessage)
                    receive(EnactRequestMessage.class, playerAID);
                
                if (enactRequestMessage != null) {
                    logInfo("Enact request received.");
                    roleName = enactRequestMessage.getRoleName();
  
                    if (roles.containsKey(roleName)) {
                        // The role is defined for this organizaiton.
                        if (!knowledgeBase.isRoleEnacted(roleName)) {
                            // The role is not yet enacted.
                            setExitValue(Event.SUCCESS);
                        } else {
                            // The role is already enacted.
                            setExitValue(Event.FAILURE);
                        }
                    } else {
                        // The role is not defined for this organization.
                        setExitValue(Event.FAILURE);
                    }
                }  else {
                    loop();
                }
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
                logInfo("Sending requirements inform.");
                
                // Create the 'Requirements' message.
                RequirementsInformMessage requirementsInformMessage = new RequirementsInformMessage();
                requirementsInformMessage.setReceiverPlayer(playerAID);

                requirementsInformMessage.setRequirements(requirements.get(roleName));

                // Send the 'Requirements' message.
                send(RequirementsInformMessage.class, requirementsInformMessage);
                
                logInfo("Requirements inform sent.");
            }
            
            // </editor-fold>
        }

        /**
         * The state in which the 'Refuse' message is send.
         */
        private class SendFailure extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "send-failure";

            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            SendFailure() {
                super(NAME);
            }

            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                // Create the 'Failure' JadeOrg message.
                ACLMessageWrapper failureMessage = EnactRoleProtocol.getInstance()
                    .getACLMessageWrapper(ACLMessage.FAILURE);
                failureMessage.addReceiver(playerAID);

                send(ACLMessageWrapper.class, failureMessage);
            }
            
            // </editor-fold>
        }

        /**
         * The state in which the 'Requirements' message is received.
         */
        private class ReceiveRequirementsReply extends PassiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "receive-requirements-reply";

            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            ReceiveRequirementsReply() {
                super(NAME);
            }

            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                logInfo("Receiving requirements reply.");
              
                ACLMessageWrapper requirementsReplyMessage = (ACLMessageWrapper)
                    receive(ACLMessageWrapper.class, playerAID);
                if (requirementsReplyMessage != null) {
                    logInfo("Requirements reply received.");
                    
                    if (requirementsReplyMessage.getWrappedACLMessage().getPerformative() == ACLMessage.AGREE) {
                        setExitValue(Event.SUCCESS);
                    } else if (requirementsReplyMessage.getWrappedACLMessage().getPerformative() == ACLMessage.REFUSE) {
                        setExitValue(Event.FAILURE);
                    }
                } else {
                    loop();
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
                logInfo("Creating role agent.");
               
                Role role = createRoleAgent(roleName, roleName);
                startRoleAgent(role);

                knowledgeBase.updateRoleIsEnacted(role, playerAID);
                
                logInfo("Role agent created.");
                
                // TODO Consider moving the following section to a separate state.
                
                logInfo("Sending role AID.");
                
                // Create the 'RoleAID' message.
                RoleAIDMessage roleAIDMessage = new RoleAIDMessage();
                roleAIDMessage.setReceiverPlayer(playerAID);
                roleAIDMessage.setRoleAID(role.getAID());

                // Send the 'RoleAID' message.
                send(RoleAIDMessage.class, roleAIDMessage);
                logInfo("Role AID sent.");
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
                //System.out.println("ROLE CLASS: " + roleClass);
                Class organizationClass = myAgent.getClass();
                //System.out.println("ORGANIZATION CLASS: " + organizationClass);
                
                Constructor roleConstructor = null;
                try {
                    roleConstructor = roleClass.getConstructor(organizationClass);
                } catch (NoSuchMethodException ex) {
                    ex.printStackTrace();
                } catch (SecurityException ex) {
                    ex.printStackTrace();
                }
                //System.out.println("CTOR: " + roleConstructor.getParameterTypes());
                Role role = null;
                try {
                    role = (Role)roleConstructor.newInstance(myAgent);
                } catch (InstantiationException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                }
                //System.out.println("ROLE: " + role);
                role.setRoleName(roleName);
                role.setMyOrganization((Organization)myAgent);
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
         * The 'Success end' state.
         */
        public class SuccessEnd extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "success-end";

            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            SuccessEnd() {
                super(NAME);
            }

            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                logInfo("Enact role responder party succeeded.");
            }
            
            // </editor-fold>           
        }
        
        /**
         * The 'Failure end' state.
         */
        public class FailureEnd extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "failure-end";

            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            FailureEnd() {
                super(NAME);
            }

            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                logInfo("Enact role responder party failed.");
            }
            
            // </editor-fold>        
        }
        
        // </editor-fold>
    }

    /**
     * A 'Deact' protocol responder.
     */
    private class DeactProtocolResponder extends Party {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "deact-protocol-responder";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private String roleName;
        
        private AID player;

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public DeactProtocolResponder(String roleName, AID player) {
            super(NAME);
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
            return DeactRoleProtocol.getInstance();
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
        class ReceiveDeactRequest extends PassiveState {

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
                logInfo("Receiving deact request.");
                
                DeactRequestMessage deactRequestMessage = (DeactRequestMessage)
                    receive(DeactRequestMessage.class, null);
                if (deactRequestMessage != null) {
                    roleName = deactRequestMessage.getRoleName();
  
                    if (roles.containsKey(roleName)) {
                        // The role is defined for this organization.
                        if (knowledgeBase.isRoleEnactedByPlayer(roleName, player)) {
                            // The is enacted by the player.
                        } else {
                            // The role is not enacted by the player.
                        }
                    } else {
                        // The role is not defined for this organization.
                    }
                } else {
                    loop();
                }
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
    
    // </editor-fold>
}