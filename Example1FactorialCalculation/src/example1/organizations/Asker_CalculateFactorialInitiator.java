package example1.organizations;

import example1.protocols.calculatefactorialprotocol.CalculateFactorialProtocol;
import example1.protocols.calculatefactorialprotocol.ReplyMessage;
import example1.protocols.calculatefactorialprotocol.RequestMessage;
import jade.core.AID;
import jadeorg.core.organization.Role;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.Protocol;
import jadeorg.proto.SingleReceiverState;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;

/**
 * The 'Calculate factorial' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public class Asker_CalculateFactorialInitiator extends InitiatorParty {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID answererAID;
    
    private int argument;
    
    private int result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Asker_CalculateFactorialInitiator() {                
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @Override
    public Protocol getProtocol() {
        return CalculateFactorialProtocol.getInstance();
    }
    
    public void setArgument(int argument) {
        this.argument = argument;
    }
    
    public int getResult() {
        return result;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
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
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            answererAID = getMyRole().getMyOrganization().getRoleAID("Answerer");
        }
        
        // </editor-fold>
    }
    
    private class SendRequest extends SingleSenderState {
      
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getReceiverAID() {
            return answererAID;
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
            message.setArgument(argument);
            
            send(message, answererAID);
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Calculate factorial request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveReply extends SingleReceiverState {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getSenderAID() {
            return answererAID;
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
                result = message.getResult();
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
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyRole().logInfo("'Calculate factorial' initiator party ended.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
