package example2.organizations.expressionevaluation.evaluator;

import example2.organizations.expressionevaluation.adder.Adder_Role;
import example2.organizations.expressionevaluation.divisor.Divider_Role;
import example2.organizations.expressionevaluation.multiplier.Multiplier_Role;
import example2.organizations.expressionevaluation.subtractor.Subtractor_Role;
import example2.protocols.Protocols;
import example2.protocols.evaluatebinaryoperation.EvaluateBinaryOperationReplyMessage;
import example2.protocols.evaluatebinaryoperation.EvaluateBinaryOperationRequestMessage;
import jade.core.AID;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.behaviours.states.receiver.SingleReceiverState;
import thespian4jade.behaviours.states.sender.SingleSenderState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;

/**
 * The 'Evaluate binary operation' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateBinaryOperation_InitiatorParty
    extends InitiatorParty<Evaluator_Role> {
   
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The binary operator; more precisely its AID.
     * The responder party.
     */
    private AID binaryOperator;
    
    /**
     * The concrete binary operation (addition, subtraction, multiplicaiton or division).
     */
    private Operation operation;
    
    /**
     * The first operand expression.
     */
    private String operand1;
    
    /**
     * The second operand expression.
     */
    private String operand2;
    
    /**
     * The result.
     */
    private int result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EvaluateBinaryOperation_InitiatorParty class.
     * @param operation the operation
     */
    public EvaluateBinaryOperation_InitiatorParty(Operation operation) {
        super(ProtocolRegistry.getProtocol(Protocols.EVALUATE_BINARY_OPERATION_PROTOCOL));
        
        this.operation = operation;
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Sets the first operand expression.
     * @param operand1 the first operand expression
     */
    void setOperand1(String operand1) {
        this.operand1 = operand1;
    }
    
    /**
     * Sets the second operand expression.
     * @param operand2 the second operand expression
     */
    void setOperand2(String operand2) {
        this.operand2 = operand2;
    }
    
    /**
     * Gets the result.
     * @return the result
     */
    int getResult() {
        return result;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        // ----- States -----
        IState initialize = new Initialize();
        IState sendEvaluteRequest = new SendEvaluateRequest();
        IState receiveEvaluateReply = new ReceiveEvaluateReply();
        IState end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);      
        registerState(sendEvaluteRequest);       
        registerState(receiveEvaluateReply);
        registerLastState(end);
        
        // Register the transitions.
        initialize.registerDefaultTransition(sendEvaluteRequest);        
        sendEvaluteRequest.registerDefaultTransition(receiveEvaluateReply);
        receiveEvaluateReply.registerDefaultTransition(end);
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
            getMyAgent().logInfo(String.format(
                "Initiating the 'Evaluate binary operation' protocol (id = %1$s)",
                getProtocolId()));
            
            // Get an active 'Binary operator' position.
            binaryOperator = getMyAgent().getMyOrganization()
                .getActivePosition(getBinaryOperatorRoleName()).getAID();
        }
        
        private String getBinaryOperatorRoleName() {
            switch (operation) {
                case ADDITION:
                    return Adder_Role.NAME;
                case SUBTRACTION:
                    return Subtractor_Role.NAME;
                case MULTIPLICATION:
                    return Multiplier_Role.NAME;
                case DIVISION:
                    return Divider_Role.NAME;
                default:
                    throw new IllegalArgumentException();
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send evaluate request' (single sender) state.
     * A state in which the 'Evaluate binary operation request' message is sent.
     */
    private class SendEvaluateRequest extends SingleSenderState<EvaluateBinaryOperationRequestMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { binaryOperator };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending evaluate request.");
        }
        
        @Override
        protected EvaluateBinaryOperationRequestMessage prepareMessage() {
            EvaluateBinaryOperationRequestMessage message = new EvaluateBinaryOperationRequestMessage();
            message.setOperand1(operand1);
            message.setOperand2(operand2);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Evaluate request received.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive evaluate reply' (single receiver) state.
     * A state in which the 'Evaluate binary operation reply' message is received.
     */
    private class ReceiveEvaluateReply extends SingleReceiverState<EvaluateBinaryOperationReplyMessage> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveEvaluateReply() {
            super(new EvaluateBinaryOperationReplyMessage.Factory());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { binaryOperator };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving evaluate reply.");
        }
        
        @Override
        protected void handleMessage(EvaluateBinaryOperationReplyMessage message) {
            result = message.getResult();
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Evaluate reply received.");
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
            getMyAgent().logInfo(String.format(
                "'Evaluate binary operation' protocol (id = %1$s) initiator party ended.",
                getProtocolId()));
        }
    
        // </editor-fold>
    }
    
    // </editor-fold>
}
