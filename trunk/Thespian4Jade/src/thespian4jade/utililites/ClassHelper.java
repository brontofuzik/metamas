package thespian4jade.utililites;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A static class containing class helper methods.
 * @author Lukáš Kúdela
 * @since 2012-03-21
 * @version %I% %G%
 */
public /* static */ class ClassHelper {

    /**
     * Instantiates a clas.
     * @param <T> The type of the class
     * @param clazz the class to instantiate
     * @return an instance of the clas
     */
    public static <T> T instantiateClass(Class clazz) {
        // Get the default constructor.
        Constructor constructor = null;
        try {
            constructor = clazz.getConstructor();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        //System.out.println("----- constructor: " + constructor + " -----");

        // Instantiate the class.
        T instance = null;
        try {
            instance = (T)constructor.newInstance();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
        //System.out.println("----- instance: " + instance + " -----");

        return instance;
    }
    
    /**
     * Creates an instance of a template (class) in a generic class.
     * Note: This method is currently not used.
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
    
    /**
     * Creates an instance of a template (class) in a generic class.
     * Note: This method is currently not used.
     * @param object instance of a class that is a subclass of a generic class
     * @return new instance of T (created by calling the default constructor)
     * @throws RuntimeException if T has no accessible default constructor
     */
    @SuppressWarnings("unchecked")
    public static <T> T createTemplateInstance(Object object) {
        return createTemplateInstance(object, 0);
    } 
}

/**
 * A class testing the ClassHelper.createTemplateInstance() method.
 */
abstract class GenericClass<T> {
    
    T createTemplateInstance() {
        return ClassHelper.createTemplateInstance(this);
    }

    public static void main() {
        GenericClass<ArrayList<String>> genericInstance = new GenericClass<ArrayList<String>>() {};
        ArrayList<String> templateInstance = genericInstance.createTemplateInstance();
    }
}