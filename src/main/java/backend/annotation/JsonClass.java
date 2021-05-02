/**
 * 
 */
package backend.annotation;

import backend.jsonMapping.JsonIn;
import backend.jsonMapping.JsonOut;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
/**
 * @author tixwho
 *
 */
public @interface JsonClass {
    
    Class<? extends JsonIn> jsonIn() default JsonIn.class;
    Class<? extends JsonOut> jsonOut() default JsonOut.class;

}
