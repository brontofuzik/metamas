package example2.players;

import thespian4jade.core.player.responsibility.AsynchronousResponsibility;

/**
 * The 'Subtract' (asynchronous) responsibility.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Subtract_Responsibility extends AsynchronousResponsibility<OperandPair, Integer> {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        int minuend = getArgument().getOperand1();
        int subtrahend = getArgument().getOperand2();
        
        int difference = subtract(minuend, subtrahend);
        
        setResult(new Integer(difference));
    }
    
    // ----- PRIVATE -----
    
    /**
     * Computes subtraction.
     * @param minuend the minuend
     * @param subtrahend the subtrahend
     * @return the difference
     */
    private int subtract(int minuend, int subtrahend) {
        return minuend - subtrahend;
    }
    
    // </editor-fold>
}
