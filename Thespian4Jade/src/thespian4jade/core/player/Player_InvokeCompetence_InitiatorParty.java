package thespian4jade.core.player;

import jade.core.AID;
import thespian4jade.asynchrony.Future;
import thespian4jade.asynchrony.IObserver;
import thespian4jade.behaviours.ExitValueState;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.behaviours.states.receiver.ReceiveSuccessOrFailure;
import thespian4jade.behaviours.states.sender.SingleSenderState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;
import thespian4jade.protocols.role.invokecompetence.InvokeCompetenceRequestMessage;
import thespian4jade.protocols.role.invokecompetence.CompetenceArgumentMessage;
import thespian4jade.protocols.role.invokecompetence.ArgumentRequestMessage;
import thespian4jade.protocols.role.invokecompetence.CompetenceResultMessage;
import java.io.Serializable;
import thespian4jade.asynchrony.IObservable;
import thespian4jade.asynchrony.Observable;
import thespian4jade.behaviours.parties.IResultParty;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;

/**
 * An 'Invoke competence' protocol initiator party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Player_InvokeCompetence_InitiatorParty
    <TArgument extends Serializable, TResult extends Serializable>
    extends InitiatorParty<Player> implements IResultParty<TResult>, IObservable {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The role; more precisely its AID.
     */
    private AID role;
    
    /**
     * The name of the competence.
     */
    private String competenceName;
    
    /**
     * The competence argument.
     */
    private TArgument competenceArgument;
    
    /**
     * The competence result.
     */
    private TResult competenceResult;
    
    private IObservable observable = new Observable();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initialize a new instance of the Player_InvokeCompetence_InitiatorParty class.
     * @param competenceName the name of the competence
     * @param competenceArgument the competence argument
     */
    public Player_InvokeCompetence_InitiatorParty(String competenceName, TArgument competenceArgument) {
        super(ProtocolRegistry.getProtocol(Protocols.INVOKE_COMPETENCE_PROTOCOL));
        // ----- Preconditions -----
        assert competenceName != null && !competenceName.isEmpty();
        // -------------------------

        this.competenceName = competenceName;
        this.competenceArgument = competenceArgument;
        
        buildFSM();
    }
    
    /**
     * Initializes a new instance of the Player_InvokeCompetence_InitiatorParty class.
     * @param competenceName the name of the competence
     */
    public Player_InvokeCompetence_InitiatorParty(String competenceName) {
        this(competenceName, null);
    }
    
    /**
     * Initializes a new instance of the Player_InvokeCompetence_InitiatorParty class.
     */
    public Player_InvokeCompetence_InitiatorParty() {
        this("TO BE PROVIDED LATER", null);
    }
 
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    /**
     * Gets the name of the competence.
     * @return the name of the competence
     */
    public String getCometenceName() {
        return competenceName;
    }
    
    /**
     * Sets the name of the competence.
     * @param competenceName the name of the competence
     */
    public void setCompetenceName(String competenceName) {
        this.competenceName = competenceName;
    }
    
    /**
     * Sets the competence argument.
     * @param competenceArgument the competence argument
     */
    public void setCompetenceArgument(TArgument competenceArgument) {
        this.competenceArgument = competenceArgument;
    }
    
    /**
     * Gets the competence result.
     * @return the competence result
     */
    public TResult getCompetenceResult() {
        return competenceResult;
    }
    
    /**
     * Gets the result.
     * The IResultParty method.
     * @return the result
     */
    @Override
    public TResult getResult() {
        return getCompetenceResult();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
   
    /**
     * Gets the result future.
     * The IResultParty method.
     * @return the reuslt future
     */
    @Override
    public Future getResultFuture() {
        Future<TResult> future = new Future();
        addObserver(future);
        return future;
    }
    
    /**
     * The IObservable method.
     * @param observer 
     */
    @Override
    public void addObserver(IObserver observer) {
        observable.addObserver(observer);
    }

    /**
     * The IObservable method.
     * @param observer 
     */
    @Override
    public void removeObserver(IObserver observer) {
        observable.removeObserver(observer);
    }

    /**
     * The IObservable method.
     * @param observable 
     */
    @Override
    public void notifyObservers(IObservable observable) {
        this.observable.notifyObservers(observable);
    }
    
    public void notifyObservers() {
        notifyObservers(this);
    }
    
    // ----- PRIVATE -----
    
    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        // ----- States -----
        IState initialize = new Initialize();
        IState sendInvokeCompetenceRequest = new SendInvokeCompetenceRequest();
        IState receiveCompetenceArgumentRequest = new ReceiveCompetenceArgumentRequest();
        IState sendCompetenceArgument = new SendCompetenceArgument();
        IState receiveCompetenceResult = new ReceiveCompetenceResult();
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);     
        registerState(sendInvokeCompetenceRequest);
        registerState(receiveCompetenceArgumentRequest);
        registerState(sendCompetenceArgument);
        registerState(receiveCompetenceResult);       
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register the transitions.
        initialize.registerTransition(Initialize.OK, sendInvokeCompetenceRequest);
        initialize.registerTransition(Initialize.FAIL, failureEnd);        
        sendInvokeCompetenceRequest.registerDefaultTransition(receiveCompetenceArgumentRequest);       
        receiveCompetenceArgumentRequest.registerTransition(ReceiveCompetenceArgumentRequest.SUCCESS, sendCompetenceArgument);
        receiveCompetenceArgumentRequest.registerTransition(ReceiveCompetenceArgumentRequest.FAILURE, failureEnd);       
        sendCompetenceArgument.registerDefaultTransition(receiveCompetenceResult);        
        receiveCompetenceResult.registerTransition(ReceiveCompetenceResult.SUCCESS, successEnd);       
        receiveCompetenceResult.registerTransition(ReceiveCompetenceResult.FAILURE, failureEnd);
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
                "Initiating the 'Invoke competence' (%1$s) protocol.",
                competenceName));

            if (getMyAgent().knowledgeBase.query().canInvokeCompetence(competenceName)) {
                // The player can invoke the competence.
                role = getMyAgent().knowledgeBase.query().getActiveRole().getAID();
                return OK;
            } else {
                // The player can not invoke the competence.
                String message = String.format(
                    "I cannot invoke the competence '%1$s'.",
                    competenceName);
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    private class SendInvokeCompetenceRequest
        extends SingleSenderState<InvokeCompetenceRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { role };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending invoke competence request.");
        }
        
        @Override
        protected InvokeCompetenceRequestMessage prepareMessage() {
            InvokeCompetenceRequestMessage message = new InvokeCompetenceRequestMessage();
            message.setCompetence(competenceName);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Invoke competence requets sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveCompetenceArgumentRequest
        extends ReceiveSuccessOrFailure<ArgumentRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveCompetenceArgumentRequest() {
            super(new ArgumentRequestMessage.Factory());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { role };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving competence argument request.");
        }
        
        @Override
        protected void onExit() {
            getMyAgent().logInfo("Competence argument request received.");
        }
        
        // </editor-fold>
    }
    
    private class SendCompetenceArgument
        extends SingleSenderState<CompetenceArgumentMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { role };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
       
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending competence argument.");
        }
        
        @Override
        protected CompetenceArgumentMessage prepareMessage() {
            CompetenceArgumentMessage<TArgument> message = new CompetenceArgumentMessage<TArgument>();
            message.setArgument(competenceArgument);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Competence argument sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveCompetenceResult
        extends ReceiveSuccessOrFailure<CompetenceResultMessage<TResult>> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveCompetenceResult() {
            super(new CompetenceResultMessage.Factory<TResult>());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { role };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving competence result.");
        }
        
        @Override
        protected void handleSuccessMessage(CompetenceResultMessage<TResult> message) {
            competenceResult = message.getResult();
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Competence result received.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Success end' (one-shot) state.
     * A state in which the party succeeds.
     */
    private class SuccessEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Notify the observers about the party success.
            ((Player_InvokeCompetence_InitiatorParty)getParty())
                .notifyObservers();
            
            // LOG
            getMyAgent().logInfo(String.format(
                "'Invoke competence' protocol (id = %1$s) initiator party succeeded.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Failure end' (one-shot) state.
     * A state in which the party fails.
     */
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
//            // Notify the observers about the party failure.
//            ((Player_InvokeCompetence_InitiatorParty)getParty())
//                .notifyObservers();
            
            // LOG
            getMyAgent().logInfo(String.format(
                "'Invoke competence' protocol (id = %1$s) initiator party failed.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
