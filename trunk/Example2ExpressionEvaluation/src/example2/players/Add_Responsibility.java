package example2.players;

import thespian4jade.core.player.responsibility.AsynchronousResponsibility;

/**
 * The 'Add' (asynchronous) responsibility.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Add_Responsibility extends AsynchronousResponsibility<OperandPair, Integer> {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        int addend1 = getArgument().getOperand1();
        int addend2 = getArgument().getOperand2();
        
        int sum = add(addend1, addend2);
        
        setResult(new Integer(sum));
    }
    
    // ----- PRIVATE -----
    
    /**
     * Computes addition.
     * @param addend1 the first addend
     * @param addend2 the second addend
     * @return the sum
     */
    private int add(int addend1, int addend2) {
        return addend1 + addend2;
    }
    
    // </editor-fold>
}
