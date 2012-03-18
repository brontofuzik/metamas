package thespian4jade.core.player;

import jade.core.AID;
import thespian4jade.lang.Message;
import thespian4jade.proto.Initialize;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.ReceiveSuccessOrFailure;
import thespian4jade.proto.organizationprotocol.enactroleprotocol.EnactRequestMessage;
import thespian4jade.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import thespian4jade.proto.organizationprotocol.enactroleprotocol.ResponsibilitiesInformMessage;
import thespian4jade.proto.organizationprotocol.enactroleprotocol.RoleAIDMessage;
import thespian4jade.proto.jadeextensions.State;
import thespian4jade.proto.SingleReceiverState;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.SendAgreeOrRefuse;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;

/**
 * An 'Enact role' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public class Player_EnactRoleInitiator extends InitiatorParty<Player> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The organization name. */
    private String organizationName;
    
    /** The organization AID. */
    private AID organizationAID;

    /** The role name */
    private String roleName;
    
    /** The role responsibilities. */
    private String[] responsibilities;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Player_EnactRoleInitiator(String organizationName, String roleName) {
        super(EnactRoleProtocol.getInstance());
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

    private void buildFSM() {
        // ----- States -----
        State initialize = new MyInitialize();
        State sendEnactRequest = new SendEnactRequest();
        State receiveResponsibilitiesInform = new ReceiveResponsibilitiesInform();
        State sendResponsibilitiesReply = new SendResponsibilitiesReply();
        State receiveRoleAID = new ReceiveRoleAID();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------

        // Register the states.
        registerFirstState(initialize);
        
        registerState(sendEnactRequest);
        registerState(receiveResponsibilitiesInform);
        registerState(sendResponsibilitiesReply);
        registerState(receiveRoleAID);
        
        registerLastState(successEnd);
        registerLastState(failureEnd);

        // Register the transitions.
        initialize.registerTransition(MyInitialize.OK, sendEnactRequest);
        initialize.registerTransition(MyInitialize.FAIL, failureEnd);
        
        sendEnactRequest.registerDefaultTransition(receiveResponsibilitiesInform);

        receiveResponsibilitiesInform.registerTransition(ReceiveResponsibilitiesInform.SUCCESS, sendResponsibilitiesReply);
        receiveResponsibilitiesInform.registerTransition(ReceiveResponsibilitiesInform.FAILURE, failureEnd);
        
        sendResponsibilitiesReply.registerTransition(SendResponsibilitiesReply.AGREE, receiveRoleAID);
        sendResponsibilitiesReply.registerTransition(SendResponsibilitiesReply.REFUSE, failureEnd);

        receiveRoleAID.registerDefaultTransition(successEnd);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyInitialize extends Initialize {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int initialize() {
            getMyAgent().logInfo(String.format(
                "Initiating the 'Enact role' (%1$s.%2$s) protocol.",
                organizationName, roleName));
            
//            // TAG YELLOW-PAGES
//            DFAgentDescription organization = YellowPages
//                .searchOrganizationWithRole(this, organizationName, roleName);
            
            // Check if the organization exists.
            organizationAID = new AID(organizationName, AID.ISLOCALNAME);
            if (organizationAID != null) {
                // The organization exists.
                return OK;
            } else {
                // The organization does not exist.
                String message = String.format(
                    "Error enacting a role. The organization '%1$s' does not exist.",
                    organizationName);
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send enact request' active state.
     * A state in which the 'Enact' request is sent.
     */
    private class SendEnactRequest extends SingleSenderState<EnactRequestMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { organizationAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
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
            getMyAgent().logInfo("Enact request sent.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Receive responsibilities info' (multi receiver) state.
     * A state in which the 'Responsibilities' info is received.
     */
    private class ReceiveResponsibilitiesInform extends
        ReceiveSuccessOrFailure<ResponsibilitiesInformMessage> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveResponsibilitiesInform() {
            super(new ResponsibilitiesInformMessage.Factory());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { organizationAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving responsibilities info.");
        }

        @Override
        protected void handleSuccessMessage(ResponsibilitiesInformMessage message) {
            responsibilities = message.getResponsibilities();
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibilities info received.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Send responsibilities reply' (multi sender) state.
     * A state in which the 'Agree' or 'Refuse' message is sent.
     */
    private class SendResponsibilitiesReply extends SendAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { organizationAID };
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
     * The 'Receive role AID' passive state.
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
            return new AID[] { organizationAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving role AID.");
        }
        
        @Override
        protected void handleMessage(RoleAIDMessage message) {
            AID roleAID = message.getRoleAID();
            getMyAgent().knowledgeBase.enactRole(roleName, roleAID,
                organizationAID.getLocalName(), organizationAID);
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Role AID received.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'SuccessEnd successEnd' state.
     * A state in which the 'Enact' protocol initiator party ends.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyAgent().logInfo("Enact role initiator party succeeded.");
        }

        // </editor-fold>
    }

    /**
     * The 'Fail successEnd' state.
     * A state in which the 'Enact' protocol initiator party ends.
     */
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyAgent().logInfo("Enact role initiator party failed.");
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
