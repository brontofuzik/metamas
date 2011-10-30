package jadeorg.proto.enactprotocol;

import jadeorg.proto.Protocol;

/**
 * The 'Enact' protocol.
 * DP: This class plays the role of 'Singleton' in the Singleton design pattern.
 * DP: This cass plays the role of 'Concrete factory' in the Abstract factory design pattern.
 * @author Lukáš Kúdela
 * @since 2011-10-21
 * @version %I% %G%
 */
public class EnactProtocol extends Protocol {
   
    // <editor-fold defaultstate="collapsed" desc="Fields">
   
    private static EnactProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public String getName() {
        return "enact-protocol";
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private EnactProtocol() {
        registerMessages();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public static EnactProtocol getInstance() {
        if (singleton == null) {
            singleton = new EnactProtocol();
        }
        return singleton;
    }
    
    // ---------- PRIVATE ----------

    private void registerMessages() {
        registerMessage(new RequirementsMessage());        
        registerMessage(new RefuseMessage());
    }
    
    // </editor-fold>
}
