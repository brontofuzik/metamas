package example3.players;

import example3.organizations.auction.auctioneer.auction.AuctionArgument;

/**
 * The 'Participant3' player.
 * @author Lukáš Kúdela
 * @since 2012-01-20
 * @version %I% %G%
 */
public class Player3 extends ParticipantPlayer {
       
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes the 'Participant3' player.
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
        scheduleEnactRole(getAuctioneerRoleFullName(), 2000);
        scheduleEnactRole(getBidderRoleFullName(), 4000);
        
        // Role activation
        scheduleActivateRole(getAuctioneerRoleFullName(), 14000);
        
        // Role deactment
        scheduleDeactRole(getAuctioneerRoleFullName(), 18000);
        scheduleDeactRole(getBidderRoleFullName(), 20000);
    }
    
    @Override
    protected String getItemToSellName() {
        return KLIMT;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    // </editor-fold>
}
