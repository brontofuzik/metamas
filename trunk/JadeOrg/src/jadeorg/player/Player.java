package jadeorg.player;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jadeorg.core.YellowPages;
import jadeorg.lang.Message;
import jadeorg.proto.ActiveState;
import jadeorg.proto.Party;
import jadeorg.proto.PassiveState;
import jadeorg.proto.Protocol;
import jadeorg.proto.State;
import jadeorg.proto.activateprotocol.ActivateProtocol;
import jadeorg.proto.deactivateprotocol.DeactivateProtocol;
import jadeorg.proto.deactprotocol.DeactProtocol;
import jadeorg.proto.enactprotocol.EnactProtocol;
import jadeorg.proto.enactprotocol.RefuseMessage;
import jadeorg.proto.enactprotocol.RequirementsMessage;
import jadeorg.proto.enactprotocol.RoleAIDMessage;
import jadeorg.proto.organizationprotocol.EnactRequestMessage;

/**
 * A player agent.
 * @author Lukáš Kúdela
 * @since 2011-10-17
 * @version %I% %G%
 */
public abstract class Player extends Agent {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private PlayerKnowledgeBase knowledgeBase = new PlayerKnowledgeBase();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void setup() {
        initialize();
    }
    
    protected abstract boolean evaluateRequirements(String[] requirements);
    
    // ----- INITIALIZE -----
    
    private void initialize() {
        initializeState();
        initializeBehaviour();
    }

    private void initializeState() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void initializeBehaviour() {
        addBehaviour(new PlayerManagerBehaviour());
    }
    
    // ----- ENACT & DEACT -----
    
    private void enact(String organizationName, String roleName) throws PlayerException {
        DFAgentDescription organization = YellowPages.searchOrganizationWithRole(this, organizationName, roleName);
        if (organization != null) {
            // The role exists.
            addBehaviour(new EnactProtocolInitiator(organization.getName(), roleName));
        } else {
            // The role does not exist.
            String message = "I cannot enact the role '%1$' because it does not exist.";
            throw new PlayerException(this, message);
        }
    }
    
    // TODO Check if the role is enacted.
    private void deact(String organizationName, String roleName) throws PlayerException {
        DFAgentDescription organization = YellowPages.searchOrganizationWithRole(this, organizationName, roleName);
        if (organization != null) {
            addBehaviour(new DeactProtocolInitiator(organization.getName(), roleName));
        } else {
            String message = "I cannot deact the role '%1$' because it does not exist.";
            throw new PlayerException(this, message);
        }
    }
    
    // ----- ACTIVATE & DEACTIVATE -----
    
    private void activate(String roleName) throws PlayerException {
        if (knowledgeBase.canActivateRole(roleName)) {
            // The role can be activated.
            RoleInfo roleInfo = (RoleInfo)knowledgeBase.getRoleInfo(roleName);
            addBehaviour(new ActivateProtocolInitiator(roleInfo));
        } else {
            // The role can not be activated.
            String message = String.format("I cannot activate the role '%1$' because I do not enact it.", roleName);
            throw new PlayerException(this, message);
        }
    }
    
