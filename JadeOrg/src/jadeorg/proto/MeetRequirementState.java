package jadeorg.proto;

import jadeorg.core.organization.Role_MeetRequirementInitiator;

/**
 * A 'Meet requirement' (party) state.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public abstract class MeetRequirementState extends PartyState {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected MeetRequirementState(String name, String requirementName) {
        super(name, new Role_MeetRequirementInitiator(requirementName));
    }
            
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected abstract Object getRequirementArgument();
    
    protected abstract void setRequirementResult(Object requirementResult);
    
    // ----- PROTECTED -----
    
    private Role_MeetRequirementInitiator getUnderlyingMRIParty() {
        return (Role_MeetRequirementInitiator)super.getUnderlyingParty();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        System.out.println("----- BEFORE SET ARGUMENT -----");
        getUnderlyingMRIParty().setRequirementArgument(getRequirementArgument());
        System.out.println("----- AFTER SET ARGUMENT -----");
        super.action();
        System.out.println("----- BEFORE GET RESULT -----");
        setRequirementResult(getUnderlyingMRIParty().getRequirementResult());
        System.out.println("----- AFTER GET RESULT -----");
    }
    
    // </editor-fold>
}
