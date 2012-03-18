package example2.organizations.expressionevaluation;

import example2.players.calculator.OperandPair;
import example2.protocols.EvaluateBinaryOperationReplyMessage;
import example2.protocols.EvaluateBinaryOperationRequestMessage;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.organization.Role;
import thespian4jade.proto.InvokeResponsibilityState;
import thespian4jade.proto.Protocol;
import thespian4jade.proto.ResponderParty;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.State;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public abstract class EvaluateBinaryOperation_ResponderParty extends ResponderParty<Role> {
        
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID evaluatorAID;
    
    private String operandExpression1;
    
    private String operandExpression2;
    
    private EvaluateExpression_InitiatorParty evaluateExpressionInitiator1;
    
    private EvaluateExpression_InitiatorParty evaluateExpressionInitiator2;
    
    private int operand1;
    
    private int operand2;
    
    private int result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateBinaryOperation_ResponderParty(Protocol protocol, ACLMessage message) {
        super(protocol, message);
        
        evaluatorAID = message.getSender();
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    protected abstract InvokeResponsibility_EvaluateBinaryOperation createInvokeResponsibility_EvaluateBinaryOperation();
    
    // ---------- PRIVATE ----------
    
    private void buildFSM() {
        // ----- States -----
        State receiveEvaluateRequest = new ReceiveEvaluateRequest();
        State setInitiatorArgument = new SetInitiatorArgument();
        evaluateExpressionInitiator1 = new EvaluateExpression_InitiatorParty();
        evaluateExpressionInitiator2 = new EvaluateExpression_InitiatorParty();
        State getInitiatorResult = new GetInitiatorResult();
        State invokeResponsibility_EvaluateBinaryOperation = createInvokeResponsibility_EvaluateBinaryOperation();
        State sendEvaluateReply = new SendEvaluateReply();
        State end = new End();
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
    
    protected abstract class InvokeResponsibility_EvaluateBinaryOperation
        extends InvokeResponsibilityState<OperandPair, Integer> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected InvokeResponsibility_EvaluateBinaryOperation(String responsibilityName) {
            super(responsibilityName);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
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
    
    // ---------- PRIVATE ----------
            
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
    
    private class SetInitiatorArgument extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            evaluateExpressionInitiator1.setExpression(operandExpression1);
            evaluateExpressionInitiator2.setExpression(operandExpression2);
        }
        
        // </editor-fold>
    }
    
    private class GetInitiatorResult extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            operand1 = evaluateExpressionInitiator1.getValue();
            operand2 = evaluateExpressionInitiator2.getValue();
        }
        
        // </editor-fold>
    }

    private class SendEvaluateReply extends SingleSenderState<EvaluateBinaryOperationReplyMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { evaluatorAID };
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
