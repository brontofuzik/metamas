package jadeorg.lang;

import jade.core.AID;

/**
 * A 'Player' (abstract) message.
 * @author Lukáš Kúdela (2011-10-23)
 * @version 0.1
 */
public abstract class PlayerMessage extends Message {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The player AID. */
    private AID player;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the player AID.
     * @return the player AID
     */
    public AID getPlayer() {
        return player;
    }
    
    /**
     * Sets the player AID.
     * DP: Fluent interface
     * @param player the player AID
     * @return this 'Player' message
     */
    public PlayerMessage setPlayer(AID player) {
        this.player = player;
        return this;
    }
    
    // </editor-fold>
}
