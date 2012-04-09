package thespian4jade.core.player;

import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;
import thespian4jade.behaviours.Responder;

/**
 * The player responder.
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
class Player_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Player_Responder class.
     * Configures the player to respond to the 'Publish event' and 'Invoke responsibility'
     * protocols.
     */
    Player_Responder() {
        addProtocol(ProtocolRegistry.getProtocol(Protocols.PUBLISH_EVENT_PROTOCOL));
        addProtocol(ProtocolRegistry.getProtocol(Protocols.INVOKE_RESPONSIBILITY_PROTOCOL));
    }
    
    // </editor-fold>
}
