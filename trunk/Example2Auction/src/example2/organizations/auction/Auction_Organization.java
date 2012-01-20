package example2.organizations.auction;

import example2.organizations.auction.bidder.Bidder_Responder;
import example2.organizations.auction.auctioneer.Auction_Power;
import jadeorg.core.organization.Organization;
import jadeorg.core.organization.Role;
import java.util.logging.Level;

/**
 * An Auction organization.
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
        addRole(Auctioneer_Role.class);
        addRole(Bidder_Role.class);
        log(Level.INFO, "Roles added.");
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * An Auctioneer role.
     * @author Lukáš Kúdela
     * @since 2011-11-20
     * @version %I% %G%
     */
    public class Auctioneer_Role extends Role {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void setup() {
            super.setup();
            
            // Add behaviours.
            // No behaviours.
            
            // Add powers.
            addPower(Auction_Power.class);
            logInfo("Powers added.");
        }
        
        // </editor-fold>
    }
    
    /**
     * A Bidder role.
     * @author Lukáš Kúdela
     * @since 2011-11-20
     * @version %I% %G%
     */
    public class Bidder_Role extends Role {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void setup() {
            super.setup();
            
            // Add behaviours.
            addBehaviour(new Bidder_Responder());
            logInfo("Behaviours added.");
            
            // Add powers.
            // No powers.
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
