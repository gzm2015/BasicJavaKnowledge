package generic;

import org.junit.Test;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * 泛型和反射
 *
 * @author LiuMengKe
 * @create 2019-01-08-8:48
 */
public class GenericAndReflect {

    @Test
    public void genericandreflecttest() {
       
        Class clazz = Pair.class;
        printClass(clazz);
        for (Method m : clazz.getDeclaredMethods()) {
            printMethod(m);
        }
    }

    private void printMethod(Method m) {
        String name = m.getName();
        //打印方法修饰符
        System.out.print(Modifier.toString(m.getModifiers()));
        System.out.print(" ");
        //若是泛型方法获得方法上泛型的类型变量
        printTypes(m.getTypeParameters(), "<", ",", ">", true);

        //获得版本被声明的泛型返回类型
        printType(m.getGenericReturnType(), false);
        System.out.print(" ");
        System.out.print(name);
        System.out.print(" (");

        printTypes(m.getGenericParameterTypes(), "<", ",", ">", false);
        System.out.println(" )");
    }

    private void printClass(Class clazz) {

        System.out.print(clazz);
        //clazz.getTypeParameters() 获取泛型的类型变量
        printTypes(clazz.getTypeParameters(), "<", ",", ">", true);
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type sc = clazz.getGenericSuperclass();
        if(sc!=null){
            System.out.print(" extends ");
            printType(sc,false);
        }
        //返回表示某些接口的 Type，这些接口由此对象所表示的类或接口直接实现。
        printTypes(clazz.getGenericInterfaces()," implements ",", ","",false);
        System.out.println();

    }

    private void printTypes(Type[] typeParameters, String pre, String sep, String suf, boolean isDefinition) {
        if (" extends ".equals(pre) && Arrays.equals(typeParameters, new Type[]{Object.class})) {

        }
        if (typeParameters.length > 0) {
            System.out.print(pre);
        }
        for (int i = 0; i < typeParameters.length; i++) {
            if (i > 0) System.out.println(sep);
            printType(typeParameters[i], isDefinition);
        }
        if(typeParameters.length>0){
            System.out.print(suf);
        }
    }

    private void printType(Type type, boolean isDefinition) {

        if (type instanceof Class) {
            Class<?> t = (Class<?>) type;
            System.out.print(t.getName());
        } else if (type instanceof TypeVariable) {
            TypeVariable<?> t = (TypeVariable<?>) type;
            System.out.print(t.getName());
            if (isDefinition)
                printTypes(t.getBounds(), " extends ", " & ", "", false);
        } else if (type instanceof WildcardType) {
            WildcardType t = (WildcardType) type;
            System.out.println("?");
            printTypes(t.getUpperBounds(), " extends ", " & ", "", false);
            printTypes(t.getLowerBounds(), " extends ", " & ", "", false);
        } else if (type instanceof ParameterizedType) {
            ParameterizedType t = (ParameterizedType) type;
            Type owner = t.getOwnerType();
            if (owner != null) {
                printType(owner, false);
                System.out.println(".");
                printType(t.getRawType(), false);
                printTypes(t.getActualTypeArguments(), "<", ",", ">", false);
            }
        } else if (type instanceof GenericArrayType) {
            GenericArrayType t = (GenericArrayType) type;
            System.out.print(" ");
            printType(t.getGenericComponentType(), isDefinition);
            System.out.print("[]");
        }

    }

}
