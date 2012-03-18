package thespian4jade.util;

import java.util.Arrays;
import java.util.Iterator;

/**
 * String utilities.
 * A static class containing string utility methods.
 * @author Lukáš Kúdela
 * @since 2012-03-18
 * @version %I% %G%
 */
public /* static */ class StringUtils {
    public static String join(Iterable objects, String delimiter) {
        Iterator iterator = objects.iterator();       
        if (!iterator.hasNext()) {
            return "";
        }
        
        StringBuilder builder = new StringBuilder(iterator.next().toString());
        while (iterator.hasNext()) {
            builder.append(delimiter).append(iterator.next());
        }
        return builder.toString();
    }
    
    public static String join(Object[] objects, String delimiter) {
        return thespian4jade.util.StringUtils.join(Arrays.asList(objects), delimiter);
    }
}
