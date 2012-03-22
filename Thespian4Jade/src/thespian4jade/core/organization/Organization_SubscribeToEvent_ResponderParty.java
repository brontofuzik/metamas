package thespian4jade.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.Event;
import thespian4jade.proto.Initialize;
import thespian4jade.proto.ProtocolRegistry_StaticClass;
import thespian4jade.proto.Protocols;
import thespian4jade.proto.ResponderParty;
import thespian4jade.proto.SendAgreeOrRefuse;
import thespian4jade.proto.jadeextensions.IState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.organizationprotocol.subscribetoeventprotocol.SubscribeRequestMessage;

/**
 * The 'Subscribe to event' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2012-03-21
 * @version %I% %G%
 */
public class Organization_SubscribeToEvent_ResponderParty
    extends ResponderParty<Organization> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The subscribing player; more precisely its AID.
     * The initiator party of the protocol.
     */
    private AID player;
    
    /**
     * The event to subscribe to.
     */
    private Event event;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Organization_SubscribeToEvent_ResponderParty class.
     * Creates a new 'Subscribe to event' protocol responder party.
     * @param message 
     */
    public Organization_SubscribeToEvent_ResponderParty(ACLMessage message) {
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.SUBSCRIBE_TO_EVENT_PROTOCOL), message);
        
        player = message.getSender();
        
        buildFSM();
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        // ----- States -----
        IState initialize = new MyInitialize();
        IState receiveSubscribeRequest = new ReceiveSubscribeRequest();
        IState sendSubscribeReply = new SendSubscribeReply();
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        registerState(receiveSubscribeRequest);
        registerState(sendSubscribeReply);
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register the transitions.
        initialize.registerTransition(MyInitialize.OK, receiveSubscribeRequest);
        initialize.registerTransition(MyInitialize.FAIL, failureEnd);
        receiveSubscribeRequest.registerDefaultTransition(sendSubscribeReply);
        sendSubscribeReply.registerDefaultTransition(successEnd);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'My initialize' (initialize) state.
     */
    private class MyInitialize extends Initialize {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected int initialize() {
            getMyAgent().logInfo(String.format(
                "'Subscribe to event' protocol (id = %1$s) responder party started.",
                // TODO (priority: high) Replace the following with getProtocolId().
                getACLMessage().getConversationId()));
        
            if (getMyAgent().knowledgeBase.query().doesPlayerEnact(player)) {
                // The initiator player is employed (enacts a role) in this organization.
                return OK;
            } else {
                // The initiator player is not enacting this role.
                // TODO (priority: low) Send a message to the player exaplaining
                // that a non-enacted role cannot be activated.
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive subscribe request' (single receiver) message.
     */
    private class ReceiveSubscribeRequest
        extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            SubscribeRequestMessage message = new SubscribeRequestMessage();
            message.parseACLMessage(getACLMessage());
            
            event = message.getEvent();
        }
        
        // </editor-fold>
    }

    /**
     * The 'Send subscribe reply' (send AGREE or REFUSE) message.
     */
    private class SendSubscribeReply extends SendAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            // LOG
            getMyAgent().logInfo("Sending subscribe reply.");
        }

        @Override
        protected int onManager() {
            return event != Event.NONE ? AGREE : REFUSE;
        }

        @Override
        protected void onAgree() {
            getMyAgent().knowledgeBase.update().playerSubscribesToEvent(player, event);
        } 

        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Subscribe reply sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Success end' (one-shot) state.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Subscribe to event' protocol (id = %1$s) responder party succeeded.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Failure end' (one-shot) state.
     */
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Subscribe to event' protocol (id = %1$s) responder party failure.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
