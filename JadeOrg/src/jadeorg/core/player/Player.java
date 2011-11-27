package jadeorg.core.player;

import jadeorg.core.player.kb.PlayerKnowledgeBase;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;
import jadeorg.core.organization.YellowPages;
import jadeorg.lang.ACLMessageWrapper;
import jadeorg.lang.Message;
import jadeorg.proto.ActiveState;
import jadeorg.proto.Party;
import jadeorg.proto.PassiveState;
import jadeorg.proto.Protocol;
import jadeorg.proto.State;
import jadeorg.proto.roleprotocol.activateprotocol.ActivateProtocol;
import jadeorg.proto.roleprotocol.deactivateprotocol.DeactivateProtocol;
import jadeorg.proto.organizationprotocol.deactprotocol.DeactProtocol;
import jadeorg.proto.organizationprotocol.enactprotocol.EnactProtocol;
import jadeorg.proto.organizationprotocol.enactprotocol.RequirementsInformMessage;
import jadeorg.proto.organizationprotocol.enactprotocol.RoleAIDMessage;
import jadeorg.proto.organizationprotocol.deactprotocol.DeactRequestMessage;
import jadeorg.proto.organizationprotocol.enactprotocol.EnactRequestMessage;
import jadeorg.proto.roleprotocol.activateprotocol.ActivateRequestMessage;
import jadeorg.proto.roleprotocol.deactivateprotocol.DeactivateRequestMessage;
import java.util.logging.Level;

/**
 * A player agent.
 * @author Lukáš Kúdela
 * @since 2011-10-17
 * @version %I% %G%
 */
public abstract class Player extends Agent {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private PlayerKnowledgeBase knowledgeBase = new PlayerKnowledgeBase();
    
    private Logger logger;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Player() {
        logger = jade.util.Logger.getMyLogger(this.getClass().getName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        addBehaviours();
    }
    
    protected abstract boolean evaluateRequirements(String[] requirements);
    
    /**
     * Logs a requirementsInformMessage.
     * @param level the level
     * @param requirementsInformMessage the requirementsInformMessage
     */
    protected void log(Level level, String message) {
        if (logger.isLoggable(level)) {
            logger.log(level, String.format("%1$s: %2$s", getLocalName(), message));
        }
    }
    
    /**
     * Logs an INFO-level requirementsInformMessage.
     * @param requirementsInformMessage the INFO-level requirementsInformMessage
     */
    protected void logInfo(String message) {
        log(Level.INFO, message);
    }
    
    // ---------- PRIVATE ----------
    
    private void addBehaviours() {
        addBehaviour(new PlayerManagerBehaviour());
        logInfo("Behaviours added.");
    }
    
    // ----- ENACT & DEACT -----
    
    protected void enactRoleInitiator(String organizationName, String roleName) throws PlayerException {
        logInfo(String.format("Enacting the role '%1$s' in the organization '%2$s'.", roleName, organizationName));
        
        // TAG YELLOW-PAGES
        //DFAgentDescription organization = YellowPages.searchOrganizationWithRole(this, organizationName, roleName);
        
        // Get the organization AID.
        AID organizationAID = new AID(organizationName, AID.ISLOCALNAME);
        
        if (organizationAID != null) {
            // The organization exists.
            addBehaviour(new EnactProtocolInitiator(organizationAID, roleName));
        } else {
            // The organization does not exist.
            throw new PlayerException(this, String.format(
                "Error enacting a role. The organization '%1$s' does not exist.", organizationName));
        }
    }
    
    // TODO Check if the role is enacted.
    protected void deactRoleInitiator(String organizationName, String roleName) throws PlayerException {        
        DFAgentDescription organization = YellowPages.searchOrganizationWithRole(this, organizationName, roleName);
        if (organization != null) {
            addBehaviour(new DeactProtocolInitiator(organization.getName(), roleName));
        } else {
            String message = "I cannot deact the role '%1$' because it does not exist.";
            throw new PlayerException(this, message);
        }
    }
    
    // ----- ACTIVATE & DEACTIVATE -----
    
    protected void activateRole(String roleName) throws PlayerException {
        if (knowledgeBase.canActivateRole(roleName)) {
            // The role can be activated.
            addBehaviour(new ActivateProtocolInitiator(knowledgeBase.getRoleAID(roleName)));
        } else {
            // The role can not be activated.
            String message = String.format("I cannot activate the role '%1$' because I do not enact it.", roleName);
            throw new PlayerException(this, message);
        }
    }
    
    protected void deactivateRole(String roleName) throws PlayerException {
        if (knowledgeBase.canDeactivateRole(roleName)) {
            // The role can be deactivated.
            addBehaviour(new DeactivateProtocolInitiator(knowledgeBase.getRoleAID(roleName)));
        } else {
            // The role can not be deactivated.
            String message = String.format("I cannot deactivate the role '%1$' because I do not play it.", roleName);
            throw new PlayerException(this, message);
        }
    }
    
    // ---------- PRIVATE ----------
    
    private void sendNotUnderstood(AID receiver) {
        ACLMessage message = new ACLMessage(ACLMessage.NOT_UNDERSTOOD);
        message.addReceiver(receiver);
        send(message);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * A 'Player manager' (cyclic) beahviour.
     */
    private class PlayerManagerBehaviour extends CyclicBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // TODO Implement.
        }
        
        // </editor-fold>
    }
    
