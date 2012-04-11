package example3.organizations.auction;

import example3.organizations.auction.auctioneer.Auctioneer_Role;
import example3.organizations.auction.bidder.Bidder_Role;
import thespian4jade.core.organization.RoleMultiplicity;
import thespian4jade.core.organization.Organization;
import java.util.logging.Level;

/**
 * The 'Auction' organization.
 * @author Lukáš Kúdela
 * @since 2011-11-18
 * @version %I% %G%
 */
public class Auction_Organization extends Organization {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {        
        super.setup();
        
        // Add roles.
        addRole(Auctioneer_Role.class, RoleMultiplicity.MULTIPLE);
        addRole(Bidder_Role.class, RoleMultiplicity.MULTIPLE);
        log(Level.INFO, "Roles added.");
    }
    
    // </editor-fold>
}
