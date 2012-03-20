package example3.players;

/**
 * The 'Participant2' player.
 * @author Lukáš Kúdela
 * @since 2012-01-20
 * @version %I% %G%
 */
public class Player2 extends ParticipantPlayer {  
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">  
    
    /**
     * Initializes the 'Participant2' player.
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
        scheduleEnactRole(getAuctioneerRoleFullName(), 2000);
        scheduleEnactRole(getBidderRoleFullName(), 4000);
        
        // Role activation
        scheduleActivateRole(getAuctioneerRoleFullName(), 10000);
        
        // Role deactment
        scheduleDeactRole(getAuctioneerRoleFullName(), 18000);
        scheduleDeactRole(getBidderRoleFullName(), 20000);
    }
    
    @Override
    protected String getItemToSellName() {
        return KOONING;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    // </editor-fold>
}
