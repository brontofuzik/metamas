package jadeorg.core.player;

import jade.core.AID;
import jadeorg.proto_old.State;

/**
 * A requirement (FSM) behaviour.
 * @author Lukáš Kúdela
 * @since 2011-11-11
 * @version %I% %G%
 */
class Requirement extends State {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Player myPlayer;
    
    private AID roleAID;
    
    private Object argument;
    
    private Object result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Requirement(String name) {
        super(name);
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
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        // TODO Implement.
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean done() {
        // Implement.
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // </editor-fold>
}
