package example1.organizations.functioninvocation.executer;

import example1.protocols.invokefunctionprotocol.InvokeFunctionProtocol;
import example1.protocols.invokefunctionprotocol.ReplyMessage;
import example1.protocols.invokefunctionprotocol.RequestMessage;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.proto.InvokeRequirementState;
import jadeorg.proto.ResponderParty;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;

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
    
    public InvokeFunction_ResponderParty(ACLMessage aclMessage) {
        super(InvokeFunctionProtocol.getInstance(), aclMessage);
        
        // TODO Consider moving this initialization to the 'MyInitialize' state.
        invokerAID = getACLMessage().getSender();
        
        buildFSM();
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- States -----
        State receiveRequest = new ReceiveRequest();
        State invokeResponsibility_ExecuteFunction = new InvokeResponsibility_ExecuteFunction();
        State sendReply = new SendReply();
        State end = new End();
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
    
    private class ReceiveRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo("Receiving request.");
            RequestMessage message = new RequestMessage();
            message.parseACLMessage(getACLMessage());
            
            argument = message.getArgument();
            getMyAgent().logInfo("Request received.");
        }
        
        // </editor-fold>
    }
    
    private class InvokeResponsibility_ExecuteFunction
        extends InvokeRequirementState<Integer, Integer> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        InvokeResponsibility_ExecuteFunction() {
            super("ExecuteFunction_Responsibility");
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected Integer getRequirementArgument() {
            return new Integer(argument);
        }
        
        @Override
        protected void setRequirementResult(Integer responsibilityResult) {
            result = responsibilityResult.intValue();
        }
        
        // </editor-fold>
    }
    
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
