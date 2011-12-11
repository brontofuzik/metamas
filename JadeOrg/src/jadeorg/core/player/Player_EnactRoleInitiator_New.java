package jadeorg.core.player;

import jade.core.AID;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRequestMessage;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.RequirementsInformMessage;
import jadeorg.proto.organizationprotocol.enactroleprotocol.RoleAIDMessage;
import jadeorg.proto_new.State;
import jadeorg.proto_new.toplevel.MultiReceiverState;
import jadeorg.proto_new.toplevel.MultiSenderState;
import jadeorg.proto_new.toplevel.SimpleState;
import jadeorg.proto_new.toplevel.SingleReceiverState;
import jadeorg.proto_new.toplevel.SingleSenderState;

/**
 * An 'Enact role' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public class Player_EnactRoleInitiator_New extends Party {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "enact-role-initiator-new";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">

    /** The organization AID. */
    private AID organizationAID;

    /** The role name */
    private String roleName;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Player_EnactRoleInitiator_New(AID organization, String roleName) {
        super(NAME);
        // ----- Preconditions -----
        assert organization != null;
        assert roleName != null && !roleName.isEmpty();
        // -------------------------

        this.organizationAID = organization;
        this.roleName = roleName;

        registerStatesAndTransitions();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @Override
    protected Protocol getProtocol() {
        return EnactRoleProtocol.getInstance();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void registerStatesAndTransitions() {
        // ----- States -----
        State sendEnactRequest = new SendEnactRequest();
        State receiveRequirementsInform = new ReceiveRequirementsInform();
        State sendRequirementsReply = new SendRequirementsReply();
        State receiveRoleAID = new ReceiveRoleAID();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------

        // Register the states.
        registerFirstState(sendEnactRequest);
        registerState(receiveRequirementsInform);
        registerState(sendRequirementsReply);
        registerState(receiveRoleAID);
        registerLastState(successEnd);
        registerLastState(failureEnd);

        // Register the transitions (NEW).
        sendEnactRequest.registerDefaultTransition(receiveRequirementsInform);

        receiveRequirementsInform.registerTransition(ReceiveRequirementsInform.OK, sendRequirementsReply);
        receiveRequirementsInform.registerTransition(ReceiveRequirementsInform.FAILURE, failureEnd);
        
        sendRequirementsReply.registerTransition(SendRequirementsReply.OK, receiveRoleAID);
        sendRequirementsReply.registerTransition(SendRequirementsReply.FAILURE, failureEnd);

        receiveRoleAID.registerDefaultTransition(successEnd);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Send enact request' active state.
     * A state in which the 'Enact' request is sent.
     */
    private class SendEnactRequest extends SingleSenderState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-enact-request";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendEnactRequest() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            ((Player)myAgent).logInfo("Sending enact request.");
        }
        
        @Override
        protected void onSender() {
            EnactRequestMessage message = new EnactRequestMessage();
            message.setReceiverOrganization(organizationAID);
            message.setRoleName(roleName);

            send(EnactRequestMessage.class, message);
        }
        
        @Override
        protected void onExit() {
            ((Player)myAgent).logInfo("Enact request sent.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Receive requirements info' passive state.
     * A state in which the 'Requirements' info is received.
     */
    private class ReceiveRequirementsInform extends MultiReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        // ----- Exit values -----
        public static final int OK = 1;
        public static final int FAILURE = 2;
        // -----------------------
        
        private static final String NAME = "receive-requirements-inform";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveRequirementsInform() {
            super(NAME);
            
            addReceiver(OK, new ReceiveRequirementsInform_Receiver());
            addReceiver(FAILURE, new ReceiveFailure());
            buildFSM();
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            ((Player)myAgent).logInfo("Receiving requirements info.");
        }

        @Override
        protected void onExit() {
            ((Player)myAgent).logInfo("Requirements info received.");
        }

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Classes">
        
        private class ReceiveRequirementsInform_Receiver extends BottomLevelReceiverState {

            // <editor-fold defaultstate="collapsed" desc="Fields">
            
            private static final String NAME = "receiver";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            ReceiveRequirementsInform_Receiver() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                RequirementsInformMessage requirementsInformMessage = (RequirementsInformMessage)
                    receive(RequirementsInformMessage.class, organizationAID);
                if (requirementsInformMessage != null) {
                    setExitValue(OK);
                } else {
                    setExitValue(FAILURE);
                }
            }
            
            // </editor-fold>
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send agree' active state.
     * A state in which the 'Agree' requirementsInformMessage is send.
     */
    private class SendRequirementsReply extends MultiSenderState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-requirements-reply";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendRequirementsReply() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            ((Player)myAgent).logInfo("Sending requirements reply.");

            // Create the 'Requirements reply' JadeOrg message.
            ACLMessageWrapper requirementsReplyMessage = EnactRoleProtocol.getInstance()
                .getACLMessageWrapper(ACLMessage.AGREE);
            System.out.println(requirementsReplyMessage.getWrappedACLMessage().getProtocol());
            requirementsReplyMessage.addReceiver(organizationAID);

            send(ACLMessageWrapper.class, requirementsReplyMessage);

            ((Player)myAgent).logInfo("Requirements reply sent.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Receive role AID' passive state.
     * A state in which the 'Role AID' requirementsInformMessage is received.
     */
    private class ReceiveRoleAID extends SingleReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Fields">

        private static final String NAME = "receive-role-aid";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveRoleAID() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            ((Player)myAgent).logInfo("Receiving role AID.");
        }
        
        @Override
        protected void onReceiver() {
            RoleAIDMessage roleAIDMessage = (RoleAIDMessage)
                receive(RoleAIDMessage.class, organizationAID);
            
            if (roleAIDMessage != null) {
                AID roleAID = roleAIDMessage.getRoleAID();
                ((Player)myAgent).knowledgeBase.enactRole(roleName, roleAID, organizationAID.getLocalName(), organizationAID);
            }
        }

        @Override
        protected void onExit() {
            ((Player)myAgent).logInfo("Role AID received.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'SuccessEnd successEnd' state.
     * A state in which the 'Enact' protocol initiator party ends.
     */
    private class SuccessEnd extends SimpleState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "success-end";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SuccessEnd() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            ((Player)myAgent).logInfo("Enact role initiator party succeeded.");
        }

        // </editor-fold>
    }

    /**
     * The 'Fail successEnd' state.
     * A state in which the 'Enact' protocol initiator party ends.
     */
    private class FailureEnd extends SimpleState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "failure-end";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        FailureEnd() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            ((Player)myAgent).logInfo("Enact role initiator party failed.");
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
