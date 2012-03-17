package example3.players.participant;

/**
 * An auction item.
 * @author Lukáš Kúdela
 * @since 2012-01-20
 * @version %I% %G%
 */
public class Item {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
    
    private double price;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
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
    
    String getName() {
        return name;
    }
    
    double getPrice() {
        return price;
    }
    
    // </editor-fold>
}
