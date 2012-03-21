package thespian4jade.core.organization;

import jade.core.AID;
import thespian4jade.concurrency.Future;
import thespian4jade.concurrency.IObserver;
import thespian4jade.proto.Initialize;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.ReceiveSuccessOrFailure;
import thespian4jade.proto.SendSuccessOrFailure;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.IState;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.ResponsibilityArgumentMessage;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.ArgumentRequestMessage;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.InvokeResponsibilityProtocol;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.InvokeResponsibilityRequestMessage;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.ResponsibilityResultMessage;
import java.io.Serializable;
import thespian4jade.concurrency.IObservable;
import thespian4jade.concurrency.Observable;
import thespian4jade.proto.IResultParty;

/**
 * A 'Invoke responsibility' protocol initiator party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-22
 * @version %I% %G%
 */
public class Role_InvokeResponsibility_InitiatorParty
    <TArgument extends Serializable, TResult extends Serializable>
    extends InitiatorParty<Role> implements IResultParty<TResult>, IObservable {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The player; more precisely its AID.
     */
    private AID player;
    
    /**
     * The name of the responsibility.
     */
    private String responsibilityName;
    
    /**
     * The (serializable) responsibility argument.
     */
    private TArgument responsibilityArgument;
    
    /**
     * The serializable responsibility argument.
     */
    private TResult responsibilityResult;
    
    /**
     * The observable helper.
     */
    private IObservable observable = new Observable();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Role_InvokeResponsibilityInitiator class.
     * @param responsibilityName the name of the responsibility
     * @param responsibilityArgument the responsibility argument
     */
    public Role_InvokeResponsibility_InitiatorParty(String responsibilityName, TArgument responsibilityArgument) {
        super(InvokeResponsibilityProtocol.getInstance());
        // ----- Preconditions -----
        assert responsibilityName != null && !responsibilityName.isEmpty();
        // -------------------------
        
        this.responsibilityName = responsibilityName;
        this.responsibilityArgument = responsibilityArgument;
        
        buildFSM();
    }
    
    /**
     * Initializes a new instance of the Role_InvokeResponsibilityInitiator class.
     * @param responsibilityName the name of the responsibility
     */
    public Role_InvokeResponsibility_InitiatorParty(String responsibilityName) {
        this(responsibilityName, null);
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the responsibility result.
     * @return the responsibility result
     */
    public TResult getResponsibilityResult() {
        return responsibilityResult;
    }
    
    /**
     * Sets the responsibility argument.
     * @param responsibilityArgument the responsibility argument
     */
    public void setResponsibilityArgument(TArgument responsibilityArgument) {
        this.responsibilityArgument = responsibilityArgument;
    }
    
    /**
     * Gets the result.
     * @return the result
     */
    @Override
    public TResult getResult() {
        return getResponsibilityResult();
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
        IState sendResponsibilityRequest = new SendResponsibilityRequest();
        IState receiveResponsibilityArgumentRequest = new ReceiveResponsibilityArgumentRequest();
        IState sendResponsibilityArgument = new SendResponsibilityArgument();
        IState receiveResponsibilityResult = new ReceiveResponsibilityResult();
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);       
        registerState(sendResponsibilityRequest);   
        registerState(receiveResponsibilityArgumentRequest);
        registerState(sendResponsibilityArgument);
        registerState(receiveResponsibilityResult);       
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Regster the transitions.
        initialize.registerTransition(MyInitialize.OK, sendResponsibilityRequest);
        initialize.registerTransition(MyInitialize.FAIL, failureEnd);        
        sendResponsibilityRequest.registerDefaultTransition(receiveResponsibilityArgumentRequest);        
        receiveResponsibilityArgumentRequest.registerTransition(ReceiveResponsibilityArgumentRequest.SUCCESS, sendResponsibilityArgument);
        receiveResponsibilityArgumentRequest.registerTransition(ReceiveResponsibilityArgumentRequest.FAILURE, failureEnd);       
        sendResponsibilityArgument.registerDefaultTransition(receiveResponsibilityResult);
        receiveResponsibilityResult.registerTransition(ReceiveResponsibilityResult.SUCCESS, successEnd);
        receiveResponsibilityResult.registerTransition(ReceiveResponsibilityResult.FAILURE, failureEnd);
    }
 
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyInitialize extends Initialize {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int initialize() {
            getMyAgent().logInfo(String.format(
                "Initiating the 'Invoke responsibility' (%1$s) protocol.",
                responsibilityName));

            if (true) {
                // The role can invoke the responsibility.
                player = getMyAgent().playerAID;
                return OK;
            } else {
                // The role can not invoke the responsibility.
                String message = String.format(
                    "I cannot invoke the responsibility '%1$s'.",
                    responsibilityName);
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    private class SendResponsibilityRequest
        extends SingleSenderState<InvokeResponsibilityRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending responsibility request.");
        }
        
        @Override
        protected InvokeResponsibilityRequestMessage prepareMessage() {
            InvokeResponsibilityRequestMessage message = new InvokeResponsibilityRequestMessage();
            message.setResponsibility(responsibilityName);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibility request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveResponsibilityArgumentRequest
        extends ReceiveSuccessOrFailure<ArgumentRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveResponsibilityArgumentRequest() {
            super(new ArgumentRequestMessage.Factory());
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
            getMyAgent().logInfo("Receiving argument request.");
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Argument request received.");
        }
        
        // </editor-fold>
    }
    
    private class SendResponsibilityArgument
        extends SendSuccessOrFailure<ResponsibilityArgumentMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending responsibility argument.");
        }
        
        @Override
        protected int onManager() {
            if (responsibilityArgument != null && responsibilityArgument instanceof Serializable) {
                return SUCCESS;
            } else {
                return FAILURE;
            }
        }
        
        @Override
        protected ResponsibilityArgumentMessage prepareMessage() {
            ResponsibilityArgumentMessage message = new ResponsibilityArgumentMessage();
            message.setArgument(responsibilityArgument);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibility argument sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveResponsibilityResult
        extends ReceiveSuccessOrFailure<ResponsibilityResultMessage<TResult>> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveResponsibilityResult() {
            super(new ResponsibilityResultMessage.Factory<TResult>());
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
            getMyAgent().logInfo("Receiving responsibility result.");
        }
        
        /**
         * Handles the received 'Responsibility result' message.
         * @param message the received 'Responsibility result' message
         */
        @Override
        protected void handleSuccessMessage(ResponsibilityResultMessage<TResult> message) {
            responsibilityResult = message.getResult();
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibility result received.");
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
//            // Notify the observers about the party success.
//            ((Role_InvokeResponsibility_InitiatorParty)getParty())
//                .notifyObservers();
            
            // LOG
            getMyAgent().logInfo(String.format(
                "'Invoke responsibility' protocol (id = %1$s) initiator party succeeded.",
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
//            ((Role_InvokeResponsibility_InitiatorParty)getParty())
//                .notifyObservers();
            
            // LOG
            getMyAgent().logInfo(String.format(
                "'Invoke responsibility' protocol (id = %1$s) initiator party failed.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
