/**
 * Read .different properties file from classpath
 */
package toolkit;

import org.apache.commons.io.filefilter.SuffixFileFilter;

import java.io.File;
import java.util.ArrayList;

/**
 * @author ASUS
 *
 */
public class SetupUtils {
    private static ArrayList<File> propertiesLocations;
    
    /**
     * Called when propertiesLocations is already null; add all correponding locations.
     */
    public static void initialize() {
        propertiesLocations= new ArrayList<File>();
        File propertiesDirectory = new File("properties");
        String[] inDirProperties = propertiesDirectory.list(new SuffixFileFilter(".properties"));
        for(String locString: inDirProperties) {
            propertiesLocations.add(new File(locString));
        }
        
    }

}
