package example2.players;

import thespian4jade.core.player.responsibility.AsynchronousResponsibility;

/**
 * The 'Multiply' (asynchronous) responsibility.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Multiply_Responsibility extends AsynchronousResponsibility<OperandPair, Integer> {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        int factor1 = getArgument().getOperand1();
        int factor2 = getArgument().getOperand2();
        
        int product = multiply(factor1, factor2);
        
        setResult(new Integer(product));
    }
    
    // ----- PRIVATE -----
    
    /**
     * Computes multiplication.
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the product
     */
    private int multiply(int factor1, int factor2) {
        return factor1 * factor2;
    }
    
    // </editor-fold>
}
