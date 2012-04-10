package example2.organizations.expressionevaluation;

import example2.organizations.expressionevaluation.evaluator.Evaluator_Role;
import example2.protocols.Protocols;
import example2.protocols.evaluateexpression.EvaluateExpressionReplyMessage;
import example2.protocols.evaluateexpression.EvaluateExpressionRequestMessage;
import jade.core.AID;
import thespian4jade.core.organization.Role;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.behaviours.states.receiver.SingleReceiverState;
import thespian4jade.behaviours.states.sender.SingleSenderState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;

/**
 * The 'Evalaute expression' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateExpression_InitiatorParty extends InitiatorParty<Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The evaluator; more precisely, its AID.
     * The responder party.
     */
    private AID evaluator;
    
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
     * Initializes a new instance of the EvaluateExpression_InitiatorParty class.
     */
    public EvaluateExpression_InitiatorParty() {
        super(ProtocolRegistry.getProtocol(Protocols.EVALUATE_EXPRESSION_PROTOCOL));
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Sets the expression to evaluate.
     * @param expression the expression to evaluate
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }
    
    /**
     * Gets the expression value.
     * @return the expression value
     */
    public int getValue() {
        return value;
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
    
    /**
     * The 'Initialize' initial (one-shot) state.
     * A state in which the party is initialized.
     */
    private class Initialize extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "Initiating the 'Evaluate expression' protocol (id = %1$s)",
                getProtocolId()));
            
            // Get an active 'Evaluator' position.
            evaluator = getMyAgent().getMyOrganization()
                .getActivePosition(Evaluator_Role.NAME).getAID();
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send request' (single sender) state.
     * A state in which the 'Evaluate expression request' message is sent.
     */
    private class SendRequest extends SingleSenderState<EvaluateExpressionRequestMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { evaluator };
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending request.");
        }
        
        @Override
        protected EvaluateExpressionRequestMessage prepareMessage() {
            EvaluateExpressionRequestMessage message = new EvaluateExpressionRequestMessage();
            message.setExpression(expression);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Request sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive reply' (single receiver) state.
     * A state in which the 'Evaluate expression reply' message is received.
     */
    private class ReceiveReply extends SingleReceiverState<EvaluateExpressionReplyMessage> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveReply() {
            super(new EvaluateExpressionReplyMessage.Factory());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { evaluator };
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving reply.");
        }

        @Override
        protected void handleMessage(EvaluateExpressionReplyMessage message) {
            value = message.getValue();
        }
        
        @Override
        protected void onExit() {
            getMyAgent().logInfo("Reply received.");
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
            getMyAgent().logInfo("'Evaluate expression' initiator party ended.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
