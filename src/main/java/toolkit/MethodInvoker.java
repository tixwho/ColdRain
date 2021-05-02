package toolkit;

import java.io.File;
import java.util.ArrayList;

/*
 * Usage: Judge the input and determine if the input address is a single file or folder If it is a
 * single file, there will be only one string(src) in the arraylist. If it is a folder, all files
 * with corresponding suffix will be in the arraylist. It will only check one level files within
 * folder (not recursive.)
 * 
 * @Param checkAddr: String (src) needed to be checked.
 * 
 * @Param usingMethod: method to be used for a single file. Must be a static method with a single
 * input(src)
 * 
 * @Param suffix: correct suffix for input file. (might allow multiple suffix in the future.)
 * 
 * @Return: ArrayList of string.
 */
public class MethodInvoker {

    public static ArrayList<String> singlizeInput(String checkingAddr, String suffix) {
        ArrayList<String> rtrSrcList = new ArrayList<String>();
        File checkAddr = new File(checkingAddr);
        if (checkAddr.isDirectory()) {
            String[] s = checkAddr.list();
            for (int i = 0; i < s.length; i++) {
                File f = new File(checkingAddr, s[i]);
                if (f.isDirectory()) {
                    continue; // only watch for a single level within folder
                } else {
                    String fileName = f.getAbsolutePath();
                    String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
                    if (suffix.compareTo(fileSuffix) == 0) {
                        rtrSrcList.add(fileName);
                    } else {
                        System.out.println(fileName + " is not a " + suffix + " File!");
                    }

                }
            }
        } else {
            rtrSrcList.add(checkingAddr);
        }
        return rtrSrcList;
    }

    public static ArrayList<String> singlizeInput(String checkingAddr, String[] suffixlist) {
        ArrayList<String> rtrSrcList = new ArrayList<String>();
        File checkAddr = new File(checkingAddr);
        if (checkAddr.isDirectory()) {
            String[] s = checkAddr.list();
            for (int i = 0; i < s.length; i++) {
                File f = new File(checkingAddr, s[i]);
                if (f.isDirectory()) {
                    continue; // only watch for a single level within folder
                } else {
                    String fileName = f.getAbsolutePath();
                    String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
                    if (MisUtils.checkSuffix(fileSuffix, suffixlist)) {
                        rtrSrcList.add(fileName);
                    } else {
                        System.out.println(fileName + " is not in the allowed suffix list!");
                    }

                }
            }
        } else {
            rtrSrcList.add(checkingAddr);
        }
        return rtrSrcList;
    }

    public static ArrayList<String> singlizeInputR(String checkingAddr, String[] suffixlist,
        ArrayList<String> storeList) {
        File checkAddr = new File(checkingAddr);
        if (checkAddr.isDirectory()) {
            String[] s = checkAddr.list();
            for (String singlef: s){
                singlizeInputR(new File(checkingAddr,singlef).toString(),suffixlist,storeList);
            }
        }else {
            if(MisUtils.checkSuffix(checkingAddr, suffixlist)) {
                storeList.add(checkingAddr);
            }else {
                LogMaker.logs(checkingAddr + " is not an allowed audio file!");
            }
        }
        return storeList;
    }

}
