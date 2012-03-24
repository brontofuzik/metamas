package thespian4jade.core.player;

import jade.core.AID;
import thespian4jade.core.Event;
import thespian4jade.language.SimpleMessage;
import thespian4jade.behaviours.ExitValueState;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.ProtocolRegistry_StaticClass;
import thespian4jade.protocols.Protocols;
import thespian4jade.behaviours.receiverstate.ReceiveAgreeOrRefuse;
import thespian4jade.behaviours.senderstates.SingleSenderState;
import thespian4jade.behaviours.jadeextensions.IState;
import thespian4jade.behaviours.jadeextensions.OneShotBehaviourState;
import thespian4jade.protocols.organizationprotocol.subscribetoeventprotocol.SubscribeRequestMessage;

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
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Player_SubscribeToEventEvent_InitiatorParty class.
     * Creates a new 'Subscribe to event' protocol initiator party.
     * @param organizationName the name of the organization to subscribe to
     * @param event the event to subscribe to
     */
    public Player_SubscribeToEventEvent_InitiatorParty(String organizationName,
        Event event, Class eventHandlerClass) {
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.SUBSCRIBE_TO_EVENT_PROTOCOL));
        
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
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        registerState(sendSubscribeRequest);
        registerState(receiveSubscribeReply);
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register the transitions.
        initialize.registerTransition(Initialize.OK, sendSubscribeRequest);
        initialize.registerTransition(Initialize.FAIL, failureEnd);
        sendSubscribeRequest.registerDefaultTransition(receiveSubscribeReply);
        receiveSubscribeReply.registerDefaultTransition(successEnd);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
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
                String message = String.format(
                    "Error enacting a role. The organization '%1$s' does not exist.",
                    organizationName);
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send subscribe request' (single sender) state.
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
     * The 'Receive subscribe request' (receive AGREE or REFUSE) state.
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

        @Override
        protected void handleAgreeMessage(SimpleMessage message) {
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
     * The 'Success end' (one-shot) state.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Subscribe to event' protocol (id = %1$s) initiator party succeeded.",
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
                "'Subscribe to event' protocol (id = %1$s) initiator party failed.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
