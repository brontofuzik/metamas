package thespian4jade.asynchrony;

import java.util.ArrayList;
import java.util.List;

/**
 * An observable to which the implementation of the IObservable interface
 * can be delegated instead of implementing it from scratch.
 * @author Lukáš Kúdela
 * @since 2012-03-19
 * @version %I% %G%
 */
public class Observable implements IObservable {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The observers observing this observable.
     */
    private List<IObserver> observers;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Adds an observer. Ad added observer will start observing this observable.
     * @param observer the observer to be added
     */
    @Override
    public synchronized void addObserver(IObserver observer) {
        if (observers == null) {
            observers = new ArrayList<IObserver>();
        }
        
        // Only add the observer if it is not already contained in the list.
        if (!observers.contains(observer)) {
            observers.add(observer);
        }        
    }

    /**
     * Removes an observer. A removed observer stops observing this observable.
     * @param observer the observe to be removed
     */
    @Override
    public synchronized void removeObserver(IObserver observer) {
        if (observers == null) {
            return;
        }
        
        // Only remove the observer if it is still contained in the list.
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    /**
     * Notifies the observers that a changed in an observable has occurred.
     * @param observable the observable in which a change has occurred
     */
    @Override
    public synchronized void notifyObservers(IObservable observable) {
        if (observers == null) {
            return;
        }
        
        for (IObserver observer : observers) {
            observer.update(observable);
        }
    }
    
    // </editor-fold>
}
