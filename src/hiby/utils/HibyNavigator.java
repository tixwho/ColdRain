package hiby.utils;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import exception.ErrorCodes;
import exception.WebException;
import hiby.generic.BaseHibyLoggingClass;
import toolkit.Utf8Normalizer;

public class HibyNavigator extends BaseHibyLoggingClass {
    private String url;
    private String device_name;
    private WebDriver driver;
    private String[] refPaths;
    private Utf8Normalizer normalizer = new Utf8Normalizer();

    /* Constructor */
    public HibyNavigator() {

    }

    public HibyNavigator(String url) {
        this();
        setUrl(url);
    }

    /* Functional */

    public void testFunc(String in) throws WebException {
        logger.info("isHome:" + isHome());
        /*
         * logger.info("quickSwitchStatus:" + trialQuickSwitch("DDDD")); if (!isHome()) {
         * switchHome(); }
         * 
         * guaranteeOrCreateFolder(in); rawEnterFolder(in);
         */
        String trialFilePath = "E:\\lzx\\etc\\OST\\Discography\\THE BEST “Blue”";
        // rawUploadFile(trialFilePath);
        // rawUploadFile("E:\\lzx\\Discovery\\ColdRain\\SimpDiscography\\Album1-hires\\eufonius -
        // きみがいた.flac");
        /*
         * String testStr = "zxlin"; guaranteeOrCreateFolder(testStr); rawEnterFolder(testStr);
         * logger.info("isHome:" + isHome()); //
         * rawUploadFile("E:\\lzx\\Discovery\\ColdRain\\SimpDiscography\\Album1-hires\\riya - //
         * Traveler's Tale.flac"); emphasize(); String laterPath = getCurrentPath(); emphasize();
         * 
         * trialQuickSwitch("/Discography/"); System.out.println(getCurrentPath());
         * trialQuickSwitch("/DDiscography/"); trialQuickSwitch(laterPath);
         * trialQuickSwitch("/Discography/-Orbis Terrarvm-/"); System.out.println(getCurrentPath());
         * switchHome(); finCreateFolder("/Wee/Weeaboo/"); // note: add the last / when switching to
         * a folder
         */


        emphasize();
        handleUpload(in);
        logger.info("COMPLETE!");


    }

    public void testFunc2(String testInput) throws WebException {
        emphasize();
        handleUpload4(testInput);
        logger.info("COMPLETE!");
    }

    public void initializeChromeDriver(String chromeDriverPath) {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        this.driver = new ChromeDriver();
        driver.get(url);
        this.device_name = driver.findElement(By.xpath("//li[@class='active']")).getText();
        logger.info("HibyNavigator initialized with ChromeDriver! Device:" + device_name);
    }

    /**
     * Split folderPath into several parts, try to create folder layer by layer, finally home()
     * 
     * @param folderPath
     * @throws WebException
     */
    public void finCreateFolder(String folderPath) throws WebException {
        String[] preArray = folderPath.split("/");
        logger.debug("InitialArray: " + Arrays.toString(preArray));
        ArrayList<String> existParts = new ArrayList<String>();
        ArrayList<String> toCreateParts = new ArrayList<String>();
        for (String part : preArray) {
            if (!part.isEmpty()) {// filter out empty path
                existParts.add(part);
            }
        }
        divCreateFolder(existParts, toCreateParts);
    }

    public void divCreateFolder(ArrayList<String> existParts, ArrayList<String> toCreateParts)
        throws WebException {
        String trialPath = concatPath(existParts);
        logger.debug("Exists:" + Arrays.toString(existParts.toArray()));
        logger.debug("ToCreate:" + Arrays.toString(toCreateParts.toArray()));
        if (trialQuickSwitch(trialPath)) {
            // success!start creating subsequent folders
            logger.debug("Found existed path " + trialPath + "! Start cascade folder creation");
            cascadeRawCreateFolder(toCreateParts);
        } else {
            // failed, then remove the last existingPart, add to ToCreate, and try once more.
            String lastPart = existParts.get(existParts.size() - 1);
            toCreateParts.add(0, lastPart);
            existParts.remove(lastPart);
            logger.debug(
                "Does not found existed path" + trialPath + ". Remove last and try once more.");
            divCreateFolder(existParts, toCreateParts);// try another time with modified AL.
        }

    }

    public void cascadeRawCreateFolder(ArrayList<String> parts) {
        for (String part : parts) {
            guaranteeOrCreateFolder(part);
            rawEnterFolder(part);
        }
    }

    private boolean isHome() {
        // check if active part is consistent with device name;
        // @if not, return false
        // @if true, check if can fetch any <a>
        // @@ if can, return false, else return true.
        // part 1 check active
        String activeStr = safeXPath("//li[@class='active']").getText();
        if (activeStr.equals(device_name)) {
            try {
                // try fetch an a (in home, no a will be found)
                driver.findElement(By.xpath("//a"));
            } catch (NoSuchElementException nsee) {
                return true;
            }
            // appear when there exist a folder with the same name of device
            return false;
        } else {
            // active does not match device name, direcetly return false.
            return false;
        }
    }

