package thespian4jade.core.player;

import thespian4jade.proto.roleprotocol.invokerequirementprotocol.InvokeRequirementProtocol;
import thespian4jade.core.Responder;

/**
 * The player responder.
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Player_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Player_Responder() {
        addResponder(InvokeRequirementProtocol.getInstance());
    }
    
    // </editor-fold>
}
