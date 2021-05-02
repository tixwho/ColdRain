package toolkit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnnotationUtils {

    
    

    public static List<Method> scanClzForAnnotatedMethods(Class<?> clz,
        Class<? extends Annotation> annotationClz) {
        List<Method> methodList = new ArrayList<>();
        Method[]methods = clz.getDeclaredMethods();
        if (methods != null && methods.length > 0) {
            for (Method method : methods) {
                Annotation seekAnno =  method.getAnnotation(annotationClz);
                if (seekAnno != null) {
                    //可以做权限验证
                    methodList.add(method);
                }
            }
        }
        return methodList;

    }


    // https://blog.csdn.net/u013871439/article/details/70231288 //todo
    public static List<Class<?>> scanPkgForAnnotatedClass(Package pkg,
        Class<? extends Annotation> annotationClz) {
        List<Class<?>> clzList = ClassUtils.getAllClassByPackageName(pkg);
        Iterator<Class<?>> clzIter = clzList.iterator();
        while (clzIter.hasNext()) {
            Class<?> clz = clzIter.next();
            if (!clz.isAnnotationPresent(annotationClz)) {
                clzList.remove(clz);
            }
        }
        return clzList;
    }

}
