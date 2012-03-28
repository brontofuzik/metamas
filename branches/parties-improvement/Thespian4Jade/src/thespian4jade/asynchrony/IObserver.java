package thespian4jade.asynchrony;

/**
 * An observer.
 * @author Lukáš Kúdela
 * @since 2012-03-19
 * @version %I% %G%
 */
public interface IObserver {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void update(IObservable observable);
    
    // </editor-fold>
}
