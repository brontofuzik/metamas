package thespian4jade.core.organization;

import thespian4jade.core.Initiator;
import thespian4jade.proto.roleprotocol.invokerequirementprotocol.InvokeRequirementProtocol;

// TAG OBSOLETE
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
        addInitiator(InvokeRequirementProtocol.getInstance());
    }
    
    // </editor-fold>
}
