package jadeorg.core.player;

import jade.core.AID;
import jadeorg.core.Initiator;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerProtocol;

/**
 * The player initiator.
 * @author Lukáš Kúdela
 * @since 2012-01-13
 * @version %I% %G%
 */
public class Player_Initiator extends Initiator {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Player_Initiator() {
        addInitiator(new EnactRoleInitiatorWrapper());
        addInitiator(new DeactRoleInitiatorWrapper());
        addInitiator(new ActivateRoleInitiatorWrapper());
        addInitiator(new DeactivateRoleInitiatorWrapper());
        addInitiator(new InvokePowerInitiatorWrapper());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    private Player getMyPlayer() {
        return (Player)myAgent;
    }
    
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class EnactRoleInitiatorWrapper extends InitiatorWrapper {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        EnactRoleInitiatorWrapper() {
            super(EnactRoleProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        private String getOrganizationName() {
            return (String)getArguments()[0];
        }
        
        private String getRoleName() {
            return (String)getArguments()[1];
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo(String.format("Initiating the 'Enact role' (%1$s.%2$s) protocol.",
                getOrganizationName(), getRoleName()));
        
            // TAG YELLOW-PAGES
            //DFAgentDescription organization = YellowPages.searchOrganizationWithRole(this, organizationName, roleName);

            // Check if the organization exists.
            AID organizationAID = new AID(getOrganizationName(), AID.ISLOCALNAME);
            if (organizationAID != null) {
                // The organization exists.
                getMyPlayer().addBehaviour(new Player_EnactRoleInitiator(organizationAID, getRoleName()));
            } else {
                // The organization does not exist.
                String message = String.format("Error enacting a role. The organization '%1$s' does not exist.",
                    getOrganizationName());
                //throw new PlayerException(this, message);
            }
        }
        
        // </editor-fold>
    }
    
    private class DeactRoleInitiatorWrapper extends InitiatorWrapper {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        DeactRoleInitiatorWrapper() {
            super(DeactRoleProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        private String getOrganizationName() {
            return (String)getArguments()[0];
        }
        
        private String getRoleName() {
            return (String)getArguments()[1];
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo(String.format("Initiating the 'Deact role' (%1$s.%2$s) protocol.",
                getOrganizationName(), getRoleName()));

            // TAG YellowPages
            //DFAgentDescription organization = YellowPages.searchOrganizationWithRole(this, organizationName, roleName);

            AID organizationAID = new AID(getOrganizationName(), AID.ISLOCALNAME);
            if (organizationAID != null) {
                // The organizaiton exists.
                getMyPlayer().addBehaviour(new Player_DeactRoleInitiator(organizationAID, getRoleName()));
            } else {
                // The organization does not exist.
                String message = String.format("Error deacting a role. The organization '%1$s' does not exist.",
                        getOrganizationName());
                //throw new PlayerException(this, message);
            }
        }
        
        // </editor-fold>
    }
    
    private class ActivateRoleInitiatorWrapper extends InitiatorWrapper {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ActivateRoleInitiatorWrapper() {
            super(ActivateRoleProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        private String getRoleName() {
            return (String)getArguments()[0];
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo(String.format("Initiating the 'Activate role' (%1$s) protocol.",
                getRoleName()));

            // Check if the role can be activated.
            if (getMyPlayer().knowledgeBase.canActivateRole(getRoleName())) {
                // The role can be activated.
                AID roleAID = getMyPlayer().knowledgeBase.getEnactedRole(getRoleName()).getRoleAID();
                getMyPlayer().addBehaviour(new Player_ActivateRoleInitiator(getRoleName(), roleAID));
            } else {
                // The role can not be activated.
                String message = String.format("Error activating the role '%1$s'. It is not enacted.",
                    getRoleName());
                //throw new PlayerException(this, message);
            }
        }
        
        // </editor-fold>
    }
    
    private class DeactivateRoleInitiatorWrapper extends InitiatorWrapper {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        DeactivateRoleInitiatorWrapper() {
            super(DeactRoleProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        private String getRoleName() {
            return (String)getArguments()[0];
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo(String.format("Initiating the 'Deactivate role' (%1$s) protocol.",
                getRoleName()));

            if (getMyPlayer().knowledgeBase.canDeactivateRole(getRoleName())) {
                // The role can be deactivated.
                AID roleAID = getMyPlayer().knowledgeBase.getEnactedRole(getRoleName()).getRoleAID();
                getMyPlayer().addBehaviour(new Player_DeactivateRoleInitiator(getRoleName(), roleAID));
            } else {
                // The role can not be deactivated.
                String message = String.format("I cannot deactivate the role '%1$s' because I do not play it.",
                    getRoleName());
                //throw new PlayerException(this, message);
            }
        }
        
        // </editor-fold>
    }
    
    private class InvokePowerInitiatorWrapper extends InitiatorWrapper {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        InvokePowerInitiatorWrapper() {
            super(InvokePowerProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        private String getPowerName() {
            return (String)getArguments()[0];
        }
        
        private Object getArgument() {
            return getArguments()[1];
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo(String.format("Initiating the 'Invoke power' (%1$s) protocol.",
                getPowerName()));

            if (getMyPlayer().knowledgeBase.canInvokePower(getPowerName())) {
                // The player can invoke the power.
                getMyPlayer().addBehaviour(new Player_InvokePowerInitiator(getPowerName(), getArgument()));
            } else {
                // The player can not invoke the power.
                String message = String.format("I cannot invoke the power '%1$s'.", getPowerName());
                //throw new PlayerException(getMyPlayer(), message);
            }
        }
        
        // </editor-fold> 
    }
    
    // </editor-fold>
}