    // wait, check warnings and dismiss (if there are any.)
    private boolean checkWarnings() throws InterruptedException {
        // explicit wait for 500ms
        Thread.sleep(500);
        try {
            WebElement alertCloseBtn =
                driver.findElement(By.xpath("//button[@class='close'][@data-dismiss='alert']"));
            logger.debug("alert detected!");
            alertCloseBtn.click();
            return true;
        } catch (NoSuchElementException nsee) {
            return false;
        }
        // copied
    }

    /* Supportive */

    private void switchHome() {
        driver.navigate().refresh();
        guaranteeActivePath(device_name);
    }


    public void handleUpload(String winRawPath) throws WebException {
        // check if it's a folder or file
        // if folder,
        // if a file
        File toCheckFile = new File(winRawPath);
        if (toCheckFile.isDirectory()) {
            ArrayList<String> allFilePath = new ArrayList<String>();
            colleFile(toCheckFile, allFilePath);
            emphasize();
            System.out.println(Arrays.toString(allFilePath.toArray()));
            for (String fString : allFilePath) {
                fullCreateSingleFileDir(fString);
            }
            for (String fString : allFilePath) {
                fullUploadSingleFileDir(fString);
            }
        } else {
            fullCreateSingleFileDir(winRawPath);
            fullUploadSingleFileDir(winRawPath);
        }
    }

    // todelete
    public void handleUpload2(String winRawPath) throws WebException {
        // check if it's a folder or file
        // if folder,
        // if a file
        File toCheckFile = new File(winRawPath);
        if (toCheckFile.isDirectory()) {
            ArrayList<String> allFilePath = new ArrayList<String>();
            colleFile(toCheckFile, allFilePath);
            emphasize();
            System.out.println(Arrays.toString(allFilePath.toArray()));
            for (String fString : allFilePath) {
                fullCreateSingleFileDir(fString);
            }
        } else {
            fullCreateSingleFileDir(winRawPath);
        }
    }

    // enhanced folder creation
    public void handleUpload3(String winRawPath) throws WebException {
        // check if it's a folder or file
        // if folder,
        // if a file
        File toCheckFile = new File(winRawPath);
        if (toCheckFile.isDirectory()) {
            ArrayList<String> allFilePath = new ArrayList<String>();
            colleFile(toCheckFile, allFilePath);
            emphasize();
            System.out.println(Arrays.toString(allFilePath.toArray()));
            Set<String> toCreateFolderSet = new HashSet<String>();
            for (String fString : allFilePath) {
                File af = new File(fString);
                toCreateFolderSet.add(af.getParentFile().getAbsolutePath());
            }
            revFullCreateFolder(toCreateFolderSet);
        } else {
            fullCreateSingleFileDir(winRawPath);
        }
    }
    
 // enhanced folder creation
    public void handleUpload4(String winRawPath) throws WebException {
        // check if it's a folder or file
        // if folder,
        // if a file
        File toCheckFile = new File(winRawPath);
        if (toCheckFile.isDirectory()) {
            ArrayList<String> allFilePath = new ArrayList<String>();
            colleFile(toCheckFile, allFilePath);
            emphasize();
            System.out.println(Arrays.toString(allFilePath.toArray()));
            Set<String> toCreateFolderSet = new HashSet<String>();
            for (String fString : allFilePath) {
                File af = new File(fString);
                toCreateFolderSet.add(af.getParentFile().getAbsolutePath());
            }
            revFullCreateFolder(toCreateFolderSet);
            for (String fString : allFilePath) {
                fullUploadSingleFileDir(fString);
            }
        } else {
            fullCreateSingleFileDir(winRawPath);
            fullUploadSingleFileDir(winRawPath);
        }
    }

    private void fullCreateSingleFileDir(String fString) throws WebException {
        File f = new File(fString);
        String modAppleParent =
            fitAppleSplitter(cutToBasePath(f.getParentFile().getAbsolutePath()));
        modAppleParent = modAppleParent.concat("/");
        if (getCurrentPath().equals(modAppleParent)) {
            return;
        } else {
            finCreateFolder(modAppleParent);
        }
    }

    private void revFullCreateFolder(Set<String> folderSet) throws WebException {
        for (String fString : folderSet) {
            File f = new File(fString);
            String modAppleParent = fitAppleSplitter(cutToBasePath(f.getAbsolutePath()));
            modAppleParent = modAppleParent.concat("/");
            if (getCurrentPath().equals(modAppleParent)) {
                return;
            } else {
                finCreateFolder(modAppleParent);
            }
        }
    }

