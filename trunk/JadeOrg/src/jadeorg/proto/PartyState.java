package jadeorg.proto;

import jade.core.Agent;
import jadeorg.proto.jadeextensions.BehaviourState;

/**
 * A party state.
 * @author Lukáš Kúdela
 * @since 2011-01-02
 * @version %I% %G%
 */
public class PartyState extends BehaviourState {

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
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public void setAgent(Agent agent) {
        party.setAgent(agent);
    }
    
    // ----- PROTECTED -----
    
    protected Party getUnderlyingParty() {
        return party;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void action() {
        party.action();
    }
    
    public boolean done() {
        return party.done();
    }
    
    // </editor-fold>
}
