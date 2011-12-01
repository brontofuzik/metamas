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
   
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the sender organization.
     * @return the sender organization AID
     */
    public AID getSenderOrganization() {
        return getSender();
    }
    
    /**
     * Sets the sender organization.
     * DP: Fluent interface
     * @param organization the sender organization AID
     * @return this 'Organization' message
     */
    public OrganizationMessage setSenderOrganization(AID organization) {
        setSender(organization);
        return this;
    }
    
    /**
     * Gets the receiver organization.
     * @return the receiver organization AID
     */
    public AID getReceiverOrganization() {
        return getReceivers()[0];
    }
    
    /**
     * Sets the receiver organization.
     * DP: Fluent interface
     * @param organization the receiver organization AID
     * @return this 'Organization' message
     */
    public OrganizationMessage setReceiverOrganization(AID organization) {
        addReceiver(organization);
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
     * Sets the sener player.
     * DP: Fluent interface
     * @param player the sender player AID
     * @return this 'Player' message
     */
    public OrganizationMessage setSenderPlayer(AID player) {
        setSender(player);
        return this;
    }
    
    /**
     * Gets the receiver player.
     * @return the receiver player AID
     */
    public AID getReceiverPlayer() {
        return getReceivers()[0];
    }
    
    /**
     * Sets the receiver player.
     * DP: Fluent interface
     * @param player the receiver player AID
     * @return this 'Organization' message
     */
    public OrganizationMessage setReceiverPlayer(AID player) {
        addReceiver(player);
        return this;
    }
    
    // </editor-fold>
}
