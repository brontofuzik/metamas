package example2.protocols;

import example2.protocols.evaluatebinaryoperation.EvaluateBinaryOperationProtocol;
import example2.protocols.evaluateexpression.EvaluateExpressionProtocol;

/**
 * A static class containing the application-specific (domain logic) protocols
 * used in Example 2 - Expression Evaluation.
 * @author Lukáš Kúdela
 * @since 2012-03-21
 * @version %I% %G%
 */
public class Protocols {
    
    public static final Class EVALUATE_EXPRESSION_PROTOCOL = EvaluateExpressionProtocol.class;
    public static final Class EVALUATE_BINARY_OPERATION_PROTOCOL = EvaluateBinaryOperationProtocol.class;
}