    /**
     * An 'Enact' protocol initiator party.
     */
    private class EnactProtocolInitiator extends Party {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "enact-protocol-initiator";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        /** The organization AID. */
        private AID organizationAID;
        
        /** The role name */
        private String roleName;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public EnactProtocolInitiator(AID organization, String roleName) {
            super(NAME);
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
            State receiveRequirementsInform = new ReceiveRequirementsInform();
            State sendRequirementsReply = new SendRequirementsReply();
            State sendFailure = new SendFailure();
            State receiveRoleAID = new ReceiveRoleAID();
            State successEnd = new SuccessEnd();
            State failureEnd = new FailureEnd();
            // ------------------
            
            // Register the states.
            registerFirstState(sendEnactRequest);
            registerState(receiveRequirementsInform);
            registerState(sendRequirementsReply);
            registerState(sendFailure);
            registerState(receiveRoleAID);
            registerLastState(successEnd);
            registerLastState(failureEnd);
            
            // Register the transitions (OLD).
            registerDefaultTransition(sendEnactRequest, receiveRequirementsInform);
            
            registerTransition(receiveRequirementsInform, sendRequirementsReply, PassiveState.Event.SUCCESS);
            registerTransition(receiveRequirementsInform, sendFailure, PassiveState.Event.FAILURE);
            
            registerDefaultTransition(sendRequirementsReply, receiveRoleAID);
            
            registerDefaultTransition(sendFailure, failureEnd);
            
            registerDefaultTransition(receiveRoleAID, successEnd);
            
//            // Register the transitions (NEW).
//            sendEnactRequest.registerDefaultTransition(receiveRequirementsInfo);
//            
//            receiveRequirementsInfo.registerTransition(PassiveState.Event.SUCCESS, sendAgree);
//            receiveRequirementsInfo.registerTransition(PassiveState.Event.FAILURE, sendRefuse);
//            
//            sendAgree.registerDefaultTransition(receiveRoleAID);
//            
//            sendRefuse.registerDefaultTransition(failureEnd);
//            
//            receiveRoleAID.registerDefaultTransition(successEnd);
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
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">

            SendEnactRequest() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                logInfo("Sending enact request.");
                EnactRequestMessage message = new EnactRequestMessage();
                message.setReceiverOrganization(organizationAID);
                message.setRoleName(roleName);
                
                send(EnactRequestMessage.class, message);
                logInfo("Enact request sent.");
            }

            // </editor-fold>
        }
        
