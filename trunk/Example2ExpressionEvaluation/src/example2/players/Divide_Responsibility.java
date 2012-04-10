package example2.players;

import thespian4jade.core.player.responsibility.AsynchronousResponsibility;

/**
 * The 'Divide' (asynchronous) responsibility.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Divide_Responsibility extends AsynchronousResponsibility<OperandPair, Integer> {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        int dividend = getArgument().getOperand1();
        int divisor = getArgument().getOperand2();
        
        int quotient = divide(dividend, divisor);
        
        setResult(new Integer(quotient));
    }
    
    // ----- PRIVATE -----
   
    /**
     * Computes division.
     * @param dividend the dividend
     * @param divisor the divisor
     * @return the quotient
     */
    private int divide(int dividend, int divisor) {
        return dividend / divisor;
    }
    
    // </editor-fold>
}
