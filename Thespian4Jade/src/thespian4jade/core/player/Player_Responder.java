package thespian4jade.core.player;

import jade.lang.acl.ACLMessage;
import thespian4jade.protocols.ProtocolRegistry_StaticClass;
import thespian4jade.protocols.Protocols;
import thespian4jade.behaviours.Responder;

/**
 * The player responder.
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Player_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Player_Responder class.
     * Configures the player responder - adds individual protocol responders.
     */
    Player_Responder() {
        addResponder(ProtocolRegistry_StaticClass.getProtocol(Protocols.INVOKE_RESPONSIBILITY_PROTOCOL));
        addResponder(ProtocolRegistry_StaticClass.getProtocol(Protocols.PUBLISH_EVENT_PROTOCOL), ACLMessage.INFORM);
    }
    
    // </editor-fold>
}
