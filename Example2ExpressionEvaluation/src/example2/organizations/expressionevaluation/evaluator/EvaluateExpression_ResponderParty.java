package example2.organizations.expressionevaluation.evaluator;

import example2.protocols.Protocols;
import example2.protocols.evaluateexpression.EvaluateExpressionReplyMessage;
import example2.protocols.evaluateexpression.EvaluateExpressionRequestMessage;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.ProtocolRegistry_StaticClass;
import thespian4jade.proto.ResponderParty;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.IState;
import thespian4jade.proto.jadeextensions.StateWrapperState;

/**
 * The 'Evaluate expression' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateExpression_ResponderParty extends ResponderParty<Evaluator_Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The initiating binary operator; more precisely its AID.
     */
    private AID binaryOperator;
    
    /**
     * The expression to evaluate.
     */
    private String expression;
    
    /**
     * The value of the expression.
     */
    private int value;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EvaluateExpression_ResponderParty class.
     * @param message the received ACL message
     */
    public EvaluateExpression_ResponderParty(ACLMessage message) {
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.EVALUATE_EXPRESSION_PROTOCOL), message);
        
        // TODO (priority: low) Consider moving this initialization to the 'Initialize' state.
        binaryOperator = message.getSender();
        
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
        IState evaluateExpressionWrapper = new EvaluteExpressionWrapper();
        IState sendReply = new SendReply();
        IState end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(receiveRequest); 
        registerState(evaluateExpressionWrapper);
        registerState(sendReply);
        registerLastState(end);
        
        // Register the transitions.
        receiveRequest.registerDefaultTransition(evaluateExpressionWrapper);
        evaluateExpressionWrapper.registerDefaultTransition(sendReply);
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
            
            EvaluateExpressionRequestMessage message = new EvaluateExpressionRequestMessage();
            message.parseACLMessage(getACLMessage());          
            expression = message.getExpression();
            
            // LOG
            getMyAgent().logInfo("Request received.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Evaluate expression' (wrapper) state.
     */
    private class EvaluteExpressionWrapper
        extends StateWrapperState<EvaluateExpression> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        private EvaluteExpressionWrapper() {
            super(new EvaluateExpression());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void setWrappedStateArgument(EvaluateExpression wrappedState) {
            wrappedState.setExpression(expression);
        }

        @Override
        protected void getWrappedStateResult(EvaluateExpression wrappedState) {
            value = wrappedState.getValue();
        }
        
        // </editor-fold> 
    }
    
    /**
     * The 'Send reply' (single sender) state.
     */
    private class SendReply extends SingleSenderState<EvaluateExpressionReplyMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { binaryOperator };
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Sending reply.");
        }
        
        @Override
        protected EvaluateExpressionReplyMessage prepareMessage() {
            EvaluateExpressionReplyMessage message = new EvaluateExpressionReplyMessage();
            message.setValue(value);
            return message;
        }

        @Override
        protected void onEntry() {
            // LOG
            getMyAgent().logInfo("Reply sent.");
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
            // LOG
            getMyAgent().logInfo("'Evaluate expression' responder party ended.");
        }
    
        // </editor-fold> 
    }
    
    // </editor-fold>
}
