package thespian4jade.protocols;

import java.io.Serializable;
import thespian4jade.core.organization.Role_InvokeResponsibility_InitiatorParty;
import thespian4jade.protocols.jadeextensions.StateWrapperState;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-17
 * @version %I% %G%
 */  
public abstract class InvokeResponsibilityState
    <TArgument extends Serializable, TResult extends Serializable>
    extends StateWrapperState<Role_InvokeResponsibility_InitiatorParty> {

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
     * @return the responsibility argument
     */
    protected abstract TArgument getResponsibilityArgument();
    
    /**
     * Sets the responsibility result.
     * @param responsibilityResult the responsibility result
     */
    protected abstract void setResponsibilityResult(TResult competenceResult);
    
    // </editor-fold>   
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected final void setWrappedStateArgument(Role_InvokeResponsibility_InitiatorParty wrappedState) {
        wrappedState.setResponsibilityName(getResponsibilityName());
        wrappedState.setResponsibilityArgument(getResponsibilityArgument());
    }

    @Override
    protected final void getWrappedStateResult(Role_InvokeResponsibility_InitiatorParty wrappedState) {
        setResponsibilityResult((TResult)wrappedState.getResponsibilityResult());
    }
    
    // </editor-fold>
}
