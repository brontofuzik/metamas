package example3.players;

import thespian4jade.core.Event;

/**
 * 'Player2' player - a 'Participant' player playing the 'Auctioneer' role
 * in the second round and the 'Bidder' role in the first and third rounds.
 * @author Lukáš Kúdela
 * @since 2012-01-20
 * @version %I% %G%
 */
public class Player2 extends ParticipantPlayer {  
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">  
    
    /**
     * Initializes a new instance of the Player2 class.
     */
    public Player2() {
        // Add items to sell.
        addItemToSell(new Item(KOONING, 137.5));
        
        // Add items to buy.
        addItemToBuy(new Item(POLLOCK, 156.8)); // Highest bid.
        addItemToBuy(new Item(KLIMT, 149.2));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    protected void setup() {
        super.setup();
        
        // Add event handlers.
        
        // Add behaviours.
        // Role enactnement
        scheduleEnactRole(getAuctioneerRoleFullName(), 4000);
        scheduleEnactRole(getBidderRoleFullName(), 4000);
        scheduleSubscribeToEvent(getAuctionOrganizationName(), Event.ROLE_ACTIVATED,
            RoleActivated_EventHandler.class, 5000);
        scheduleSubscribeToEvent(getAuctionOrganizationName(), Event.ROLE_DEACTIVATED,
            RoleDeactivated_EventHandler.class, 5000);
        
        // Role activation
        scheduleActivateRole(getAuctioneerRoleFullName(), 10000);
        
        // Role deactment
        scheduleDeactRole(getAuctioneerRoleFullName(), 22000);
        scheduleDeactRole(getBidderRoleFullName(), 23000);
    }
    
    /**
     * Gets the name of the item to sell.
     * @return the name of the item to sell 
     */
    @Override
    protected String getItemToSellName() {
        return KOONING;
    }
    
    // </editor-fold>
}
