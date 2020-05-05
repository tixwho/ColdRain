package old.readers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import test.TestAA;

public class M3U8Reader2 extends TestAA {

    ArrayList<String> allStrInfoList = new ArrayList<String>();

    public M3U8Reader2(String addr) throws IOException {
        // read as a file, then divide into songParts and pack into ArrayList
        int lineDivideNum = 3;
        InputStreamReader in = new InputStreamReader(new FileInputStream(addr), "utf-8");
        BufferedReader br = new BufferedReader(in);
        String str = "";
        String tempData;
        int lineCount = 0;
        while ((tempData = br.readLine()) != null) {
            lineCount = lineCount + 1;
            if (lineCount % lineDivideNum != 0) {
                str = str + tempData + "/r/n";
                System.out.println("TEST in M3U8Reader Str: " + str);
            } else {
                System.out.println("TEST in M3U8Reader toAddStr: " + str);
                allStrInfoList.add(str);
                str = "";
            }
        }
        br.close();



    }

    public ArrayList<String> getStrInfoList() {
        return allStrInfoList;
        // return Arraylist packing songParts String
    }

}
