package thespian4jade.core.player;

import jade.core.AID;
import thespian4jade.behaviours.states.special.ExitValueState;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;
import thespian4jade.behaviours.states.receiver.ReceiveSuccessOrFailure;
import thespian4jade.protocols.organization.enactrole.EnactRequestMessage;
import thespian4jade.protocols.organization.enactrole.ResponsibilitiesMessage;
import thespian4jade.protocols.organization.enactrole.RoleAIDMessage;
import thespian4jade.behaviours.states.IState;
import thespian4jade.behaviours.states.receiver.SingleReceiverState;
import thespian4jade.behaviours.states.sender.SingleSenderState;
import thespian4jade.behaviours.states.sender.SendAgreeOrRefuse;
import thespian4jade.behaviours.states.OneShotBehaviourState;

/**
 * An 'Enact role' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public class Player_EnactRole_InitiatorParty extends InitiatorParty<Player> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The organization in which to enact the role; more precisely its AID.
     * The responder party.
     */
    private AID organization;
    
    /**
     * The name of the organization in which to enact the role.
     */
    private String organizationName;
    
    /**
     * The name of the role to enact.
     */
    private String roleName;
    
    /**
     * The responsibilities of the role.
     */
    private String[] responsibilities;
    
    /**
     * The error message.
     */
    private String errorMessage;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Player_EnactRole_InitiatorParty class.
     * @param organizationName the name of the organization in which to enact the role
     * @param roleName the name of the role to enact
     */
    public Player_EnactRole_InitiatorParty(String organizationName, String roleName) {
        super(ProtocolRegistry.getProtocol(Protocols.ENACT_ROLE_PROTOCOL));
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
        IState sendEnactRequest = new SendEnactRequest();
        IState receiveResponsibilities = new ReceiveResponsibilities();
        IState sendResponsibilitiesReply = new SendResponsibilitiesReply();
        IState receiveRoleAID = new ReceiveRoleAID();
        IState roleEnacted = new RoleEnacted();
        IState roleNotEnacted = new RoleNotEnacted();
        // ------------------

        // Register the states.
        registerFirstState(initialize);     
        registerState(sendEnactRequest);
        registerState(receiveResponsibilities);
        registerState(sendResponsibilitiesReply);
        registerState(receiveRoleAID);       
        registerLastState(roleEnacted);
        registerLastState(roleNotEnacted);

        // Register the transitions.
        initialize.registerTransition(Initialize.OK, sendEnactRequest);
        initialize.registerTransition(Initialize.FAIL, roleNotEnacted);       
        sendEnactRequest.registerDefaultTransition(receiveResponsibilities);
        receiveResponsibilities.registerTransition(ReceiveResponsibilities.SUCCESS, sendResponsibilitiesReply);
        receiveResponsibilities.registerTransition(ReceiveResponsibilities.FAILURE, roleNotEnacted);      
        sendResponsibilitiesReply.registerTransition(SendResponsibilitiesReply.AGREE, receiveRoleAID);
        sendResponsibilitiesReply.registerTransition(SendResponsibilitiesReply.REFUSE, roleNotEnacted);
        receiveRoleAID.registerDefaultTransition(roleEnacted);
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
            getMyAgent().logInfo(String.format(
                // LOG
                "'Enact role' protocol (id = %1$s) initiator party started.",
                getProtocolId()));
            
//            // TAG YELLOW-PAGES
//            DFAgentDescription organization = YellowPages
//                .searchOrganizationWithRole(this, organizationName, roleName);
            
            // Check if the organization exists.
            organization = new AID(organizationName, AID.ISLOCALNAME);
            if (organization != null) {
                // The organization exists.
                return OK;
            } else {
                // The organization does not exist.
                errorMessage = String.format(
                    "Role can not be enacted, because the organization called '%1$s' does not exist.",
                    organizationName);
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send enact request' (single sender) state.
     * A state in which the 'Enact' request is sent.
     */
    private class SendEnactRequest extends SingleSenderState<EnactRequestMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { organization };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            // LOG
            getMyAgent().logInfo("Sending enact request.");
        }
        
        @Override
        protected EnactRequestMessage prepareMessage() {
            // Create the 'Enact request' message.
            EnactRequestMessage message = new EnactRequestMessage();
            message.setRoleName(roleName);
            return message;
        }
        
        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Enact request sent.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Receive responsibilities' (receive-success-or-failure) state.
     * A state in which a 'Responsibilities' message is received in case
     * of success, or a FAILURE message.
     */
    private class ReceiveResponsibilities extends
        ReceiveSuccessOrFailure<ResponsibilitiesMessage> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveResponsibilities() {
            super(new ResponsibilitiesMessage.Factory());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { organization };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving responsibilities info.");
        }

        @Override
        protected void handleSuccessMessage(ResponsibilitiesMessage message) {
            responsibilities = message.getResponsibilities();
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibilities info received.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Send responsibilities reply' (send-agree-or-refuse) state.
     * A state in which the 'Responsibilities reply' message is sent.
     */
    private class SendResponsibilitiesReply extends SendAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { organization };
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending responsibilities reply.");
        }
        
        @Override
        protected int onManager() {
            if (getMyAgent().evaluateResponsibilities(responsibilities)) {
                // The player meets the responsibilities.
                return AGREE;
            } else {
                // The player does not meet the responsibilities.
                return REFUSE;
            }
        }
        
        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibilities reply sent.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Receive role AID' (single receiver) state.
     * A state in which the 'Role AID' message is received.
     */
    private class ReceiveRoleAID extends SingleReceiverState<RoleAIDMessage> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveRoleAID() {
            super(new RoleAIDMessage.Factory());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { organization };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            // LOG
            getMyAgent().logInfo("Receiving role AID.");
        }
        
        @Override
        protected void handleMessage(RoleAIDMessage message) {
            AID roleAID = message.getRoleAID();
            getMyAgent().knowledgeBase.update().enactRole(roleName, roleAID,
                organization.getLocalName(), organization);
        }

        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Role AID received.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Role enacted' final (one-shot) state.
     */
    private class RoleEnacted extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Enact role' protocol (id = %1$s) initiator party ended; role was enacted.",
                getProtocolId()));
        }

        // </editor-fold>
    }

    /**
     * The 'Role not enacted' final (one-shot) state.
     */
    private class RoleNotEnacted extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Enact role' protocol (id = %1$s) initiator party ended; role was not enacted.",
                getProtocolId()));
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
