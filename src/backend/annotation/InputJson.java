/**
 * 
 */
package backend.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
@Documented
/**
 * @author tixwho
 *
 */
public @interface InputJson {
    
    Class<?> jsonClass() default Object.class;
    //will be changed later to InputJson.class extend will be updated then.

}
