package jadeorg.core.organization;

import jadeorg.utils.Logger;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jadeorg.core.organization.behaviours.InvokePowerResponder;
import jadeorg.core.organization.behaviours.Power;
import jadeorg.proto.ActiveState;
import jadeorg.proto.Party;
import jadeorg.proto.PassiveState;
import jadeorg.proto.Protocol;
import jadeorg.proto.State;
import jadeorg.proto.roleprotocol.activateprotocol.ActivateRequestMessage;
import jadeorg.proto.roleprotocol.deactivateprotocol.DeactivateRequestMessage;
import jadeorg.proto.roleprotocol.RoleMessage;
import jadeorg.proto.roleprotocol.RoleProtocol;
import jadeorg.proto.roleprotocol.activateprotocol.ActivateProtocol;
import jadeorg.proto.roleprotocol.activateprotocol.ActivateReplyMessage;
import jadeorg.proto.roleprotocol.deactivateprotocol.DeactivateProtocol;
import jadeorg.proto.roleprotocol.deactivateprotocol.DeactivateReplyMessage;
import jadeorg.proto.roleprotocol.invokeprotocol.InvokeRequestMessage;

/**
 * A role agent.
 * @author Lukáš Kúdela
 * @since 2011-10-16
 * @version %I% %G%
 */