    private void fullUploadSingleFileDir(String fString) throws WebException {
        File f = new File(fString);
        String filePath = f.getAbsolutePath();
        String modAppleParent =
            fitAppleSplitter(cutToBasePath(f.getParentFile().getAbsolutePath()));
        modAppleParent = modAppleParent.concat("/");
        if (getCurrentPath().equals(modAppleParent)) {
            smartUploadFile(filePath);
        } else {
            trialQuickSwitch(modAppleParent);
            smartUploadFile(filePath);
        }
    }

    private String getCurrentPath() {
        // read current path in Hiby Navigator (possible aHtml + active)
        // first check if home?
        if (isHome()) {
            return "/";
        } else {
            List<WebElement> LiElement = driver.findElements(By.xpath("//li[@data-path]"));
            String partOneStr = LiElement.get(LiElement.size() - 1).getAttribute("data-path");
            logger.trace("PartOne:" + partOneStr);
            String activeStr = safeXPath("//li[@class='active']").getText();
            String fullPath = partOneStr + activeStr + "/";
            logger.debug("Current Path:" + fullPath);
            return fullPath;
        }
    }

    private void guaranteeOrCreateFolder(String folderName) {
        if (rawCheckPresent(folderName)) {
            logger.debug("folder '" + folderName + "' already exists!");
            return;
        } else {
            rawCreateFolder(folderName);
            logger.debug("folder '" + folderName + "' does not exist, creating...");
            return;
        }
    }

    private boolean trialQuickSwitch(String path) throws WebException {
        // first normalize
        path = normalizer.normalize(path);
        // check isHome?
        if (isHome()) {
            try {
                rawEnterRandomFolder();
            } catch (InterruptedException ie) {
                logger.error("InterruptedException when entering random folder!");
                throw new WebException("InterruptedException", ie,
                    ErrorCodes.WEB_INTERRUPTED);
            }
        }

        if (getCurrentPath().equals(path)) {
            // do not switch to self in order to avoid bug
            logger.debug("Does not switch to self at path:" + path);
            return true;
        }
        rawQuickSwitch(path);
        try {
            if (checkWarnings()) {
                logger.debug("Failed switch to '" + path + "',refreshing to home.");
                switchHome();
                return false;
            } else {
                logger.debug("Sucessfully switched to '" + path + "'");
                return true;
            }
        } catch (InterruptedException ie) {
            logger.error("InterruptedException when switching back home(refresh)!");
            throw new WebException("InterruptedException", ie, ErrorCodes.WEB_INTERRUPTED);
        }
    }

    private boolean rawCheckPresent(String presentName) {
        // first normalize
        presentName = normalizer.normalize(presentName, "checkPresent");
        try {
            driver.findElement(By.xpath("//td[p='" + presentName + "']"));
        } catch (NoSuchElementException nsse) {
            return false;
        }
        return true;
    }

