package jadeorg.player;

import java.util.Hashtable;

/**
 * A player knowledge base.
 * @author Lukáš Kúdela (2011-10-29)
 * @version 0.1
 */
class PlayerKnowledgeBase {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Hashtable<String, RoleInfo> enactedRoles = new Hashtable<String, RoleInfo>();
    
    private String activeRole;
    
    // </editor-fold>
}
