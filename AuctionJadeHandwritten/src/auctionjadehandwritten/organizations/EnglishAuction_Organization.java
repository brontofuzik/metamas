package auctionjadehandwritten.organizations;

import auctionjadehandwritten.organizations.powers.Auction_Power;
import auctionjadehandwritten.organizations.powers.Bid_Power;
import jadeorg.core.organization.Organization;
import jadeorg.core.organization.Role;
import java.util.logging.Level;

/**
 * An English auction organization.
 * @author Lukáš Kúdela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class EnglishAuction_Organization extends Organization {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        addRoles();
        
        super.setup();
    }
    
    private void addRoles() {
        addRole(Auctioneer_Role.class);
        addRole(Bidder_Role.class);
        log(Level.INFO, "Roles added.");
    }
    
    // </editor-fold>
    
    /**
     * An Uuctioneer role.
     * @author Lukáš Kúdela
     * @since 2011-11-20
     * @version %I% %G%
     */
    private class Auctioneer_Role extends Role {
    
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void setup() {
            super.setup();
            
            // Add powers.
            addPower(new Auction_Power());
        }
        
        // </editor-fold>
    }
    
    /**
     * A Bidder role.
     * @author Lukáš Kúdela
     * @since 2011-11-20
     * @version %I% %G%
     */
    private class Bidder_Role extends Role {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void setup() {
            super.setup();
            
            // Add powers.
            addPower(new Bid_Power());
        }
        
        // </editor-fold>
    }
}
