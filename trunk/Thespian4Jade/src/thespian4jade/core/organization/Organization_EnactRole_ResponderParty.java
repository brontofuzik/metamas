package thespian4jade.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import thespian4jade.protocols.organization.enactrole.EnactRequestMessage;
import thespian4jade.protocols.organization.enactrole.ResponsibilitiesMessage;
import thespian4jade.protocols.organization.enactrole.RoleAIDMessage;
import thespian4jade.behaviours.states.IState;
import thespian4jade.behaviours.states.sender.SingleSenderState;
import thespian4jade.behaviours.states.receiver.ReceiveAgreeOrRefuse;
import thespian4jade.behaviours.parties.ResponderParty;
import thespian4jade.behaviours.states.sender.SendSuccessOrFailure;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.core.Event;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;
import thespian4jade.utililites.ClassHelper;

/**
 * An 'Enact role' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public class Organization_EnactRole_ResponderParty extends ResponderParty<Organization> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The player requesting the role enactment; more precisely its AID.
     * The initiator party.
     */
    private AID player;

    /**
     * The name of the role to enact.
     */
    private String roleName;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Organization_EnactRole_ResponderParty class.
     * @param aclMessage the ACL message initiating the protocol
     */
    public Organization_EnactRole_ResponderParty(ACLMessage aclMessage) {
        super(ProtocolRegistry.getProtocol(Protocols.ENACT_ROLE_PROTOCOL), aclMessage);
        
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
        IState receiveEnactRequest = new ReceiveEnactRequest();
        IState sendResponsibilities = new SendResponsibilities();
        IState receiveResponsibilitiesReply = new ReceiveResponsibilitiesReply();
        IState sendRoleAID = new SendRoleAID();
        IState roleEnacted = new RoleEnacted();
        IState roleNotEnacted = new RoleNotEnacted();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);        
        registerState(receiveEnactRequest);
        registerState(sendResponsibilities);
        registerState(receiveResponsibilitiesReply);
        registerState(sendRoleAID);     
        registerLastState(roleEnacted);
        registerLastState(roleNotEnacted);
        
        // Register the transitions.
        initialize.registerDefaultTransition(receiveEnactRequest);  
        receiveEnactRequest.registerDefaultTransition(sendResponsibilities);
        sendResponsibilities.registerTransition(SendResponsibilities.SUCCESS, receiveResponsibilitiesReply);
        sendResponsibilities.registerTransition(SendResponsibilities.FAILURE, roleNotEnacted);       
        receiveResponsibilitiesReply.registerTransition(ReceiveResponsibilitiesReply.AGREE, sendRoleAID);
        receiveResponsibilitiesReply.registerTransition(ReceiveResponsibilitiesReply.REFUSE, roleNotEnacted);   
        sendRoleAID.registerDefaultTransition(roleEnacted);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Initialize' initial (one-shot) state.
     * A state in which the party is initialized.
     */
    private class Initialize extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Enact role' protocol (id = %1$s) responder party started.",
                getProtocolId()));
            
            player = getACLMessage().getSender();
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive enact request' (sone-shot) state.
     * A state in which the 'Enact' request is received.
     */
    private class ReceiveEnactRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {  
            // Receive the 'Enact request' message.
            EnactRequestMessage message = new EnactRequestMessage();
            message.parseACLMessage(getACLMessage());
            
            roleName = message.getRoleName();
        }
        
        // </editor-fold>        
    }
    
    /**
     * The 'Send responsibilities' (send-success-or-failure) state.
     * A state in which a 'Responsibilities' message is sent in sent in case
     * of success, or a FAILURE message.
     */
    private class SendResponsibilities
        extends SendSuccessOrFailure<ResponsibilitiesMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending responsibilities inform.");
        }
        
        @Override
        protected int onManager() {
            if (getMyAgent().roles.containsKey(roleName)) {
                // The role is defined for this organizaiton.
                if (!getMyAgent().knowledgeBase.query().isRoleEnacted(roleName)
                    || getMyAgent().roles.get(roleName).getMultiplicity() == RoleMultiplicity.MULTIPLE) {
                    // The role is not yet enacted.
                    return SUCCESS;
                } else {
                    // The role is already enacted.
                    return FAILURE;
                }
            } else {
                // The role is not defined for this organization.
                return FAILURE;
            }
        }
        
        @Override
        protected ResponsibilitiesMessage prepareMessage() {
            // Create the 'Responsibilities inform' message.
            ResponsibilitiesMessage message = new ResponsibilitiesMessage();
            message.setResponsibilities(getMyAgent().roles.get(roleName).getResponsibilities());
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibilities inform sent.");
        }
      
        // </editor-fold>
    }
    
    /**
     * The 'Receive responsibilities reply' (receive-agree-or-refuse) state.
     * A state in which the reply message (AGREE or REFUSE) is received.
     */
    private class ReceiveResponsibilitiesReply extends ReceiveAgreeOrRefuse {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving responsibilities reply.");
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibilities reply received.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send role AID' (single sender) state.
     * A state in which the 'Role AID' message is sent.
     */
    private class SendRoleAID extends SingleSenderState<RoleAIDMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending role AID.");
        }
        
        @Override
        protected RoleAIDMessage prepareMessage() {
            getMyAgent().logInfo("Creating role agent.");
            
            // Create the role agent.
            Class roleClass = getMyAgent().roles.get(roleName).getRoleClass();
            Role role = ClassHelper.instantiateClass(roleClass);
            
            // Link the position to its organization.
            getMyAgent().addPosition(role);
            
            // Link the position to its player.
            role.setEnactingPlayer(player);
            
            // Start the role agent.
            startRoleAgent(role);
            
            // Update the knowledge base.
            getMyAgent().knowledgeBase.update().roleIsEnacted(roleName, role.getAID(), player);
            
            getMyAgent().logInfo("Role agent created.");
            
            // Create the 'RoleAID' message.
            RoleAIDMessage roleAIDMessage = new RoleAIDMessage();
            roleAIDMessage.setRoleAID(role.getAID());

            return roleAIDMessage;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Role AID sent.");
        }
        
        // ---------- PRIVATE ----------

        private void startRoleAgent(Role roleAgent) {
            //System.out.println("----- STARTING ROLE AGENT: " + roleAgent.getNickname() + " -----");
            try {
                AgentController agentController = myAgent.getContainerController()
                    .acceptNewAgent(roleAgent.getNickname(), roleAgent);
                agentController.start();
            } catch (StaleProxyException ex) {
                ex.printStackTrace();
            }    
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
            // Publish the 'Role enacted' event.
            getMyAgent().publishEvent(Event.ROLE_ENACTED, roleName, player);
            
            // LOG
            getMyAgent().logInfo(String.format(
                "'Enact role' protocol (id = %1$s) responder party ended; role was enacted.",
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
                "'Enact role' protocol (id = %1$s) responder party ended; role was not enacted.",
                getProtocolId()));
        }

        // </editor-fold>        
    }
    
    // </editor-fold>
}
