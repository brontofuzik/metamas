package jadeorg.core.organization;

import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;

/**
 * An 'Enact role' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public class Organization_EnactRoleResponder_New extends Party {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "enact-role-responder-new";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Organization_EnactRoleResponder_New() {
        super(NAME);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @Override
    protected Protocol getProtocol() {
        return EnactRoleProtocol.getInstance();
    }
    
    // </editor-fold>
}
