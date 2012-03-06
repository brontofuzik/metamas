package thespian.utilities;

/**
 * @author Lukáš Kúdela
 * @since 2012-01-10
 * @version %I% %G%
 */
public class Assert {

    public static void isNotNull(Object argumentValue, String argumentName) {
        if (argumentValue == null) {
            throw new IllegalArgumentException(
                String.format("Argument '%1$' cannot be null.", argumentName));
        }
    }

    public static void isNotEmpty(String argumentValue, String argumentName) {
        if (argumentValue.isEmpty()) {
            throw new IllegalArgumentException(
                String.format("Argument '%1$' cannot be empty.", argumentName));
        }
    }
}
