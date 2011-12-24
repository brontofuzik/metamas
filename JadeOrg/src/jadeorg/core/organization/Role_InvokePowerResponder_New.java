package jadeorg.core.organization;

import jade.core.AID;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerProtocol;

/**
 * An 'Invoke power' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Role_InvokePowerResponder_New extends Party {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">

    private static final String NAME = "invoke-power-responder-new";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID playerAID;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Role_InvokePowerResponder_New(AID playerAID) {
        super(NAME);
        // ----- Preconditions -----
        assert playerAID != null;
        // -------------------------
        
        this.playerAID = playerAID;
        
        registerStatesAndTransitions();
    }
    
    private void registerStatesAndTransitions() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @Override
    public Protocol getProtocol() {
        return InvokePowerProtocol.getInstance();
    }
    
    // </editor-fold>
}
