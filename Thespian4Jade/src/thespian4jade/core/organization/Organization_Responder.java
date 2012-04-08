package thespian4jade.core.organization;

import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;
import thespian4jade.behaviours.Responder;

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
        addProtocol(ProtocolRegistry.getProtocol(Protocols.ENACT_ROLE_PROTOCOL));
        addProtocol(ProtocolRegistry.getProtocol(Protocols.DEACT_ROLE_PROTOCOL));
        addProtocol(ProtocolRegistry.getProtocol(Protocols.SUBSCRIBE_TO_EVENT_PROTOCOL));
    }

    // </editor-fold>
}
