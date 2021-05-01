package backend.utils;

import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.Map;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import toolkit.AnnotationUtils;

public class WebAnnotationUtils extends AnnotationUtils {
    
    /**
     * Bind the given servlet into specific ServletContextHandler
     * There should be exactly one urlPatterns designated by WebServlet annotation
     * @param handler ServletContextHandler to be binded by servlet.
     * @param clazz Servlet with urlPattern annotated by @WebServlet annotation.
     */
    public static void addAnnotatedServlet(ServletContextHandler handler,
        Class<? extends HttpServlet> clazz) {
        if (clazz.isAnnotationPresent(WebServlet.class)) {
            WebServlet servletInfo = clazz.getAnnotation(WebServlet.class);
            if (servletInfo.urlPatterns().length == 0) {
                // raise an error
            } else if (servletInfo.urlPatterns().length > 1) {
                // raise an error
            } else { // length equal to 1
                handler.addServlet(clazz, servletInfo.urlPatterns()[0]);
            }
        }
    }

    /**
     * Bind the given filter into specific ServletContextHandler
     * There should be exactly one urlPatterns designated by WebFilter annotation
     * @param handler ServletContextHandler to be binded by filter.
     * @param clazz Filter with urlPattern annotated by @WebFilter annotation.
     */
    public static void addAnnotatedFilter(ServletContextHandler handler,
        Class<? extends Filter> clazz) {
        if (clazz.isAnnotationPresent(WebFilter.class)) {
            WebFilter filterInfo = clazz.getAnnotation(WebFilter.class);
            if (filterInfo.urlPatterns().length == 0) {
                // raise an error
            } else if (filterInfo.urlPatterns().length > 1) {
                // raise an error
            } else { // length equal to 1
                handler.addFilter(clazz, filterInfo.urlPatterns()[0],
                    EnumSet.of(DispatcherType.REQUEST));
            }
        }
    }
    
    //todo
    //@JsonClass annotation on methods
    //@(new annotation) annotation on controllers.
    public static Map<String,Method> webPathInit(){
        return null;
    }

}
