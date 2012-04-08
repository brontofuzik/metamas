package thespian4jade.core.organization;

import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;
import thespian4jade.behaviours.Responder;

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
        addProtocol(ProtocolRegistry.getProtocol(Protocols.ACTIVATE_ROLE_PROTOCOL));
        addProtocol(ProtocolRegistry.getProtocol(Protocols.DEACTIVATE_ROLE_PROTOCOL));
        addProtocol(ProtocolRegistry.getProtocol(Protocols.INVOKE_COMPETENCE_PROTOCOL));
    }
        
    // </editor-fold>
}
