package jadeorg.core.player;

import jadeorg.core.Initiator;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import jadeorg.proto.roleprotocol.deactivateroleprotocol.DeactivateRoleProtocol;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerProtocol;

// TAG OBSOLETE
/**
 * The player initiator.
 * @author Lukáš Kúdela
 * @since 2012-01-13
 * @version %I% %G%
 */
public class Player_Initiator extends Initiator {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Player_Initiator(Player player) {
        super(player);
        addInitiator(EnactRoleProtocol.getInstance());
        addInitiator(DeactRoleProtocol.getInstance());
        addInitiator(ActivateRoleProtocol.getInstance());
        addInitiator(DeactivateRoleProtocol.getInstance());
        addInitiator(InvokePowerProtocol.getInstance());
    }
    
    // </editor-fold>
}
