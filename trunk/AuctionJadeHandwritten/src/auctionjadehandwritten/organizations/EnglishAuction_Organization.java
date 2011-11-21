package auctionjadehandwritten.organizations;

import jadeorg.core.organization.Organization;
import jadeorg.core.organization.Role;

/**
 * An English auction organization.
 * @author Luk� K�dela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class EnglishAuction_Organization extends Organization {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add roles.
        addRole(Auctioneer_Role.class);
        addRole(Bidder_Role.class);
    }
    
    // </editor-fold>
    
    /**
     * An Uuctioneer role.
     * @author Luk� K�dela
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
     * @author Luk� K�dela
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
