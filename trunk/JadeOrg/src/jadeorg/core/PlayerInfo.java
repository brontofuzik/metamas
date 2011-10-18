/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeorg.core;

import jade.core.AID;

/**
 * A player info.
 * @author Lukáš Kúdela (2011-10-18)
 */
public class PlayerInfo {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID playerAID;
    
    private String name;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public PlayerInfo(AID playerAID) {
        // ----- Preconditions -----
        if (playerAID == null) {
            throw new NullPointerException("playerAID");
        }
        // -------------------------
        
        this.playerAID = playerAID;
        name = playerAID.getName();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public AID getAID() {
        return playerAID;
    }
    
    public String getName() {
        return name;
    }
    
    // </editor-fold>
}
