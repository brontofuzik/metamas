package example3.players;

import example3.organizations.auction.auctioneer.auction.AuctionArgument;
import example3.organizations.auction.auctioneer.auction.AuctionResult;
import java.util.HashMap;
import java.util.Map;
import thespian4jade.concurrency.Future;
import thespian4jade.concurrency.IObservable;
import thespian4jade.concurrency.IObserver;
import thespian4jade.core.player.EventHandler;
import thespian4jade.core.player.Player;

/**
 * A Participant player.
 * @author Lukáš Kúdela
 * @since 2011-11-18
 * @version %I% %G%
 */
public abstract class ParticipantPlayer extends Player implements IObserver {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    protected static final String POLLOCK = "No. 5, 1948";
    protected static final String KOONING = "Woman III";
    protected static final String KLIMT = "Portrait of Adele Bloch-Bauer I";
    
    // ----- PRIVATE -----
    
    /** The full name of the 'Auctioneer' role. */
    private static final String AUCTIONEER_ROLE_FULL_NAME
        = "auction_Organization.Auctioneer_Role";
    
    /** The full name of the 'Bidder' role. */
    private static final String BIDDER_ROLE_FULL_NAME
        = "auction_Organization.Bidder_Role";
    
    /** The name of the 'Auction' competence. */
    private static final String AUCTION_COMPETENCE_NAME
        = "Auction_Competence";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">    
    
    /**
     * The number of active bidders.
     */
    static int bidders;
    
    // ----- PRIVATE -----
    
    /**
     * The full name of the 'Auctioneer' role.
     */
    private final RoleFullName auctioneerRoleFullName
        = new RoleFullName(AUCTIONEER_ROLE_FULL_NAME);
    
    /**
     * The full name of the 'Bidder' role.
     */
    private final RoleFullName bidderRoleFullName
        = new RoleFullName(BIDDER_ROLE_FULL_NAME);
    
    /**
     * The item to be sold.
     */
    private final Map<String, Item> itemsToSell = new HashMap<String, Item>();
    
    /**
     * The items to be bought.
     */
    private final Map<String, Item> itemsToBuy = new HashMap<String, Item>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the full name of the 'Auctioneer' role.
     * @return the full name of the 'Auctioneer' role
     */
    RoleFullName getAuctioneerRoleFullName() {
        return auctioneerRoleFullName;
    }
    
    /**
     * Gets the name of the 'Aucitoneer' role.
     * @return the name of the 'Aucitoneer' role
     */
    String getAuctioneerRoleName() {
        return auctioneerRoleFullName.getRoleName();
    }
    
    /**
     * Gets the full name of the 'Bidder' role.
     * @return the full name of the 'Bidder' role
     */
    RoleFullName getBidderRoleFullName() {
        return bidderRoleFullName;
    }
    
    /**
     * Gets the name of the 'Bidder' role.
     * @return the name of the 'Bidder' role
     */
    String getBidderRoleName() {
        return bidderRoleFullName.getRoleName();
    }
    
    /**
     * Gets the name of the 'Auction' competence.
     * @return the name of the 'Auction' competence
     */
    String getAuctionCompetenceName() {
        return AUCTION_COMPETENCE_NAME;
    }
    
    /**
     * Gets the name of the item to sell.
     * @return the name of the item to sell
     */
    Item getItemToSell() {
        return getItemToSell(getItemToSellName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * The IObserver method.
     * @param observable 
     */
    @Override
    public void update(IObservable observable) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // ----- PROTECTED -----
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add the responsibilites.
        addResponsibility(Bid_Responsibility.class);
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
    
    protected abstract String getItemToSellName();
      
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Role enacted' event handler.
     */
    public static class RoleActivated_EventHandler
        extends EventHandler<ParticipantPlayer> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Handles the 'Role activated' event.
         * @param roleName the naem of the activated role 
         */
        @Override
        protected void handleEvent(String roleName) {
            if (roleName.equals(getMyPlayer().getAuctioneerRoleName())) {
                handleAuctioneerRoleActivated();
            } else if (roleName.equals(getMyPlayer().getBidderRoleName())) {
                handleBidderRoleActivated();
            }
        }
        
        // ----- PRIVATE -----
        
        private void handleAuctioneerRoleActivated() {
            getMyPlayer().activateRole(getMyPlayer().getAuctioneerRoleName());
        }
        
        private void handleBidderRoleActivated() {
            bidders++;
            if (bidders == 2) {
                // Set the 'Auction' competence argument.
                Item item = getMyPlayer().getItemToSell();
                AuctionArgument auctionArgument = AuctionArgument.createEnvelopeAuctionArgument(
                    item.getName(), item.getPrice());
                System.out.println("----- Auction argument: " + auctionArgument.toString() + " -----");
                
                Future<AuctionResult> future = getMyPlayer().invokeCompetence(
                    getMyPlayer().getAuctionCompetenceName(), auctionArgument);
                future.addObserver(getMyPlayer());
            }
        }
               
        // </editor-fold>
    }
    
    /**
     * The 'Role deactivated' event handler.
     */
    public static class RoleDeactivated_EventHandler
        extends EventHandler<ParticipantPlayer> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Handles the 'Role deactivated' event.
         * @param roleName the name of the deactivated role 
         */
        @Override
        protected void handleEvent(String roleName) {
            if (roleName.equals(getMyPlayer().getAuctioneerRoleName())) {
                getMyPlayer().deactivateRole(getMyPlayer().getAuctioneerRoleName());
            }
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
