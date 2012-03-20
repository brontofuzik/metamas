package example1.players;

import thespian4jade.core.player.responsibility.AsynchronousResponsibility;

/**
 * The 'Execute function' (asynchronous) responsibility.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public class ExecuteFunction_Responsibility extends AsynchronousResponsibility<Integer, Integer> {
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        // Get and convert the argument.
        int argument = getArgument().intValue();
        
        int result = factorial(argument);
        
        // Convert and set the result.
        setResult(new Integer(result));
    }
    
    // ----- PRIVATE -----
    
    private static int factorial(int n) {
        if (n > 1) {
            return n * factorial(n - 1);
        } else {
            return 1;
        }
    }
    
    // </editor-fold>
}
