package thespian4jade.asynchrony;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-19
 * @version %I% %G%
 */  
public interface IObservable {
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void addObserver(IObserver observer);

    public void removeObserver(IObserver observer);

    public void notifyObservers(IObservable observable);
    
    // </editor-fold>
}
