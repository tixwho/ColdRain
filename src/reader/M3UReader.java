package reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class M3UReader {
    
    ArrayList<String> allStrInfoList = new ArrayList<String>();
    public M3UReader(String addr) throws IOException {
      //read as a file, then divide into songParts and pack into ArrayList
        InputStreamReader in = new InputStreamReader(new FileInputStream(addr),"utf-8");
        BufferedReader br=new BufferedReader(in);
        String tempData;
        while((tempData = br.readLine())!=null) {
            allStrInfoList.add(tempData);
        }
        br.close();
    }
    public ArrayList<String>getStrInfoList() {
        return allStrInfoList;
        //return Arraylist packing songParts String
    }

}
