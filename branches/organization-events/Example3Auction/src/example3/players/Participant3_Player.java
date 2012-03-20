package example3.players;

import example3.organizations.auction.auctioneer.auction.AuctionArgument;

/**
 * The 'Participant3' player.
 * @author Lukáš Kúdela
 * @since 2012-01-20
 * @version %I% %G%
 */
public class Participant3_Player extends Participant_Player {
       
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes the 'Participant3' player.
     */
    public Participant3_Player() {
        addItemToBuy(new Item(POLLOCK, 155.8));
        addItemToBuy(new Item(KOONING, 154)); // Highest bid.
        
        addItemToSell(new Item(KLIMT, 135));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Schedule the Pollock auction.
     * Design pattern: Template method, Role: Primitive operation
     * @param
     * @return
     */
    @Override
    protected int doSchedulePollockAuction(int timeout) {
        // Enact the 'Bidder' role.
        timeout = scheduleEnactRole(bidderRoleFullName, timeout);
        
        timeout += 2000;
        
        // Activate the 'Bidder' role.
        timeout = scheduleActivateRole(bidderRoleFullName, timeout);
        
        timeout += 4000;
        
        // Deactivate the 'Bidder' role.
        timeout = scheduleDeactivateRole(bidderRoleFullName, timeout);
        
        timeout += 2000;
        
        // Deact the 'Bidder' role.
        return scheduleDeactRole(bidderRoleFullName, timeout);
    }

    /**
     * Schedule the Kooning auction.
     * Design pattern: Template method, Role: Primitive operation
     * @param timeout 
     * @return 
     */
    @Override
    protected int doScheduleKooningAuction(int timeout) {
        // Enact the 'Bidder' role.
        timeout = scheduleEnactRole(bidderRoleFullName, timeout);
        
        timeout += 2000;
        
        // Activate the 'Bidder' role.
        timeout = scheduleActivateRole(bidderRoleFullName, timeout);
        
        timeout += 4000;
        
        // Deactivate the 'Bidder' role.
        timeout = scheduleDeactivateRole(bidderRoleFullName, timeout);
        
        timeout += 2000;
        
        // Deact the 'Bidder' role.
        return scheduleDeactRole(bidderRoleFullName, timeout);
    }

    /**
     * Schedule the Klimt auction.
     * Design pattern: Template method, Role: Primitive operation
     * @param timeout
     * @return 
     */
    @Override
    protected int doScheduleKlimtAuction(int timeout) {
        // Enact the 'Auctioneer' role.
        timeout = scheduleEnactRole(auctioneerRoleFullName, timeout);
                
        timeout += 2000;
        
        // Activate the 'Auctioneer' role.
        timeout = scheduleActivateRole(auctioneerRoleFullName, timeout);
        
        // Invoke the 'Auction' competence.
        Item klimt = getItemToSell(KLIMT);
        AuctionArgument auctionArgument = AuctionArgument.createEnvelopeAuctionArgument(
            klimt.getName(), klimt.getPrice());
        timeout = scheduleInvokeCompetence(auctionCompetenceFullName, auctionArgument,
            timeout, 4000);
       
        // Deactivate the 'Auctioneer' role.
        timeout = scheduleDeactivateRole(auctioneerRoleFullName, timeout);
        
        timeout += 2000;
        
        // Deact the 'Auctioneer' role.
        return scheduleDeactRole(auctioneerRoleFullName, timeout);
    }
    
    // </editor-fold>
}
