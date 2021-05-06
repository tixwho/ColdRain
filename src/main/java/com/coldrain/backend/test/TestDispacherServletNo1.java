package com.coldrain.backend.test;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="dispacher1", urlPatterns="/dispatch/*")
public abstract class TestDispacherServletNo1 extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 607769386359229922L;
    //private Map<String, GetDispatcher> getMappings = new HashMap<>();
    //private Map<String, PostDispatcher> postMappings = new HashMap<>();
    
    //servlet with JsonClass annotation
    private final Map<String,HttpServlet> postMap = new HashMap<String,HttpServlet>();
    
    @Override
    public void init() {
        scanControllers();
    }
    
    //designate controllers related
    //mapping should be assigned to different services
    public abstract void scanControllers();
    
    

}
