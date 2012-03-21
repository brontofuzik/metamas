package thespian4jade.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.Event;
import thespian4jade.proto.Initialize;
import thespian4jade.proto.ProtocolRegistry_StaticClass;
import thespian4jade.proto.Protocols;
import thespian4jade.proto.ResponderParty;
import thespian4jade.proto.roleprotocol.deactivateroleprotocol.DeactivateRequestMessage;
import thespian4jade.proto.jadeextensions.IState;
import thespian4jade.proto.SendAgreeOrRefuse;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;

/**
 * A 'Deactivate role' protocol responder (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-20
 * @version %I% %G%
 */
public class Role_DeactivateRole_ResponderParty extends ResponderParty<Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID playerAID;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Role_DeactivateRole_ResponderParty(ACLMessage aclMessage) {
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.DEACTIVATE_ROLE_PROTOCOL), aclMessage);
        
        playerAID = getACLMessage().getSender();

        buildFSM();
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        // ----- States -----
        IState initialize = new MyInitialize();
        IState receiveActivateRequest = new ReceiveDeactivateRequest();
        IState sendActivateReply = new SendDeactivateReply();
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
        sendActivateReply.registerTransition(SendDeactivateReply.AGREE, successEnd);
        sendActivateReply.registerTransition(SendDeactivateReply.REFUSE, failureEnd);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyInitialize extends Initialize {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int initialize() {
            getMyAgent().logInfo(String.format(
                "'Deactivate role' protocol (id = %1$s) responder party started.",
                getACLMessage().getConversationId()));
        
            if (playerAID.equals(getMyAgent().playerAID)) {
                // The sender player is enacting this role.
                return OK;
            } else {
                // The sender player is not enacting this role.
                // TODO (priority: low) Send a message to the player exaplaining
                // that a non-enacted role cannot be deactivated.
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive deactivate request' (single receiver) state.
     */
    private class ReceiveDeactivateRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            DeactivateRequestMessage message = new DeactivateRequestMessage();
            message.parseACLMessage(getACLMessage());
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send deactivate reply' (multi sender) state.
     */
    private class SendDeactivateReply extends SendAgreeOrRefuse {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { playerAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending deactivate reply.");
        }
        
        @Override
        protected int onManager() {
            if (getMyAgent().isDeactivable()) {            
                return SendAgreeOrRefuse.AGREE;
            } else {
                return SendAgreeOrRefuse.REFUSE;
            }
        }
        
        @Override
        protected void onAgree() {
            getMyAgent().deactivate();
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Deactivate reply sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Success end' (simple) state.
     * A state in which the 'Deactivate role' protocol responder party succeeds.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // Publish the 'Role deactivated' event.
            getMyAgent().myOrganization.publishEvent(Event.ROLE_DEACTIVATED,
                getMyAgent().getRoleName(), playerAID);
            
            // LOG
            getMyAgent().logInfo(String.format(
                "'Deactivate role' protocol (id = %1$s) responder party succeeded.",
                getProtocolId()));
        }

        // </editor-fold>
    }
        
    /**
     * The 'Failure end' (simple) state.
     * A state in which the 'Deactivate role' protocol responder party fails.
     */
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Deactivate role' protocol (id = %1$s) responder party failed.",
                getProtocolId()));
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
