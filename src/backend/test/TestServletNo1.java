package backend.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import backend.annotation.InputJson;
import backend.prototype.JsonPrototype;

@WebServlet(name="servletNo1", urlPatterns={"/one"})
@InputJson(jsonClass=TestSingleJson.class)
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
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) { 
          sb.append(temp);
        }
        br.close();
        String params = sb.toString();
        System.out.println(params);
        jsonInst = (JsonPrototype) gson.fromJson(params,this.getClass().getAnnotation(InputJson.class).jsonClass());
        System.out.println(jsonInst);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
