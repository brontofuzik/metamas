package thespian4jade.proto;

import java.io.Serializable;
import thespian4jade.core.organization.Role_InvokeRequirementInitiator;
import thespian4jade.proto.jadeextensions.StateWrapperState;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-17
 * @version %I% %G%
 */  
public abstract class InvokeRequirementState<TArgument extends Serializable, TResult extends Serializable>
    extends StateWrapperState<Role_InvokeRequirementInitiator> {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public InvokeRequirementState(String requirementName) {
        super(new Role_InvokeRequirementInitiator(requirementName));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setWrappedStateArgument(Role_InvokeRequirementInitiator wrappedState) {
        wrappedState.setRequirementArgument(getRequirementArgument());
    }
    
    protected abstract TArgument getRequirementArgument();

    @Override
    protected void getWrappedStateResult(Role_InvokeRequirementInitiator wrappedState) {
        setRequirementResult((TResult)wrappedState.getRequirementResult());
    }
    
    protected abstract void setRequirementResult(TResult powerResult);
    
    // </editor-fold>
}
