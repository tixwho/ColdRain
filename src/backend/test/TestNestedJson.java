package backend.test;

import java.util.Date;

public class TestNestedJson {
    
    int order;
    Date date;
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
    TestSingleJson thatJson;
    

}
