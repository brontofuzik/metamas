package jadeorg.core.organization;

import jade.core.AID;
import jadeorg.proto_old.ActiveState;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerProtocol;
import jadeorg.proto_old.State;
import java.util.Hashtable;
import java.util.Map;

/**
 * An 'Invoke power' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2011-11-12
 * @version %I% %G%
 */
public class zzzRole_InvokePowerResponder extends  Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">

    private static final String NAME = "invoke-power-responder";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Map<String, Power> powers = new Hashtable<String, Power>();
    
    private Power currentPower;
    
    private State managePower;
    
    private State sendPowerResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
   
    public zzzRole_InvokePowerResponder() {
        super(NAME);
        initializeFSM();
    }
    
    private void initializeFSM() {
        // ----- States -----
        managePower = new ManagePower();
        sendPowerResult = new SendPowerResult();
        // ------------------
        
        // Register states.
        registerFirstState(managePower, managePower.getName());
        registerLastState(sendPowerResult, sendPowerResult.getName());
        
        // Register transitions.
        // No transitions.
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public Protocol getProtocol() {
        return InvokePowerProtocol.getInstance();
    }
    
    // ----- PRIVATE -----
    
    private Role getMyRole() {
        return (Role)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    boolean containsPower(String powerName) {
        return powers.containsKey(powerName);
    }
    
    void addPower(Power power) {    
        power.buildFSM();
        
        powers.put(power.getName(), power);
        
        // Register the power-related state.
        registerState(power, power.getName());
        
        // Register the power-related transitions.
        registerTransition(managePower.getName(), power.getName(), power.hashCode());
        registerDefaultTransition(power.getName(), sendPowerResult.getName());
    }
    
    // ---------- PROTECTED ----------
    
    protected void invokePower(String powerName, AID playerAID, String arguments) {
        if (containsPower(powerName)) {
            currentPower = getPower(powerName);
            reset();
        }
    }
    
    // ---------- PRIVATE ----------
    
    private Power getPower(String powerName) {
        return powers.get(powerName);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Manage power' (active) state.
     */
    private class ManagePower extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "manage-power";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ManagePower() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            setExitValue(currentPower.hashCode());
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Manage result' (active) state.
     */
    private class SendPowerResult extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-power-result";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendPowerResult() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
