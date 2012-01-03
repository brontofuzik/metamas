package jadeorg.core.player.requirement;

import jadeorg.core.player.Player;
import jadeorg.proto.Party;
import jadeorg.proto.PartyState;

/**
 * A complex requirement.
 * @author Lukáš Kúdela
 * @since 2011-01-02
 * @version %I% %G%
 */
public class ComplexRequirement extends PartyState implements Requirement {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Object argument;
    
    private Object result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ComplexRequirement(String name, Party party) {
        super(name, party);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public Object getArgument() {
        return argument;
    }
    
    public void setArgument(Object argument) {
        this.argument = argument;
    }
    
    public Object getResult() {
        return result;
    }
    
    public void setResult(Object result) {
        this.result = result;
    }
    
    // ----- PROTECTED -----
        
    protected Player getMyPlayer() {
        return (Player)myAgent;
    }
    
    // </editor-fold>
}
