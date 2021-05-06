package com.coldrain.test;

public class TestCounter {
    private int count = 1;
    public void addOne() {
        this.count++;
    }
    public int getCount() {
        return this.count;
    }
    
    public void fuckyou() {
        int count = 2;
        System.out.println(count);
        System.out.println(this.count);
    }
    
    public void fuck2() {
        System.out.println(count);
    }
    
    public static void main (String[]args) {
        TestCounter a = new TestCounter();
        a.fuckyou();
        a.fuck2();
    }

}
