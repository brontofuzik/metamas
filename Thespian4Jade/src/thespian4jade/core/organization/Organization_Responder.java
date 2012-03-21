package thespian4jade.core.organization;

import jade.lang.acl.ACLMessage;
import thespian4jade.proto.Responder;
import thespian4jade.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import thespian4jade.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import thespian4jade.proto.organizationprotocol.subscribetoeventprotocol.SubscribeToEventProtocol;

/**
 * The organization responder.
 * @author Lukáš Kúdela
 * @since 2011-12-16
 * @version %I% %G%
 */
public class Organization_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Initializes a new instance of the Organization_Responder class.
     * Configures the organization responder - adds individual protocol responders.
     */
    Organization_Responder() {
        addResponder(EnactRoleProtocol.getInstance());
        addResponder(DeactRoleProtocol.getInstance());
        addResponder(SubscribeToEventProtocol.getInstance(), ACLMessage.SUBSCRIBE);
    }

    // </editor-fold>
}
