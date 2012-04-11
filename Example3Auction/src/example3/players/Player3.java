package example3.players;

import thespian4jade.core.Event;

/**
 * 'Player1' player - a 'Participant' player playing the 'Auctioneer' role
 * in the third round and the 'Bidder' role in the first and second rounds.
 * @author Lukáš Kúdela
 * @since 2012-01-20
 * @version %I% %G%
 */
public class Player3 extends ParticipantPlayer {
       
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Player3 class.
     */
    public Player3() {
        // Add items to sell.
        addItemToSell(new Item(KLIMT, 135));
        
        // Add items to buy.
        addItemToBuy(new Item(POLLOCK, 155.8));
        addItemToBuy(new Item(KOONING, 154)); // Highest bid.
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    protected void setup() {
        super.setup();
        
        // Add event handlers.
        
        // Add behaviours.
        // Role enactment
        scheduleEnactRole(getAuctioneerRoleFullName(), 6000);
        scheduleEnactRole(getBidderRoleFullName(), 6000);
        scheduleSubscribeToEvent(getAuctionOrganizationName(), Event.ROLE_ACTIVATED,
            RoleActivated_EventHandler.class, 7000);
        scheduleSubscribeToEvent(getAuctionOrganizationName(), Event.ROLE_DEACTIVATED,
            RoleDeactivated_EventHandler.class, 7000);
        
        // Role activation
        scheduleActivateRole(getAuctioneerRoleFullName(), 14000);
        
        // Role deactment
        scheduleDeactRole(getAuctioneerRoleFullName(), 24000);
        scheduleDeactRole(getBidderRoleFullName(), 25000);
    }
    
    /**
     * Gets the name of the item to sell.
     * @return the name of the item to sell 
     */
    @Override
    protected String getItemToSellName() {
        return KLIMT;
    }
    
    // </editor-fold>
}
