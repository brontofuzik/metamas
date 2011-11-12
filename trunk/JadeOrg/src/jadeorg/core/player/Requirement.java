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
class Requirement extends Behaviour implements State {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Player myPlayer;
    
    private AID roleAID;
    
    private Object argument;
    
    private Object result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Requirement(String name) {
        setBehaviourName(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    Player getMyPlayer() {
        return myPlayer;
    }
    
    Requirement setMyPlayer(Player player) {
        this.myPlayer = player;
        return this;
    }
    
    AID getRoleAID() {
        return roleAID;
    }
    
    Requirement setRoleAID(AID roleAID) {
        this.roleAID = roleAID;
        return this;
    }
    
    Object getArgument() {
        return argument;
    }
    
    Requirement setArgument(Object argument) {
        this.argument = argument;
        return this;
    }
    
    Object getResult() {
        return result;
    }
    
    Requirement setResult() {
        this.result = result;
        return this;
    }
    
    @Override
    public String getName() {
        return getBehaviourName();
    }

    @Override
    public Party getParty() {
        return (Party)getParent();
    }

    @Override
    public void setParty(Party party) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean done() {
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
        return this;
    }
    
    // </editor-fold>
}
