package example1.players.demo;

import thespian4jade.core.Future;
import thespian4jade.core.IObservable;
import thespian4jade.core.IObserver;
import thespian4jade.core.player.EventHandler;

/**
 * The Demo1 player. The player playing the 'Invoker' role.
 * @author Lukáš Kúdela
 * @since 2011-12-31
 * @version %I% %G%
 */
public class Demo1_Player extends Demo_Player implements IObserver {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Creates a new Demo player who will enact the Invoker role.
     * @param competenceFullName the full name of the competence to invoke 
     */
    public Demo1_Player() {
        super(new RoleFullName("functionInvocation_Organization.Invoker_Role"));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    public void update(IObservable observable) {
        Integer competenceResult = ((Future<Integer>)observable).getValue();
        System.out.println("----- 'Invoke function' competence result: " + competenceResult + " -----");
        deactivateRole();
    }
    
    // ----- PACKAGE -----
    
    void invokeCompetence() {
        CompetenceFullName competenceFullName = new CompetenceFullName("functionInvocation_Organization.Invoker_Role.InvokeFunction_Competence");
        Integer competenceArgument = new Integer(10);
        System.out.println("----- 'Invoke function' competence argument: " + competenceArgument + " -----");
        
        Future<Integer> future = invokeCompetence(competenceFullName.getCompetenceName(), competenceArgument);
        future.addObserver(this);
    }

    // ----- PROTECTED -----
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add event handlers.
        addEventHandler("role-enacted", ExecuterRoleEnacted_EventHandler.class);
        addEventHandler("role-activated", ExecuterRoleActivated_EventHandler.class);
        addEventHandler("role-deactivated", ExecuterRoleDeactivated_EventHandler.class);
        
        // Schedule behaviours.
        scheduleEnactRole(2000);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * @author Lukáš Kúdela
     * @since 2012-03-19
     * @version %I% %G%
     */
    public static class ExecuterRoleEnacted_EventHandler
        extends EventHandler<Demo1_Player> {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void handleEvent(String roleName) {
            if (roleName.equals("Executer_Role")) {
                getMyPlayer().activateRole();
            }
        }

        // </editor-fold>
    }
    
    /**
     * @author Lukáš Kúdela
     * @since 2012-03-19
     * @version %I% %G%
     */
    public static class ExecuterRoleActivated_EventHandler
        extends EventHandler<Demo1_Player> {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void handleEvent(String roleName) {
            if (roleName.equals("Executer_Role")) {
                getMyPlayer().invokeCompetence();
            }
        }
    
        // </editor-fold>    
    }
    
    /**
     * @author Lukáš Kúdela
     * @since 2012-03-19
     * @version %I% %G%
     */
    public static class ExecuterRoleDeactivated_EventHandler
        extends EventHandler<Demo1_Player> {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void handleEvent(String roleName) {
        if (roleName.equals("Executer_Role")) {
            getMyPlayer().deactRole();
        }
    }
    
    // </editor-fold>  
    }
    

    
    // </editor-fold>
}
