package example2.organizations.expressionevaluation.evaluator;

import example2.protocols.Protocols;
import example2.protocols.evaluateexpression.EvaluateExpressionReplyMessage;
import example2.protocols.evaluateexpression.EvaluateExpressionRequestMessage;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.behaviours.parties.ResponderParty;
import thespian4jade.behaviours.states.sender.SingleSenderState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;
import thespian4jade.behaviours.states.special.WrapperState;

/**
 * The 'Evaluate expression' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateExpression_ResponderParty extends ResponderParty<Evaluator_Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The binary operator; more precisely its AID.
     * The initiator party.
     */
    private AID binaryOperator;
    
    /**
     * The expression to evaluate.
     */
    private String expression;
    
    /**
     * The expression value.
     */
    private int value;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EvaluateExpression_ResponderParty class.
     * @param message the ACL message initiating the protocol
     */
    public EvaluateExpression_ResponderParty(ACLMessage message) {
        super(ProtocolRegistry.getProtocol(Protocols.EVALUATE_EXPRESSION_PROTOCOL), message);
        
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
     * The 'Receive request' initial (single sender) state.
     * A state in which the 'Evaluate expression request' message is received.
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
     * A state in which the 'Evaluate expression' beahviour is executed.
     */
    private class EvaluteExpressionWrapper
        extends WrapperState<EvaluateExpression> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        private EvaluteExpressionWrapper() {
            super(new EvaluateExpression());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void doActionBefore(EvaluateExpression wrappedState) {
            wrappedState.setExpression(expression);
        }

        @Override
        protected void doActionAfter(EvaluateExpression wrappedState) {
            value = wrappedState.getValue();
        }
        
        // </editor-fold> 
    }
    
    /**
     * The 'Send reply' (single sender) state.
     * A state in which the 'Evaluate expression reply' message is sent.
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
     * The 'End' final (one-shot) state.
     * A state in which the party ends.
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
