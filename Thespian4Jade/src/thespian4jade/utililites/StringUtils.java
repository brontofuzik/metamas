package thespian4jade.utililites;

import java.util.Arrays;
import java.util.Iterator;

/**
 * A static class containing string utility methods.
 * @author Lukáš Kúdela
 * @since 2012-03-18
 * @version %I% %G%
 */
public /* static */ class StringUtils {
    
    /**
     * Concatenates a sequence of objects (their string representations).
     * @param objects the sequence of objects
     * @param delimiter the delimiter separating the objects
     * @return a concatenation of the objects
     */
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
    
    /**
     * Concatenates an array of objects (their string representations).
     * @param objects an array of objects
     * @param delimiter the delimiter separating the objects
     * @return a concatenation of the objects
     */
    public static String join(Object[] objects, String delimiter) {
        return thespian4jade.utililites.StringUtils.join(Arrays.asList(objects), delimiter);
    }
}
