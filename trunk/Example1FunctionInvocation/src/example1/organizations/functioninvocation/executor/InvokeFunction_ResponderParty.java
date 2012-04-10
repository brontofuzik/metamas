package example1.organizations.functioninvocation.executor;

import example1.protocols.Protocols;
import example1.protocols.invokefunctionprotocol.InvokeFunctionReplyMessage;
import example1.protocols.invokefunctionprotocol.InvokeFunctionRequestMessage;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.behaviours.states.special.InvokeResponsibilityState;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.behaviours.parties.ResponderParty;
import thespian4jade.behaviours.states.sender.SingleSenderState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;

/**
 * The 'Invoke function' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public class InvokeFunction_ResponderParty extends ResponderParty<Executor_Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The function invoker; more precisely, its AID.
     * The initiator party.
     */
    private AID invoker;
    
    /**
     * The function argument.
     */
    private int argument;
    
    /**
     * The function result.
     */
    private int result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the InvokeFunction_ResponderParty class.
     * @param message the ACL message initiating the protocol
     */
    public InvokeFunction_ResponderParty(ACLMessage aclMessage) {
        super(ProtocolRegistry.getProtocol(Protocols.INVOKE_FUNCTION_PROTOCOL), aclMessage);
        
        // TODO (priority: low) Consider moving this initialization to the Initialize' state.
        invoker = getACLMessage().getSender();
        
        buildFSM();
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        // ----- States -----
        IState receiveRequest = new ReceiveRequest();
        IState invokeResponsibility_ExecuteFunction = new InvokeResponsibility_ExecuteFunction();
        IState sendReply = new SendReply();
        IState end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(receiveRequest);       
        registerState(invokeResponsibility_ExecuteFunction);
        registerState(sendReply);       
        registerLastState(end);
        
        // Register the transitions.
        receiveRequest.registerDefaultTransition(invokeResponsibility_ExecuteFunction);       
        invokeResponsibility_ExecuteFunction.registerDefaultTransition(sendReply);       
        sendReply.registerDefaultTransition(end);  
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Receive request' (one-shot) state.
     * A state in which the 'Invoke function request' message is received.
     */
    private class ReceiveRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo("Receiving request.");
            
            InvokeFunctionRequestMessage message = new InvokeFunctionRequestMessage();
            message.parseACLMessage(getACLMessage());          
            argument = message.getArgument();
            
            // LOG
            getMyAgent().logInfo("Request received.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Invoke responsibility - Execute function' (invoke responsibility) state.
     * A state in which the 'Execute function' responsibility is invoked.
     */
    private class InvokeResponsibility_ExecuteFunction
        extends InvokeResponsibilityState<Integer, Integer> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        InvokeResponsibility_ExecuteFunction() {
            super(Executor_Role.EXECUTE_FUNCTION_RESPONSIBILITY);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected Integer getResponsibilityArgument() {
            return new Integer(argument);
        }
        
        @Override
        protected void setResponsibilityResult(Integer responsibilityResult) {
            result = responsibilityResult.intValue();
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send reply' (sinle sender) state.
     * A state in which the 'Invoke function reply' message is sent.
     */
    private class SendReply extends SingleSenderState<InvokeFunctionReplyMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { invoker };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending result.");
        }

        @Override
        protected InvokeFunctionReplyMessage prepareMessage() {
            InvokeFunctionReplyMessage message = new InvokeFunctionReplyMessage();
            message.setResult(result);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Result sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'End' final (one-shot) state.
     * A state in which the party ends.
     */
    private class End extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyAgent().logInfo("'Invoke function' responder party ended.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
