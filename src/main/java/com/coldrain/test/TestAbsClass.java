package com.coldrain.test;

import com.coldrain.toolkit.LogMaker;

import java.util.ArrayList;

public class TestAbsClass {
    
    public abstract class motherAbsClass{
        int iint = 1;
        public void speak() {
            LogMaker.logs("weebs out!");
        }
        public abstract void declareInt();
    }
    
    public class childClassOne extends motherAbsClass{
        @Override
        public  void speak() {
            LogMaker.logs("weebs in!");
        }

        @Override
        public void declareInt() {
            iint = 2;
            LogMaker.logs("intnow: "+iint);
        }
    }
    
    public class childClassTwo extends motherAbsClass{

        @Override
        public void declareInt() {
            LogMaker.logs("intnow: " +iint);
            
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        TestAbsClass me = new TestAbsClass();
        ArrayList<motherAbsClass> arrList = new ArrayList<motherAbsClass>();
        arrList.add(me.new childClassOne());
        arrList.add(me.new childClassTwo());
        for (motherAbsClass oneChild: arrList) {
            oneChild.speak();
            oneChild.declareInt();
        }

    }

}


