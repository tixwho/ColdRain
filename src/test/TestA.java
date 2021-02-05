package test;

public class TestA extends TestAA{
    
    public static int doCommon(int current) {
        return current -=4;
    }
    
    public static int doUncommon(int current) {
        return current -=10;
    }
    
    public static int doRare(int current) {
        return current -=10;
    }
    
    public static int doMythic(int current) {
        return current -=10;
    }
    
    public static void main (String[]args) {
        int hammer = 1200;//change this
        
        int commonCount = 0;
        int uncommonCount = 0;
        int rareCount = 0;
        int mythicCount = 0;
        
        do {
            hammer = doCommon(hammer);
        }while(hammer >0);
        
        
    }
    
}