        /**
         * The 'Receive requirements info' passive state.
         * A state in which the 'Requirements' info is received.
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
                logInfo("Receiving requirements info.");
                
                // TODO A 'Refuse' (ACL) message can be received as well.
                RequirementsInformMessage requirementsInformMessage = (RequirementsInformMessage)
                    receive(RequirementsInformMessage.class, organizationAID);
                
                if (requirementsInformMessage != null) {
                    logInfo("Requirements info received.");
                    if (evaluateRequirements(requirementsInformMessage.getRequirements())) {
                        // The player meets the requirements.
                        setExitValue(Event.SUCCESS);
                    } else {
                        // The player does not meet the requirements.
                        setExitValue(Event.FAILURE);
                    }
                } else {
                    loop();
                }
            }

            // </editor-fold>
        }
        
        /**
         * The 'Send agree' active state.
         * A state in which the 'Agree' requirementsInformMessage is send.
         */
        private class SendRequirementsReply extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "send-requirements-reply";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            SendRequirementsReply() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                logInfo("Sending requirements reply.");
                
                // Create the 'Requirements reply' JadeOrg message.
                ACLMessageWrapper requirementsReplyMessage = EnactProtocol.getInstance()
                    .getACLMessageWrapper(ACLMessage.AGREE);
                System.out.println(requirementsReplyMessage.getWrappedACLMessage().getProtocol());
                requirementsReplyMessage.addReceiver(organizationAID);
                
                send(ACLMessageWrapper.class, requirementsReplyMessage);
                
