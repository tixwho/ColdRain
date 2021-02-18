package backend.test;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import backend.utils.WebAnnotationUtils;
import toolkit.AnnotationUtils;

public class TestServerNo1 {

    public static void main(String[] args) throws Exception {

        Server server = new Server();
        // possibly use multiple connector to handle http/https connection and authentication
        ServerConnector connector = new ServerConnector(server);
        connector.setName("coldrain");
        connector.setPort(5438);
        // registor connector (allowed port) to the server
        server.addConnector(connector);

        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)


        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setVirtualHosts(new String[] {"127.0.0.2"});
        handler.setContextPath("/coldrain");


        // we are not using context, just normal handler here is enough

        // requestDispacher needs a context. Don't directly use ServletHandler
        /*
         * ServletHandler handler = new ServletHandler(); server.setHandler(handler);
         */

        // no need to use servletHolder if no specific initialization parameter needed.

        WebAnnotationUtils.addAnnotatedServlet(handler, TestServletNo2.class);
        
        WebAnnotationUtils.addAnnotatedServlet(handler, TestServletNo1.class);
        
        //filters belong to handler level.
        WebAnnotationUtils.addAnnotatedFilter(handler,TestFilterNo1.class); 
        
        HandlerList theList = new HandlerList();
        theList.setHandlers(new Handler[] {handler, new DefaultHandler()});
        server.setHandler(theList);
        server.start();
        server.join();

    }

    
    
    

}
