package thespian4jade.example;

import java.io.Serializable;
import thespian4jade.asynchrony.Future;
import thespian4jade.asynchrony.IObserver;

/**
 * Competence-invoker player - a player with a predetermined competence to invoke.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public abstract class CompetenceInvokerPlayer<TCompetenceArgument extends Serializable>
    extends RoleEnacterPlayer implements IObserver {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String competenceName;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected CompetenceInvokerPlayer(RoleFullName roleFullName, String competenceName) {
        super(roleFullName);
        // ----- Preconditions -----
        assert competenceName != null && !competenceName.isEmpty();
        // -------------------------
        
        this.competenceName = competenceName;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Invokes the competence.
     * @param <TArgument> the type of the competence argument
     * @param <TResult> the type of the competence result
     * @param argument the competence argument
     * @return the competence result future
     */
    public <TArgument extends Serializable, TResult extends Serializable>
        Future<TResult> invokeCompetence(TArgument argument) {
        System.out.println("----- Invoking competence: " + competenceName + " -----");
        
        return invokeCompetence(competenceName, argument);
    }
    
    /**
     * Schedules the competence invocation.
     * @param <TArgument> The type of the competence argument
     * @param argument the competence argument
     * @param timeout the timeout of execution
     */
    public <TArgument extends Serializable> void scheduleInvokeCompetence(TArgument argument,
        final int timeout) {
        System.out.println("----- Scheduling competence invocation: " + competenceName + " -----");
        
        scheduleInvokeCompetence(competenceName, argument, timeout);
    }
    
    // </editor-fold>
}
