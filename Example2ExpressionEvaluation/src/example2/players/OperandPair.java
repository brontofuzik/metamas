package example2.players;

import java.io.Serializable;

/**
 * A pair of operands.
 * @author Lukáš Kúdela
 * @since 2011-03-12
 * @version %I% %G%
 */
public class OperandPair implements Serializable {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The first operand.
     */
    private int operand1;
    
    /**
     * The second operand.
     */
    private int operand2;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the OperandPair class.
     * @param operand1
     * @param operand2 
     */
    public OperandPair(int operand1, int operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the first operand.
     * @return the first operand
     */
    public int getOperand1() {
        return operand1;
    }
    
    /**
     * Gets the second operand.
     * @return the second operand
     */
    public int getOperand2() {
        return operand2;
    }
    
    // </editor-fold>
}
