package thespian4jade.core.organization;

import jade.core.AID;
import java.io.Serializable;
import thespian4jade.asynchrony.Future;
import thespian4jade.asynchrony.IObserver;
import thespian4jade.behaviours.states.special.ExitValueState;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.behaviours.states.receiver.ReceiveSuccessOrFailure;
import thespian4jade.behaviours.states.sender.SendSuccessOrFailure;
import thespian4jade.behaviours.states.sender.SingleSenderState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;
import thespian4jade.protocols.role.invokeresponsibility.ResponsibilityArgumentMessage;
import thespian4jade.protocols.role.invokeresponsibility.ArgumentRequestMessage;
import thespian4jade.protocols.role.invokeresponsibility.InvokeResponsibilityRequestMessage;
import thespian4jade.protocols.role.invokeresponsibility.ResponsibilityResultMessage;
import thespian4jade.asynchrony.IObservable;
import thespian4jade.asynchrony.Observable;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;

/**
 * A 'Invoke responsibility' protocol initiator party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-22
 * @version %I% %G%
 */
public class Role_InvokeResponsibility_InitiatorParty
    <TArgument extends Serializable, TResult extends Serializable>
    extends InitiatorParty<Role> implements IObservable {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The player; more precisely its AID.
     * The responder party.
     */
    private AID player;
    
    /**
     * The name of the responsibility.
     */
    private String responsibilityName;
    
    /**
     * The responsibility argument.
     */
    private TArgument responsibilityArgument;
    
    /**
     * The serializable responsibility argument.
     */
    private TResult responsibilityResult;
    
    /**
     * The error message.
     */
    private String errorMessage;
    
    /**
     * The observable helper.
     */
    private IObservable observable = new Observable();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Role_InvokeResponsibility_InitiatorParty class.
     * @param responsibilityName the name of the responsibility
     * @param responsibilityArgument the responsibility argument
     */
    public Role_InvokeResponsibility_InitiatorParty(String responsibilityName, TArgument responsibilityArgument) {
        super(ProtocolRegistry.getProtocol(Protocols.INVOKE_RESPONSIBILITY_PROTOCOL));
        // ----- Preconditions -----
        assert responsibilityName != null && !responsibilityName.isEmpty();
        // -------------------------
        
        this.responsibilityName = responsibilityName;
        this.responsibilityArgument = responsibilityArgument;
        
        buildFSM();
    }
    
    /**
     * Initializes a new instance of the Role_InvokeResponsibility_InitiatorParty class.
     * @param responsibilityName the name of the responsibility
     */
    public Role_InvokeResponsibility_InitiatorParty(String responsibilityName) {
        this(responsibilityName, null);
    }
    
    /**
     * Initializes a new instance of the Role_InvokeResponsibility_InitiatorParty class.
     */
    public Role_InvokeResponsibility_InitiatorParty() {
        this("TO BE PROVIDED LATER", null);
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of the responsibility.
     * @return the name of the responsibility
     */
    public String getResponsibilityName() {
        return responsibilityName;
    }
    
    /**
     * Sets the name of the responsibility.
     * @param responsibilityName the name of the responsibility
     */
    public void setResponsibilityName(String responsibilityName) {
        this.responsibilityName = responsibilityName;
    }
    
    /**
     * Sets the responsibility argument.
     * @param responsibilityArgument the responsibility argument
     */
    public void setResponsibilityArgument(TArgument responsibilityArgument) {
        this.responsibilityArgument = responsibilityArgument;
    }
    
    /**
     * Gets the responsibility result.
     * @return the responsibility result
     */
    public TResult getResponsibilityResult() {
        return responsibilityResult;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Gets the result future.
     * The IResultParty method.
     * @return the reuslt future
     */
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
        IState sendResponsibilityRequest = new SendResponsibilityRequest();
        IState receiveResponsibilityArgumentRequest = new ReceiveResponsibilityArgumentRequest();
        IState sendResponsibilityArgument = new SendResponsibilityArgument();
        IState receiveResponsibilityResult = new ReceiveResponsibilityResult();
        IState responsibilityInvoked = new ResponsibilityInvoked();
        IState responsibilityNotInvoked = new ResponsibilityNotInvoked();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);       
        registerState(sendResponsibilityRequest);   
        registerState(receiveResponsibilityArgumentRequest);
        registerState(sendResponsibilityArgument);
        registerState(receiveResponsibilityResult);       
        registerLastState(responsibilityInvoked);
        registerLastState(responsibilityNotInvoked);
        
        // Regster the transitions.
        initialize.registerTransition(Initialize.OK, sendResponsibilityRequest);
        initialize.registerTransition(Initialize.FAIL, responsibilityNotInvoked);
        sendResponsibilityRequest.registerDefaultTransition(receiveResponsibilityArgumentRequest);        
        receiveResponsibilityArgumentRequest.registerTransition(ReceiveResponsibilityArgumentRequest.SUCCESS, sendResponsibilityArgument);
        receiveResponsibilityArgumentRequest.registerTransition(ReceiveResponsibilityArgumentRequest.FAILURE, responsibilityNotInvoked);       
        sendResponsibilityArgument.registerDefaultTransition(receiveResponsibilityResult);
        receiveResponsibilityResult.registerTransition(ReceiveResponsibilityResult.SUCCESS, responsibilityInvoked);
        receiveResponsibilityResult.registerTransition(ReceiveResponsibilityResult.FAILURE, responsibilityNotInvoked);
    }
 
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Initialize' initial (exit value) state.
     * A state in which the party is initialized.
     */
    private class Initialize extends ExitValueState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit values -----
        public static final int OK = 1;
        public static final int FAIL = 2;
        // -----------------------
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected int doAction() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Invoke responsibility' protocol (id = %1$s) initiator party started.",
                getProtocolId()));

            // TODO (priority: low) Determine if the responsbility can be invoked.
            if (true) {
                // The role can invoke the responsibility.
                player = getMyAgent().enactingPlayer;
                return OK;
            } else {
                // The role can not invoke the responsibility.
                errorMessage = "Responsibility can not be invoked, because ... TODO.";
                return FAIL;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send invoke responsibility request' (single sender) state.
     * A state in which the 'Invoke responsibility request' is sent.
     */
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
    
    /**
     * The 'Receive responsibility argument request' (receive-success-or-failure) state.
     * A state in which a 'Responsibility argument request' message is received
     * in case of success, or a FAILURE message. 
     */
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
    
    /**
     * The 'Send responsibility argument' (single sender) state.
     * A stat in which the responsibility argument is sent.
     */
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
    
    /**
     * The 'Receive responsibility result' (receive-success-or-failure) state.
     * A state in which either a responsibility result is received in case of success,
     * or a FAILURE message.
     */
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
     * The 'Responsibility invoked' final (one-shot) state.
     */
    private class ResponsibilityInvoked extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
//            // Notify the observers about the party success.
//            ((Role_InvokeResponsibility_InitiatorParty)getParty())
//                .notifyObservers();
            
            // LOG
            getMyAgent().logInfo(String.format(
                "'Invoke responsibility' protocol (id = %1$s) initiator party ended; responsibility was invoked.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Responsibility not invoked' final (one-shot) state.
     * A state in which the party fails.
     */
    private class ResponsibilityNotInvoked extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
//            // Notify the observers about the party failure.
//            ((Role_InvokeResponsibility_InitiatorParty)getParty())
//                .notifyObservers();
            
            // LOG
            getMyAgent().logInfo(String.format(
                "'Invoke responsibility' protocol (id = %1$s) initiator party ended; responsibility was not invoked.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
