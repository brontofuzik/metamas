/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeorg.proto.roleprotocol.invokepowerprotocol;

import jadeorg.proto_new.Protocol;

/**
 * @author Lukáš Kúdela
 * @since 2011-11-30
 * @version %I% %G%
 */
public class InvokePowerProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    /** The name of the protocol. */
    private static final String NAME = "invoke-power-protocol";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The singleton instance of this protocol. */
    private static InvokePowerProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Creates a new instance of the InvokePowerProtocol class.
     */
    private InvokePowerProtocol() {
        super(NAME);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the singleton instance of this protocol.
     * @return the singleton instance of this protocol
     */
    public static InvokePowerProtocol getInstance() {
        if (singleton == null) {
            singleton = new InvokePowerProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>
}
