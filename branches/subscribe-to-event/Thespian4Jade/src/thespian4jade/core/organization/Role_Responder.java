package thespian4jade.core.organization;

import thespian4jade.proto.ProtocolRegistry;
import thespian4jade.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import thespian4jade.proto.roleprotocol.deactivateroleprotocol.DeactivateRoleProtocol;
import thespian4jade.proto.roleprotocol.invokecompetenceprotocol.InvokeCompetenceProtocol;
import thespian4jade.proto.Responder;

/**
 * The role responder.
 * @author Lukáš Kúdela
 * @since 2011-12-18
 * @version %I% %G%
 */
public class Role_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
  
    /**
     * Initializes a new instance of the Role_Responder class.
     * Configures the role responder - adds individual protocol responders.
     */
    Role_Responder() {
        addResponder(ProtocolRegistry.getProtocol(ProtocolRegistry.ACTIVATE_ROLE_PROTOCOL));
        addResponder(ProtocolRegistry.getProtocol(ProtocolRegistry.DEACTIVATE_ROLE_PROTOCOL));
        addResponder(ProtocolRegistry.getProtocol(ProtocolRegistry.INVOKE_COMPETENCE_PROTOCOL));
    }
        
    // </editor-fold>
}
