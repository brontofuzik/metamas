package example3.players.calculator;

import thespian4jade.core.player.requirement.OneShotRequirement;

/**
 * The 'Add' (one-shot) responsibility.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Add_Responsibility extends OneShotRequirement<OperandPair, Integer> {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        int addend1 = getArgument().getOperand1();
        int addend2 = getArgument().getOperand2();
        
        int sum = add(addend1, addend2);
        
        setResult(new Integer(sum));
    }
    
    // ----- PRIVATE -----
    
    // addend + addend = sum
    private int add(int addend1, int addend2) {
        return addend1 + addend2;
    }
    
    // </editor-fold>
}
