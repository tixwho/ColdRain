/**
  * Copyright 2021 bejson.com 
  */
package com.coldrain.ncm.jsonSupp;
import java.util.List;

/**
 * Auto-generated: 2021-01-24 6:43:28
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Artists {

    private int id;
    private String name;
    private List<String> tns;
    private List<String> alias;
    
    //for DJ tracks only
    private long userId;
    
    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setTns(List<String> tns) {
         this.tns = tns;
     }
     public List<String> getTns() {
         return tns;
     }

    public void setAlias(List<String> alias) {
         this.alias = alias;
     }
     public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public List<String> getAlias() {
         return alias;
     }

}