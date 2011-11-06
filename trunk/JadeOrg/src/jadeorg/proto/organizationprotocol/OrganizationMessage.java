package jadeorg.proto.organizationprotocol;

import jade.core.AID;
import jadeorg.lang.Message;

/**
 * An 'Organization' message.
 * An 'Organization' message is a message exchanged between an organization and a player
 * containing a request to enact/deact a role.
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @version %I% %G%
 */
public abstract class OrganizationMessage extends Message {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The organization AID. */
    private AID organization;
    
    /** The player AID. */
    private AID player;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the organization AID.
     * @return the organization AID
     */
    public AID getOrganization() {
        return organization;
    }
    
    /**
     * Sets the organization AID.
     * DP: Fluent interface
     * @param organization the organization AID
     * @return this 'Organization' message
     */
    public OrganizationMessage setOrganization(AID organization) {
        this.organization = organization;
        return this;
    }
    
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
    public OrganizationMessage setPlayer(AID player) {
        this.player = player;
        return this;
    }
    
    // </editor-fold>
}
