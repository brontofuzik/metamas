package thespian4jade.core.player;

import jade.core.AID;
import thespian4jade.language.SimpleMessage;
import thespian4jade.behaviours.states.special.ExitValueState;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;
import thespian4jade.protocols.organization.deactrole.DeactRequestMessage;
import thespian4jade.behaviours.states.sender.SingleSenderState;
import thespian4jade.behaviours.states.IState;
import thespian4jade.behaviours.states.receiver.ReceiveAgreeOrRefuse;
import thespian4jade.behaviours.states.OneShotBehaviourState;

/**
 * A 'Deact role' protocol initiator party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Player_DeactRole_InitiatorParty extends InitiatorParty<Player> {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    /**
     * The organization name.
     */
    private String organizationName;
    
    /**
     * The organization AID.
     */
    private AID organizationAID;

    /**
     * The role name.
     */
    private String roleName;

    /**
     * The error message.
     */
    private String errorMessage;
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Player_DeactRole_InitiatorParty(String organizationName, String roleName) {
        super(ProtocolRegistry.getProtocol(Protocols.DEACT_ROLE_PROTOCOL));
        // ----- Preconditions -----
        assert organizationName != null && !organizationName.isEmpty();
        assert roleName != null && !roleName.isEmpty();
        // -------------------------

        this.organizationName = organizationName;
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
        IState sendDeactRequest = new SendDeactRequest();
        IState receiveDeactReply = new ReceiveDeactReply();
        IState roleDeacted = new RoleDeacted();
        IState roleNotDeacted = new RoleNotDeacted();
        // ------------------

        // Register the states.
        registerFirstState(initialize);       
        registerState(sendDeactRequest);
        registerState(receiveDeactReply);        
        registerLastState(roleDeacted);
        registerLastState(roleNotDeacted);
        
        // Register the transitions.
        initialize.registerTransition(Initialize.OK, sendDeactRequest);
        initialize.registerTransition(Initialize.FAIL, roleNotDeacted);       
        sendDeactRequest.registerDefaultTransition(receiveDeactReply);            
        receiveDeactReply.registerTransition(ReceiveDeactReply.AGREE, roleDeacted);
        receiveDeactReply.registerTransition(ReceiveDeactReply.REFUSE, roleNotDeacted);
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
                "Initiating the 'Deact role' (%1$s.%2$s) protocol.",
                organizationName, roleName));

//            // TAG YELLOW-PAGES
//            DFAgentDescription organization = YellowPages
//                .searchOrganizationWithRole(this, organizationName, roleName);

            organizationAID = new AID(organizationName, AID.ISLOCALNAME);
            if (organizationAID != null) {
                // The organizaiton exists.
                return OK;
            } else {
                // The organization does not exist.
                errorMessage = String.format(
                    "Role can not be deacted, because the organization called '%1$s' does not exist.",
                    organizationName);
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send deact request' (single sender) state.
     * A state in which the 'Deact request' message is sent.
     */
    private class SendDeactRequest extends SingleSenderState<DeactRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { organizationAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending deact request.");
        }
        
        @Override
        protected DeactRequestMessage prepareMessage() {
            DeactRequestMessage message = new DeactRequestMessage();
            message.setRoleName(roleName);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Deact request sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive deact reply' (multi receiver) state.
     * A state in which the 'Deact reply' message is received.
     */
    private class ReceiveDeactReply extends ReceiveAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { organizationAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving deact reply.");
        }
        
        /**
         * Handles the received AGREE message
         * @param message the received AGREE message
         */
        @Override
        protected void handleAgreeMessage(SimpleMessage message) {
            getMyAgent().knowledgeBase.update().deactRole(roleName);
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Deact reply received.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Role deacted' final (one-shot) state.
     */
    private class RoleDeacted extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Deact role' protocol (id = %1$s) initiator party ended; role was enacted.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Role not deacted' final (one-shot) state.
     */
    private class RoleNotDeacted extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Deact role' protocol (id = %1$s) initiator party ended; role was not enacted.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}