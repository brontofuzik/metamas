package thespian4jade.core.player;

import jade.core.AID;
import thespian4jade.language.SimpleMessage;
import thespian4jade.proto.ExitValueState;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.ProtocolRegistry_StaticClass;
import thespian4jade.proto.Protocols;
import thespian4jade.proto.roleprotocol.activateroleprotocol.ActivateRequestMessage;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.jadeextensions.IState;
import thespian4jade.proto.ReceiveAgreeOrRefuse;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;

/**
 * An 'Activate role' protocol initiator party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-09
 * @version %I% %G%
 */
public class Player_ActivateRole_InitiatorParty extends InitiatorParty<Player> {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    /** The role name. */
    private String roleName;
    
    /** The role AID. */
    private AID roleAID;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Player_ActivateRole_InitiatorParty(String roleName) {
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.ACTIVATE_ROLE_PROTOCOL));
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
        IState sendActivateRequest = new SendActivateRequest();
        IState receiveActivateReply = new ReceiveActivateReply();
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();
        // ------------------

        // Register the states.
        registerFirstState(initialize);
        
        registerState(sendActivateRequest);
        registerState(receiveActivateReply);
        
        registerLastState(successEnd);
        registerLastState(failureEnd);

        // Register the transitions.
        initialize.registerTransition(Initialize.OK, sendActivateRequest);
        initialize.registerTransition(Initialize.FAIL, failureEnd);
        
        sendActivateRequest.registerDefaultTransition(receiveActivateReply);

        receiveActivateReply.registerTransition(ReceiveActivateReply.AGREE, successEnd); 
        receiveActivateReply.registerTransition(ReceiveActivateReply.REFUSE, failureEnd);
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
                "Initiating the 'Activate role' (%1$s) protocol.",
                roleName));

            // Check if the role can be activated.
            if (getMyAgent().knowledgeBase.query().canActivateRole(roleName)) {
                // The role can be activated.
                roleAID = getMyAgent().knowledgeBase.query().getEnactedRole(roleName).getAID();
                return OK;
            } else {
                // The role can not be activated.
                String message = String.format("Error activating the role '%1$s'. It is not enacted.",
                    roleName);
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send activate request' (single sender) state.
     * A state in which the 'Activate request' message is sent.
     */
    private class SendActivateRequest extends SingleSenderState<ActivateRequestMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { roleAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending activate request.");
        }
        
        @Override
        protected ActivateRequestMessage prepareMessage() {
            ActivateRequestMessage message = new ActivateRequestMessage();
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Activate request sent.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Receive activate reply' (multi sender) state.
     * A state in which the 'Activate reply' message is received.
     */
    private class ReceiveActivateReply extends ReceiveAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { roleAID };
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving activate reply.");
        }
        
        /**
         * Handles the received AGREE message
         * @param message the received AGREE message
         */
        @Override
        protected void handleAgreeMessage(SimpleMessage message) {
            getMyAgent().knowledgeBase.update().activateRole(roleName);
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Activate reply received.");
        }

        // </editor-fold>
    }
        
    /**
     * The 'Success end' (simple) state.
     * A state in which the 'Activate role' protocol initiator party succeeds.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyAgent().logInfo("Activate role initiator party succeeded.");
        }

        // </editor-fold>
    }
        
    /**
     * The 'Failure end' (simple) state.
     * A state in which the 'Activate role' protocol initiator party fails.
     */
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyAgent().logInfo("Activate role initiator party failed.");
        }

        // </editor-fold>
    }
        
    // </editor-fold>
}
