package jadeorg.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import jadeorg.lang.SimpleMessage;
import jadeorg.proto.ActiveState;
import jadeorg.proto.Party;
import jadeorg.proto.PassiveState;
import jadeorg.proto.Protocol;
import jadeorg.proto.State;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRequestMessage;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.RequirementsInformMessage;
import jadeorg.proto.organizationprotocol.enactroleprotocol.RoleAIDMessage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * An 'Enact role' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2011-12-09
 * @version %I% %G%
 */
class Organization_EnactRoleResponder extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">

    private static final String NAME = "enact-role-responder";

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fields">

    private AID playerAID;

    private String roleName;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    Organization_EnactRoleResponder(AID player) {
        super(NAME);
        // ----- Preconditions -----
        assert player != null;
        // -------------------------

        this.playerAID = player;

        registerStatesAndTransitions();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    @Override
    protected Protocol getProtocol() {
        return EnactRoleProtocol.getInstance();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Registers the states and transitions.
     */
    private void registerStatesAndTransitions() {
//        // ----- States -----
//        State receiveEnactRequest = new ReceiveEnactRequest();
//        State sendRequirementsInform = new SendRequirementsInform();
//        State sendFailure = new SendFailure();
//        State receiveRequirementsReply = new ReceiveRequirementsReply();
//        State sendRoleAID = new SendRoleAID();
//        State successEnd = new SuccessEnd();
//        State failureEnd = new FailureEnd();
//        // ------------------
//
//        // Register the states.
//        registerFirstState(receiveEnactRequest);
//        registerState(sendRequirementsInform);
//        registerState(sendFailure);
//        registerState(receiveRequirementsReply);
//        registerState(sendRoleAID);
//        registerLastState(successEnd);
//
//        // Register the transitions (OLD).
//        registerTransition(receiveEnactRequest, sendRequirementsInform, PassiveState.Event.SUCCESS);
//        registerTransition(receiveEnactRequest, sendFailure, PassiveState.Event.FAILURE);
//
//        registerDefaultTransition(sendRequirementsInform, receiveRequirementsReply);
//
//        registerTransition(receiveRequirementsReply, sendRoleAID, PassiveState.Event.SUCCESS);
//        registerTransition(receiveRequirementsReply, failureEnd, PassiveState.Event.FAILURE);
//
//        registerDefaultTransition(sendRoleAID, successEnd);
//
//        registerDefaultTransition(sendFailure, failureEnd);
//
////            // Register the transitions (NEW).
////            receiveEnactRequest.registerTransition(0, sendRequirementsInform);
////            receiveEnactRequest.registerTransition(1, sendFailure);
////            
////            sendRequirementsInform.registerDefaultTransition(receiveRequirementsInform);
////            
////            receiveRequirementsInform.registerTransition(0, sendRoleAID);
////            receiveRequirementsInform.registerTransition(1, sendFailure);   
////            
////            sendRoleAID.registerDefaultTransition(end);
////            
////            sendFailure.registerDefaultTransition(end);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * The state in which the 'Enact' message is received.
     */
    private class ReceiveEnactRequest extends PassiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "receive-enact-request";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveEnactRequest() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            ((Organization)myAgent).logInfo("Receiving enact request.");

            EnactRequestMessage enactRequestMessage = (EnactRequestMessage)
                receive(EnactRequestMessage.class, playerAID);

            if (enactRequestMessage != null) {
                ((Organization)myAgent).logInfo("Enact request received.");
                roleName = enactRequestMessage.getRoleName();

                if (((Organization)myAgent).roles.containsKey(roleName)) {
                    // The role is defined for this organizaiton.
                    if (!((Organization)myAgent).knowledgeBase.isRoleEnacted(roleName)) {
                        // The role is not yet enacted.
                        setExitValue(Event.SUCCESS);
                    } else {
                        // The role is already enacted.
                        setExitValue(Event.FAILURE);
                    }
                } else {
                    // The role is not defined for this organization.
                    setExitValue(Event.FAILURE);
                }
            }  else {
                loop();
            }
        }

        // </editor-fold>
    }

    /**
     * The state in which the 'Requirements' message is send.
     */
    private class SendRequirementsInform extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-requirements-inform";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendRequirementsInform() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
//            ((Organization)myAgent).logInfo("Sending requirements inform.");
//
//            // Create the 'Requirements' message.
//            RequirementsInformMessage requirementsInformMessage = new RequirementsInformMessage();
//            requirementsInformMessage.setReceiverPlayer(playerAID);
//
//            requirementsInformMessage.setRequirements(((Organization)myAgent).requirements.get(roleName));
//
//            // Send the 'Requirements' message.
//            send(RequirementsInformMessage.class, requirementsInformMessage);
//
//            ((Organization)myAgent).logInfo("Requirements inform sent.");
        }

        // </editor-fold>
    }

    /**
     * The state in which the 'Refuse' message is send.
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
//            ((Organization)myAgent).logInfo("Sending failure.");
//
//            // Create the 'Failure' message.
//            FailureMessage failureMessage = new FailureMessage();
//            failureMessage.addReceiver(playerAID);
//
//            // Send the message.
//            send(FailureMessage.class, failureMessage);
//            
//            ((Organization)myAgent).logInfo("Failure sent");
        }

        // </editor-fold>
    }

    /**
     * The state in which the 'Requirements' message is received.
     */
    private class ReceiveRequirementsReply extends PassiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "receive-requirements-reply";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveRequirementsReply() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            ((Organization)myAgent).logInfo("Receiving requirements reply.");

            SimpleMessage requirementsReplyMessage = (SimpleMessage)
                receive(SimpleMessage.class, playerAID);
            if (requirementsReplyMessage != null) {
                ((Organization)myAgent).logInfo("Requirements reply received.");

                if (requirementsReplyMessage.getPerformative() == ACLMessage.AGREE) {
                    setExitValue(Event.SUCCESS);
                } else if (requirementsReplyMessage.getPerformative() == ACLMessage.REFUSE) {
                    setExitValue(Event.FAILURE);
                }
            } else {
                loop();
            }
        }

        // </editor-fold>
    }

    /**
     * The state in which the 'Role AID' message is send.
     */
    private class SendRoleAID extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-role-aid";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendRoleAID() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
