package backend.test;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns="/five/*")
public class TestFilterNo1 implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        System.out.println("A Five filter");
        chain.doFilter(request, response); //must add or the request will stop here
        
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

}
