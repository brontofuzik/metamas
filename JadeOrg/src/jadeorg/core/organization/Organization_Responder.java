package jadeorg.core.organization;

import jadeorg.core.Responder;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;

/**
 * The organization responder.
 * @author Lukáš Kúdela
 * @since 2011-12-16
 * @version %I% %G%
 */
public class Organization_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    Organization_Responder() {
        addResponder(EnactRoleProtocol.getInstance());
        addResponder(DeactRoleProtocol.getInstance());
    }

    // </editor-fold>
}
