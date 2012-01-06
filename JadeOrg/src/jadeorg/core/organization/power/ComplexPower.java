package jadeorg.core.organization.power;

import jadeorg.core.organization.Role;
import jadeorg.proto.Party;
import jadeorg.proto.PartyState;
import jadeorg.proto.PowerParty;

/**
 * A complex power.
 * @author Lukáš Kúdela
 * @since 2011-01-02
 * @version %I% %G%
 */
public abstract class ComplexPower<TArgument, TResult> extends PartyState implements Power<TArgument, TResult> {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ComplexPower(String name, PowerParty party) {
        super(name, party);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public void setArgument(TArgument argument) {
        getUnderlyingPowerParty().setArgument(argument);
    }
    
    public TResult getResult() {
        return getUnderlyingPowerParty().getResult();
    }
    
    // ----- PRIVATE -----
    
    private PowerParty<TArgument, TResult> getUnderlyingPowerParty() {
        return (PowerParty<TArgument, TResult>)getUnderlyingParty();
    }
    
    // </editor-fold>
}
