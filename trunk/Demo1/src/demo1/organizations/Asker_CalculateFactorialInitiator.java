package demo1.organizations;

import demo1.protocols.calculatefactorialprotocol.CalculateFactorialProtocol;
import demo1.protocols.calculatefactorialprotocol.ReplyMessage;
import demo1.protocols.calculatefactorialprotocol.RequestMessage;
import jade.core.AID;
import jadeorg.core.organization.Role;
import jadeorg.proto.PowerParty;
import jadeorg.proto.Protocol;
import jadeorg.proto.SingleReceiverState;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;

/**
 * The 'Calculate factorial' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2011-01-02
 * @version %I% %G%
 */
public class Asker_CalculateFactorialInitiator extends PowerParty<Integer, Integer> {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "calculate-factorial-initiator";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID answererAID;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Asker_CalculateFactorialInitiator() {
        super(NAME);
        
        // TODO Think about automating the protocol id setting.
        setProtocolId(new Integer(hashCode()).toString());
        
        registerStatesAndTransitions();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @Override
    public Protocol getProtocol() {
        return CalculateFactorialProtocol.getInstance();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void registerStatesAndTransitions() {
        // ----- States -----
        State initialize = new Initialize();
        State sendRequest = new SendRequest();
        State receiveReply = new ReceiveReply();
        State end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        
        registerState(sendRequest);
        registerState(receiveReply);
        
        registerLastState(end);
        
        // Register the transitions.
        initialize.registerDefaultTransition(sendRequest);
        
        sendRequest.registerDefaultTransition(receiveReply);
        
        receiveReply.registerDefaultTransition(end);
    }
    
    // ----- PRIVATE -----
    
    private Role getMyRole() {
        return (Role)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class Initialize extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "initialize";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        Initialize() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            answererAID = getMyRole().getMyOrganization().getRoleAID("Answerer");
        }
        
        // </editor-fold>
    }
    
    private class SendRequest extends SingleSenderState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-request";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendRequest() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Sending calculate factorial request.");
        }
        
        @Override
        protected void onSingleSender() {
            RequestMessage message = new RequestMessage();
            message.setArgument(getArgument());
            
            send(message, answererAID);
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Calculate factorial request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveReply extends SingleReceiverState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-reply";  
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveReply() {
            super(NAME);
        }      
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Receiving result.");
        }
        
        @Override
        protected int onSingleReceiver() {
            ReplyMessage message = new ReplyMessage();
            boolean messageReceived = receive(message, answererAID);
            
            if (messageReceived) {
                setResult(message.getResult());
                return InnerReceiverState.RECEIVED;
            } else {
                return InnerReceiverState.NOT_RECEIVED;
            }
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Result received.");
        }
        
        // </editor-fold>
    }
    
    private class End extends OneShotBehaviourState {
        
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
            getMyRole().logInfo("'Calculate factorial' initiator party ended.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
