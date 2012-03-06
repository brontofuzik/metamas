package example1.organizations.functioninvocation.invoker;

import example1.protocols.invokefunctionprotocol.InvokeFunctionProtocol;
import example1.protocols.invokefunctionprotocol.ReplyMessage;
import example1.protocols.invokefunctionprotocol.RequestMessage;
import jade.core.AID;
import thespian4jade.core.organization.Role;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.SingleReceiverState;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.State;

/**
 * The 'Invoke function' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
// TODO Change the type argument from Role to Invoker_Role.
public class InvokeFunction_InitiatorParty extends InitiatorParty<Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID executerAID;
    
    private int argument;
    
    private int result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public InvokeFunction_InitiatorParty() {
        super(InvokeFunctionProtocol.getInstance());
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
                "Initiating the 'Invoke function' protocol (id = %1$s)",
                getProtocolId()));
            
            executerAID = getMyRole().getMyOrganization()
                .getRoleInstance("Executer_Role");
        }
        
        // </editor-fold>
    }
    
    private class SendRequest extends SingleSenderState<RequestMessage> {
      
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { executerAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Sending invoke function request.");
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
            getMyRole().logInfo("Invoke function request sent.");
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
            return new AID[] { executerAID };
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
            getMyRole().logInfo("'Invoke function' initiator party ended.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
