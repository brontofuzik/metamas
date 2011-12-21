package jadeorg.core.player;

import jade.core.AID;
import jadeorg.proto.ActiveState;
import jadeorg.proto.Party;
import jadeorg.proto.PassiveState;
import jadeorg.proto.Protocol;
import jadeorg.proto.State;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRequestMessage;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.RequirementsInformMessage;
import jadeorg.proto.organizationprotocol.enactroleprotocol.RoleAIDMessage;

/**
 * An 'Enact role' protocol initiator party. 
 * @author Lukáš Kúdela
 * @since 2011-12-09
 * @version %I% %G%
 */
class Player_EnactRoleInitiator extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">

    private static final String NAME = "enact-protocol-initiator";

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fields">

    /** The organization AID. */
    private AID organizationAID;

    /** The role name */
    private String roleName;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    Player_EnactRoleInitiator(AID organization, String roleName) {
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
//        // ----- States -----
//        State sendEnactRequest = new SendEnactRequest();
//        State receiveRequirementsInform = new ReceiveRequirementsInform();
//        State sendRequirementsReply = new SendRequirementsReply();
//        State sendFailure = new SendFailure();
//        State receiveRoleAID = new ReceiveRoleAID();
//        State successEnd = new SuccessEnd();
//        State failureEnd = new FailureEnd();
//        // ------------------
//
//        // Register the states.
//        registerFirstState(sendEnactRequest);
//        registerState(receiveRequirementsInform);
//        registerState(sendRequirementsReply);
//        registerState(sendFailure);
//        registerState(receiveRoleAID);
//        registerLastState(successEnd);
//        registerLastState(failureEnd);
//
//        // Register the transitions (OLD).
//        registerDefaultTransition(sendEnactRequest, receiveRequirementsInform);
//
//        registerTransition(receiveRequirementsInform, sendRequirementsReply, PassiveState.Event.SUCCESS);
//        registerTransition(receiveRequirementsInform, sendFailure, PassiveState.Event.FAILURE);
//
//        registerDefaultTransition(sendRequirementsReply, receiveRoleAID);
//
//        registerDefaultTransition(sendFailure, failureEnd);
//
//        registerDefaultTransition(receiveRoleAID, successEnd);
//
////            // Register the transitions (NEW).
////            sendEnactRequest.registerDefaultTransition(receiveRequirementsInfo);
////            
////            receiveRequirementsInfo.registerTransition(PassiveState.Event.SUCCESS, sendAgree);
////            receiveRequirementsInfo.registerTransition(PassiveState.Event.FAILURE, sendRefuse);
////            
////            sendAgree.registerDefaultTransition(receiveRoleAID);
////            
////            sendRefuse.registerDefaultTransition(failureEnd);
////            
////            receiveRoleAID.registerDefaultTransition(successEnd);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * The 'Send enact request' active state.
     * A state in which the 'Enact' request is sent.
     */
    private class SendEnactRequest extends ActiveState {

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
        public void action() {
//            ((Player)myAgent).logInfo("Sending enact request.");
//            EnactRequestMessage message = new EnactRequestMessage();
//            message.setReceiverOrganization(organizationAID);
//            message.setRoleName(roleName);
//
//            send(EnactRequestMessage.class, message);
//            ((Player)myAgent).logInfo("Enact request sent.");
        }

        // </editor-fold>
    }

    /**
     * The 'Receive requirements info' passive state.
     * A state in which the 'Requirements' info is received.
     */
    private class ReceiveRequirementsInform extends PassiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "receive-requirements-inform";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveRequirementsInform() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            ((Player)myAgent).logInfo("Receiving requirements info.");

            // TODO A 'Refuse' (ACL) message can be received as well.
            RequirementsInformMessage requirementsInformMessage = (RequirementsInformMessage)
                receive(RequirementsInformMessage.class, organizationAID);

            if (requirementsInformMessage != null) {
                ((Player)myAgent).logInfo("Requirements info received.");

                if (((Player)myAgent).evaluateRequirements(requirementsInformMessage.getRequirements())) {
                    // The player meets the requirements.
                    setExitValue(Event.SUCCESS);
                } else {
                    // The player does not meet the requirements.
                    setExitValue(Event.FAILURE);
                }
            } else {
                loop();
            }
        }

        // </editor-fold>
    }

    /**
     * The 'Send agree' active state.
     * A state in which the 'Agree' requirementsInformMessage is send.
     */
    private class SendRequirementsReply extends ActiveState {

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
//            ((Player)myAgent).logInfo("Sending requirements reply.");
//
//            // Create the 'Requirements reply' JadeOrg message.
//            AgreeMessage requirementsReplyMessage = new AgreeMessage();
//            requirementsReplyMessage.addReceiver(organizationAID);
//
//            // Send the message.
//            send(AgreeMessage.class, requirementsReplyMessage);
//
//            ((Player)myAgent).logInfo("Requirements reply sent.");
        }

        // </editor-fold>
    }

    /**
     * The 'Send failure' active state,
     * A state in which the 'Refuse' requirementsInformMessage is send.
     */
    private class SendFailure extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-failure";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendFailure() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
//            // Create the 'Failure' message.
//            FailureMessage failureMessage = new FailureMessage();
//            failureMessage.addReceiver(organizationAID);
//
//            // Send the message.
//            send(FailureMessage.class, failureMessage);
        }

        // </editor-fold>
    }

    /**
     * The 'Receive role AID' passive state.
     * A state in which the 'Role AID' requirementsInformMessage is received.
     */
    private class ReceiveRoleAID extends PassiveState {

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
        public void action() {
            ((Player)myAgent).logInfo("Receiving role AID.");

            RoleAIDMessage roleAIDMessage = (RoleAIDMessage)
                receive(RoleAIDMessage.class, organizationAID);

            if (roleAIDMessage != null) {
                ((Player)myAgent).logInfo("Role AID received.");

                AID roleAID = roleAIDMessage.getRoleAID();
                ((Player)myAgent).knowledgeBase.enactRole(roleName, roleAID, organizationAID.getLocalName(), organizationAID);
                setExitValue(Event.SUCCESS);
            } else {
                block();
            }
        }

        // </editor-fold>
    }

    /**
     * The 'SuccessEnd successEnd' state.
     * A state in which the 'Enact' protocol initiator party ends.
     */
    private class SuccessEnd extends ActiveState {

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
    private class FailureEnd extends ActiveState {

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
