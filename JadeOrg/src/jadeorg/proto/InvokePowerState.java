package jadeorg.proto;

import jadeorg.core.player.Player_InvokePowerInitiator;

/**
 * An 'Invoke power' (party) state.
 * @author Lukáš Kúdela
 * @since 2012-01-04
 * @version %I% %G%
 */
public abstract class InvokePowerState extends PartyState {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected InvokePowerState(String name, String powerName) {
        super(name, new Player_InvokePowerInitiator(powerName));
    }
            
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected abstract Object getPowerArgument();
    
    protected abstract void setPowerResult(Object powerResult);
    
    // ----- PROTECTED -----
    
    private Player_InvokePowerInitiator getUnderlyingIPIParty() {
        return (Player_InvokePowerInitiator)super.getUnderlyingParty();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        getUnderlyingIPIParty().setPowerArgument(getPowerArgument());
        super.action();
        setPowerResult(getUnderlyingIPIParty().getPowerResult());
    }
    
    // </editor-fold>
}
