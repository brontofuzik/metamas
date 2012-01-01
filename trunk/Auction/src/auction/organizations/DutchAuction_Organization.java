package auction.organizations;

import jadeorg.core.organization.Organization;
import jadeorg.core.organization.Role;

/**
 * A Dutch auction organization.
 * @author Luk� K�dela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class DutchAuction_Organization extends Organization {
   
    /**
     * An Auctioneer role.
     * @author Luk� K�dela
     * @since 2011-11-20
     * @version %I% %G%
     */
    private class Auctioneer_Role extends Role {
    }
    
    /**
     * A Bidder role.
     * @author Luk� K�dela
     * @since 2011-11-20
     * @version %I% %G%
     */
    private class Bidder_Role extends Role {
    }
}