    private void guaranteeActivePath(String lastpath) {
        FluentWait<WebDriver> wait = getWait();
        wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                String nowActive = driver.findElement(By.xpath("//li[@class='active']")).getText();
                logger.trace("NowActive:" + nowActive + ", lastPath:" + lastpath);
                return lastpath.equals(nowActive);
            }
        });
        logger.debug("guaranteed active '" + lastpath + "'");

    }

    private void rawCreateFolder(String folderName) {
        /* folder creation */
        WebElement folderCreateBtn = driver.findElement(By.id("create-folder"));
        folderCreateBtn.click();
        // first normalize utf8
        folderName = normalizer.normalize(folderName, "createFolder");
        // use safeXPath to guarantee model is completely open.
        WebElement folderCreateModal =
            safeXPath("//div[@id='create-modal'][@class='modal fade in']");
        logger.debug("switched to folder create modal");
        // fetch input text and confirm button. Since modal is already open, directly fetch for
        // efficiency.
        WebElement folderCreateText =
            folderCreateModal.findElement(By.xpath("//input[@id='create-input']"));
        WebElement folderCreateConfirmBtn =
            folderCreateModal.findElement(By.xpath("//button[@id='create-confirm']"));
        // fill in input, click button
        folderCreateText.clear();
        folderCreateText.sendKeys(folderName);
        folderCreateConfirmBtn.click();
        logger.debug("folder creation info sent");
        // guarantee modal is closed (back to normal)
        safeXPath("//body[@class='']");
    }

    private void rawEnterFolder(String folderName) {
        // first normalize utf8
        folderName = normalizer.normalize(folderName, "enterFolder");
        // fetch button
        WebElement thatFolderBtn =
            safeXPath("//td[p='" + folderName + "']/../td[@class='column-icon']/button");
        // scroll to button
        scrollToElem(thatFolderBtn);
        // click button
        thatFolderBtn.click();
        // guarantee inside
        guaranteeActivePath(folderName);
    }

    private void rawEnterRandomFolder() throws InterruptedException {
        // fetch button
        WebElement thatFolderBtn = safeXPath("//td[@class='column-icon']/button");
        // click button
        thatFolderBtn.click();
        // guarantee inside
        logger.debug("Entered random folder. Wait for 0.5 sec.");
        Thread.sleep(500);
    }

    private void rawDeleteFolder(String folderName) {
        WebElement thatFolderDeleteBtn =
            safeXPath("//td[p='" + folderName + "']/../td[@class='column-delete']/button");
        scrollToElem(thatFolderDeleteBtn);
        thatFolderDeleteBtn.click();
    }

    private void smartUploadFile(String filePath) {
        File uf = new File(filePath);
        String fileName = uf.getName();
        if (rawCheckPresent(fileName)) {
            logger.debug(fileName + "already exists in current directory!");
            return;
        }
        rawUploadFile(filePath);
    }

    private void rawUploadFile(String filePath) {
        WebElement uploadText = driver.findElement(By.id("fileupload"));
        // sendKeys is enough to trigger javascript
        uploadText.sendKeys(filePath);
    }


    /**
     * Modify
     * 
     * @param path
     */
    private void rawQuickSwitch(String path) {

        // first normalize utf8
        path = normalizer.normalize(path, "quickSwitch");
        // log
        logger.trace("Raw switching to " + path);
        WebElement firstLi = safeXPath("//li[@data-path]");
        WebElement firstHref = firstLi.findElement(By.xpath("//a"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", firstLi,
            "data-path", path);
        logger.debug("script executed in quickSwitch!");
        firstLi = safeXPath("//li[@data-path='" + path + "']");
        firstHref.click();



    }


    private FluentWait<WebDriver> getWait() {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        wait.withTimeout(Duration.ofSeconds(10));
        wait.pollingEvery(Duration.ofMillis(500));
        wait.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
        return wait;
    }

    private WebElement safeXPath(String xPathExpression) {
        FluentWait<WebDriver> wait = getWait();
        WebElement elem = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath(xPathExpression));
            }
        });
        return elem;
    }

    private String[] getParentReferencePaths(String[] refPaths) {
        ArrayList<String> tempArrlist = new ArrayList<String>();
        for (String path : refPaths) {
            File aFile = new File(path);
            String parentPath = aFile.getParentFile().getAbsolutePath();
            tempArrlist.add(parentPath);
        }
        String[] rtrArr = new String[tempArrlist.size()];
        rtrArr = tempArrlist.toArray(rtrArr);
        return rtrArr;
    }

    public void emphasize() {
        System.out.println("-----------------------------");
    }

    public String concatPath(ArrayList<String> pathParts) {
        String concatedPath = "/";
        for (String part : pathParts) {
            concatedPath = concatedPath.concat(part).concat("/");
        }
        return concatedPath;
    }

    /* Getter&Setter */

    public String getUrl() {
        return url;
    }



    public void setUrl(String url) {
        this.url = url;
    }



    public WebDriver getDriver() {
        return driver;
    }



    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public String[] getRefPaths() {
        return refPaths;
    }

    // !notice: modified to parent path here
    public void setRefPaths(String[] refPaths) {
        this.refPaths = refPaths;
    }

    public void initializeRefPaths(String[] refPaths) {
        setRefPaths(refPaths);
        this.refPaths = getParentReferencePaths(this.refPaths);
        logger.debug("RefPath Initialized! " + Arrays.toString(this.refPaths));
    }



    public void scrollToElem(WebElement elem) {
        Actions actions = new Actions(driver);
        actions.moveToElement(elem);
        actions.perform();
    }

    public String fitAppleSplitter(String winPath) {
        String modifiedPath = winPath.replaceAll("\\\\", "/");
        return modifiedPath;
    }

    public String cutToBasePath(String winRawPath) throws WebException {
        String modifiedPath = "";
        boolean foundFlag = false;
        for (String refPath : refPaths) {
            if (winRawPath.contains(refPath)) {
                modifiedPath = winRawPath.replace(refPath, "");
                foundFlag = true;
                break;
            }

        }
        if (!foundFlag) {
            logger.warn("Looped thorough refPaths but unable to find a corresponding base path!");
            throw new WebException("Does not found corresponding reference base folder!",
                ErrorCodes.WEB_REFERENCE_PATH_NOTFOUND);
        }
        logger.info("CUT TO BASE PATH:" + modifiedPath);
        return modifiedPath;
    }

    private void colleFile(File file, ArrayList<String> al) {
        File[] fs = file.listFiles();
        for (File f : fs) {
            if (f.isDirectory())
                colleFile(f, al);
            if (f.isFile())
                al.add(f.getAbsolutePath());
        }
    }



}
