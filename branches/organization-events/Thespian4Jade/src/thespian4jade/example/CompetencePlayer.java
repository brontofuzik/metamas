package thespian4jade.example;

import java.io.Serializable;
import thespian4jade.concurrency.Future;
import thespian4jade.concurrency.IObserver;

/**
 * Competence player - a player with competence to be invoked known at compile-time.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public abstract class CompetencePlayer<TCompetenceArgument extends Serializable>
    extends RolePlayer implements IObserver {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private CompetenceFullName competenceFullName;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected CompetencePlayer(CompetenceFullName competenceFullName) {
        super(competenceFullName.getRoleFullName());
        // ----- Preconditions -----
        assert competenceFullName != null;
        // -------------------------
        
        this.competenceFullName = competenceFullName;
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
        System.out.println("----- Invoking competence: " + competenceFullName.toString() + " -----");
        
        return invokeCompetence(competenceFullName.getCompetenceName(), argument);
    }
    
    /**
     * Schedules the competence invocation.
     * @param <TArgument> The type of the competence argument
     * @param argument the competence argument
     * @param timeout the timeout
     */
    public <TArgument extends Serializable> void scheduleInvokeCompetence(TArgument argument,
        final int timeout) {
        System.out.println("----- Scheduling competence invocation: " + competenceFullName.toString() + " -----");
        
        scheduleInvokeCompetence(competenceFullName, argument, timeout);
    }
    
    // </editor-fold>
}
