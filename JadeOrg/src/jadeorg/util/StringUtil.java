package jadeorg.util;

import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author hp
 */
public class StringUtil {
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
        return jadeorg.util.StringUtil.join(Arrays.asList(objects), delimiter);
    }
}
