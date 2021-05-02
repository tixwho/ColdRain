package scriptrunner;

import com.google.common.collect.ObjectArrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;

public class ScriptRunner {
    private static final Logger logger = LoggerFactory.getLogger(ScriptRunner.class);
    
    public static int run(File scriptFile,String[] args) {
        
        try {
            Runtime rt = Runtime.getRuntime();
            
            String[] executeArray = {"cmd","/c",scriptFile.getName()};
            logger.debug("Running script for:"+Arrays.toString(executeArray));
            logger.debug("Arguments:"+Arrays.toString(args));
            executeArray = addStrArray(executeArray,args);
            String[] envp = {"path="+scriptFile.getParent()};
            File dir = scriptFile.getParentFile();
            Process proc = rt.exec(executeArray, envp, dir);
            InputStream stderr = proc.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            logger.info("<-- Script Outputs -->");
            while ((line = br.readLine()) != null)
                logger.info(line);
            int exitVal = proc.waitFor();
            logger.info("<-- Script successfully executed! exitVal:"+exitVal+" -->");
            return exitVal;
        }catch(IOException ioe) {
            logger.error("Error running script or printing output for "+scriptFile);
            return -1;
        } catch (InterruptedException ie) {
            logger.error("Failed catching exit value after executing script!");
            logger.error("File:"+scriptFile.getAbsolutePath()+";arguments:"+Arrays.toString(args));
            return -1;
        }
        
        
    }
    
    private static String[] addStrArray(String[] origArray, String[] toAddArray) {
        String[] rtrArray = ObjectArrays.concat(origArray, toAddArray, String.class);
        return rtrArray;
    }

}
