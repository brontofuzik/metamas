package thespian4jade.proto;

import java.io.Serializable;
import thespian4jade.core.player.Player_InvokePowerInitiator;
import thespian4jade.proto.jadeextensions.StateWrapperState;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-17
 * @version %I% %G%
 */  
public abstract class InvokePowerState<TArgument extends Serializable, TResult extends Serializable>
    extends StateWrapperState<Player_InvokePowerInitiator> {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public InvokePowerState(String powerName) {
        super(new Player_InvokePowerInitiator(powerName));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setWrappedStateArgument(Player_InvokePowerInitiator wrappedState) {
        wrappedState.setPowerArgument(getPowerArgument());
    }
    
    protected abstract TArgument getPowerArgument();

    @Override
    protected void getWrappedStateResult(Player_InvokePowerInitiator wrappedState) {
        setPowerResult((TResult)wrappedState.getPowerResult());
    }
    
    protected abstract void setPowerResult(TResult powerResult);
    
    // </editor-fold>
}
