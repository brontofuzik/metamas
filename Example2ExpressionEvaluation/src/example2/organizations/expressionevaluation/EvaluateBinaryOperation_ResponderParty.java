package example2.organizations.expressionevaluation;

import example2.players.OperandPair;
import example2.protocols.Protocols;
import example2.protocols.evaluatebinaryoperation.EvaluateBinaryOperationReplyMessage;
import example2.protocols.evaluatebinaryoperation.EvaluateBinaryOperationRequestMessage;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.behaviours.states.special.InvokeResponsibilityState;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.behaviours.parties.ResponderParty;
import thespian4jade.behaviours.states.sender.SingleSenderState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;

/**
 * The 'Evaluate binary operation' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateBinaryOperation_ResponderParty
    extends ResponderParty<BinaryOperator_Role> {
        
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The evaluator; more precisely its AID.
     * The initiator party.
     */
    private AID evaluator;
    
    /**
     * The first operand expression.
     */
    private String operandExpression1;
    
    /**
     * The second operand expression.
     */
    private String operandExpression2;
    
    private EvaluateExpression_InitiatorParty evaluateExpressionInitiator1;
    
    private EvaluateExpression_InitiatorParty evaluateExpressionInitiator2;
    
    /**
     * The first operand.
     */
    private int operand1;
    
    /**
     * The second operand.
     */
    private int operand2;
    
    /**
     * The result.
     */
    private int result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EvaluateBinaryOperation_ResponderParty class.
     * @param message 
     */
    public EvaluateBinaryOperation_ResponderParty(ACLMessage message) {
        super(ProtocolRegistry.getProtocol(Protocols.EVALUATE_BINARY_OPERATION_PROTOCOL), message);
        
        evaluator = message.getSender();
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        // ----- States -----
        IState receiveEvaluateRequest = new ReceiveEvaluateRequest();
        IState setInitiatorArgument = new SetInitiatorArgument();
        evaluateExpressionInitiator1 = new EvaluateExpression_InitiatorParty();
        evaluateExpressionInitiator2 = new EvaluateExpression_InitiatorParty();
        IState getInitiatorResult = new GetInitiatorResult();
        IState invokeResponsibility_EvaluateBinaryOperation = new InvokeResponsibility_EvaluateBinaryOperation();
        IState sendEvaluateReply = new SendEvaluateReply();
        IState end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(receiveEvaluateRequest);       
        registerState(setInitiatorArgument);
        registerState(evaluateExpressionInitiator1);
        registerState(evaluateExpressionInitiator2);
        registerState(getInitiatorResult);
        registerState(invokeResponsibility_EvaluateBinaryOperation);
        registerState(sendEvaluateReply); 
        registerLastState(end);
        
        // Register the transitions.
        receiveEvaluateRequest.registerDefaultTransition(setInitiatorArgument);        
        setInitiatorArgument.registerDefaultTransition(evaluateExpressionInitiator1);       
        evaluateExpressionInitiator1.registerDefaultTransition(evaluateExpressionInitiator2);      
        evaluateExpressionInitiator2.registerDefaultTransition(getInitiatorResult);      
        getInitiatorResult.registerDefaultTransition(invokeResponsibility_EvaluateBinaryOperation);       
        invokeResponsibility_EvaluateBinaryOperation.registerDefaultTransition(sendEvaluateReply);       
        sendEvaluateReply.registerDefaultTransition(end);
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Receive evaluate request' initial (single receiver) state.
     * A state in which the 'Evaluate binary operation request' message is received.
     */
    private class ReceiveEvaluateRequest extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo("Receiving evaluate request.");
            
            EvaluateBinaryOperationRequestMessage message = new EvaluateBinaryOperationRequestMessage();
            message.parseACLMessage(getACLMessage()); 
            operandExpression1 = message.getOperand1();
            operandExpression2 = message.getOperand2();
            
            getMyAgent().logInfo("Evaluate request received.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Set initiator argument' (one-shot) state.
     * A state in which the 'Evaluate expression' protocol initiators' arguments
     * are provided to them.
     */
    private class SetInitiatorArgument extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            evaluateExpressionInitiator1.setExpression(operandExpression1);
            evaluateExpressionInitiator2.setExpression(operandExpression2);
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Get initiator result' (one-shot) states.
     * A state in which the 'Evaluate expression' protocol initiators' results
     * are obtianed from them.
     */
    private class GetInitiatorResult extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            operand1 = evaluateExpressionInitiator1.getValue();
            operand2 = evaluateExpressionInitiator2.getValue();
        }
        
        // </editor-fold>
    }

    /**
     * The 'Invoke responsibility - Evaluate binary operation' (invoke responsibility) state.
     * A state in which the 'Evaluate binary operation' responsibility is invoked.
     */
    private class InvokeResponsibility_EvaluateBinaryOperation
        extends InvokeResponsibilityState<OperandPair, Integer> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        InvokeResponsibility_EvaluateBinaryOperation() {
            super();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">

        @Override
        protected String getResponsibilityName() {
            return getMyAgent().getResponsibilityName();
        }
        
        @Override
        protected OperandPair getResponsibilityArgument() {
            return new OperandPair(operand1, operand2);
        }

        @Override
        protected void setResponsibilityResult(Integer responsibilityResult) {
            result = responsibilityResult.intValue();
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send evaluate reply' (single sender) state.
     * A state in which the 'Evaluate binary operation reply' message is Sent.
     */
    private class SendEvaluateReply extends SingleSenderState<EvaluateBinaryOperationReplyMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { evaluator };
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending evalaute result.");
        }
        
        @Override
        protected EvaluateBinaryOperationReplyMessage prepareMessage() {
            EvaluateBinaryOperationReplyMessage message = new EvaluateBinaryOperationReplyMessage();
            message.setResult(result);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Evaluate result sent.");
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
            getMyAgent().logInfo("'Evaluate addition' responder party ended.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
