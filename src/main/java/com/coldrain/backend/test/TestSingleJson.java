package com.coldrain.backend.test;

import com.coldrain.backend.jsonMapping.JsonIn;

import java.util.List;

public class TestSingleJson extends JsonIn{

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
