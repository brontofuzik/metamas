package example1.organizations.demo.answerer;

import example1.protocols.calculatefactorialprotocol.CalculateFactorialProtocol;
import example1.protocols.calculatefactorialprotocol.ReplyMessage;
import example1.protocols.calculatefactorialprotocol.RequestMessage;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.core.organization.Role;
import jadeorg.lang.Message;
import jadeorg.proto.InvokeRequirementState;
import jadeorg.proto.ResponderParty;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;

/**
 * The 'Calculate factorial' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public class Answerer_CalculateFactorialResponder extends ResponderParty {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID askerAID;
    
    private int argument;
    
    private int result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Answerer_CalculateFactorialResponder(ACLMessage aclMessage) {
        super(CalculateFactorialProtocol.getInstance(), aclMessage);
        
        // TODO Consider moving this initialization to the 'MyInitialize' state.
        askerAID = getACLMessage().getSender();
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    private Role getMyRole() {
        return (Role)myAgent;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- States -----
        State receiveRequest = new ReceiveRequest();
        State invokeRequirement_CalculateFactorial = new InvokeRequirement_CalculateFactorial();
        State sendReply = new SendReply();
        State end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(receiveRequest);
        
        registerState(invokeRequirement_CalculateFactorial);
        registerState(sendReply);
        
        registerLastState(end);
        
        // Register the transitions.
        receiveRequest.registerDefaultTransition(invokeRequirement_CalculateFactorial);
        
        invokeRequirement_CalculateFactorial.registerDefaultTransition(sendReply);
        
        sendReply.registerDefaultTransition(end);  
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class ReceiveRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyRole().logInfo("Receiving request.");
            RequestMessage message = new RequestMessage();
            message.parseACLMessage(getACLMessage());
            
            argument = message.getArgument();
            getMyRole().logInfo("Request received.");
        }
        
        // </editor-fold>
    }
    
    private class InvokeRequirement_CalculateFactorial
        extends InvokeRequirementState<Integer, Integer> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        InvokeRequirement_CalculateFactorial() {
            super("example1.players.demo.CalculateFactorial_Requirement");
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected Integer getRequirementArgument() {
            return new Integer(argument);
        }
        
        @Override
        protected void setRequirementResult(Integer requirementResult) {
            result = requirementResult.intValue();
        }
        
        // </editor-fold>
    }
    
    private class SendReply extends SingleSenderState {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { askerAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Sending result.");
        }

        @Override
        protected Message prepareMessage() {
            ReplyMessage message = new ReplyMessage();
            message.setResult(result);
            return message;
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Result sent.");
        }
        
        // </editor-fold>
    }
    
    private class End extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyRole().logInfo("'Calculate factorial' responder party ended.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
