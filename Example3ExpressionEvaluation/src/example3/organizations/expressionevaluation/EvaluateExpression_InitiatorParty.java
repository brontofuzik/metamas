package example3.organizations.expressionevaluation;

import example3.organizations.expressionevaluation.evaluator.Evaluator_Role;
import example3.protocols.evaluateexpression.EvaluateExpressionProtocol;
import example3.protocols.evaluateexpression.ReplyMessage;
import example3.protocols.evaluateexpression.RequestMessage;
import jade.core.AID;
import thespian4jade.core.organization.Role;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.SingleReceiverState;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.State;

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
        super(EvaluateExpressionProtocol.getInstance());
        
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
    
    private void buildFSM() {
        // ----- States -----
        State initialize = new Initialize();
        State sendRequest = new SendRequest();
        State receiveReply = new ReceiveReply();
        State end = new End();
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
            
            evaluatorAID = getMyAgent().getMyOrganization()
                .getRoleInstance(Evaluator_Role.NAME);
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send request' (single sender) state.
     */
    private class SendRequest extends SingleSenderState<RequestMessage> {

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
        protected RequestMessage prepareMessage() {
            RequestMessage message = new RequestMessage();
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
    private class ReceiveReply extends SingleReceiverState<ReplyMessage> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveReply() {
            super(new ReplyMessage.Factory());
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
        protected void handleMessage(ReplyMessage message) {
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
