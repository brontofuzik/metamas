package demo1.organizations;

import demo1.protocols.calculatefactorialprotocol.CalculateFactorialProtocol;
import demo1.protocols.calculatefactorialprotocol.ReplyMessage;
import demo1.protocols.calculatefactorialprotocol.RequestMessage;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.core.organization.Role;
import jadeorg.proto.MeetRequirementState;
import jadeorg.proto.Protocol;
import jadeorg.proto.ResponderParty;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;

/**
 * The 'Calculate factorial' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2011-01-02
 * @version %I% %G%
 */
public class Answerer_CalculateFactorialResponder extends ResponderParty {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private final ACLMessage aclMessage;
    
    private AID askerAID;
    
    private int argument;
    
    private int result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Answerer_CalculateFactorialResponder(ACLMessage aclMessage) {
        super(aclMessage);
        
        this.aclMessage = aclMessage;
        askerAID = aclMessage.getSender();
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @Override
    public Protocol getProtocol() {
        return CalculateFactorialProtocol.getInstance();
    }
    
    // ----- PRIVATE -----
    
    private Role getMyRole() {
        return (Role)myAgent;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- States -----
        State receiveRequest = new ReceiveRequest();
        State meetRequirementCalculateFactorial = new MeetRequirementCalculateFactorial();
        State sendReply = new SendReply();
        State end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(receiveRequest);
        
        registerState(meetRequirementCalculateFactorial);
        registerState(sendReply);
        
        registerLastState(end);
        
        // Register the transitions.
        receiveRequest.registerDefaultTransition(meetRequirementCalculateFactorial);
        
        meetRequirementCalculateFactorial.registerDefaultTransition(sendReply);
        
        sendReply.registerDefaultTransition(end);  
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class ReceiveRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            RequestMessage message = new RequestMessage();
            message.parseACLMessage(aclMessage);
            
            argument = message.getArgument();
        }
        
        // </editor-fold>
    }
    
    private class MeetRequirementCalculateFactorial extends MeetRequirementState {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        MeetRequirementCalculateFactorial() {
            super("demo1.players.requirements.CalculateFactorial_Requirement");
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected Object getRequirementArgument() {
            return new Integer(argument);
        }
        
        @Override
        protected void setRequirementResult(Object requirementResult) {
            result = ((Integer)requirementResult).intValue();
        }
        
        // </editor-fold>
    }
    
    private class SendReply extends SingleSenderState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Sending result.");
        }

        @Override
        protected void onSingleSender() {
            ReplyMessage message = new ReplyMessage();
            message.setResult(result);
            
            send(message, askerAID);
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
