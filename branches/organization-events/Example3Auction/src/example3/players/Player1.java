package example3.players;

/**
 * The 'Participant1' player.
 * @author Lukáš Kúdela
 * @since 2012-01-20
 * @version %I% %G%
 */
public class Player1 extends ParticipantPlayer {
            
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes the 'Participant1' player.
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
        scheduleEnactRole(getBidderRoleFullName(), 4000);
        
        // Role activation
        scheduleActivateRole(getAuctioneerRoleFullName(), 6000);
        
        // Role deactment
        scheduleDeactRole(getAuctioneerRoleFullName(), 18000);
        scheduleDeactRole(getBidderRoleFullName(), 20000);
    }
    
    @Override
    protected String getItemToSellName() {
        return POLLOCK;
    }

    // </editor-fold>
}
