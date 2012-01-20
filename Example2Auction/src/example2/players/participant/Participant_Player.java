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
        auctioneerRoleFullName = new RoleFullName("auction_Organization.Auctioneer");
        bidderRoleFullName = new RoleFullName("auction_Organization.Asker");
        auctionPowerFullName = new PowerFullName("auction_Organization.Auctioneer.Auction");
        
        // Add abilities.
        addAbility("bid"); // For the Bidder role.
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add the requirements.
        addRequirement(Bid_Requirement.class);
        
        int timeout = 4000;
        timeout = scheduleEnactRole(auctioneerRoleFullName, timeout);
        timeout = scheduleEnactRole(bidderRoleFullName, timeout);
        
        // Schedule individual behaviours.
        timeout = doScheduleBehaviours(timeout);
        
        timeout = scheduleDeactRole(auctioneerRoleFullName, timeout);
        scheduleDeactRole(bidderRoleFullName, timeout);
    }
    
    /**
     * Schedule individual behaviours.
     * Design pattern: Template method, Role: Primitive operation
     */
    protected abstract int doScheduleBehaviours(int timeout);
    
    protected void addItemToBuy(Item itemToBuy) {
        itemsToBuy.put(itemToBuy.getName(), itemToBuy);
    }
    
    protected void addItemToSell(Item itemToSell) {
        itemsToSell.put(itemToSell.getName(), itemToSell);
    }
      
    // </editor-fold>
}
