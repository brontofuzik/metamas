package thespian4jade.proto;

import java.io.Serializable;
import thespian4jade.core.player.Player_InvokeCompetenceInitiator;
import thespian4jade.proto.jadeextensions.StateWrapperState;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-17
 * @version %I% %G%
 */  
public abstract class InvokeCompetenceState<TArgument extends Serializable, TResult extends Serializable>
    extends StateWrapperState<Player_InvokeCompetenceInitiator> {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public InvokeCompetenceState(String cpmpetenceName) {
        super(new Player_InvokeCompetenceInitiator(cpmpetenceName));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setWrappedStateArgument(Player_InvokeCompetenceInitiator wrappedState) {
        wrappedState.setCompetenceArgument(getCompetenceArgument());
    }
    
    protected abstract TArgument getCompetenceArgument();

    @Override
    protected void getWrappedStateResult(Player_InvokeCompetenceInitiator wrappedState) {
        setCompetenceResult((TResult)wrappedState.getCompetenceResult());
    }
    
    protected abstract void setCompetenceResult(TResult competenceResult);
    
    // </editor-fold>
}
