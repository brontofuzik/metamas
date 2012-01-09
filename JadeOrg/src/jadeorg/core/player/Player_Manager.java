package jadeorg.core.player;

import jade.lang.acl.ACLMessage;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.MeetRequirementProtocol;
import jadeorg.util.ManagerBehaviour;

/**
 * The player manager behaviour.
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Player_Manager extends ManagerBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Player_Manager() {
        addHandler(new MeetRequirementHandler());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    private Player getMyPlayer() {
        return (Player)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MeetRequirementHandler extends HandlerBehaviour {
    
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        MeetRequirementHandler() {
            super(MeetRequirementProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void handleMessage(ACLMessage message) {
            getMyPlayer().respondToMeetRequirement(message);
        }
               
        // </editor-fold>
    }
    
    // </editor-fold>
}
