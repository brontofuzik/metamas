package demo1.protocols;

import jadeorg.proto.Protocol;

/**
 * The 'Calculate factorial' protocol.
 * @author Lukáš Kúdela
 * @since 2011-01-02
 * @version %I% %G%
 */
public class CalculateFactorialProtocol extends Protocol {
    
    private static final String NAME = "calculate-factorial-protocol";
    
    private static CalculateFactorialProtocol singleton;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private CalculateFactorialProtocol() {
        super(NAME);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public static CalculateFactorialProtocol getInstance() {
        if (singleton == null) {
            singleton = new CalculateFactorialProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>
}