//            ((Organization)myAgent).logInfo("Creating role agent.");
//
//            Role role = createRoleAgent(roleName, roleName);
//            role.setPlayerAID(playerAID);
//            startRoleAgent(role);
//
//            ((Organization)myAgent).knowledgeBase.updateRoleIsEnacted(role, playerAID);
//
//           ((Organization)myAgent).logInfo("Role agent created.");
//
//            // TODO Consider moving the following section to a separate state.
//
//            ((Organization)myAgent).logInfo("Sending role AID.");
//
//            // Create the 'RoleAID' message.
//            RoleAIDMessage roleAIDMessage = new RoleAIDMessage();
//            roleAIDMessage.setReceiverPlayer(playerAID);
//            roleAIDMessage.setRoleAID(role.getAID());
//
//            // Send the 'RoleAID' message.
//            send(RoleAIDMessage.class, roleAIDMessage);
//            ((Organization)myAgent).logInfo("Role AID sent.");
        }

        // ---------- PRIVATE ----------

        /**
         * Create a role agent.
         * @param roleClassName the name of the role agent class.
         * @param roleName the name of the role agent instance.
         * @return the role agent.
         */
        private Role createRoleAgent(String roleClassName, String roleName) {
            Class roleClass = ((Organization)myAgent).roles.get(roleClassName);
            //System.out.println("ROLE CLASS: " + roleClass);
            Class organizationClass = myAgent.getClass();
            //System.out.println("ORGANIZATION CLASS: " + organizationClass);

            Constructor roleConstructor = null;
            try {
                roleConstructor = roleClass.getConstructor(organizationClass);
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            } catch (SecurityException ex) {
                ex.printStackTrace();
            }
            //System.out.println("CTOR: " + roleConstructor.getParameterTypes());
            Role role = null;
            try {
                role = (Role)roleConstructor.newInstance(myAgent);
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }
            //System.out.println("ROLE: " + role);
            role.setRoleName(roleName);
            role.setMyOrganization((Organization)myAgent);
            return role;
        }

        private void startRoleAgent(Role role) {
            AgentController agentController = null;
            try {
                agentController = myAgent.getContainerController().acceptNewAgent(roleName, role);
                agentController.start();
            } catch (StaleProxyException ex) {
                ex.printStackTrace();
            }
        }

        // </editor-fold>    
    }

    /**
     * The 'Success end' state.
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
            ((Organization)myAgent).logInfo("Enact role responder party succeeded.");
        }

        // </editor-fold>           
    }

    /**
     * The 'Failure end' state.
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
            ((Organization)myAgent).logInfo("Enact role responder party failed.");
        }

        // </editor-fold>        
    }

    // </editor-fold>
}
