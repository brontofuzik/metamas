package thespian4jade.core.player;

import thespian4jade.core.Initiator;
import thespian4jade.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import thespian4jade.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import thespian4jade.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import thespian4jade.proto.roleprotocol.deactivateroleprotocol.DeactivateRoleProtocol;
import thespian4jade.proto.roleprotocol.invokepowerprotocol.InvokePowerProtocol;

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
