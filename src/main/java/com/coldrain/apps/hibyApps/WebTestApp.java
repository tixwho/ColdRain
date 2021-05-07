package com.coldrain.apps.hibyApps;

import com.coldrain.hiby.generic.BaseHibyTestingClass;
import com.coldrain.hiby.utils.HibyUtils;
import java.io.IOException;
import java.time.Duration;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;


public class WebTestApp extends BaseHibyTestingClass {

    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "http://192.168.1.101:4399/";
        String audioLoc =
            "E:\\lzx\\Discovery\\ColdRain\\SimpDiscography\\Album1-hires\\riya - Traveler's Tale.flac";
        String audioLoc2 =
            "E:\\lzx\\Discovery\\ColdRain\\SimpDiscography\\Album1-hires\\eufonius - きみがいた.flac";
        String[] audioLocs = {audioLoc, audioLoc2};
        String audioLoc3 = HibyUtils.packArray(audioLocs);
        logger.error("START");
        tim.timerStart();
        System.setProperty("webdriver.chrome.driver", "D:\\WebDrivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        tim.timerPeriod("Driver initialized!");
        driver.get(url);
        tim.timerPeriod("Connected to Hiby!");
        String device = driver.findElement(By.xpath("//li[@class='active']")).getText();
        System.out.println("DEVIEC NAME:"+device);
        /* folder creation */
        WebElement folderCreateBtn = driver.findElement(By.id("create-folder"));
        folderCreateBtn.click();
         //force wait method
        /*
        Thread.sleep(2000);
        WebElement folderCreateModal = driver.findElement(By.id("create-modal"));
        */
        //fluent wait method
        FluentWait<WebDriver> wait= new FluentWait<WebDriver>(driver);
        wait.withTimeout(Duration.ofSeconds(10));
        wait.pollingEvery(Duration.ofSeconds(1));
        wait.ignoring(NoSuchElementException.class);
        WebElement folderFoundModal = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath("//body[@class='modal-open']"));
              }
            });
        System.out.println(folderFoundModal.getTagName());
        System.out.println(folderFoundModal.getText());
        System.out.println("OPEN MODAL LOCATED");
        FluentWait<WebDriver> wait6= new FluentWait<WebDriver>(driver);
        wait6.withTimeout(Duration.ofSeconds(10));
        wait6.pollingEvery(Duration.ofSeconds(1));
        wait6.ignoring(NoSuchElementException.class);
        WebElement folderCreateModal = wait6.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath("//div[@class='modal fade in']"));
              }
            });
        //By.id("create-input")
        WebElement folderCreateText = folderCreateModal.findElement(By.xpath("//input[@id='create-input']"));
        System.out.println(folderCreateText.getTagName());
        System.out.println(folderCreateText.getText());
        System.out.println("LOCATED");
        folderCreateText.clear();
        folderCreateText.sendKeys("TestFolder");
        WebElement folderCreateConfirmBtn = folderCreateModal.findElement(By.xpath("//button[@id='create-confirm']"));
        folderCreateConfirmBtn.click();
        tim.timerPeriod("Folder Created!");
        System.out.println("CLICKED!");
        //Thread.sleep(2000);
        FluentWait<WebDriver> wait2= new FluentWait<WebDriver>(driver);
        wait2.withTimeout(Duration.ofSeconds(10));
        wait2.pollingEvery(Duration.ofSeconds(1));
        wait2.ignoring(NoSuchElementException.class);
        //does not catch the element because we don't need that.
        wait2.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath("//body[@class='']"));
              }
            });
        System.out.println("back!");
        WebElement thatFolderBtn =
            driver.findElement(By.xpath("//td[p='TestFolder']/../td[@class='column-icon']/button"));
        
        System.out.println(thatFolderBtn.getText());
        System.out.println(thatFolderBtn.getTagName());
        tim.timerPeriod("FolderSelected!");
        thatFolderBtn.click();
        tim.timerPeriod("FolderDeleted!");
        
        /* Path Movement */

        /* file upload */
        //Thread.sleep(2000);
        /*
        WebElement uploadText = driver.findElement(By.id("fileupload"));
        WebElement uploadButton = driver.findElement(By.id("upload-file"));
        tim.timerPeriod("Found upload text and button!");
        uploadText.sendKeys(audioLoc3);
        uploadButton.click();
        */
        /*Path navigation*/
        FluentWait<WebDriver> wait3= new FluentWait<WebDriver>(driver);
        wait3.withTimeout(Duration.ofSeconds(10));
        wait3.pollingEvery(Duration.ofSeconds(1));
        wait3.ignoring(NoSuchElementException.class);
        //does not catch the element because we don't need that.
        WebElement liElement = wait3.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath("//li[@data-path='/']"));
              }
            });
        tim.timerPeriod("LI");
        WebElement homeBtn = liElement.findElement(By.xpath("//a"));
        System.out.println("HomeBtn Located");
        tim.timerPeriod("HomeBTN-A");
        System.out.println("ACTIVE:"+driver.findElement(By.xpath("//li[@class='active']")).getText());
        homeBtn.click();
        FluentWait<WebDriver>wait4= new FluentWait<WebDriver>(driver);
        wait4.withTimeout(Duration.ofSeconds(10));
        wait4.pollingEvery(Duration.ofSeconds(1));
        wait4.ignoring(NoSuchElementException.class);
        wait4.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath("//body[@class='']"));
              }
            });
        tim.timerPeriod("BACKHOME");
        WebElement thatFolderDeleteBtn = safeXPath("//td[p='TestFolder']/../td[@class='column-delete']/button",driver);
        thatFolderDeleteBtn.click();

        tim.timerEnd("All Finished");

    }
    
    /*Copied*/
    private static FluentWait<WebDriver> getWait(WebDriver driver){
        FluentWait<WebDriver> wait =new FluentWait<WebDriver>(driver);
        wait.withTimeout(Duration.ofSeconds(10));
        wait.pollingEvery(Duration.ofSeconds(1));
        wait.ignoring(NoSuchElementException.class);
        return wait;
    }
    
    private static WebElement safeXPath(String xPathExpression,WebDriver driver) {
        FluentWait<WebDriver> wait = getWait(driver);
        WebElement elem = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath(xPathExpression));
              }
            });
        return elem;
    }


}
