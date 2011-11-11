package jadeorg.core.player;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jadeorg.proto.Party;
import jadeorg.proto.PassiveState.Event;
import jadeorg.proto.State;

/**
 * A requirement.
 * @author Lukáš Kúdela
 * @since 2011-11-11
 * @version %I% %G%
 */
class Requirement implements State {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID roleAID;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public AID getRoleAID() {
        return roleAID;
    }
    
    public Requirement setRoleAID(AID roleAID) {
        this.roleAID = roleAID;
        return this;
    }
    
    // </editor-fold>
    
    
    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Party getParty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setParty(Party party) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void registerTransition(Event event, State targetState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void registerDefaultTransition(State targetState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Behaviour toBehaviour() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
