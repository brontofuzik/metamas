package jadeorg.core.player;

import jadeorg.core.Initiator;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import jadeorg.proto.roleprotocol.deactivateroleprotocol.DeactivateRoleProtocol;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerProtocol;

/**
 * The player initiator.
 * @author Lukáš Kúdela
 * @since 2012-01-13
 * @version %I% %G%
 */
public class Player_Initiator extends Initiator {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Player_Initiator(Player player) {
        super(player);
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

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private String organizationName;
        
        private String roleName;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        EnactRoleInitiatorWrapper() {
            super(EnactRoleProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        public void setArguments(Object[] arguments) {
            organizationName = (String)arguments[0];
            roleName = (String)arguments[1];           
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().addBehaviour(new Player_EnactRoleInitiator(organizationName, roleName));
        }
        
        // </editor-fold>
    }
    
    private class DeactRoleInitiatorWrapper extends InitiatorWrapper {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private String organizationName;
        
        private String roleName;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        DeactRoleInitiatorWrapper() {
            super(DeactRoleProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        public void setArguments(Object[] arguments) {
            organizationName = (String)arguments[0];
            roleName = (String)arguments[1];           
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().addBehaviour(new Player_DeactRoleInitiator(organizationName, roleName));
        }
        
        // </editor-fold>
    }
    
    private class ActivateRoleInitiatorWrapper extends InitiatorWrapper {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private String roleName;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ActivateRoleInitiatorWrapper() {
            super(ActivateRoleProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        public void setArguments(Object[] arguments) {
            roleName = (String)arguments[0];
        }
            
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().addBehaviour(new Player_ActivateRoleInitiator(roleName));
        }
        
        // </editor-fold>
    }
    
    private class DeactivateRoleInitiatorWrapper extends InitiatorWrapper {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private String roleName;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        DeactivateRoleInitiatorWrapper() {
            super(DeactivateRoleProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        public void setArguments(Object[] arguments) {
            roleName = (String)arguments[0];
        }
            
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().addBehaviour(new Player_DeactivateRoleInitiator(roleName));
        }
        
        // </editor-fold>
    }
    
    private class InvokePowerInitiatorWrapper extends InitiatorWrapper {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private String powerName;
        
        private Object argument;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        InvokePowerInitiatorWrapper() {
            super(InvokePowerProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        public void setArguments(Object[] arguments) {
            powerName = (String)arguments[0];
            argument = arguments[1];
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().addBehaviour(new Player_InvokePowerInitiator(powerName, argument));
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
