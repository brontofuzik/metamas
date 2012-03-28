package thespian4jade.asynchrony;

import java.io.Serializable;
import thespian4jade.core.player.Player_InvokeCompetence_InitiatorParty;

/**
 * A future - an asynchronous method call result.
 * @author Lukáš Kúdela
 * @since 2012-03-19
 * @version %I% %G%
 */
public class Future<TValue extends Serializable> implements IObserver, IObservable {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * A flag indicating whether the future is resolved.
     * An alternative term is "fulfilled".
     */
    private boolean isResolved;
    
    /**
     * The value of the future.
     */
    private TValue value;
    
    private IObservable observable = new Observable();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the value of the future.
     * @return the value
     */
    public TValue getValue() {
        return value;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Determines whether the future is resolved.
     * @return <c>true</c> if the future is resolved; <c>false</c> otherwise.
     */
    public boolean isResolved() {
        return isResolved;
    }
    
    /**
     * The IObserver method.
     * @param observable 
     */
    @Override
    public void update(IObservable observable) {
        value = ((Player_InvokeCompetence_InitiatorParty<?, TValue>)observable)
            .getCompetenceResult();
        notifyObservers();
    }
    
    /**
     * The IObservable method.
     * @param observer 
     */
    @Override
    public void addObserver(IObserver observer) {
        observable.addObserver(observer);
    }

    /**
     * The IObservable method.
     * @param observer 
     */
    @Override
    public void removeObserver(IObserver observer) {
        observable.removeObserver(observer);
    }

    /**
     * The IObservable method.
     * @param observable 
     */
    @Override
    public void notifyObservers(IObservable observable) {
        this.observable.notifyObservers(observable);
    }
    
    public void notifyObservers() {
        observable.notifyObservers(this);
    }
    
    // </editor-fold>
}
