package thespian4jade.core.player;

import jade.core.AID;
import thespian4jade.core.Future;
import thespian4jade.core.IObserver;
import thespian4jade.proto.Initialize;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.ReceiveSuccessOrFailure;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.IState;
import thespian4jade.proto.roleprotocol.invokecompetenceprotocol.InvokeCompetenceProtocol;
import thespian4jade.proto.roleprotocol.invokecompetenceprotocol.InvokeCompetenceRequestMessage;
import thespian4jade.proto.roleprotocol.invokecompetenceprotocol.CompetenceArgumentMessage;
import thespian4jade.proto.roleprotocol.invokecompetenceprotocol.ArgumentRequestMessage;
import thespian4jade.proto.roleprotocol.invokecompetenceprotocol.CompetenceResultMessage;
import java.io.Serializable;
import thespian4jade.core.IObservable;
import thespian4jade.core.Observable;
import thespian4jade.proto.IResultParty;

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
     * Initialize a new instance of the Player_InvokeCompetenceInitiator class.
     * @param competenceName the name of the competence
     * @param competenceArgument the competence argument
     */
    public Player_InvokeCompetence_InitiatorParty(String competenceName, TArgument competenceArgument) {
        super(InvokeCompetenceProtocol.getInstance());
        // ----- Preconditions -----
        assert competenceName != null && !competenceName.isEmpty();
        // -------------------------

        this.competenceName = competenceName;
        this.competenceArgument = competenceArgument;
        
        buildFSM();
    }
    
    /**
     * Initializes a new instance of the Player_InvokeCompetenceInitiator class.
     * @param competenceName the name of the competence
     */
    public Player_InvokeCompetence_InitiatorParty(String competenceName) {
        this(competenceName, null);
    }
 
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    /**
     * Gets the competence result.
     * @return the competence result
     */
    public TResult getCompetenceResult() {
        return competenceResult;
    }
    
    /**
     * Sets the competence argument.
     * @param competenceArgument the competence argument
     */
    public void setCompetenceArgument(TArgument competenceArgument) {
        this.competenceArgument = competenceArgument;
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
        IState initialize = new MyInitialize();
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
        initialize.registerTransition(MyInitialize.OK, sendInvokeCompetenceRequest);
        initialize.registerTransition(MyInitialize.FAIL, failureEnd);        
        sendInvokeCompetenceRequest.registerDefaultTransition(receiveCompetenceArgumentRequest);       
        receiveCompetenceArgumentRequest.registerTransition(ReceiveCompetenceArgumentRequest.SUCCESS, sendCompetenceArgument);
        receiveCompetenceArgumentRequest.registerTransition(ReceiveCompetenceArgumentRequest.FAILURE, failureEnd);       
        sendCompetenceArgument.registerDefaultTransition(receiveCompetenceResult);        
        receiveCompetenceResult.registerTransition(ReceiveCompetenceResult.SUCCESS, successEnd);       
        receiveCompetenceResult.registerTransition(ReceiveCompetenceResult.FAILURE, failureEnd);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyInitialize extends Initialize {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int initialize() {
            getMyAgent().logInfo(String.format(
                "Initiating the 'Invoke competence' (%1$s) protocol.",
                competenceName));

            if (getMyAgent().knowledgeBase.canInvokeCompetence(competenceName)) {
                // The player can invoke the competence.
                role = getMyAgent().knowledgeBase.getActiveRole().getRoleAID();
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
            getMyAgent().knowledgeBase.getActiveRole()
                .saveCompetenceResult(competenceName, message.getResult());
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
