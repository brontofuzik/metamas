package example1.organizations.functioninvocation.executer;

import example1.protocols.invokefunctionprotocol.InvokeFunctionProtocol;
import example1.protocols.invokefunctionprotocol.ReplyMessage;
import example1.protocols.invokefunctionprotocol.RequestMessage;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.InvokeResponsibilityState;
import thespian4jade.proto.ResponderParty;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.IState;

/**
 * The 'Invoke function' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public class InvokeFunction_ResponderParty extends ResponderParty<Executer_Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID invokerAID;
    
    private int argument;
    
    private int result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the InvokeFunction_ResponderParty class.
     * @param message the received ACL message
     */
    public InvokeFunction_ResponderParty(ACLMessage aclMessage) {
        super(InvokeFunctionProtocol.getInstance(), aclMessage);
        
        // TODO (priority: low) Consider moving this initialization to the Initialize' state.
        invokerAID = getACLMessage().getSender();
        
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
     */
    private class ReceiveRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo("Receiving request.");
            
            RequestMessage message = new RequestMessage();
            message.parseACLMessage(getACLMessage());          
            argument = message.getArgument();
            
            // LOG
            getMyAgent().logInfo("Request received.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Invoke responsibility - Execute function' (invoke responsibility) state.
     */
    private class InvokeResponsibility_ExecuteFunction
        extends InvokeResponsibilityState<Integer, Integer> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        InvokeResponsibility_ExecuteFunction() {
            super("ExecuteFunction_Responsibility");
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
     */
    private class SendReply extends SingleSenderState<ReplyMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { invokerAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending result.");
        }

        @Override
        protected ReplyMessage prepareMessage() {
            ReplyMessage message = new ReplyMessage();
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
     * The 'End' (one-shot) state.
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
