package thespian4jade.asynchrony;

/**
 * An interface of an object acting as the 'Observer' in the 'Observer'
 * design pattern.
 * @author Lukáš Kúdela
 * @since 2012-03-19
 * @version %I% %G%
 */
public interface IObserver {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Updates this observer at the notification from the observable.
     * @param observable the observable that caused the update
     */
    public void update(IObservable observable);
    
    // </editor-fold>
}
