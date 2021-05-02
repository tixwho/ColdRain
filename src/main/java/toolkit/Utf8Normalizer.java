package toolkit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map.Entry;

public class Utf8Normalizer {

    public static Logger logger = LoggerFactory.getLogger(Utf8Normalizer.class);
    public HashMap<String, String> replaceMap = new HashMap<String, String>();

    public Utf8Normalizer() {
        replaceMap.put("？", "?");
        replaceMap.put("／", "-");
        replaceMap.put("'",""); // conflict with xPath expression
        replaceMap.put("\\s{2,}", " "); //folder creation will remove duplicate blank
    }


    public String normalize(String toNormalizeStr) {
        String normalizedStr = toNormalizeStr;
        // apply hiby specific normalizing map
        for (Entry<String, String> entry : replaceMap.entrySet()) {
            if (normalizedStr.contains(entry.getKey())) {
                
                logger.trace("got " + entry.getKey() + " replaced by " + entry.getValue());
            }
            normalizedStr = normalizedStr.replaceAll(entry.getKey(), entry.getValue()); //apply regex
        }
        // use java embedded normalizer
        normalizedStr = Normalizer.normalize(normalizedStr, Normalizer.Form.NFKD);
        if (!toNormalizeStr.equals(normalizedStr)) {
            logger.debug("Normalize Action Done!");
            logger.trace("Before: " + toNormalizeStr);
            logger.trace("After: " + normalizedStr);
        }
        return normalizedStr;
    }

    public String normalize(String toNormalizeStr, String methodName) {
        

        String normalizedStr = normalize(toNormalizeStr);
        if (!toNormalizeStr.equals(normalizedStr)) {
            logger.debug("Normalized in " + methodName + "!");            
        }
        return normalizedStr;
    }
    

}
