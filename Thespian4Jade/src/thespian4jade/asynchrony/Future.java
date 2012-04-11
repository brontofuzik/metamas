package thespian4jade.asynchrony;

import java.io.Serializable;
import thespian4jade.core.player.Player_InvokeCompetence_InitiatorParty;

/**
 * A future is an object that acts as a proxy
 * for a initially unknown result of a yet-to-be-completed computation.
 * @author Lukáš Kúdela
 * @since 2012-03-19
 * @version %I% %G%
 */
public class Future<TValue extends Serializable> implements IObserver, IObservable {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * A flag indicating whether the future is resolved.
     */
    private boolean isResolved;
    
    /**
     * The value of the future.
     */
    private TValue value;
    
    /**
     * An observable to which the implementation of the IObservable
     * interface is delegated.
     */
    private IObservable observable = new Observable();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the value of the future.
     * @return the value of the future
     */
    public TValue getValue() {
        return value;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Determines whether the future is resolved.
     * @return <c>true</c> if the future is resolved, <c>false</c> otherwise.
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
     * Adds an observer. Ad added observer will start observing this observable.
     * @param observer the observer to be added
     */
    @Override
    public void addObserver(IObserver observer) {
        observable.addObserver(observer);
    }

    /**
     * Removes an observer. A removed observer stops observing this observable.
     * @param observer the observe to be removed
     */
    @Override
    public void removeObserver(IObserver observer) {
        observable.removeObserver(observer);
    }

    /**
     * Notifies the observers that a changed in an observable has occurred.
     * @param observable the observable in which a change has occurred
     */
    @Override
    public void notifyObservers(IObservable observable) {
        this.observable.notifyObservers(observable);
    }
    
    /**
     * Notifies the observers that a changed in this future has occurred.
     */
    public void notifyObservers() {
        observable.notifyObservers(this);
    }
    
    // </editor-fold>
}