    private void deactivate(String roleName) throws PlayerException {
        if (knowledgeBase.canDeactivateRole(roleName)) {
            // The role can be deactivated.
            RoleInfo roleInfo = (RoleInfo)knowledgeBase.getRoleInfo(roleName);
            addBehaviour(new DeactivateProtocolInitiator(roleInfo));
        } else {
            // The role can not be deactivated.
            String message = String.format("I cannot deactivate the role '%1$' because I do not play it.", roleName);
            throw new PlayerException(this, message);
        }
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * A 'Player manager' (cyclic) beahviour.
     */
    private class PlayerManagerBehaviour extends CyclicBehaviour {
        
        @Override
        public void action() {
        }
    }
    
    /**
     * An 'Enact' protocol initiator party.
     */
    private class EnactProtocolInitiator extends Party {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        /** The organization AID. */
        private AID organizationAID;
        
        /** The role name */
        private String roleName;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public EnactProtocolInitiator(AID organization, String roleName) {
            // ----- Preconditions -----
            assert organization != null;
            assert roleName != null && !roleName.isEmpty();
            // -------------------------
            
            this.organizationAID = organization;
            this.roleName = roleName;
            
            registerStatesAndTransitions();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

        @Override
        protected Protocol getProtocol() {
            return EnactProtocol.getInstance();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        private void registerStatesAndTransitions() {
            // ----- States -----
            State sendEnactRequest = new SendEnactRequest();
            State receiveRequirementsInfo = new ReceiveRequirementsInfo();
            State sendAgree = new SendAgree();
            State sendRefuse = new SendRefuse();
            State receiveRoleAID = new ReceiveRoleAID();
            State end = new End();
            // ------------------
            
            // Register the states.
            registerFirstState(sendEnactRequest);
            registerState(receiveRequirementsInfo);
            registerState(sendAgree);
            registerState(sendRefuse);
            registerState(receiveRoleAID);
            registerLastState(end);
            
            // Register the transitions (OLD).
            registerDefaultTransition(sendEnactRequest, receiveRequirementsInfo);
            
            registerTransition(receiveRequirementsInfo, sendAgree, PassiveState.Event.SUCCESS);
            registerTransition(receiveRequirementsInfo, sendRefuse, PassiveState.Event.FAILURE);
            
            registerDefaultTransition(sendAgree, receiveRoleAID);
            
            registerDefaultTransition(sendRefuse, end);
            
            registerDefaultTransition(receiveRoleAID, end);
            
//            // Register the transitions (NEW).
//            sendEnactRequest.registerDefaultTransition(receiveRequirementsInfo);
//            
//            receiveRequirementsInfo.registerTransition(PassiveState.Event.SUCCESS, sendAgree);
//            receiveRequirementsInfo.registerTransition(PassiveState.Event.FAILURE, sendRefuse);
//            
//            sendAgree.registerDefaultTransition(receiveRoleAID);
//            
//            sendRefuse.registerDefaultTransition(end);
//            
//            receiveRoleAID.registerDefaultTransition(end);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Classes">
        
        /**
         * The 'Send enact request' active state.
         * A state in which the 'Enact' request is sent.
         */
        private class SendEnactRequest extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "send-enact-request";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Getters and setters">
            
            @Override
            public String getName() {
                return NAME;
            }
                        
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                EnactRequestMessage message = new EnactRequestMessage();
                message.setOrganization(organizationAID);
                message.setRoleName(roleName);
                
                send(EnactRequestMessage.class, message);
            }

            // </editor-fold>
        }
        
        /**
         * The 'Receive requirements info' passive state.
         * A state in which the 'Requirements' info is received.
         */
        private class ReceiveRequirementsInfo extends PassiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "receive-requirements-info";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Getters and setters">
            
            @Override
            public String getName() {
                return NAME;
            }
                        
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                // TODO A 'Refuse' (ACL) message can be receied as well.
                Message message = receive(RequirementsMessage.class);
                    
                if (message != null) {
                    if (message instanceof RequirementsMessage) {
                        RequirementsMessage requirementsMessage = (RequirementsMessage)message;
                        if (evaluateRequirements(requirementsMessage.getRequirements())) {
                            setExitValue(Event.SUCCESS);
                        } else {
                            setExitValue(Event.FAILURE);
                        }
                    } else if (message instanceof RefuseMessage) {
                        setExitValue(Event.FAILURE); 
                    } else {
                        // TODO Send 'Not understood' message.
                    }
                } else {
                    block();
                }
            }

            // </editor-fold>
        }
        
        /**
         * The 'Send agree' active state.
         * A state in which the 'Agree' message is send.
         */
        private class SendAgree extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Fields">
            
            private static final String NAME = "send-agree";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Getters and setters">
            
            @Override
            public String getName() {
                return NAME;
            }
                        
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                ACLMessage aclMessage = EnactProtocol.getInstance().getACLMessage(ACLMessage.AGREE);
                aclMessage.addReceiver(organizationAID);
                myAgent.send(aclMessage);
            }

