package com.coldrain.apps.hibyApps;

import com.coldrain.exception.WebException;
import com.coldrain.hiby.generic.BaseHibyTestingClass;
import com.coldrain.hiby.utils.HibyNavigator;

public class WebTestApp2 extends BaseHibyTestingClass {

    public static void main(String[] args) throws WebException {
        logger.info("wow!");

        String url = "http://192.168.1.100:4399/";
        String folderName = "E:\\lzx\\etc\\OST\\Discography";
        String chromeDriverPath = "D:\\WebDrivers\\chromedriver.exe";
        String[] refPaths = {"E:\\lzx\\etc\\OST\\Discography", "F:\\CloudMusic",
            "E:\\lzx\\Discovery\\ColdRain\\SimpDiscography","E:\\lzx\\etc\\OST\\Discography2"};
        HibyNavigator hiby = new HibyNavigator(url);
        hiby.setAllLevel("trace");
        hiby.initializeChromeDriver(chromeDriverPath);
        hiby.initializeRefPaths(refPaths);
        hiby.testFunc("E:\\lzx\\etc\\OST\\Discography\\割れたリンゴ _雪に咲く花");
        //com.coldrain.hiby.testFunc2("E:\\lzx\\etc\\OST\\Discography2\\ふ・れ・ん・ど・し・た・い");
        //com.coldrain.hiby.testFunc2("E:\\lzx\\etc\\OST\\Discography2\\\\モノノケ・イン・ザ・フィクション");
        //com.coldrain.hiby.testFunc2(folderName);
    }

}
