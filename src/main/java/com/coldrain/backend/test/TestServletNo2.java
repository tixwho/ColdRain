package com.coldrain.backend.test;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="servletNo2", urlPatterns={"/nana/*"})
public class TestServletNo2 extends HttpServlet{

    /**
     * 
     */
    private static final long serialVersionUID = 6271974503771469620L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("NOOOO");
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request,response);
    }

}
