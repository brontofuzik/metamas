package thespian4jade.core.player;

import jade.core.AID;
import thespian4jade.language.SimpleMessage;
import thespian4jade.proto.ExitValueState;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.ProtocolRegistry_StaticClass;
import thespian4jade.proto.Protocols;
import thespian4jade.proto.roleprotocol.deactivateroleprotocol.DeactivateRequestMessage;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.jadeextensions.IState;
import thespian4jade.proto.ReceiveAgreeOrRefuse;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;

/**
 * A 'Deactivate role' protocol initiator (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-20
 * @version %I% %G%
 */
public class Player_DeactivateRole_InitiatorParty extends InitiatorParty<Player> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">

    /** The role name. */
    private String roleName;
    
    /** The role AID. */
    private AID roleAID;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Player_DeactivateRole_InitiatorParty(String roleName) {
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.DEACTIVATE_ROLE_PROTOCOL));
        // ----- Preconditions -----
        assert roleAID != null;
        // -------------------------

        this.roleName = roleName;
        
        registerStatesAndtransitions();
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void registerStatesAndtransitions() {
        // ----- States -----
        IState initialize = new Initialize();
        IState sendDeactivateRequest = new SendDeactivateRequest();
        IState receiveDeactivateReply = new ReceiveDeactivateReply();
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();
        // ------------------

        // Register the states.
        registerFirstState(initialize);
        
        registerState(sendDeactivateRequest);
        registerState(receiveDeactivateReply);
        
        registerLastState(successEnd);
        registerLastState(failureEnd);

        // Register the transitions.
        initialize.registerTransition(Initialize.OK, sendDeactivateRequest);
        initialize.registerTransition(Initialize.FAIL, failureEnd);
        
        sendDeactivateRequest.registerDefaultTransition(receiveDeactivateReply);

        receiveDeactivateReply.registerTransition(ReceiveDeactivateReply.AGREE, successEnd); 
        receiveDeactivateReply.registerTransition(ReceiveDeactivateReply.REFUSE, failureEnd);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class Initialize extends ExitValueState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit values -----
        public static final int OK = 1;
        public static final int FAIL = 2;
        // -----------------------
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int doAction() {
            getMyAgent().logInfo(String.format(
                "Initiating the 'Deactivate role' (%1$s) protocol.",
                roleName));

            if (getMyAgent().knowledgeBase.query().canDeactivateRole(roleName)) {
                // The role can be deactivated.
                roleAID = getMyAgent().knowledgeBase.query().getEnactedRole(roleName).getAID();
                return OK;
            } else {
                // The role can not be deactivated.
                String message = String.format(
                    "I cannot deactivate the role '%1$s' because I do not play it.",
                    roleName);
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * 
     */
    private class SendDeactivateRequest
        extends SingleSenderState<DeactivateRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { roleAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending deactivate request.");
        }
        
        @Override
        protected DeactivateRequestMessage prepareMessage() {
            DeactivateRequestMessage message = new DeactivateRequestMessage();
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Deactivate request sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * 
     */
    private class ReceiveDeactivateReply extends ReceiveAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { roleAID };
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving deactivate reply.");
        }
        
        /**
         * Handles the received AGREE message
         * @param message the received AGREE message
         */
        @Override
        protected void handleAgreeMessage(SimpleMessage message) {
            getMyAgent().knowledgeBase.update().deactivateRole();
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Deactivate reply received.");
        }    
        
        // </editor-fold>
    }
    
    /**
     * The 'Success end' (simple) state.
     * A state in which the 'Deactivate role' protocol initiator party succeeds.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyAgent().logInfo("Deactivate role initiator party succeeded.");
        }

        // </editor-fold>
    }
        
    /**
     * The 'Failure end' (simple) state.
     * A state in which the 'Deactivate role' protocol initiator party fails.
     */
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyAgent().logInfo("Deactivate role initiator party failed.");
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
