package thespian4jade.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-19
 * @version %I% %G%
 */
public class Observable implements IObservable {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private List<IObserver> observers;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public synchronized void addObserver(IObserver observer) {
        if (observers == null) {
            observers = new ArrayList<IObserver>();
        }
        
        // Only add the observer if it is not already contained in the list.
        if (!observers.contains(this)) {
            observers.add(observer);
        }        
    }

    @Override
    public synchronized void removeObserver(IObserver observer) {
        if (observers == null) {
            return;
        }
        
        // Only remove the observer if it is still contained in the list.
        if (observers.contains(this)) {
            observers.remove(observer);
        }
    }

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
