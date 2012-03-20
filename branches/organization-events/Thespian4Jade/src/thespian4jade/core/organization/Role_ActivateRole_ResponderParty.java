package thespian4jade.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.Event;
import thespian4jade.proto.Initialize;
import thespian4jade.proto.ResponderParty;
import thespian4jade.proto.roleprotocol.activateroleprotocol.ActivateRequestMessage;
import thespian4jade.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import thespian4jade.proto.jadeextensions.IState;
import thespian4jade.proto.SendAgreeOrRefuse;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;

/**
 * An 'Activate role' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2011-12-10
 * @version %I% %G%
 */
public class Role_ActivateRole_ResponderParty extends ResponderParty<Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID playerAID;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Role_ActivateRole_ResponderParty(ACLMessage aclMessage) {
        super(ActivateRoleProtocol.getInstance(), aclMessage);
        
        playerAID = getACLMessage().getSender();
        
        buildFSM();
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void buildFSM() {
        // ----- States -----
        IState initialize = new MyInitialize();
        IState receiveActivateRequest = new ReceiveActivateRequest();
        IState sendActivateReply = new SendActivateReply();
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();
        // ------------------

        // Register states.
        registerFirstState(initialize);       
        registerState(receiveActivateRequest);
        registerState(sendActivateReply);        
        registerLastState(successEnd);
        registerLastState(failureEnd);

        // Register transitions.
        initialize.registerTransition(MyInitialize.OK, receiveActivateRequest);
        initialize.registerTransition(MyInitialize.FAIL, failureEnd);        
        receiveActivateRequest.registerDefaultTransition(sendActivateReply);       
        sendActivateReply.registerTransition(SendActivateReply.AGREE, successEnd);
        sendActivateReply.registerTransition(SendActivateReply.REFUSE, failureEnd);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">

    private class MyInitialize extends Initialize {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int initialize() {
            getMyAgent().logInfo(String.format(
                "Responding to the 'Activate role' protocol (id = %1$s).",
                getACLMessage().getConversationId()));
        
            if (playerAID.equals(getMyAgent().playerAID)) {
                // The sender player is enacting this role.
                return OK;
            } else {
                // The sender player is not enacting this role.
                // TODO (priority: low) Send a message to the player exaplaining
                // that a non-enacted role cannot be activated.
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive activate request' (single receiver) state.
     * A state in which the 'Activate request' message is received.
     */
    private class ReceiveActivateRequest extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
       
        @Override
        public void action() {
            ActivateRequestMessage message = new ActivateRequestMessage();
            message.parseACLMessage(getACLMessage());
        }

        // </editor-fold>
    }

    /**
     * The 'Send activate reply' (multi sender) state.
     * A state in which the 'Activate reply' message is sent.
     */
    private class SendActivateReply extends SendAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { playerAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending activate reply.");
        }

        @Override
        protected int onManager() {
            if (getMyAgent().isActivable()) {            
                return SendAgreeOrRefuse.AGREE;
            } else {
                return SendAgreeOrRefuse.REFUSE;
            }
        }
        
        @Override
        protected void onAgree() {
            getMyAgent().activate();
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Activate reply sent.");
        }
        
        // </editor-fold>
    }

    /**
     * The 'Success end' (simple) state.
     * A state in which the 'Activate role' protocol responder party secceeds.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // Raise the 'Role activated' event.
            getMyRole().myOrganization.raiseEvent(Event.ROLE_ACTIVATED,
                getMyRole().getClass().getSimpleName(), playerAID);
            
            // LOG
            getMyAgent().logInfo(String.format(
                "'Activate role' protocol (id = %1$s) responder party succeeded.",
                getProtocolId()));
        }

        // </editor-fold>
    }

    /**
     * The 'Failure end' (simple) state.
     * A state in which the 'Activate role' protocol responder party fails.
     */
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Activate role' protocol (id = %1$s) responder party failed.",
                getProtocolId()));
        }

        // </editor-fold>
    }

    // </editor-fold>
}
