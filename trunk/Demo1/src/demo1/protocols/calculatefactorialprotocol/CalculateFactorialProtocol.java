package demo1.protocols.calculatefactorialprotocol;

import jadeorg.proto.Protocol;

/**
 * The 'Calculate factorial' protocol.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public class CalculateFactorialProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static CalculateFactorialProtocol singleton;
    
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
