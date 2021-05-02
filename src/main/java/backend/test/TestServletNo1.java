package backend.test;

import backend.annotation.JsonClass;
import backend.jsonMapping.JsonPrototype;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@WebServlet(name="servletNo1", urlPatterns={"/one"})
@JsonClass(jsonIn=TestSingleJson.class)
public class TestServletNo1 extends HttpServlet{

    /**
     * 
     */
    private static final long serialVersionUID = 6271974503771469620L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("WAYYYYY");
        try {
            //request.getRequestDispatcher("/nana").forward(request, response);
            request.getRequestDispatcher("nana").forward(request, response);
            //start with "/" means relative, start without "/" means absolute
        } catch (ServletException | IOException e) {
            System.err.println("forward failed!");
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        JsonPrototype jsonInst = new TestSingleJson();
        Gson gson = new Gson();
        try {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        String temp;
        while ((temp = br.readLine()) != null) { 
          sb.append(temp);
        }
        br.close();
        String params = sb.toString();
        System.out.println(params);
        jsonInst = gson.fromJson(params,this.getClass().getAnnotation(JsonClass.class).jsonIn());
        System.out.println(jsonInst);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
