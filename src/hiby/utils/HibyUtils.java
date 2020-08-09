package hiby.utils;

public class HibyUtils {
    
    public static String packArray (String[] toPackArray) {
        int arrSize = toPackArray.length;
        String rtrString="";
        int count = 0;
        for (String str:toPackArray) {
            count+=1;
            rtrString=rtrString.concat(str);
            if(count!=arrSize) {
                rtrString=rtrString.concat("\n");
            }
        }
        System.out.println(rtrString);
        return rtrString;
    }

}
