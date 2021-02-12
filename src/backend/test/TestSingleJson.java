package backend.test;

import java.util.List;
import backend.prototype.JsonPrototype;

public class TestSingleJson extends JsonPrototype{

   private String name;
   private long aLong;
   private List<String> args;
   public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

   public void setALong(long aLong) {
        this.aLong = aLong;
    }
    public long getALong() {
        return aLong;
    }

   public void setArgs(List<String> args) {
        this.args = args;
    }
    public List<String> getArgs() {
        return args;
    }

    
    

}
