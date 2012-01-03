package jadeorg.proto;

import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * A party state.
 * @author Lukáš Kúdela
 * @since 2011-01-02
 * @version %I% %G%
 */
public class PartyState extends OneShotBehaviourState {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Party party;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected PartyState(String name, Party party) {
        super(name);
        // ----- Preconditions -----
        assert party != null;
        // -------------------------
        
        this.party = party;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        party.action();
    }
    
    // </editor-fold>
}
