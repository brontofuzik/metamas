package thespian4jade.behaviours.states.special;

import java.io.Serializable;
import thespian4jade.core.organization.Role_InvokeResponsibility_InitiatorParty;

/**
 * A (wrapper) state in which the 'Invoke responsibility' protocol initiator party is
 * (synchronously) executed.
 * @author Lukáš Kúdela
 * @since 2012-03-17
 * @version %I% %G%
 */  
public abstract class InvokeResponsibilityState
    <TArgument extends Serializable, TResult extends Serializable>
    extends WrapperState<Role_InvokeResponsibility_InitiatorParty> {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the InvokeResponsibilityState class.
     * @param responsibilityName the name of the responsibility
     * @param responsibilityArgument the responsibility argument
     */
    public InvokeResponsibilityState(String responsibilityName, TArgument responsibilityArgument) {
        super(new Role_InvokeResponsibility_InitiatorParty(responsibilityName, responsibilityArgument));
    }
    
    /**
     * Initializes a new instance of the InvokeResponsibilityState class.
     * @param responsibilityName the name of the responsibility
     */
    public InvokeResponsibilityState(String responsibilityName) {
        super(new Role_InvokeResponsibility_InitiatorParty(responsibilityName));
    }
    
    /**
     * Initializes a new instance of the InvokeResponsibilityState class.
     */
    public InvokeResponsibilityState() {
        super(new Role_InvokeResponsibility_InitiatorParty());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of the responsibility.
     * @return the name of the responsibility
     */
    protected String getResponsibilityName() {
        return getWrappedState().getResponsibilityName();
    }
    
    /**
     * Gets the responsibility argument.
     * Override this method to provide the responsibility argument.
     * @return the responsibility argument
     */
    protected abstract TArgument getResponsibilityArgument();
    
    /**
     * Sets the responsibility result.
     * Override this method to retrieve the responsibility result.
     * @param competenceResult the responsibility result
     */
    protected abstract void setResponsibilityResult(TResult competenceResult);
    
    // </editor-fold>   
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Provides the name of the responsibility and its argument to the wrapped
     * 'Invoke responsibility' protocol initiator party.
     * @param wrappedState the wrapped 'Invoke responsibility' protocol initiator party
     */
    @Override
    protected final void doActionBefore(Role_InvokeResponsibility_InitiatorParty wrappedState) {
        wrappedState.setResponsibilityName(getResponsibilityName());
        wrappedState.setResponsibilityArgument(getResponsibilityArgument());
    }

    /**
     * Retrieves the responsibility result from the wrapped 'Invoke responsibility'
     * protocol initiator party.
     * @param wrappedState the wrapped 'Invoke responsibility' protocol initiator party
     */
    @Override
    protected final void doActionAfter(Role_InvokeResponsibility_InitiatorParty wrappedState) {
        setResponsibilityResult((TResult)wrappedState.getResponsibilityResult());
    }
    
    // </editor-fold>
}