                logInfo("Requirements reply sent.");
            }

            // </editor-fold>
        }
        
        /**
         * The 'Send failure' active state,
         * A state in which the 'Refuse' requirementsInformMessage is send.
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
                ACLMessageWrapper failureMessage = EnactProtocol.getInstance()
                    .getACLMessageWrapper(ACLMessage.FAILURE);
                failureMessage.addReceiver(organizationAID);
                
                send(ACLMessageWrapper.class, failureMessage);
            }

            // </editor-fold>
        }
        
        /**
         * The 'Receive role AID' passive state.
         * A state in which the 'Role AID' requirementsInformMessage is received.
         */
        private class ReceiveRoleAID extends PassiveState {

            // <editor-fold defaultstate="collapsed" desc="Fields">
            
            private static final String NAME = "receive-role-aid";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            ReceiveRoleAID() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                RoleAIDMessage roleAIDMessage = (RoleAIDMessage)receive(RoleAIDMessage.class, organizationAID);
                if (roleAIDMessage != null) {
                    AID roleAID = roleAIDMessage.getRoleAID();
                    knowledgeBase.enactRole(roleAID, organizationAID);
                } else {
                    block();
                }
            }

            // </editor-fold>
        }
        
        /**
         * The 'Success end' state.
         * A state in which the 'Enact' protocol initiator party ends.
         */
        private class SuccessEnd extends ActiveState {

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
                logInfo("Enact role protocol succeeded.");
            }

            // </editor-fold>
        }
        
        /**
         * The 'Fail end' state.
         * A state in which the 'Enact' protocol initiator party ends.
         */
        private class FailureEnd extends ActiveState {

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
                logInfo("Enact role protocol failed.");
            }

            // </editor-fold>
        }
        
        // </editor-fold>
    }
    
    /**
     * A 'Deact' protocol initiator party.
     */
    private class DeactProtocolInitiator extends Party {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "deact-protocol-initiator";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        /** The organization AID */
        private AID organizationAID;
        
        /** The role name */
        private String roleName;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public DeactProtocolInitiator(AID organization, String roleName) {
            super(NAME);
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
         * A state in which the 'Deact request' requirementsInformMessage is send.
         */
        private class SendDeactRequest extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "send-deact-request";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">

            SendDeactRequest() {
                super(NAME);
            }
  
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                DeactRequestMessage message = new DeactRequestMessage();
                message.setReceiverOrganization(organizationAID);
                message.setRoleName(roleName);
                
                send(DeactRequestMessage.class, message);
            }

            // </editor-fold>
        }
        
        /**
         * The 'Receive deact reply' passive state.
         * A state in which the 'Deact reply' requirementsInformMessage is send.
         */
        private class ReceiveDeactReply extends PassiveState {

            // <editor-fold defaultstate="collapsed" desc="Fields">
            
            private static final String NAME = "receive-deact-reply";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            ReceiveDeactReply() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                ACLMessageWrapper aclMessageWrapper = (ACLMessageWrapper)receive(ACLMessageWrapper.class, organizationAID);            
                if (aclMessageWrapper != null) {
                    if (aclMessageWrapper.getWrappedACLMessage().getPerformative() == ACLMessage.AGREE) {
                        // The request was agreed.
                        knowledgeBase.deactRole(roleName);
                    } else if (aclMessageWrapper.getWrappedACLMessage().getPerformative() == ACLMessage.REFUSE) {
                        // The request was refused.
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
        
        /**
         * The 'End' active state.
         * A state in which the 'Deact' initiator party ends.
         */
        private class End extends ActiveState {
            
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
                // TODO Implement.
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
            // </editor-fold>
        }
        
        // </editor-fold>
    }
    
    /**
     * An 'Activate' protocol initiator party.
     */
    private class ActivateProtocolInitiator extends Party {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "activate-protocol-initiator";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private AID roleAID;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public ActivateProtocolInitiator(AID roleAID) {
            super(NAME);
            // ----- Preconditions -----
            assert roleAID != null;
            // -------------------------
            
            this.roleAID = roleAID;
            
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
         * A state in which the 'Activate request' requirementsInformMessage is send.
         */
        private class SendActivateRequest extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "send-activate-request";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            SendActivateRequest() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                ActivateRequestMessage activateRequestMessage = new ActivateRequestMessage();
                activateRequestMessage.setReceiverRole(roleAID);
                
                send(ActivateRequestMessage.class, activateRequestMessage);
            }
            
            // </editor-fold>
        }
        
        /**
         * The 'Receive activate reply' passive state.
         * A state in which the 'Activate reply' requirementsInformMessage is received.
         */
        private class ReceiveActivateReply extends PassiveState {

            // <editor-fold defaultstate="collapsed" desc="Fields">
            
            private static final String NAME = "receive-activate-reply";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            ReceiveActivateReply() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">         
            
            @Override
            public void action() {
                ACLMessageWrapper aclMessageWraper = (ACLMessageWrapper)receive(ACLMessageWrapper.class, roleAID);
                
                if (aclMessageWraper != null) {
                    if (aclMessageWraper.getWrappedACLMessage().getPerformative() == ACLMessage.AGREE) {
                        // The 'Activate' request was agreed.
                        knowledgeBase.activateRole(roleAID.getName());
                        setExitValue(Event.SUCCESS);
                    } else if (aclMessageWraper.getWrappedACLMessage().getPerformative() == ACLMessage.REFUSE) {
                        // The 'Activate' request was refused.
                        setExitValue(Event.FAILURE);
                    } else {
                        // TODO Send not understood to the role.
                        assert false;
                    }
                } else {
                    block();
                }
            }

            // </editor-fold>
        }
        
        /**
         * The 'End' active state.
         * A state in which the 'Activate' protocol initiator party ends.
         */
        private class End extends ActiveState {

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
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
            @Override
            public int onEnd() {
                // TODO Implement.
                return 0;
            }
            
            // </editor-fold>
        }
        
        // </editor-fold>
    }
    
    /**
     * A 'Deactivate' protocol initiator party.
     */
    private class DeactivateProtocolInitiator extends Party {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "deactivate-protocol-initiator";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private AID roleAID;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public DeactivateProtocolInitiator(AID roleAID) {
            super(NAME);
            // ----- Preconditions -----
            assert roleAID != null;
            // -------------------------
            
            this.roleAID = roleAID;
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
         * A state in which the 'Deactivate request' requirementsInformMessage is send.
         */
        private class SendDeactivateRequest extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "send-deactivate-request";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            SendDeactivateRequest()  {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                DeactivateRequestMessage deactivateRequestMessage = new DeactivateRequestMessage();
                deactivateRequestMessage.setReceiverRole(roleAID);
                
                send(DeactivateRequestMessage.class, deactivateRequestMessage);
            }
            
            // </editor-fold>
        }
        
        /**
         * The 'Receive deactivate reply' passive state.
         * A state in which the 'Deactivate reply' requirementsInformMessage is received.
         */
        private class ReceiveDeactivateReply extends PassiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "receive-deactivate-reply";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            ReceiveDeactivateReply() {
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
         * The 'End' active state.
         * A state in which the 'Deactivate' protocol initiator party ends.
         */
        private class End extends ActiveState {

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
