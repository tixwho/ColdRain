package test;

import exception.RuntimeE.EmptyListException;

public class LogClassTest {
    
    public static void main(String[] args) {
        new LogbackTest().doSomethingElse();
        LogbackTest.doStatic();
        LogbackTest.main(null);
        LogbackTest2.main(null);
        throw new EmptyListException("hey");
        
    }

}
