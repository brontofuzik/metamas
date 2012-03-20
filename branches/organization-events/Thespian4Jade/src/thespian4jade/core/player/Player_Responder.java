package thespian4jade.core.player;

import jade.lang.acl.ACLMessage;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.InvokeResponsibilityProtocol;
import thespian4jade.proto.Responder;
import thespian4jade.proto.organizationprotocol.raiseeventprotocol.RaiseEventProtocol;

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
        addResponder(RaiseEventProtocol.getInstance(), ACLMessage.INFORM);
        addResponder(InvokeResponsibilityProtocol.getInstance());
    }
    
    // </editor-fold>
}
