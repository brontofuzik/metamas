package example2.organizations.expressionevaluation;

import example2.organizations.expressionevaluation.evaluator.Evaluator_Role;
import example2.protocols.Protocols;
import example2.protocols.evaluateexpression.EvaluateExpressionProtocol;
import example2.protocols.evaluateexpression.EvaluateExpressionReplyMessage;
import example2.protocols.evaluateexpression.EvaluateExpressionRequestMessage;
import jade.core.AID;
import thespian4jade.core.organization.Role;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.behaviours.receiverstate.SingleReceiverState;
import thespian4jade.behaviours.senderstates.SingleSenderState;
import thespian4jade.behaviours.jadeextensions.OneShotBehaviourState;
import thespian4jade.behaviours.jadeextensions.IState;

/**
 * The 'Evalaute expression' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateExpression_InitiatorParty extends InitiatorParty<Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID evaluatorAID;
    
    private String expression;
    
    private int value;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateExpression_InitiatorParty() {
        super(ProtocolRegistry.getProtocol(Protocols.EVALUATE_EXPRESSION_PROTOCOL));
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public void setExpression(String expression) {
        this.expression = expression;
    }
    
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
     * The 'Initialize' (one-shot) state.
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
            evaluatorAID = getMyAgent().getMyOrganization()
                .getActivePosition(Evaluator_Role.NAME).getAID();
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send request' (single sender) state.
     */
    private class SendRequest extends SingleSenderState<EvaluateExpressionRequestMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { evaluatorAID };
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
     * The 'Receive request' (single receiver) state.
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
            return new AID[] { evaluatorAID };
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
     * The 'End' (one-shot) state.
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
