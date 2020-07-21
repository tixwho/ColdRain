package apps.webApps;

import exception.WebException;
import web.generic.BaseWebTestingClass;
import web.utils.HibyNavigator;

public class WebTestApp2 extends BaseWebTestingClass {

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
        //hiby.testFunc2("E:\\lzx\\etc\\OST\\Discography2\\ふ・れ・ん・ど・し・た・い");
        //hiby.testFunc2("E:\\lzx\\etc\\OST\\Discography2\\\\モノノケ・イン・ザ・フィクション");
        //hiby.testFunc2(folderName);
    }

}
