package example3.players.calculator;

import thespian4jade.core.player.requirement.OneShotRequirement;

/**
 * The 'Divide' (one-shot) responsibility.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Divide_Responsibility extends OneShotRequirement<OperandPair, Integer> {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        int dividend = getArgument().getOperand1();
        int divisor = getArgument().getOperand2();
        
        int quotient = divide(dividend, divisor);
        
        setResult(new Integer(quotient));
    }
    
    // ----- PRIVATE -----
    
    // dividend / divisor = quotient (remainder) 
    private int divide(int dividend, int divisor) {
        return dividend / divisor;
    }
    
    // </editor-fold>
}
