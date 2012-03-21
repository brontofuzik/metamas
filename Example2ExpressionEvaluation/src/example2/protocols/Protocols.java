/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example2.protocols;

import example2.protocols.evaluateaddition.EvaluateAdditionProtocol;
import example2.protocols.evaluatedivision.EvaluateDivisionProtocol;
import example2.protocols.evaluateexpression.EvaluateExpressionProtocol;
import example2.protocols.evaluatemultiplication.EvaluateMultiplicationProtocol;
import example2.protocols.evaluatesubtraction.EvaluateSubtractionProtocol;

/**
 * A static class containing the protocols used in Example 2 - ExpressionEvaluation.
 * @author Lukáš Kúdela
 * @since 2012-03-21
 * @version %I% %G%
 */
public class Protocols {
    
    public static final Class EVALUATE_EXPRESSION_PROTOCOL = EvaluateExpressionProtocol.class; 
    public static final Class EVALUATE_ADDITION_PROTOCOL = EvaluateAdditionProtocol.class;
    public static final Class EVALUATE_SUBTRACTION_PROTOCOL = EvaluateSubtractionProtocol.class;
    public static final Class EVALUATE_MULTIPLICATION_PROTOCOL = EvaluateMultiplicationProtocol.class;
    public static final Class EVALUATE_DIVISION_PROTOCOL = EvaluateDivisionProtocol.class;
}
