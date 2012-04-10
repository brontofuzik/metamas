package thespian4jade.core.player;

import jade.core.AID;
import thespian4jade.behaviours.states.special.ExitValueState;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;
import thespian4jade.protocols.role.deactivaterole.DeactivateRequestMessage;
import thespian4jade.behaviours.states.sender.SingleSenderState;
import thespian4jade.behaviours.states.IState;
import thespian4jade.behaviours.states.receiver.ReceiveAgreeOrRefuse;
import thespian4jade.behaviours.states.OneShotBehaviourState;

/**
 * A 'Deactivate role' protocol initiator (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-20
 * @version %I% %G%
 */
public class Player_DeactivateRole_InitiatorParty extends InitiatorParty<Player> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">

    /**
     * The role to deactivate; more precisely, its AID.
     * The responder party.
     */
    private AID role;
    
    /**
     * The name of the role to activate.
     */
    private String roleName;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Initializes a new instance of the Player_DeactivateRole_InitiatorParty class.
     * @param roleName the name of the role to deactivate
     */
    public Player_DeactivateRole_InitiatorParty(String roleName) {
        super(ProtocolRegistry.getProtocol(Protocols.DEACTIVATE_ROLE_PROTOCOL));
        // ----- Preconditions -----
        assert role != null;
        // -------------------------

        this.roleName = roleName;
        
        buildFSM();
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        // ----- States -----
        IState initialize = new Initialize();
        IState sendDeactivateRequest = new SendDeactivateRequest();
        IState receiveDeactivateReply = new ReceiveDeactivateReply();
        IState roleDeactivated = new RoleDeactivated();
        IState roleNotDeactivated = new RoleNotDeactivated();
        // ------------------

        // Register the states.
        registerFirstState(initialize);     
        registerState(sendDeactivateRequest);
        registerState(receiveDeactivateReply);       
        registerLastState(roleDeactivated);
        registerLastState(roleNotDeactivated);

        // Register the transitions.
        initialize.registerTransition(Initialize.OK, sendDeactivateRequest);
        initialize.registerTransition(Initialize.FAIL, roleNotDeactivated);      
        sendDeactivateRequest.registerDefaultTransition(receiveDeactivateReply);
        receiveDeactivateReply.registerTransition(ReceiveDeactivateReply.AGREE, roleDeactivated); 
        receiveDeactivateReply.registerTransition(ReceiveDeactivateReply.REFUSE, roleNotDeactivated);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Initialize' initial (exit value) state.
     * A state in which the party is initialized.
     */
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
            // LOG
            getMyAgent().logInfo(String.format(
                "'Deactivate role' protocol (id = %1$s) initiator party started.",
                getProtocolId()));

            if (getMyAgent().knowledgeBase.query().canDeactivateRole(roleName)) {
                // The role can be deactivated.
                role = getMyAgent().knowledgeBase.query().getEnactedPositions(roleName).getAID();
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
     * The 'Send deactivate request' (single sender) state.
     * A state in which the 'Deactivate request' message is sent.
     */
    private class SendDeactivateRequest
        extends SingleSenderState<DeactivateRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { role };
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
     * The 'Receive deactivate reply' (receive-agree-or-refuse) state.
     * A state in which the reply message (AGREE or REFUSE) is received.
     */
    private class ReceiveDeactivateReply extends ReceiveAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { role };
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving deactivate reply.");
        }
        
        /**
         * Handles the received AGREE simple message.
         * @param messageContent the received AGREE simple message
         */
        @Override
        protected void onAgree(String messageContent) {
            getMyAgent().knowledgeBase.update().deactivateRole();
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Deactivate reply received.");
        }    
        
        // </editor-fold>
    }
    
    /**
     * The 'Role deactivated' final (one-shot) state.
     */
    private class RoleDeactivated extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Deactivate role' protocol (id = %1$s) protocol initiator party ended; role was deactivated.",
                getProtocolId()));
        }

        // </editor-fold>
    }
        
    /**
     * The 'Role deactivated' final (one-shot) state.
     */
    private class RoleNotDeactivated extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Deactivate role' protocol (id = %1$s) protocol initiator party ended; role was not deactivated.",
                getProtocolId()));
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
