package jadeorg.core.organization;

import jadeorg.core.Initiator;
import jadeorg.proto.roleprotocol.meetrequirementprotocol.MeetRequirementProtocol;

/**
 * A role initiator.
 * @author Lukáš Kúdela
 * @since 2012-01-14
 * @version %I% %G%
 */
public class Role_Initiator extends Initiator {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Role_Initiator(Role role) {
        super(role);        
        addInitiator(MeetRequirementProtocol.getInstance());
    }
    
    // </editor-fold>
}
