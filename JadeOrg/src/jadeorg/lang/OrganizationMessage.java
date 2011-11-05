package jadeorg.lang;

import jade.core.AID;

/**
 * A 'Organization' (abstract) message.
 * @author Lukáš Kúdela
 * @since 2011-11-05
 * @version %I% %G%
 */
public abstract class OrganizationMessage extends Message {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The organization AID. */
    private AID organization;
    
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
    
    // </editor-fold>
}
