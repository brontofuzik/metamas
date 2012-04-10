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
import thespian4jade.behaviours.states.special.WrapperState;
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
     * The competence argument.
     */
    private TArgument argument;
    
    /**
     * The competence result.
     */
    private TResult result;
    
    /**
     * The 'Receive competence argument' state.
     */
    private IState receiveCompetenceArgument;
    
    /**
     * The 'Send competence result' state.
     */
    private IState sendCompetenceResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Role_InvokeCompetence_ResponderParty class.
     * @param message the ACL message initiating the protocol
     */
    public Role_InvokeCompetence_ResponderParty(ACLMessage message) {
        super(ProtocolRegistry.getProtocol(Protocols.INVOKE_COMPETENCE_PROTOCOL), message);
                
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
        IState selectCompetence = new SelectCompetence();
        IState sendCompetenceArgumentRequest = new SendCompetenceArgumentRequest();
        receiveCompetenceArgument = new ReceiveCompetenceArgument();
        sendCompetenceResult = new SendCompetenceResult();
        IState competenceInvoked = new CompetenceInvoked();
        IState competenceNotInvoked = new CompetenceNotInvoked();
        // ------------------
        
        // Register states.
        registerFirstState(initialize);       
        registerState(receiveInvokeCompetenceRequest);   
        registerState(selectCompetence);
        registerState(sendCompetenceArgumentRequest);
        registerState(receiveCompetenceArgument);
        registerState(sendCompetenceResult);       
        registerLastState(competenceInvoked);
        registerLastState(competenceNotInvoked);
        
        // Register transitions.
        initialize.registerDefaultTransition(receiveInvokeCompetenceRequest);
        receiveInvokeCompetenceRequest.registerTransition(ReceiveInvokeCompetenceRequest.ROLE_ACTIVE, selectCompetence);
        receiveInvokeCompetenceRequest.registerTransition(ReceiveInvokeCompetenceRequest.ROLE_NOT_ACTIVE, competenceNotInvoked);
        selectCompetence.registerTransition(SelectCompetence.COMPETENCE_EXISTS, sendCompetenceArgumentRequest);
        selectCompetence.registerTransition(SelectCompetence.COMPETENCE_DOES_NOT_EXIST, competenceNotInvoked);
        sendCompetenceArgumentRequest.registerDefaultTransition(receiveCompetenceArgument);
        sendCompetenceResult.registerDefaultTransition(competenceInvoked);
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
                "'Invoke competence' protocol (id = %1$s) initiator party started.",
                getProtocolId()));
        
            player = getACLMessage().getSender();
        }      
    
        // </editor-fold>
    }
    
    /**
     * The 'Receive invoke competence request' (single receiver) state.
     * A state in which the 'Invoke competence request' is received.
     */
    private class ReceiveInvokeCompetenceRequest extends ExitValueState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit values -----
        public static final int ROLE_ACTIVE = 1;
        public static final int ROLE_NOT_ACTIVE = 2;
        // -----------------------
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected int doAction() {
            if (player.equals(getMyAgent().enactingPlayer)) {
                // The initiator player is enacting this role.
                InvokeCompetenceRequestMessage message = new InvokeCompetenceRequestMessage();
                message.parseACLMessage(getACLMessage());
            
                competenceName = message.getCompetenceName();  
                return ROLE_ACTIVE;
            } else {
                // The initiator player is not enacting this role.
                // TODO (priority: low) Send a message to the player exaplaining
                // that a competence cannot be invoked on a non-enacted role.
                return ROLE_NOT_ACTIVE;
            }          
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Select competence' (exit value) state.
     * A state in which the competence is selected.
     */
    private class SelectCompetence extends ExitValueState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit values -----
        static final int COMPETENCE_EXISTS = 1;
        static final int COMPETENCE_DOES_NOT_EXIST = 2;
        // -----------------------
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected int doAction() {
            Class competenceClass = getMyAgent().competences.get(competenceName);
            if (competenceClass != null) {
                // The competence exsits.
                ICompetence competence = ClassHelper.instantiateClass(competenceClass);
                CompetenceWrapperState competenceWrapper = new CompetenceWrapperState(competence);
        
                // Register the competence-related states.
                registerState(competenceWrapper);
        
                // Register the competence-related transitions.
                receiveCompetenceArgument.registerDefaultTransition(competenceWrapper);
                competenceWrapper.registerDefaultTransition(sendCompetenceResult);
                return COMPETENCE_EXISTS;
            }   else {
                // The competence does not exist.
                return COMPETENCE_DOES_NOT_EXIST;
            }
        }
    
        // </editor-fold>
    }
   
    /**
     * The 'Send competence argument request' (send-success-or-failure) state.
     * A state in which a 'Competence argument request' message is sent
     * in case of success, or a FAILURE message. 
     */
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
            // LOG
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
            // LOG
            getMyAgent().logInfo("Competence argument request sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive competence argument' (single receiver) state.
     * A stat in which the competence argument is received.
     */
    private class ReceiveCompetenceArgument
        extends SingleReceiverState<CompetenceArgumentMessage<TArgument>> {
        
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
            // LOG
            getMyAgent().logInfo("Receiving competence argument.");
        }
        
        @Override
        protected void handleMessage(CompetenceArgumentMessage<TArgument> message) {
            argument = message.getArgument();
        }

        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Competence argument received.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Competence wrapper' (wrapper) state.
     * A state in which the competence is executed.
     */
    private class CompetenceWrapperState
        extends WrapperState<ICompetence<TArgument, TResult>> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        CompetenceWrapperState(ICompetence competence) {
            super(competence);
        } 

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void doActionBefore(ICompetence<TArgument, TResult> competence) {
            competence.setArgument(argument);
        }

        @Override
        protected void doActionAfter(ICompetence<TArgument, TResult> competence) {
            result = competence.getResult();
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send competence result' (send-success-or-failure) state.
     * A state in which either a competence result is sent in case of success,
     * or a FAILURE message.
     */
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
            // LOG
            getMyAgent().logInfo("Sending competence result.");
        }

        @Override
        protected int onManager() {
            return SUCCESS;
        }
        
        @Override
        protected CompetenceResultMessage prepareMessage() {
            CompetenceResultMessage message = new CompetenceResultMessage();
            message.setResult(result);
            return message;
        }

        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Competence result sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Competence invoked' final (one-shot) state.
     */
    private class CompetenceInvoked extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Invoke competence' protocol (id = %1$s) responder party ended; competence was invoked.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Competence not invoked' final (one-shot) state.
     */
    private class CompetenceNotInvoked extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Invoke competence' protocol (id = %1$s) responder party ended; compatence was not invoked.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
