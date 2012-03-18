package thespian4jade.core.organization;

import jade.lang.acl.ACLMessage;
import thespian4jade.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import thespian4jade.proto.roleprotocol.deactivateroleprotocol.DeactivateRoleProtocol;
import thespian4jade.proto.roleprotocol.invokecompetenceprotocol.InvokeCompetenceProtocol;
import thespian4jade.core.Responder;

/**
 * The role responder.
 * @author Lukáš Kúdela
 * @since 2011-12-18
 * @version %I% %G%
 */
public class Role_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
        
    Role_Responder() {
        addResponder(ActivateRoleProtocol.getInstance());
        addResponder(DeactivateRoleProtocol.getInstance());
        addResponder(InvokeCompetenceProtocol.getInstance());
    }
        
    // </editor-fold>
}
