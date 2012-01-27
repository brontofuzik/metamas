package example2.players.participant;

import jadeorg.core.player.Player;
import java.util.HashMap;
import java.util.Map;

/**
 * A Participant player.
 * @author Lukáš Kúdela
 * @since 2011-11-18
 * @version %I% %G%
 */
public abstract class Participant_Player extends Player {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    protected static final String POLLOCK = "No. 5, 1948";
    protected static final String KOONING = "Woman III";
    protected static final String KLIMT = "Portrait of Adele Bloch-Bauer I";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">    
    
    protected static final RoleFullName auctioneerRoleFullName;
    
    protected static final RoleFullName bidderRoleFullName;
    
    protected static final PowerFullName auctionPowerFullName;
    
    /**
     * The items to be bought.
     */
    private Map<String, Item> itemsToBuy = new HashMap<String, Item>();
    
    /**
     * The item to be sold.
     */
    private Map<String, Item> itemsToSell = new HashMap<String, Item>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes the Participant_Player class.
     */
    static {
        auctioneerRoleFullName = new RoleFullName("auction_Organization.Auctioneer_Role");
        bidderRoleFullName = new RoleFullName("auction_Organization.Bidder_Role");
        auctionPowerFullName = new PowerFullName("auction_Organization.Auctioneer_Role.Auction_Power");
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add the requirements.
        addRequirement(Bid_Requirement.class);
        
        int timeout = 4000;
        
        // Enact the 'Auctioneer' and 'Bidder' roles.
        timeout = scheduleEnactRole(auctioneerRoleFullName, timeout);
        timeout = scheduleEnactRole(bidderRoleFullName, timeout);
        
        timeout = doSchedulePollockAuction(timeout);
        
        timeout += 8000;
        
//        timeout = doScheduleKooningAuction(timeout);
//        
//        timeout += 8000;
//        
//        timeout = doScheduleKlimtAuction(timeout);
        
        // Deact the 'Auctioneer' and 'Bidder' roles.
        timeout = scheduleDeactRole(auctioneerRoleFullName, timeout);
        scheduleDeactRole(bidderRoleFullName, timeout);
    }
    
    /**
     * Schedule the Pollock auction.
     * Design pattern: Template method, Role: Primitive operation
     * @param
     * @return
     */
    protected abstract int doSchedulePollockAuction(int timeout);
    
    /**
     * Schedule the Kooning auction.
     * Design pattern: Template method, Role: Primitive operation
     * @param timeout 
     * @return 
     */
    protected abstract int doScheduleKooningAuction(int timeout);
    
    /**
     * Schedule the Klimt auction.
     * Design pattern: Template method, Role: Primitive operation
     * @param timeout
     * @return 
     */
    protected abstract int doScheduleKlimtAuction(int timeout);
    
    /**
     * Adds an item to be bought.
     * @param itemToBuy the item to be bought
     */
    protected void addItemToBuy(Item itemToBuy) {
        itemsToBuy.put(itemToBuy.getName(), itemToBuy);
    }
    
    /**
     * Gets an item to be bought specified by its name.
     * @param itemName the name of the item to be bought
     * @return the item to be bought
     */
    protected Item getItemToBuy(String itemName) {
        return itemsToBuy.get(itemName);
    }
    
    /**
     * Adds an item to be sold.
     * @param itemToSell the item to be sold
     */
    protected void addItemToSell(Item itemToSell) {
        itemsToSell.put(itemToSell.getName(), itemToSell);
    }
    
    /**
     * Gets an item to be sold specified by its name.
     * @param itemName the name of the item to be sold
     * @return the item to be sold
     */
    protected Item getItemToSell(String itemName) {
        return itemsToSell.get(itemName);
    }
      
    // </editor-fold>
}
