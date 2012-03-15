package example3.organizations.expressionevaluation.evaluator;

import example3.protocols.evaluateexpression.EvaluateExpressionProtocol;
import example3.protocols.evaluateexpression.ReplyMessage;
import example3.protocols.evaluateexpression.RequestMessage;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import thespian4jade.lang.Message;
import thespian4jade.proto.ResponderParty;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.State;

/**
 * The 'Evaluate expression' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateExpression_ResponderParty extends ResponderParty<Evaluator_Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID binaryEvaluatorAID;
    
    private State setInitiatorArgument;
    
    private EvaluateBinaryOperation_InitiatorParty evaluateBinaryOperationInitiator;
    
    private State getInitiatorResult;
    
    private String expression;
    
    private String operand1;
    
    private String operand2;
    
    private int value;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EvaluateExpression_ResponderParty class.
     * @param message the received ACL message
     */
    public EvaluateExpression_ResponderParty(ACLMessage message) {
        super(EvaluateExpressionProtocol.getInstance(), message);
        
        // TODO Consider moving this initialization to the Initialize' state.
        binaryEvaluatorAID = message.getSender();
        
        buildFSM();
    }
    
    // </editor-fold>    

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- States -----
        State receiveRequest = new ReceiveRequest();
        State process = new Process();
        setInitiatorArgument = new SetInitiatorArgument();
        getInitiatorResult = new GetInitiatorResult();
        State sendReply = new SendReply();
        State end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(receiveRequest);
        
        registerState(process);
        registerState(setInitiatorArgument);
        registerState(getInitiatorResult);
        registerState(sendReply);
        
        registerLastState(end);
        
        // Register the transitions.
        receiveRequest.registerDefaultTransition(process);
        
        process.registerTransition(Process.NUMBER, sendReply);
        process.registerTransition(Process.BINARY_OPERATION, setInitiatorArgument);
        
        getInitiatorResult.registerDefaultTransition(sendReply);
        
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
            expression = message.getExpression();
            
            // LOG
            getMyAgent().logInfo("Request received.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Process' (one-shot) state.
     */
    private class Process extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        // ----- Exit values -----
        static final int NUMBER = 1;
        static final int UNARY_OPERATION = 2;
        static final int BINARY_OPERATION = 3;
        // -----------------------
        
        private int exitValue;
        
        // </editor-fold>
        
        @Override
        public void action() {
            // TODO Try to change the operator order.
            final Pattern pattern = Pattern.compile("\\(([^)]*)\\)([*+-/])\\(([^)]*)\\)");
            Matcher matcher = pattern.matcher(expression);
            boolean matches = matcher.matches();
            
            if (matches) {
                // Binary operation
                operand1 = matcher.group(1);
                operand2 = matcher.group(3);
                switch (matcher.group(2).charAt(0)) {
                    case '+':
                        evaluateBinaryOperationInitiator = new EvaluateAddition_InitiatorParty();
                        break;
                        
                    case '-':
                        evaluateBinaryOperationInitiator = new EvaluateSubtraction_InitiatorParty();
                        break;
                        
                    case '*':
                        evaluateBinaryOperationInitiator = new EvaluateMultiplication_InitiatorParty();
                        break;
                        
                    case '/':
                        evaluateBinaryOperationInitiator = new EvaluateDivision_InitiatorParty();
                        break;
                }
                registerState(evaluateBinaryOperationInitiator);
                setInitiatorArgument.registerDefaultTransition(evaluateBinaryOperationInitiator);
                evaluateBinaryOperationInitiator.registerDefaultTransition(getInitiatorResult);
                exitValue = BINARY_OPERATION;
            } else {
                // Number
                value = new Integer(expression).intValue();
                exitValue = NUMBER;
            }
        }

        @Override
        public int onEnd() {
            return exitValue;
        }    
    }
    
    /**
     * The 'Set initiator argument' (one-shot) state.
     */
    private class SetInitiatorArgument extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            evaluateBinaryOperationInitiator.setOperand1(operand1);
            evaluateBinaryOperationInitiator.setOperand2(operand2);
        }
    
        // </editor-fold>
    }
    
    /**
     * The 'Get initiator result' (one-shot) state.
     */
    private class GetInitiatorResult extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            value = evaluateBinaryOperationInitiator.getResult();
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send reply' (single sender) state.
     */
    private class SendReply extends SingleSenderState<ReplyMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { binaryEvaluatorAID };
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Sending reply.");
        }
        
        @Override
        protected ReplyMessage prepareMessage() {
            ReplyMessage message = new ReplyMessage();
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
