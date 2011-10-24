package jadeorg.proto.deactprotocol;

import jadeorg.proto.Protocol;

/**
 * The 'Deact' protocol.
 * DP: Singleton - Singleton
 * DP: Abstract factory - Concrete factory
 * @author Lukáš Kúdela (2011-10-24)
 * @version 0.1
 */
public class DeactProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "deact-protocol";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static DeactProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private DeactProtocol() {
        registerMessages();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public static DeactProtocol getInstance() {
        if (singleton == null) {
            singleton = new DeactProtocol();
        }
        return singleton;              
    }
    
    @Override
    public String getName() {
        return NAME;
    }
        
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void registerMessages() {
        registerMessage(new FailureMessage());
    }
    
    // </editor-fold>
}
