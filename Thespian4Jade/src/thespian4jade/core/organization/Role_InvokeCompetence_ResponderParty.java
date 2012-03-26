package thespian4jade.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.organization.competence.ICompetence;
import thespian4jade.behaviours.states.special.ExitValueState;
import thespian4jade.behaviours.parties.ResponderParty;
import thespian4jade.behaviours.states.sender.SendSuccessOrFailure;
import thespian4jade.behaviours.states.receiver.SingleReceiverState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;
import thespian4jade.protocols.role.invokecompetence.InvokeCompetenceRequestMessage;
import thespian4jade.protocols.role.invokecompetence.CompetenceArgumentMessage;
import thespian4jade.protocols.role.invokecompetence.ArgumentRequestMessage;
import thespian4jade.protocols.role.invokecompetence.CompetenceResultMessage;
import java.io.Serializable;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;
import thespian4jade.utililites.ClassHelper;

/**
 * An 'Invoke competence' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Role_InvokeCompetence_ResponderParty<TArgument extends Serializable,
    TResult extends Serializable> extends ResponderParty<Role> {
 
    // <editor-fold defaultstate="collapsed" desc="Fields"> 
    
    /**
     * The player requesting the competence invocation; more precisely its AID.
     * The initiator party.
     */
    private AID player;
    
    /**
     * The name of the competence.
     */
    private String competenceName;
    
    /**
     * The 'Receive competence argument' state.
     */
    private IState receiveCompetenceArgument;
    
    /**
     * The competence state.
     */
    private ICompetence competence;
    
    /**
     * The 'Send competence result' state.
     */
    private IState sendCompetenceResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Role_InvokeCompetence_ResponderParty class.
     * @param aclMessage the received ACL message
     */
    public Role_InvokeCompetence_ResponderParty(ACLMessage aclMessage) {
        super(ProtocolRegistry.getProtocol(Protocols.INVOKE_COMPETENCE_PROTOCOL), aclMessage);
        
        player = getACLMessage().getSender();
        
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
        IState receiveInvokeCompetenceRequest = new ReceiveInvokeCompetenceRequest();
        IState sendCompetenceArgumentRequest = new SendCompetenceArgumentRequest();
        receiveCompetenceArgument = new ReceiveCompetenceArgument();
        sendCompetenceResult = new SendCompetenceResult();
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();
        // ------------------
        
        // Register states.
        registerFirstState(initialize);       
        registerState(receiveInvokeCompetenceRequest);      
        registerState(sendCompetenceArgumentRequest);
        registerState(receiveCompetenceArgument);
        registerState(sendCompetenceResult);       
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register transitions.
        initialize.registerTransition(Initialize.OK, receiveInvokeCompetenceRequest);
        initialize.registerTransition(Initialize.FAIL, failureEnd);       
        receiveInvokeCompetenceRequest.registerDefaultTransition(sendCompetenceArgumentRequest);       
        sendCompetenceArgumentRequest.registerTransition(SendCompetenceArgumentRequest.SUCCESS, receiveCompetenceArgument);
        sendCompetenceArgumentRequest.registerTransition(SendCompetenceArgumentRequest.FAILURE, failureEnd);       
        sendCompetenceResult.registerTransition(SendCompetenceResult.SUCCESS, successEnd);
        sendCompetenceResult.registerTransition(SendCompetenceResult.FAILURE, failureEnd);
    }
    
    /**
     * Selects a competence specified by its name.
     * @param competenceName the name of the competence to select
     */
    private void selectCompetence(String competenceName) {
//        System.out.println("----- competenceName: " + competenceName + " -----");
        
        Class competenceClass = getMyAgent().competences.get(competenceName);
//        System.out.println("----- competenceClass: " + competenceClass + " -----");

        competence = ClassHelper.instantiateClass(competenceClass);
        
        // Register the competence-related states.
        registerState(competence);
        
        // Register the competence-related transitions.
        receiveCompetenceArgument.registerDefaultTransition(competence);
        competence.registerDefaultTransition(sendCompetenceResult);
        //System.out.println("----- COMPETENCE ADDED -----");
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
            getMyAgent().logInfo(String.format(
                "Responding to the 'Invoke competence' protocol (id = %1$s).",
                getProtocolId()));
        
            if (player.equals(getMyAgent().enactingPlayer)) {
                // The initiator player is enacting this role.
                return OK;
            } else {
                // The initiator player is not enacting this role.
                // TODO (priority: low) Send a message to the player exaplaining
                // that a competence cannot be invoked on a non-enacted role.
                return FAIL;
            }
        }      
    
        // </editor-fold>
    }
    
    private class ReceiveInvokeCompetenceRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            InvokeCompetenceRequestMessage message = new InvokeCompetenceRequestMessage();
            message.parseACLMessage(getACLMessage());
            
            competenceName = message.getCompetence();                       
            selectCompetence(competenceName);
        }
        
        // </editor-fold>
    }
    
    private class SendCompetenceArgumentRequest
        extends SendSuccessOrFailure<ArgumentRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Send competence argument request.");
        }

        @Override
        protected int onManager() {
            return SUCCESS;
        }
        
        @Override
        protected ArgumentRequestMessage prepareMessage() {
            ArgumentRequestMessage message = new ArgumentRequestMessage();
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Competence argument request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveCompetenceArgument
        extends SingleReceiverState<CompetenceArgumentMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveCompetenceArgument() {
            super(new CompetenceArgumentMessage.Factory());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving competence argument.");
        }
        
        @Override
        protected void handleMessage(CompetenceArgumentMessage message) {
            competence.setArgument(message.getArgument());
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Competence argument received.");
        }
        
        // </editor-fold>
    }
    
    private class SendCompetenceResult
        extends SendSuccessOrFailure<CompetenceResultMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending competence result.");
        }

        @Override
        protected int onManager() {
            return SUCCESS;
        }
        
        @Override
        protected CompetenceResultMessage prepareMessage() {
            CompetenceResultMessage message = new CompetenceResultMessage();
            message.setResult(competence.getResult());
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Competence result sent.");
        }
        
        // </editor-fold>
    }
    
    private class SuccessEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo("The 'Invoke competence' responder party succeeded.");
        }
        
        // </editor-fold>
    }
    
    private class FailureEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo("The 'Invoke competence' responder party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
