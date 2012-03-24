package thespian4jade.core.organization;

import jade.lang.acl.ACLMessage;
import thespian4jade.protocols.ProtocolRegistry_StaticClass;
import thespian4jade.protocols.Protocols;
import thespian4jade.protocols.Responder;

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
        addResponder(ProtocolRegistry_StaticClass.getProtocol(Protocols.ENACT_ROLE_PROTOCOL));
        addResponder(ProtocolRegistry_StaticClass.getProtocol(Protocols.DEACT_ROLE_PROTOCOL));
        addResponder(ProtocolRegistry_StaticClass.getProtocol(Protocols.SUBSCRIBE_TO_EVENT_PROTOCOL));
    }

    // </editor-fold>
}
