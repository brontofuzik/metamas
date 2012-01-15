package jadeorg.core.player;

import jade.lang.acl.ACLMessage;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.MeetRequirementProtocol;
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
        addResponder(new MeetRequirementResponderWrapper());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    private Player getMyPlayer() {
        return (Player)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MeetRequirementResponderWrapper extends ResponderWrapper {
    
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        MeetRequirementResponderWrapper() {
            super(MeetRequirementProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void handleMessage(ACLMessage message) {
            getMyPlayer().addBehaviour(new Player_MeetRequirementResponder(message));
        }
               
        // </editor-fold>
    }
    
    // </editor-fold>
}
