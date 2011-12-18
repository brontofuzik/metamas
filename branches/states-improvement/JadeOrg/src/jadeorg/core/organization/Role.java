package jadeorg.core.organization;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.Logger;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRequestMessage;
import jadeorg.proto.roleprotocol.deactivateroleprotocol.DeactivateRequestMessage;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import jadeorg.proto.roleprotocol.deactivateroleprotocol.DeactivateRoleProtocol;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerProtocol;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokeRequestMessage;
import jadeorg.util.ManagerBehaviour;
import java.util.logging.Level;

/**
 * A role agent.
 * @author Lukáš Kúdela
 * @since 2011-10-16
 * @version %I% %G%
 */
public class Role extends Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    String name;
    
    Organization myOrganization;
    
    Role_InvokePowerResponder invokePowerResponder = new Role_InvokePowerResponder();
    
    RoleState state = RoleState.INACTIVE;
    
    AID playerAID;
    
    private Logger logger;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Role() {
        logger = jade.util.Logger.getMyLogger(this.getClass().getName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Enums">
    
    enum RoleState
    {
        INACTIVE,
        ACTIVE,
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of this role.
     * @return the name of this role
     */
    public String getRoleName() {
        return name;
    }
    
    /**
     * Sets the name of this role.
     * @param name the name of this role
     */
    public void setRoleName(String name) {
        // ----- Preconditions -----
        assert name != null && !name.isEmpty();
        // -------------------------
        
        this.name = name;
    }
    
    /**
     * Gets my organization
     * @return my organization
     */
    public Organization getMyOrganization() {
        return myOrganization;
    }
    
    /**
     * Sets my organizaiton.
     * @param organization my organization
     */
    public void setMyOrganization(Organization organization) {
        // ----- Preconditions -----
        assert organization != null;
        // -------------------------
        
        this.myOrganization = organization;
    }
    
    public AID getPlayerAID() {
        return playerAID;
    }
    
    public void setPlayerAID(AID playerAID) {
        this.playerAID = playerAID;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        addBehaviours();
        
        // TAG YellowPages
        //registerWithYellowPages();
    }
    
    protected void addPower(Power power) {
        invokePowerResponder.addPower(power);
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
    
    // ----- Initialization -----

    private void addBehaviours() {
        addBehaviour(new Role_Manager());
        logInfo("Behaviours added.");
    }
    
    // ----- Yellow pages registration -----
    
    // TAG YellowPages
    private void registerWithYellowPages() { 
        try {
            DFService.register(this, createAgentDescription());
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
        logInfo("Registered with the Yellow Pages.");
    }
    
    // TAG YellowPages
    private DFAgentDescription createAgentDescription() {
        // Create the agent description.
        DFAgentDescription agentDescription = new DFAgentDescription();
        agentDescription.setName(getAID());
        
        // Create the service description.
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType("TODO");
        serviceDescription.setName("TODO");
        agentDescription.addServices(serviceDescription);
        
        return agentDescription;
    }
    
    // ----- Role activation/deactivation -----
    
    void activateRoleResponder(AID playerAID) {
        logInfo("Responding to the 'Activate role' protocol.");
        
        if (playerAID.equals(this.playerAID)) {
            addBehaviour(new Role_ActivateRoleResponder(playerAID));
        } else {
            // You are not enacting this role.
        }
    }

    void deactivateRoleResponder(AID playerAID) {
        logInfo("Responding to the 'Deactivate role' protocol.");
        
        if (playerAID.equals(this.playerAID)) {
            addBehaviour(new Role_DeactivateRoleResponder(playerAID));
        } else {
            // You are not enacting this role.
        }
    }
    
    // ----- Power invocation -----
    
    private void invokePower(AID player, String power, String[] arguments) {
    }
    
    // ----- Error handling -----
    
    /**
     * Sends a 'Not understood' message.
     * @param receiver the receiver.
     */
    private void sendNotUnderstood(AID receiver) {
        ACLMessage message = new ACLMessage(ACLMessage.NOT_UNDERSTOOD);
        message.addReceiver(receiver);
        send(message);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
//    /**
//     * A role manager behaviour.
//     */
//    private class RoleManager extends ManagerBehaviour {
//
//    }
    
//    /**
//     * An 'Activate' protocol responder behaviour.
//     */
//    private class ActivateProtocolResponder extends Party {
//
//        // <editor-fold defaultstate="collapsed" desc="Constant fields">
//        
//        private static final String NAME = "activate-protocol-responder";
//        
//        // </editor-fold>
//        
//        // <editor-fold defaultstate="collapsed" desc="Fields">
//        
//        private AID playerAID;
//        
//        // </editor-fold>
//        
//        // <editor-fold defaultstate="collapsed" desc="Constructors">
//        
//        ActivateProtocolResponder(AID playerAID) {
//            super(NAME);
//            this.playerAID = playerAID;
//            
//            registerStatesAndTransitions();
//        }
//        
//        // </editor-fold>
//        
//        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
//        
//        @Override
//        protected Protocol getProtocol() {
//            return ActivateRoleProtocol.getInstance();
//        }
//        
//        // </editor-fold>
//        
//        // <editor-fold defaultstate="collapsed" desc="Methods">
//
//        private void registerStatesAndTransitions() {
//            // ----- States -----
//            State receiveActivateRequest = new ReceiveActivateRequest();
//            State sendActivateReply = new SendActivateReply();
//            State sendFailure = new SendFailure();
//            State successEnd = new SuccessEnd();
//            State failureEnd = new FailureEnd();
//            // ------------------
//            
//            // Register states.
//            registerFirstState(receiveActivateRequest);
//            registerState(sendActivateReply);
//            registerState(sendFailure);
//            registerLastState(successEnd);
//            registerLastState(failureEnd);
//            
//            // Register transitions.
//            receiveActivateRequest.registerTransition(Event.SUCCESS ,sendActivateReply);
//            receiveActivateRequest.registerTransition(Event.FAILURE, sendFailure);
//          
//            sendActivateReply.registerDefaultTransition(successEnd);
//            
//            sendActivateReply.registerDefaultTransition(successEnd);
//            
//            sendFailure.registerDefaultTransition(failureEnd);
//        }
//        
//        // </editor-fold>
//        
//        // <editor-fold defaultstate="collapsed" desc="Classes">
//        
//        /**
//         * The 'Receive activate request' (passive) state.
//         */
//        private class ReceiveActivateRequest extends PassiveState {
//
//            // <editor-fold defaultstate="collapsed" desc="Constant fields">
//            
//            private static final String NAME = "receive-activate-request";
//            
//            // </editor-fold>
//            
//            // <editor-fold defaultstate="collapsed" desc="Constructors">
//            
//            ReceiveActivateRequest() {
//                super(NAME);
//            }
//            
//            // </editor-fold>
//            
//            // <editor-fold defaultstate="collapsed" desc="Methods">
//            
//            @Override
//            public void action() {
//                logInfo("Receiving activate request.");
//                
//                ActivateRequestMessage activateRequestMessage = (ActivateRequestMessage)
//                    receive(ActivateRequestMessage.class, playerAID);
//                
//                if (activateRequestMessage != null) {
//                    logInfo("Activate request received.");
//                    
//                    if (isActivable()) {
//                        state = RoleState.ACTIVE;
//                        setExitValue(Event.SUCCESS);
//                    } else {
//                        setExitValue(Event.FAILURE);
//                    }
//                } else {
//                    loop();
//                }
//            }
//            
//            // ---------- PRIVATE ----------
//            
//            private boolean isActivable() {
//                System.out.println(state);
//                return state == RoleState.INACTIVE;
//            }
//            
//            // </editor-fold>
//        }
//        
//        /**
//         * The 'Send activate reply' (active) state.
//         */
//        private class SendActivateReply extends ActiveState {
//
//            // <editor-fold defaultstate="collapsed" desc="Constant fields">
//            
//            private static final String NAME = "send-activate-reply";
//            
//            // </editor-fold>
//            
//            // <editor-fold defaultstate="collapsed" desc="Constructors">
//            
//            SendActivateReply() {
//                super(NAME);
//            }
//            
//            // </editor-fold>
//            
//            // <editor-fold defaultstate="collapsed" desc="Methods">
//            
//            @Override
//            public void action() {
//                logInfo("Sending activate reply.");
//            
//                // Create the 'Activate reply' JadeOrg message.
//                ACLMessageWrapper activateReplyMessage = ActivateRoleProtocol.getInstance()
//                    .getACLMessageWrapper(ACLMessage.AGREE);
//                activateReplyMessage.addReceiver(playerAID);
//
//                send(ACLMessageWrapper.class, activateReplyMessage);
//                logInfo("Activate reply sent");
//            }
//            
//            // </editor-fold>
//        }
//        
//        /**
//         * The 'Send failure' (active) state.
//         */
//        private class SendFailure extends ActiveState {
//
//            // <editor-fold defaultstate="collapsed" desc="Constant fields">
//            
//            private static final String NAME = "send-failure";
//            
//            // </editor-fold>
//            
//            // <editor-fold defaultstate="collapsed" desc="Constructors">
//            
//            SendFailure() {
//                super(NAME);
//            }
//            
//            // </editor-fold>
//            
//            // <editor-fold defaultstate="collapsed" desc="Methods">
//            
//            @Override
//            public void action() {
//                logInfo("Sending failure.");
//                
//                // Create the 'Failure' JadeOrg message.
//                ACLMessageWrapper failureMessage = ActivateRoleProtocol.getInstance()
//                    .getACLMessageWrapper(ACLMessage.FAILURE);
//                failureMessage.addReceiver(playerAID);
//
//                send(ACLMessageWrapper.class, failureMessage);
//                logInfo("Failure sent.");
//            }
//            
//            // </editor-fold>
//        }
//        
//        /**
//         * The 'Success end' (active) state.
//         */
//        private class SuccessEnd extends ActiveState {
//
//            // <editor-fold defaultstate="collapsed" desc="Constant fields">
//            
//            private static final String NAME = "success-end";
//            
//            // </editor-fold>
//            
//            // <editor-fold defaultstate="collapsed" desc="Constructors">
//            
//            SuccessEnd() {
//                super(NAME);
//            }
//            
//            // </editor-fold>
//            
//            // <editor-fold defaultstate="collapsed" desc="Methods">
//            
//            @Override
//            public void action() {
//                logInfo("Activate role initiator protocol succeeded.");
//            }
//            
//            // </editor-fold>
//        }
//        
//        /**
//         * The 'Failure end' (active) state.
//         */
//        private class FailureEnd extends ActiveState {
//
//            // <editor-fold defaultstate="collapsed" desc="Constant fields">
//            
//            private static final String NAME = "failure-end";
//            
//            // </editor-fold>
//            
//            // <editor-fold defaultstate="collapsed" desc="Constructors">
//            
//            FailureEnd() {
//                super(NAME);
//            }
//            
//            // </editor-fold>
//            
//            // <editor-fold defaultstate="collapsed" desc="Methods">
//            
//            @Override
//            public void action() {
//                logInfo("Activate role initiator protocol failed.");
//            }
//            
//            // </editor-fold>
//        }
//        
//        // </editor-fold>
//    }
    
//    /**
//     * A 'Deactivate' protocol responder behaviour.
//     */
//    private class DeactivateProtocolResponder extends Party {
//
//        // <editor-fold defaultstate="collapsed" desc="Constant fields">
//        
//        private static final String NAME = "deactivate-protocol-responder";
//        
//        // </editor-fold>
//        
//        // <editor-fold defaultstate="collapsed" desc="Fields">
//        
//        private AID playerAID;
//        
//        // </editor-fold>
//        
//        // <editor-fold defaultstate="collapsed" desc="Constructors">
//        
//        DeactivateProtocolResponder(AID playerAID) {
//            super(NAME);
//            this.playerAID = playerAID;
//            
//            registerStatesAndTransitions();
//        }
//                
//        // </editor-fold>
//        
//        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
//        
//        @Override
//        protected Protocol getProtocol() {
//            return DeactivateRoleProtocol.getInstance();
//        }
//        
//        // </editor-fold>
//        
//        // <editor-fold defaultstate="collapsed" desc="Methods">
//        
//        private void registerStatesAndTransitions() {
//            // ----- States -----
//            State receiveDeactivateRequest = new ReceiveDeactivateRequest();
//            State sendDeactivateReply = new SendDeactivateReply();
//            State end = new End();
//            // ------------------
//            
//            // Register states.
//            registerFirstState(receiveDeactivateRequest);
//            registerState(sendDeactivateReply);
//            registerLastState(end);
//            
//            // Register transitions.
//            receiveDeactivateRequest.registerDefaultTransition(sendDeactivateReply);
//            sendDeactivateReply.registerDefaultTransition(end);
//        }
//        
//        // </editor-fold>
//
//        // <editor-fold defaultstate="collapsed" desc="Classes">
//        
//        /**
//         * The 'Receive deactivate request' (passive) state.
//         */
//        private class ReceiveDeactivateRequest extends PassiveState {
//
//            // <editor-fold defaultstate="collapsed" desc="Constant fields">
//            
//            private static final String NAME = "recive-deactivate-request";
//            
//            // </editor-fold>
//            
//            // <editor-fold defaultstate="collapsed" desc="Constructors">
//            
//            ReceiveDeactivateRequest() {
//                super(NAME);
//            }
//            
//            // </editor-fold>
//            
//            // <editor-fold defaultstate="collapsed" desc="Methods">
//            
//            @Override
//            public void action() {
//            }
//            
//            // </editor-fold>
//        }
//        
//        /**
//         * The 'Send deactivate reply' (active) state.
//         */
//        private class SendDeactivateReply extends ActiveState {
//
//            // <editor-fold defaultstate="collapsed" desc="Fields">
//            
//            private static final String NAME = "send-deactivate-reply";
//            
//            // </editor-fold>
//            
//            // <editor-fold defaultstate="collapsed" desc="Constructors">
//            
//            SendDeactivateReply() {
//                super(NAME);
//            }
//            
//            // </editor-fold>
//            
//            // <editor-fold defaultstate="collapsed" desc="Methods">
//            
//            @Override
//            public void action() {
//                DeactivateReplyMessage deactivateReplyMessage = new DeactivateReplyMessage();      
//                if (isDeactivable()) {
//                    deactivateReplyMessage.setAgree(true);
//                    state = RoleState.INACTIVE;
//                } else {
//                    deactivateReplyMessage.setAgree(false);
//                }
//                deactivateReplyMessage.addReceiver(playerAID);
//                    
//                send(DeactivateReplyMessage.class, deactivateReplyMessage);
//            }
//            
//            private boolean isDeactivable() {
//                return state == RoleState.ACTIVE;
//            }
//            
//            // </editor-fold>  
//        }
//        
//        /**
//         * The 'SuccessEnd' (active) state.
//         */
//        private class End extends ActiveState {
//
//            // <editor-fold defaultstate="collapsed" desc="Constant fields">
//            
//            private static final String NAME = "end";
//            
//            // </editor-fold>
//            
//            // <editor-fold defaultstate="collapsed" desc="Constructors">
//            
//            End() {
//                super(NAME);
//            }
//            
//            // </editor-fold>
//            
//            // <editor-fold defaultstate="collapsed" desc="Methods">
//            
//            @Override
//            public void action() {
//                // Do nothing.
//            }
//            
//            // </editor-fold>
//        }
//        
//        // </editor-fold>
//    }
  
    // </editor-fold>
}
