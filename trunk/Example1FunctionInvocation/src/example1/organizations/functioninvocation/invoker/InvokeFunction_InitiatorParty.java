package example1.organizations.functioninvocation.invoker;

import example1.organizations.functioninvocation.executor.Executer_Role;
import example1.protocols.Protocols;
import example1.protocols.invokefunctionprotocol.InvokeFunctionProtocol;
import example1.protocols.invokefunctionprotocol.InvokeFunctionReplyMessage;
import example1.protocols.invokefunctionprotocol.InvokeFunctionRequestMessage;
import jade.core.AID;
import thespian4jade.core.organization.Role;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.behaviours.states.receiver.SingleReceiverState;
import thespian4jade.behaviours.states.sender.SingleSenderState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;

/**
 * The 'Invoke function' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public class InvokeFunction_InitiatorParty extends InitiatorParty<Invoker_Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID executerAID;
    
    private int argument;
    
    private int result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public InvokeFunction_InitiatorParty() {
        super(ProtocolRegistry.getProtocol(Protocols.INVOKE_FUNCTION_PROTOCOL));
        
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
    
    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        // ----- States -----
        IState initialize = new Initialize();
        IState sendRequest = new SendRequest();
        IState receiveReply = new ReceiveReply();
        IState end = new End();
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
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class Initialize extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo(String.format(
                "Initiating the 'Invoke function' protocol (id = %1$s)",
                getProtocolId()));
            
            // Get an active 'Executer' position.
            executerAID = getMyAgent().getMyOrganization()
                .getActivePosition(Executer_Role.NAME).getAID();
        }
        
        // </editor-fold>
    }
    
    private class SendRequest extends SingleSenderState<InvokeFunctionRequestMessage> {
      
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { executerAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending invoke function request.");
        }
        
        /**
         * Prepares the 'Request' message.
         * @return the 'Request' message
         */
        @Override
        protected InvokeFunctionRequestMessage prepareMessage() {
            InvokeFunctionRequestMessage message = new InvokeFunctionRequestMessage();
            message.setArgument(argument);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Invoke function request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveReply extends SingleReceiverState<InvokeFunctionReplyMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveReply() {
            super(new InvokeFunctionReplyMessage.Factory());
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
            getMyAgent().logInfo("Receiving result.");
        }

        @Override
        protected void handleMessage(InvokeFunctionReplyMessage message) {
            result = message.getResult();
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Result received.");
        }
        
        // </editor-fold>
    }
    
    private class End extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Invoke function' protocol (id = %1$s) initiator party ended.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
