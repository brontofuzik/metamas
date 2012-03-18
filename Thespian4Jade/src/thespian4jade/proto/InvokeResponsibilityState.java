package thespian4jade.proto;

import java.io.Serializable;
import thespian4jade.core.organization.Role_InvokeResponsibilityInitiator;
import thespian4jade.proto.jadeextensions.StateWrapperState;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-17
 * @version %I% %G%
 */  
public abstract class InvokeResponsibilityState<TArgument extends Serializable, TResult extends Serializable>
    extends StateWrapperState<Role_InvokeResponsibilityInitiator> {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public InvokeResponsibilityState(String responsibilityName) {
        super(new Role_InvokeResponsibilityInitiator(responsibilityName));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setWrappedStateArgument(Role_InvokeResponsibilityInitiator wrappedState) {
        wrappedState.setResponsibilityArgument(getResponsibilityArgument());
    }
    
    protected abstract TArgument getResponsibilityArgument();

    @Override
    protected void getWrappedStateResult(Role_InvokeResponsibilityInitiator wrappedState) {
        setResponsibilityResult((TResult)wrappedState.getResponsibilityResult());
    }
    
    protected abstract void setResponsibilityResult(TResult competenceResult);
    
    // </editor-fold>
}
