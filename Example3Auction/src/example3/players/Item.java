package example3.players;

/**
 * An auction item.
 * @author Lukáš Kúdela
 * @since 2012-01-20
 * @version %I% %G%
 */
public class Item {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The name of the item.
     */
    private String name;
    
    /**
     * The price.
     */
    private double price;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Item class.
     * @param name
     * @param price 
     */
    Item(String name, double price) {
        // ----- Preconditions -----
        assert name != null && !name.isEmpty();
        assert price > 0;
        // -------------------------
        
        this.name = name;
        this.price = price;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of the item.
     * @return the name of the item
     */
    String getName() {
        return name;
    }
    
    /**
     * Gets the price.
     * @return the price
     */
    double getPrice() {
        return price;
    }
    
    // </editor-fold>
}
