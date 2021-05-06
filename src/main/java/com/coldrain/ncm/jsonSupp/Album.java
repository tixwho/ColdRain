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
//note: for DJ tracks, only name and id will present.
public class Album {

    private long id;
    private String name;
    private String picId;
    private String picUrl;
    private List<String> alias;
    private List<String> transNames;
    public void setId(long id) {
         this.id = id;
     }
     public long getId() {
         return id;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setPicId(String picId) {
         this.picId = picId;
     }
     public String getPicId() {
         return picId;
     }

    public void setPicUrl(String picUrl) {
         this.picUrl = picUrl;
     }
     public String getPicUrl() {
         return picUrl;
     }

    public void setAlias(List<String> alias) {
         this.alias = alias;
     }
     public List<String> getAlias() {
         return alias;
     }

    public void setTransNames(List<String> transNames) {
         this.transNames = transNames;
     }
     public List<String> getTransNames() {
         return transNames;
     }

}