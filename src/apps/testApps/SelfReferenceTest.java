package apps.testApps;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SelfReferenceTest {
    
    public String aStr = "aaaa";
    public SelfReferenceTest myself;
    public Set<SelfReferenceTest> childrens = new HashSet<SelfReferenceTest>();

    public static void main(String[] args) {
        SelfReferenceTest me = new SelfReferenceTest();
        me.myself=me;
        me.childrens.add(me);
        
        Iterator<SelfReferenceTest> it =me.childrens.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().aStr);
        }

    }

}
