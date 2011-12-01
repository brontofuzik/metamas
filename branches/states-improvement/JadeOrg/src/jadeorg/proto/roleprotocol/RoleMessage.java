/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeorg.proto.roleprotocol;

import jade.core.AID;
import jadeorg.lang.Message;

/**
 * A 'Role' message.
 * An 'Role' message is a message exchanged between a role and a player
 * containing a request to activate/deactivate a role or invoke a responsibility.
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @version %I% %G%
 */
public abstract class RoleMessage extends Message {
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the sender role.
     * @return the sender role AID
     */
    public AID getSenderRole() {
        return getSender();
    }
    
    /**
     * Sets the sender role.
     * DP: Fluent interface
     * @param role the sender role AID
     * @return this 'Role' message
     */
    public RoleMessage setSenderRole(AID role) {
        // ----- Preconditions -----
        assert role != null;
        // -------------------------
        
        setSender(role);
        return this;
    }
    
    /**
     * Gets the reciver role.
     * @return the receiver role AID
     */
    public AID getReceiverRole() {
        return getReceivers()[0];
    }
    
    /**
     * Sets the reciver role.
     * DP: Fluent interface
     * @param role the receiver role AID
     * @return this 'Role' message
     */
    public RoleMessage setReceiverRole(AID role) {
        addReceiver(role);
        return this;
    }
    
    /**
     * Gets the sender player.
     * @return the sender player AID
     */
    public AID getSenderPlayer() {
        return getSender();
    }
    
    /**
     * Sets the sender player.
     * DP: Fluent interface
     * @param player the sender player AID
     * @return this 'Role' message
     */
    public RoleMessage setSenderPlayer(AID player) {
        // ----- Preconditions -----
        assert player != null;
        // -------------------------
        
        setSender(player);
        return this;
    }
    
    /**
     * Gets the receier player
     * @return the receiver player AID
     */
    public AID getReceiverPlayer() {
        return getReceivers()[0];
    }
    
    /**
     * Sets the receiver player.
     * DP: Fluent interface
     * @param player the receiver player AID
     * @return this 'Role' message
     */
    public RoleMessage setReceiverPlayer(AID player) {
        addReceiver(player);
        return this;
    }
    
    // </editor-fold>
}
