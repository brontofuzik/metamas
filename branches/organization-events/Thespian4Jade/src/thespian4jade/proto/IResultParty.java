package thespian4jade.proto;

import thespian4jade.core.Future;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-20
 * @version %I% %G%
 */
public interface IResultParty<TResult> {
        
    public TResult getResult();
    
    public Future getResultFuture();
}
