package thespian4jade.core.player;

import jade.core.AID;
import thespian4jade.core.Event;
import thespian4jade.behaviours.states.special.ExitValueState;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;
import thespian4jade.behaviours.states.receiver.ReceiveAgreeOrRefuse;
import thespian4jade.behaviours.states.sender.SingleSenderState;
import thespian4jade.behaviours.states.IState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.protocols.organization.subscribetoevent.SubscribeRequestMessage;

/**
 * The 'Subscribe to event' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2012-03-21
 * @version %I% %G%
 */
public class Player_SubscribeToEventEvent_InitiatorParty
    extends InitiatorParty<Player> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The organization to subscribe to; more precisely its AID.
     * The responder party.
     */
    private AID organization;
    
    /**
     * The name of the organization to subscribe to.
     */
    private final String organizationName;
    
    /**
     * The event to subscribe to.
     */
    private final Event event;
    
    /**
     * The event handler.
     */
    private final Class eventHandlerClass;
    
    /**
     * The error message.
     */
    private String errorMessage;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Player_SubscribeToEventEvent_InitiatorParty class.
     * @param organizationName the name of the organization to subscribe to
     * @param event the event to subscribe to
     */
    public Player_SubscribeToEventEvent_InitiatorParty(String organizationName,
        Event event, Class eventHandlerClass) {
        super(ProtocolRegistry.getProtocol(Protocols.SUBSCRIBE_TO_EVENT_PROTOCOL));
        
        this.organizationName = organizationName;
        this.event = event;
        this.eventHandlerClass = eventHandlerClass;
        
        buildFSM();
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Buidls the party FSM.
     */
    private void buildFSM() {
        // ----- States -----
        IState initialize = new Initialize();
        IState sendSubscribeRequest = new SendSubscribeRequest();
        IState receiveSubscribeReply = new ReceiveSubscribeReply();
        IState eventSubscribedTo = new EventSubscribedTo();
        IState eventNotSubscribedTo = new EventNotSubscribedTo();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        registerState(sendSubscribeRequest);
        registerState(receiveSubscribeReply);
        registerLastState(eventSubscribedTo);
        registerLastState(eventNotSubscribedTo);
        
        // Register the transitions.
        initialize.registerTransition(Initialize.OK, sendSubscribeRequest);
        initialize.registerTransition(Initialize.FAIL, eventNotSubscribedTo);
        sendSubscribeRequest.registerDefaultTransition(receiveSubscribeReply);
        receiveSubscribeReply.registerDefaultTransition(eventSubscribedTo);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Initialize' initial (exit value) state.
     * A state in which the party is initialized.
     */
    private class Initialize extends ExitValueState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit values -----
        public static final int OK = 1;
        public static final int FAIL = 2;
        // -----------------------
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected int doAction() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Subscribe to event' protocol (id = %1$s) initiator party started.",
                getProtocolId()));
            
            // Check if the organization exists.
            organization = new AID(organizationName, AID.ISLOCALNAME);
            if (organization != null) {
                // The organization exists.
                return OK;
            } else {
                // The organization does not exist.
                errorMessage = String.format(
                    "Can not subscribe to event, because the organization called '%1$s' does not exist.",
                    organizationName);
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send subscribe request' (single sender) state.
     * A state in which the 'Subscribe request' message is sent.
     */
    private class SendSubscribeRequest extends SingleSenderState<SubscribeRequestMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { organization };
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            // LOG
            getMyAgent().logInfo("Sending subscribe request.");
        }
        
        @Override
        protected SubscribeRequestMessage prepareMessage() {
            // Create the 'Subscribe request' message.
            SubscribeRequestMessage message = new SubscribeRequestMessage();
            message.setEvent(event);
            return message;
        }

        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Subscribe request sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive subscribe reply' (receive-agree-or-refuse) state.
     * A state in which the reply message (AGREE or REFUSE) is received.
     */
    private class ReceiveSubscribeReply extends ReceiveAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { organization };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            // LOG
            getMyAgent().logInfo("Receiving subscribe reply.");
        }

        /**
         * Handles the received AGREE simple message.
         * @param messageContent the content of the received AGREE simple message
         */
        @Override
        protected void onAgree(String messageContent) {
            getMyAgent().addEventHandler(event, eventHandlerClass);
        }  

        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Subscribe reply received.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Event subscribed to' final (one-shot) state.
     */
    private class EventSubscribedTo extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Subscribe to event' protocol (id = %1$s) initiator party ended; event subscribed to.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Event not subscribed to' final (one-shot) state.
     */
    private class EventNotSubscribedTo extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Subscribe to event' protocol (id = %1$s) initiator party ended: event not subscribed to.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
