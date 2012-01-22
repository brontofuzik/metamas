package jadeorg.core.player;

import jade.core.AID;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.ReceiveSuccessOrFailure;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRequestMessage;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.RequirementsInformMessage;
import jadeorg.proto.organizationprotocol.enactroleprotocol.RoleAIDMessage;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.SingleReceiverState;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.SendAgreeOrRefuse;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * An 'Enact role' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public class Player_EnactRoleInitiator extends InitiatorParty {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The organization name. */
    private String organizationName;
    
    /** The organization AID. */
    private AID organizationAID;

    /** The role name */
    private String roleName;
    
    /** The role requirements. */
    private String[] requirements;

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
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    private Player getMyPlayer() {
        return (Player)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void buildFSM() {
        // ----- States -----
        State initialize = new Initialize();
        State sendEnactRequest = new SendEnactRequest();
        State receiveRequirementsInform = new ReceiveRequirementsInform();
        State sendRequirementsReply = new SendRequirementsReply();
        State receiveRoleAID = new ReceiveRoleAID();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------

        // Register the states.
        registerFirstState(initialize);
        
        registerState(sendEnactRequest);
        registerState(receiveRequirementsInform);
        registerState(sendRequirementsReply);
        registerState(receiveRoleAID);
        
        registerLastState(successEnd);
        registerLastState(failureEnd);

        // Register the transitions.
        initialize.registerTransition(Initialize.OK, sendEnactRequest);
        initialize.registerTransition(Initialize.FAIL, failureEnd);
        
        sendEnactRequest.registerDefaultTransition(receiveRequirementsInform);

        receiveRequirementsInform.registerTransition(ReceiveRequirementsInform.SUCCESS, sendRequirementsReply);
        receiveRequirementsInform.registerTransition(ReceiveRequirementsInform.FAILURE, failureEnd);
        
        sendRequirementsReply.registerTransition(SendRequirementsReply.AGREE, receiveRoleAID);
        sendRequirementsReply.registerTransition(SendRequirementsReply.REFUSE, failureEnd);

        receiveRoleAID.registerDefaultTransition(successEnd);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class Initialize extends OneShotBehaviourState {

        public static final int OK = 0;
        public static final int FAIL = 1;
        
        private int exitValue;
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo(String.format(
                "Initiating the 'Enact role' (%1$s.%2$s) protocol.",
                organizationName, roleName));
            
//            // TAG YELLOW-PAGES
//            DFAgentDescription organization = YellowPages
//                .searchOrganizationWithRole(this, organizationName, roleName);
            
            // Check if the organization exists.
            organizationAID = new AID(organizationName, AID.ISLOCALNAME);
            if (organizationAID != null) {
                // The organization exists.
                exitValue = OK;
            } else {
                // The organization does not exist.
                String message = String.format(
                    "Error enacting a role. The organization '%1$s' does not exist.",
                    organizationName);
                exitValue = FAIL;
            }
        }
        
        @Override
        public int onEnd() {
            return exitValue;
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send enact request' active state.
     * A state in which the 'Enact' request is sent.
     */
    private class SendEnactRequest extends SingleSenderState {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getReceiverAID() {
            return organizationAID;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Sending enact request.");
        }
        
        @Override
        protected void onSingleSender() {
            // Create the 'Enact request' message.
            EnactRequestMessage message = new EnactRequestMessage();
            message.setRoleName(roleName);

            // Send the message.
            send(message, organizationAID);
        }
        
        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Enact request sent.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Receive requirements info' (multi receiver) state.
     * A state in which the 'Requirements' info is received.
     */
    private class ReceiveRequirementsInform extends ReceiveSuccessOrFailure {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getSenderAID() {
            return organizationAID;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Receiving requirements info.");
        }
        
        @Override
        protected int onSuccessReceiver() {
            // Receive the 'Requirements inform' message.
            RequirementsInformMessage message = new RequirementsInformMessage();
            boolean messageReceived = receive(message, organizationAID);

            // Process the message.
            if (messageReceived) {
                requirements = message.getRequirements();
                return InnerReceiverState.RECEIVED;
            } else {
                return InnerReceiverState.NOT_RECEIVED;
            }
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Requirements info received.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Send requirements reply' (multi sender) state.
     * A state in which the 'Agree' or 'Refuse' message is sent.
     */
    private class SendRequirementsReply extends SendAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getReceiverAID() {
            return organizationAID;
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Sending requirements reply.");
        }
        
        @Override
        protected int onManager() {
            if (getMyPlayer().evaluateRequirements(requirements)) {
                // The player invokes the requirements.
                return AGREE;
            } else {
                // The player does not invoke the requirements.
                return REFUSE;
            }
        }
        
        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Requirements reply sent.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Receive role AID' passive state.
     * A state in which the 'Role AID' requirementsInformMessage is received.
     */
    private class ReceiveRoleAID extends SingleReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getSenderAID() {
            return organizationAID;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Receiving role AID.");
        }
        
        @Override
        protected int onSingleReceiver() {
            RoleAIDMessage message = new RoleAIDMessage();
            boolean messageReceived = receive(message, organizationAID);      
            
            if (messageReceived) {
                AID roleAID = message.getRoleAID();
                getMyPlayer().knowledgeBase.enactRole(roleName, roleAID, organizationAID.getLocalName(), organizationAID);
                return InnerReceiverState.RECEIVED;
            } else {
                return InnerReceiverState.NOT_RECEIVED;
            }
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Role AID received.");
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
            getMyPlayer().logInfo("Enact role initiator party succeeded.");
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
            getMyPlayer().logInfo("Enact role initiator party failed.");
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
