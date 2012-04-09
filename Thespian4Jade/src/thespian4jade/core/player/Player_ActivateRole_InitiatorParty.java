package thespian4jade.core.player;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import thespian4jade.language.SimpleMessage;
import thespian4jade.behaviours.states.special.ExitValueState;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;
import thespian4jade.protocols.role.activaterole.ActivateRequestMessage;
import thespian4jade.behaviours.states.sender.SingleSenderState;
import thespian4jade.behaviours.states.IState;
import thespian4jade.behaviours.states.receiver.ReceiveAgreeOrRefuse;
import thespian4jade.behaviours.states.OneShotBehaviourState;

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
    
    private String errorMessage;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Player_ActivateRole_InitiatorParty(String roleName) {
        super(ProtocolRegistry.getProtocol(Protocols.ACTIVATE_ROLE_PROTOCOL));
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
        IState roleActivated = new RoleActivated();
        IState roleNotActivated = new RoleNotActivated();
        // ------------------

        // Register the states.
        registerFirstState(initialize);   
        registerState(sendActivateRequest);
        registerState(receiveActivateReply);      
        registerLastState(roleActivated);
        registerLastState(roleNotActivated);

        // Register the transitions.
        initialize.registerDefaultTransition(sendActivateRequest); 
        sendActivateRequest.registerDefaultTransition(receiveActivateReply);
        receiveActivateReply.registerTransition(ReceiveActivateReply.AGREE, roleActivated); 
        receiveActivateReply.registerTransition(ReceiveActivateReply.REFUSE, roleNotActivated);
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
            // LOG
            getMyAgent().logInfo(String.format(
                "'Activate role' protocol (id = %1$s) initiator party started.",
                getProtocolId()));

            // Check if the role can be activated.
            if (getMyAgent().knowledgeBase.query().canActivateRole(roleName)) {
                // The role can be activated.
                roleAID = getMyAgent().knowledgeBase.query().getEnactedRole(roleName).getAID();
                return OK;
            } else {
                // The role can not be activated.
                errorMessage = "Role can not be activated because it it not enacted.";
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
         * Handles the received AGREE simple message.
         * @param messageContent the content of the received AGREE simple message
         */
        @Override
        protected void onAgree(String messageContent) {
            getMyAgent().knowledgeBase.update().activateRole(roleName);
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Activate reply received.");
        }

        // </editor-fold>
    }
        
    /**
     * The 'Role activated' final (one-shot) state.
     */
    private class RoleActivated extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Activate role' protocol (id = %1$s) initiator party ended; role was actiavted.",
                getProtocolId()));
        }

        // </editor-fold>
    }
        
    /**
     * The 'Role activated' final (one-shot) state.
     */
    private class RoleNotActivated extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Activate role' protocol (id = %1$s) initiator party ended; role was not actiavted.",
                getProtocolId()));
        }

        // </editor-fold>
    }
        
    // </editor-fold>
}
