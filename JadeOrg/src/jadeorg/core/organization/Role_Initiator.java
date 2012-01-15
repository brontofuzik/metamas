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
        
        addInitiator(new MeetRequirementInitiatorWrapper());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    private Role getMyRole() {
        return (Role)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MeetRequirementInitiatorWrapper extends InitiatorWrapper {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private String requirementName;
        
        private Object argument;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        MeetRequirementInitiatorWrapper() {
            super(MeetRequirementProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        public void setArguments(Object[] arguments) {
            requirementName = (String)arguments[0];
            argument = arguments[1]; 
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyRole().logInfo(String.format("Initiating the 'Meet requirement' (%1$s) protocol.",
                requirementName));

            if (true) {
                // The role can invoke the requirement.
                getMyRole().addBehaviour(new Role_MeetRequirementInitiator(requirementName, argument));
            } else {
                // The role can not invoke the requirement.
                String message = String.format("I cannot invoke the requirement '%1$s'.",
                    requirementName);
                //throw new PlayerException(getMyPlayer(), message);
            }
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
