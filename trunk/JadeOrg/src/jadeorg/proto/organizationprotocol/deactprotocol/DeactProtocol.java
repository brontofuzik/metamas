package jadeorg.proto.organizationprotocol.deactprotocol;

import jadeorg.proto.Protocol;

/**
 * The 'Deact' protocol.
 * DP: Singleton - Singleton
 * DP: Abstract factory - Concrete factory
 * @author Lukáš Kúdela
 * @since 2011-10-24
 * @version %I% %G%
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
        super(NAME);
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
        
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void registerMessages() {
        registerMessage(FailureMessage.class);
    }
    
    // </editor-fold>
}
