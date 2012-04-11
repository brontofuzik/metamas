package example3.players;

import thespian4jade.core.Event;

/**
 * 'Player1' player - a 'Participant' player playing the 'Auctioneer' role
 * in the first round and the 'Bidder' role in the second and third rounds.
 * @author Lukáš Kúdela
 * @since 2012-01-20
 * @version %I% %G%
 */
public class Player1 extends ParticipantPlayer {
            
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Player1 class.
     */
    public Player1() {
        // Add items to sell.
        addItemToSell(new Item(POLLOCK, 140));
        
        // Add items to buy.
        addItemToBuy(new Item(KOONING, 153));
        addItemToBuy(new Item(KLIMT, 150.2)); // Highest bid.
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add event handlers.
        
        // Add behaviours.
        // Role enactment
        scheduleEnactRole(getAuctioneerRoleFullName(), 2000);
        scheduleEnactRole(getBidderRoleFullName(), 2000);
        scheduleSubscribeToEvent(getAuctionOrganizationName(), Event.ROLE_ACTIVATED,
            RoleActivated_EventHandler.class, 3000);
        scheduleSubscribeToEvent(getAuctionOrganizationName(), Event.ROLE_DEACTIVATED,
            RoleDeactivated_EventHandler.class, 3000);
        
        // Role activation
        scheduleActivateRole(getAuctioneerRoleFullName(), 8000);
        
        // Role deactment
        scheduleDeactRole(getAuctioneerRoleFullName(), 20000);
        scheduleDeactRole(getBidderRoleFullName(), 21000);
    }
    
    /**
     * Gets the name of the item to sell.
     * @return the name of the item to sell 
     */
    @Override
    protected String getItemToSellName() {
        return POLLOCK;
    }

    // </editor-fold>
}
