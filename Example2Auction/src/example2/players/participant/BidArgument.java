package example2.players.participant;

/**
 * A 'Bid' requirement arguemnt.
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class BidArgument {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String itemName;
    
    private double currentPrice;
    
    private double bidChange;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public BidArgument(String itemName, double currentPrice, double bidChange) {
        // ----- Preconditions -----
        assert itemName != null && !itemName.isEmpty();
        assert currentPrice > 0;
        assert bidChange > 0;
        // -------------------------
        
        this.itemName = itemName;
        this.currentPrice = currentPrice;
        this.bidChange = bidChange;
    }   
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getItemName() {
        return itemName;
    }
    
    public double getCurrentPrice() {
        return currentPrice;
    }
    
    public double getBidChange() {
        return bidChange;
    }
  
    // </editor-fold>
}
