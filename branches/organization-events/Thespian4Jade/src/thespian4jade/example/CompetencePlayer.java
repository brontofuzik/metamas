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
    
    public <TArgument extends Serializable, TResult extends Serializable>
        Future<TResult> invokeCompetence(TArgument argument) {
        System.out.println("----- Invoking competence: " + competenceFullName.toString() + " -----");
        
        return invokeCompetence(competenceFullName.getCompetenceName(), argument);
    }
    
    // </editor-fold>
}
