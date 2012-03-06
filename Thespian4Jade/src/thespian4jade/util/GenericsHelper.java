package thespian4jade.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * The generics helper.
 * @author Lukáš Kúdela
 * @since 2012-01-24
 * @version %I% %G%
 */
public class GenericsHelper {
    
    /**
     * @param object instance of a class that is a subclass of a generic class
     * @param index index of the generic type that should be instantiated
     * @return new instance of T (created by calling the default constructor)
     * @throws RuntimeException if T has no accessible default constructor
     */
    @SuppressWarnings("unchecked")
    public static <T> T createTemplateInstance(Object object, int index) {
        ParameterizedType superClass = (ParameterizedType)object.getClass()
            .getGenericSuperclass();
        Type type = superClass.getActualTypeArguments()[index];
        
        Class<T> clazz;
        if (type instanceof ParameterizedType) {
            // The actual type argument is itself parameterized.
            ParameterizedType parametrizedType = (ParameterizedType)type;
            clazz = (Class<T>)parametrizedType.getRawType();
        }
        else {
            // The actual type argument is not parameterized.
            clazz = (Class<T>)type;
        }
        
        try {
            return clazz.newInstance();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T createTemplateInstance(Object object) {
        return createTemplateInstance(object, 0);
    }
}

/**
 * The generics class.
 * @author Lukáš Kúdela
 * @since 2011-01-24
 * @version %I% %G%
 */
abstract class GenericClass<T> {
    
    T createTemplateInstance() {
        return GenericsHelper.createTemplateInstance(this);
    }

    public static void test() {
        GenericClass<ArrayList<String>> genericInstance = new GenericClass<ArrayList<String>>() {};
        ArrayList<String> templateInstance = genericInstance.createTemplateInstance();
    }
}