            // </editor-fold>
        }
        
        /**
         * The 'Send refuse' active state,
         * A state in which the 'Refuse' message is send.
         */
        private class SendRefuse extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Fields">
            
            private static final String NAME = "send-refuse";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Getters and setters">
            
            @Override
            public String getName() {
                return NAME;
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                ACLMessage aclMessage = EnactProtocol.getInstance().getACLMessage(ACLMessage.REFUSE);
                aclMessage.addReceiver(organizationAID);
                myAgent.send(aclMessage);
            }

            // </editor-fold>
        }
        
        /**
         * The 'Receive role AID' passive state.
         * A state in which the 'Role AID' message is received.
         */
        private class ReceiveRoleAID extends PassiveState {

            // <editor-fold defaultstate="collapsed" desc="Fields">
            
            private static final String NAME = "receive-role-aid";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Getters and setters">
            
            @Override
            public String getName() {
                return NAME;
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                RoleAIDMessage roleAIDMessage = (RoleAIDMessage)receive(RoleAIDMessage.class);
                if (roleAIDMessage != null) {
                    AID roleAID = roleAIDMessage.getRoleAID();
                    RoleInfo roleInfo = new RoleInfo(roleAID, organizationAID);
                    knowledgeBase.enactRole(roleInfo);
                } else {
                    block();
                }
            }

            // </editor-fold>
        }
        
        /**
         * The 'End' state.
         * A state in which the 'Enact' protocol initiator party ends.
         */
        private class End extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Fields">
            
            private static final String NAME = "end";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Getters and setters">
            
            @Override
            public String getName() {
                return NAME;
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            // </editor-fold>
        }
        
        // </editor-fold>
    }
    
    /**
     * A 'Deact' protocol initiator party.
     */
    private class DeactProtocolInitiator extends Party {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        /** The organization AID */
        private AID organization;
        
        /** The role name */
        private String roleName;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public DeactProtocolInitiator(AID organization, String roleName) {
            // ----- Preconditions -----
            assert organization != null;
            assert roleName != null && !roleName.isEmpty();
            // -------------------------
            
            this.organization = organization;
            this.roleName = roleName;
            
            registerStatesAndTransitions();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
        
        @Override
        protected Protocol getProtocol() {
            return DeactProtocol.getInstance();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        private void registerStatesAndTransitions() {
            // ----- States -----
            State sendDeactRequest = new SendDeactRequest();
            State receiveDeactReply = new ReceiveDeactReply();
            State end = new End();
            // ------------------
            
            // Register the states.
            registerFirstState(sendDeactRequest);
            registerState(receiveDeactReply);
            registerLastState(end);
            
            // Register the transitions (OLD).
            registerDefaultTransition(sendDeactRequest, receiveDeactReply);
            
            registerDefaultTransition(receiveDeactReply, end);
                    
//            // Register the transitions (NEW).
//            sendDeactRequest.registerDefaultTransition(receiveDeactReply);
//            
//            receiveDeactReply.registerDefaultTransition(end);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Classes">
        
        /**
         * The 'Send deact request' active state.
         * A state in which the 'Deact request' message is send.
         */
        private class SendDeactRequest extends ActiveState {

            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
        
        /**
         * The 'Receive deact reply' passive state.
         * A state in which the 'Deact reply' message is send.
         */
        private class ReceiveDeactReply extends PassiveState {

            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
        
        /**
         * The 'End' active state.
         * A state in which the 'Deact' initiator party ends.
         */
        private class End extends ActiveState {

            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * An 'Activate' protocol initiator party.
     */
    private class ActivateProtocolInitiator extends Party {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private RoleInfo roleInfo;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public ActivateProtocolInitiator(RoleInfo roleInfo) {
            // ----- Preconditions -----
            assert roleInfo != null;
            // -------------------------
            
            this.roleInfo = roleInfo;
            
            registerStatesAndtransitions();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

        @Override
        protected Protocol getProtocol() {
            return ActivateProtocol.getInstance();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        private void registerStatesAndtransitions() {
            // ----- States -----
            State sendActivateRequest = new SendActivateRequest();
            State receiveActivateReply = new ReceiveActivateReply();
            State end = new End();
            // ------------------
            
            // Register the states.
            registerFirstState(sendActivateRequest);
            registerState(receiveActivateReply);
            registerLastState(end);
            
            // Register the transitions (OLD).
            registerDefaultTransition(sendActivateRequest, receiveActivateReply);
            
            registerDefaultTransition(receiveActivateReply, end);
           
//            // Register the transitions (NEW).
//            sendActivateRequest.registerDefaultTransition(receiveActivateReply);
//            
//            receiveActivateReply.registerDefaultTransition(end);  
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Classes">
        
        /**
         * The 'Send activate request' active state.
         * A state in which the 'Activate request' message is send.
         */
        private class SendActivateRequest extends ActiveState {

            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
        
        /**
         * The 'Receive activate reply' passive state.
         * A state in which the 'Activate reply' message is received.
         */
        private class ReceiveActivateReply extends PassiveState {

            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
        
        /**
         * The 'End' active state.
         * A state in which the 'Activate' protocol initiator party ends.
         */
        private class End extends ActiveState {

            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * A 'Deactivate' protocol initiator party.
     */
    private class DeactivateProtocolInitiator extends Party {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private RoleInfo roleInfo;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public DeactivateProtocolInitiator(RoleInfo roleInfo) {
            // ----- Preconditions -----
            assert roleInfo != null;
            // -------------------------
            
            this.roleInfo = roleInfo;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
        
        @Override
        protected Protocol getProtocol() {
            return DeactivateProtocol.getInstance();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        private void registerStatesAndtransitions() {
            // ----- States -----
            State sendDeactivateRequest = new SendDeactivateRequest();
            State receiveActivateReply = new ReceiveDeactivateReply();
            State end = new End();
            // ------------------
            
            // Register the states.
            registerFirstState(sendDeactivateRequest);
            registerState(receiveActivateReply);
            registerLastState(end);
            
            // Register the transitions (OLD).
            registerDefaultTransition(sendDeactivateRequest, receiveActivateReply);
            
            registerDefaultTransition(receiveActivateReply, end);
           
//            // Register the transitions (NEW).
//            sendDeactivateRequest.registerDefaultTransition(receiveDeactivateReply);
//            
//            receiveDeactivateReply.registerDefaultTransition(end);  
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Classes">
        
        /**
         * The 'Send deactivate request' active state.
         * A state in which the 'Deactivate request' message is send.
         */
        private class SendDeactivateRequest extends ActiveState {

            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
        
        /**
         * The 'Receive deactivate reply' passive state.
         * A state in which the 'Deactivate reply' message is received.
         */
        private class ReceiveDeactivateReply extends PassiveState {

            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
        
        /**
         * The 'End' active state.
         * A state in which the 'Deactivate' protocol initiator party ends.
         */
        private class End extends ActiveState {

            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
        
        // </editor-fold>
    }
     
    // </editor-fold>
}
