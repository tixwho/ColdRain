package backend.test;

import java.util.Date;
import backend.prototype.JsonPrototype;

public class TestNestedJson extends JsonPrototype{
    
    int order;
    Date date;
    TestSingleJson thatJson;
    String miscellaneous;
    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public TestSingleJson getThatJson() {
        return thatJson;
    }
    public void setThatJson(TestSingleJson thatJson) {
        this.thatJson = thatJson;
    }
    
    public String getMiscellaneous() {
        return miscellaneous;
    }
    public void setMiscellaneous(String miscellaneous) {
        this.miscellaneous = miscellaneous;
    }
    
    
    
    

}
