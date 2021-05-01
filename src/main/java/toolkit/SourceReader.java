package toolkit;

import java.io.*;

public class SourceReader {
    
    //最古老版本的文件读取，啥都有但是意义不明
    
    private DataInputStream in;
    private DataOutputStream out;
    private BufferedReader d;

    // Constructor
    public SourceReader(String inputName, String outputName)
        throws FileNotFoundException {
        this.in = new DataInputStream(new FileInputStream(inputName));
        this.out = new DataOutputStream(new FileOutputStream(outputName));
        this.d = new BufferedReader(new InputStreamReader(in));
    }
    
    //methods
    public BufferedReader getReader() {
        return d;
    }
    
    public DataOutputStream getOutStream() {
        return out;
    }

}
