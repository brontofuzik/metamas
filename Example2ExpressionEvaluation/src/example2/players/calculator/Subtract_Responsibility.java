package example2.players.calculator;

import thespian4jade.core.player.responsibility.OneShotResponsibility;

/**
 * The 'Subtract' (one-shot) responsibility.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Subtract_Responsibility extends OneShotResponsibility<OperandPair, Integer> {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        int minuend = getArgument().getOperand1();
        int subtrahend = getArgument().getOperand2();
        
        int difference = subtract(minuend, subtrahend);
        
        setResult(new Integer(difference));
    }
    
    // ----- PRIVATE -----
    
    // minuend - subtrahend = difference
    private int subtract(int minuend, int subtrahend) {
        return minuend - subtrahend;
    }
    
    // </editor-fold>
}
