package example1.organizations.demo.asker;

import example1.protocols.calculatefactorialprotocol.CalculateFactorialProtocol;
import example1.protocols.calculatefactorialprotocol.ReplyMessage;
import example1.protocols.calculatefactorialprotocol.RequestMessage;
import jade.core.AID;
import jadeorg.core.organization.Role;
import jadeorg.proto.InitiatorParty;
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
// TODO Change the type argument from Role to Asker_Role.
public class CalculateFactorial_InitiatorParty extends InitiatorParty<Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID answererAID;
    
    private int argument;
    
    private int result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public CalculateFactorial_InitiatorParty() {
        super(CalculateFactorialProtocol.getInstance());
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
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
    
    // TODO Consider moving this getter to the Party class.
    /**
     * Gets my role.
     * @return my role
     */
    private Role getMyRole() {
        return (Role)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class Initialize extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyRole().logInfo(String.format(
                "Initiating the 'Calculate factorial' protocol (id = %1$s)",
                getProtocolId()));
            
            answererAID = getMyRole().getMyOrganization()
                .getRoleInstance("Answerer_Role");
        }
        
        // </editor-fold>
    }
    
    private class SendRequest extends SingleSenderState<RequestMessage> {
      
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { answererAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Sending calculate factorial request.");
        }
        
        /**
         * Prepares the 'Request' message.
         * @return the 'Request' message
         */
        @Override
        protected RequestMessage prepareMessage() {
            RequestMessage message = new RequestMessage();
            message.setArgument(argument);
            return message;
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Calculate factorial request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveReply extends SingleReceiverState<ReplyMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveReply() {
            super(new ReplyMessage.Factory());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { answererAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Receiving result.");
        }

        @Override
        protected void handleMessage(ReplyMessage message) {
            result = message.getResult();
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
