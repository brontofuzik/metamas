package jadeorg.core.player;

import jade.core.AID;
import jadeorg.lang.Message;
import jadeorg.proto.Initialize;
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
        State initialize = new MyInitialize();
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
        initialize.registerTransition(MyInitialize.OK, sendEnactRequest);
        initialize.registerTransition(MyInitialize.FAIL, failureEnd);
        
        sendEnactRequest.registerDefaultTransition(receiveRequirementsInform);

        receiveRequirementsInform.registerTransition(ReceiveRequirementsInform.SUCCESS, sendRequirementsReply);
        receiveRequirementsInform.registerTransition(ReceiveRequirementsInform.FAILURE, failureEnd);
        
        sendRequirementsReply.registerTransition(SendRequirementsReply.AGREE, receiveRoleAID);
        sendRequirementsReply.registerTransition(SendRequirementsReply.REFUSE, failureEnd);

        receiveRoleAID.registerDefaultTransition(successEnd);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyInitialize extends Initialize {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int initialize() {
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
    private class SendEnactRequest extends SingleSenderState {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { organizationAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Sending enact request.");
        }
        
        @Override
        protected Message prepareMessage() {
            // Create the 'Enact request' message.
            EnactRequestMessage message = new EnactRequestMessage();
            message.setRoleName(roleName);
            return message;
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
    private class ReceiveRequirementsInform extends
        ReceiveSuccessOrFailure<RequirementsInformMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { organizationAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Receiving requirements info.");
        }
        
        @Override
        protected RequirementsInformMessage createEmptySuccessMessage() {
            return new RequirementsInformMessage();
        }

        @Override
        protected void handleSuccessMessage(RequirementsInformMessage message) {
            requirements = message.getRequirements();
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
        protected AID[] getReceivers() {
            return new AID[] { organizationAID };
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
    private class ReceiveRoleAID extends SingleReceiverState<RoleAIDMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { organizationAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Receiving role AID.");
        }
        
        @Override
        protected RoleAIDMessage createEmptyMessage() {
            return new RoleAIDMessage();
        }
        
        @Override
        protected void handleMessage(RoleAIDMessage message) {
            AID roleAID = message.getRoleAID();
            getMyPlayer().knowledgeBase.enactRole(roleName, roleAID,
                organizationAID.getLocalName(), organizationAID);
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