public class Role extends Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
    
    private Organization myOrganization;
    
    private InvokePowerResponder invokePowerResponder = new InvokePowerResponder();
    
    private RoleState state = RoleState.IDLE;
    
    private AID playerAID;
    
    private Logger logger;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Enums">
    
    private enum RoleState
    {
        IDLE,
        READY,
        ACTIVE,
        INACTIVE
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of this role.
     * @return the name of this role
     */
    String getRoleName() {
        return name;
    }
    
    /**
     * Sets the name of this role.
     * @param name the name of this role
     */
    void setRoleName(String name) {
        // ----- Preconditions -----
        assert name != null && !name.isEmpty();
        // -------------------------
        
        this.name = name;
    }
    
    /**
     * Gets my organization
     * @return my organization
     */
    Organization getMyOrganization() {
        return myOrganization;
    }
    
    /**
     * Sets my organizaiton
     * @param organization my organization
     */
    void setMyOrganization(Organization organization) {
        // ----- Preconditions -----
        assert organization != null;
        // -------------------------
        
        this.myOrganization = organization;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        initialize();
        
        // TAG YellowPages
        //registerWithYellowPages();
    }
    
    protected void addPower(Power power) {
        invokePowerResponder.addPower(power);
    }
    
    // ----- Initialization -----
    
    private void initialize() {
        initializeState();
        initializeBehaviour();
    }
    
    private void initializeState() {       
    }

    private void initializeBehaviour() {
        addBehaviour(new RoleManager());
    }
    
    // ----- Yellow pages registration -----
    
    // TAG YellowPages
    private void registerWithYellowPages() { 
        try {
            DFService.register(this, createAgentDescription());
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
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
    
    private void activateRole(AID playerAID) {
        if (playerAID.equals(this.playerAID)) {
            addBehaviour(new ActivateProtocolResponder(playerAID));
        } else {
            // You are not enacting this role.
        }
    }

    private void deactivateRole(AID playerAID) {
        if (playerAID.equals(this.playerAID)) {
            addBehaviour(new DeactivateProtocolResponder(playerAID));
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
    
    /**
     * A role manager behaviour.
     */
    private class RoleManager extends Party {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "role-manager";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        RoleManager() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected Protocol getProtocol() {
            return RoleProtocol.getInstance();
        }
                
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Classes">
        
        class ReceiveRoleRequest extends PassiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "receive-role-request";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            public ReceiveRoleRequest() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                RoleMessage roleMessage = (RoleMessage)receive(RoleMessage.class, null);              
                if (roleMessage != null) {
                    if (roleMessage instanceof ActivateRequestMessage) {
                        ActivateRequestMessage activateRequestMessage = (ActivateRequestMessage)roleMessage;
                        activateRole(activateRequestMessage.getSenderPlayer());
                    } else if (roleMessage instanceof DeactivateRequestMessage) {
                        DeactivateRequestMessage deactivateRequestMessage = (DeactivateRequestMessage)roleMessage;
                        deactivateRole(deactivateRequestMessage.getSenderPlayer());
                    } else if (roleMessage instanceof InvokeRequestMessage) {
                        InvokeRequestMessage invokeRequestMessage = (InvokeRequestMessage)roleMessage;
                        invokePower(invokeRequestMessage.getSenderPlayer(), invokeRequestMessage.getPower(), invokeRequestMessage.getArgs());
                    } else {
                        // TODO Send 'Not understood' message to the player.
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
     * An 'Activate' protocol responder behaviour.
     */
    private class ActivateProtocolResponder extends Party {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "activate-protocol-responder";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private AID playerAID;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ActivateProtocolResponder(AID playerAID) {
            super(NAME);
            this.playerAID = playerAID;
            
            registerStatesAndTransitions();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected Protocol getProtocol() {
            return ActivateProtocol.getInstance();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        private void registerStatesAndTransitions() {
            // ----- States -----
            State receiveActivateRequest = new ReceiveActivateRequest();
            State sendActivateReply = new SendActivateReply();
            State end = new End();
            // ------------------
            
            // Register states.
            registerFirstState(receiveActivateRequest);
            registerState(sendActivateReply);
            registerLastState(end);
            
            // Register transitions.
            receiveActivateRequest.registerDefaultTransition(sendActivateReply);
            sendActivateReply.registerDefaultTransition(end);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Classes">
        
        /**
         * The 'Receive activate request' (passive) state.
         * Messages received:
         * - 'Activate request' message
         */
        private class ReceiveActivateRequest extends PassiveState {

            // <editor-fold defaultstate="collapsed" desc="Fields">
            
            private static final String NAME = "receive-activate-request";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            ReceiveActivateRequest() {
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
         * The 'Send activate reply' (active) state.
         * Messages sent:
         * - 'Activate reply' message
         */
        private class SendActivateReply extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "send-activate-reply";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            SendActivateReply() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                ActivateReplyMessage activateReplyMessage = new ActivateReplyMessage();      
                if (isActivable()) {
                    activateReplyMessage.setAgree(true);
                    state = RoleState.ACTIVE;
                } else {
                    activateReplyMessage.setAgree(false);
                }
                activateReplyMessage.addReceiver(playerAID);
                    
                send(ActivateReplyMessage.class, activateReplyMessage);
            }
            
            private boolean isActivable() {
                return state == RoleState.READY || state == RoleState.INACTIVE;
            }
            
            // </editor-fold>
        }
        
        /**
         * The 'End' (active) state.
         */
        private class End extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Fields">
            
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
                // Do nothing.
            }
            
            // </editor-fold>
        }
        
        // </editor-fold>
    }
    
    /**
     * A 'Deactivate' protocol responder behaviour.
     */
    private class DeactivateProtocolResponder extends Party {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "deactivate-protocol-responder";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private AID playerAID;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        DeactivateProtocolResponder(AID playerAID) {
            super(NAME);
            this.playerAID = playerAID;
            
            registerStatesAndTransitions();
        }
                
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected Protocol getProtocol() {
            return DeactivateProtocol.getInstance();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        private void registerStatesAndTransitions() {
            // ----- States -----
            State receiveDeactivateRequest = new ReceiveDeactivateRequest();
            State sendDeactivateReply = new SendDeactivateReply();
            State end = new End();
            // ------------------
            
            // Register states.
            registerFirstState(receiveDeactivateRequest);
            registerState(sendDeactivateReply);
            registerLastState(end);
            
            // Register transitions.
            receiveDeactivateRequest.registerDefaultTransition(sendDeactivateReply);
            sendDeactivateReply.registerDefaultTransition(end);
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Classes">
        
        /**
         * The 'Receive deactivate request' (passive) state.
         */
        private class ReceiveDeactivateRequest extends PassiveState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "recive-deactivate-request";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            ReceiveDeactivateRequest() {
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
         * The 'Send deactivate reply' (active) state.
         */
        private class SendDeactivateReply extends ActiveState {

            // <editor-fold defaultstate="collapsed" desc="Fields">
            
            private static final String NAME = "send-deactivate-reply";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            SendDeactivateReply() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                DeactivateReplyMessage deactivateReplyMessage = new DeactivateReplyMessage();      
                if (isDeactivable()) {
                    deactivateReplyMessage.setAgree(true);
                    state = RoleState.INACTIVE;
                } else {
                    deactivateReplyMessage.setAgree(false);
                }
                deactivateReplyMessage.addReceiver(playerAID);
                    
                send(DeactivateReplyMessage.class, deactivateReplyMessage);
            }
            
            private boolean isDeactivable() {
                return state == RoleState.READY || state == RoleState.INACTIVE;
            }
            
            // </editor-fold>  
        }
        
        /**
         * The 'End' (active) state.
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
                // Do nothing.
            }
            
            // </editor-fold>
        }
        
        // </editor-fold>
    }
  
    // </editor-fold>
}
