package jadeorg.core.player;

import jadeorg.proto.roleprotocol.invokerequirementprotocol.InvokeRequirementProtocol;
import jadeorg.core.Responder;

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
