package Utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class ActionKeywords {
    public static WebDriver driver;
    private static Actions action;
    private static JavascriptExecutor js;
   private static WebDriverWait wait;

    private static WebElement GetElement(String locatorType, String locatorValue) {
        WebElement element = null;

        if (locatorType.equalsIgnoreCase("className"))
            element = driver.findElement(By.className(locatorValue));
        else if (locatorType.equalsIgnoreCase("cssSelector"))
            element = driver.findElement(By.cssSelector(locatorValue));
        else if (locatorType.equalsIgnoreCase("id"))
            element = driver.findElement(By.id(locatorValue));
        else if (locatorType.equalsIgnoreCase("partialLinkText"))
            element = driver.findElement(By.partialLinkText(locatorValue));
        else if (locatorType.equalsIgnoreCase("name"))
            element = driver.findElement(By.name(locatorValue));
        else if (locatorType.equalsIgnoreCase("xpath"))
            element = driver.findElement(By.xpath(locatorValue));
        else if (locatorType.equalsIgnoreCase("tagName"))
            element = driver.findElement(By.tagName(locatorValue));
        else {
            Log.error("GetElement " + locatorType + "=" + locatorValue);
        }
        return element;
    }

    //Keyword Mở trình duyệt
    public static void openBrowser(String browserName) {
        try {
            Log.info("Executing: Open browser: " + browserName);
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }catch (Exception e) {
            Log.error("Executing: Open browser: " + browserName + "FAIL");
        }
    }
    //Keyword truy cập URL
    public static void navigate(String appURL) throws InterruptedException {
        try {
            Log.info("executing: Open Url: " + appURL);
            driver.navigate().to(appURL);
            waitForPageLoaded();
        } catch (Exception e) {
            Log.error("Executing: Open Url:" + appURL + "FAIL");
        }
    }

    //Keyword thoát khỏi trình duyệt
    public static void quitDriver(){
        try {
            Log.info("Executing: Quit");
            driver.manage().deleteAllCookies();
            driver.quit();
        }catch (Exception e)
        {
            Log.error("Executing: Quit FAIL");
        }
    }

    //Keyword Di chuyển tới đối tượng nhưng không ấn
    public static void ElementPerfom(String address) {

        try {
            if (address.contains("[")) {
                Actions a = new Actions(driver);
                waitForPageLoaded();
                a.moveToElement(driver.findElement(By.xpath(address))).
                        build().perform();
                Log.info("Executing: Move mouse");
            } else {
                Actions a = new Actions(driver);
                a.moveToElement(driver.findElement(By.id(address))).build().perform();
            }
        } catch (Exception e) {
            Log.error("Move mouse: FAIL");
        }
    }

    //Keyword làm mới lại trang
    public static void Refresh() throws InterruptedException {
        driver.navigate().refresh();
        Thread.sleep(3000);
        Log.info("Executing: Refresh website");
    }
 //wait for Javascript to Loaded
//    public static void waitForPageLoaded() {
//        ExpectedCondition<Boolean> jsload = new ExpectedCondition<Boolean>() {
//            public Boolean apply(WebDriver driver) {
//                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
//                        .equals("complete");
//            }
//        };
//        try {
//            wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//            wait.until(jsload);
//        } catch (Throwable error) {
//            Log.error("Timeout waiting for Javascript request.");
//        }
//    }
    public static void waitForPageLoaded() {

        // wait for Javascript to loaded
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState")
                .toString().equals("complete");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20), Duration.ofMillis(500));
            wait.until(jsLoad);
        } catch (Throwable error) {
            Assert.fail("Timeout for page load (Javascript). (" + 20 + "s)");
        }
    }

//    public static void waitForPageLoaded() {
//
//        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//        wait.until(new Function<WebDriver, Boolean>() {
//            public Boolean apply(WebDriver driver) {
//                System.out.println("Page Loaded : "
//                        + String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
//                return String
//                        .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
//                        .equals("complete");
//            }
//        });
//    }
    //Keyword nhập vào ô textbox
    public static void setText(String locatorType, String locatorValue, String value) {
        try {
            Log.info("Executing: Enter text: " + value);
            WebElement element = GetElement(locatorType, locatorValue);
            waitForPageLoaded();
            element.clear();
            element.sendKeys(value);
        } catch (NoSuchElementException e) {
            Log.error("SendKeys:" + locatorType + "=" + locatorValue
                    + " not found to sendKeys| " + e.getMessage());
        }
    }

    //Keyword ấn chuột trái
    public static void clickElement(String locatorType, String locatorValue) throws InterruptedException {
        try {
            Log.info("Executing: Click element: "+ locatorValue);
            WebElement element = GetElement(locatorType, locatorValue);
            waitForPageLoaded();
            element.click();
        } catch (NoSuchElementException e) {
            Log.error("Click:" + locatorValue + " not found to click " + e.getMessage());
        }
        Thread.sleep(1500);
    }

    //Keyword ấn chuột trái nhưng với JS
    public static void clickElementWithJs(String locatorType, String locatorValue) throws InterruptedException {
        js = (JavascriptExecutor) driver; // khởi tạo
        try {
            Log.info("Executing: Scroll mouse down and click element: " + locatorValue);
            WebElement element = GetElement(locatorType, locatorValue);
            waitForPageLoaded();
            js.executeScript("arguments[0].scrollIntoView(true)", element);
            js.executeScript("arguments[0].click();", element);
        } catch (NoSuchElementException e) {
            Log.error("|clickElementWithJs:" + locatorType + "=" + locatorValue
                    + " not found to click| " + e.getMessage());
        }
    }
    public static void Displayed(String locatorType, String locatorValue){
        Log.info("Executing: Verify element displayed");
        WebElement element= GetElement(locatorType, locatorValue);
        try{
            element.isDisplayed();
            Log.info("Result: Element is displayed");
        }
        catch (Exception e)
        {
            Log.error("Element is not displayed");
        }
    }

    public static boolean verifyText(String locatorType, String locatorValue, String value) {
        Log.info("Executing: Verify Text");
        String result;
        WebElement element= GetElement(locatorType, locatorValue);
        waitForPageLoaded();
        result = element.getText();
        Log.info("Text result: "+result);
        Log.info("Text expected: "+value);
        if (value.equals(result))
        {
            return true;
        }
        else return false;
        //Assert.assertEquals(value,result);
    }
    //Keyword xác minh đối tượng
//    public static boolean verifyElementText(String locatorType, String locatorValue, String value) {
//        boolean result = true;
//        try {
//            System.out.println("[info] Executing: |verify Element Text| element:" + locatorType + "=" + locatorValue
//                    + "|text: " + value);
//            WebElement element = GetElement(locatorType, locatorValue);
//            result = element.getText().equals(value);
//        } catch (NoSuchElementException e) {
//            System.out.println("[>>ERROR<<]: |verify Element Text| element:" + locatorType + "=" + locatorValue
//                    + "|text: " + value + " not found to verify| " + e.getMessage());
//            result = false;
//        }
//        return result;
//    }

//    public static boolean verifyElementText1(WebElement element, String textValue) {
//        boolean result = true;
//        try {
//            System.out.println("[info] Executing: |verify Element Text| element: " + element + "|Value: " + textValue);
////			APP_LOGS.debug("[info] Executing: |verify Element Text| element: " + element + "|Value: " + textValue);
//            wait.until(ExpectedConditions.visibilityOf(element));
//            result = element.getText().equals(textValue);
//        } catch (NoSuchElementException e) {
//            System.out.println("[>>ERROR<<]: |verify Element Text| element: " + element + "|Value: " + textValue
//                    + " not found to verify| " + e.getMessage());
////			APP_LOGS.debug("[>>ERROR<<]: |verify Element Text| element: " + element + "|Value: " + textValue +" not found to verify| " + e.getMessage());
//            result = false;
//        }
//        return result;
//    }

    //Keyword bấm chuột phải
    public static void rightClickElement(String locatorType, String locatorValue) throws InterruptedException {
        try {
            Log.info("Executing: |Right click| element: " + locatorType + "= " + locatorValue);
            WebElement element = GetElement(locatorType, locatorValue);
            waitForPageLoaded();
            wait.until(ExpectedConditions.elementToBeClickable(element));
            action.contextClick().build().perform();
        } catch (NoSuchElementException e) {
            Log.error("|Right click: " + locatorType + "= " + locatorValue
                    + " not found to click| " + e.getMessage());
        }
    }

    //Keyword chon du lieu tu combobox
    public static void selectOptionByText(String locatorType, String locatorValue, String text) throws InterruptedException {
        try {
            Log.info("Executing: Selection option: " + text);
            WebElement element = GetElement(locatorType, locatorValue);
            waitForPageLoaded();
            Select select = new Select(element);
            select.selectByVisibleText(text);
        } catch (NoSuchElementException e) {
            Log.error("|select Option By Text|: " + locatorType + "= " + locatorValue + "|text: "
                    + text + " not found to select| " + e.getMessage());
        }
    }
    public static void selectOptionByValue(String locatorType, String locatorValue, String text) throws InterruptedException {
        try {
            Log.info("Executing: Selection option: " + text);
            WebElement element = GetElement(locatorType, locatorValue);
            waitForPageLoaded();
            Select select = new Select(element);
            select.selectByValue(text);
        } catch (NoSuchElementException e) {
            Log.error("Select Option By Text: " + locatorType + "= " + locatorValue + "|text: "
                    + text + " not found to select| " + e.getMessage());
        }
        Thread.sleep(1500);
    }

//    //Keyword chấp nhận hộp thoại
//    public static Boolean acceptAlert() {
//        try {
//
//            System.out.println("Accepting current alert");
//            driver.switchTo().alert().getText();
//            return true;
//        } catch (Exception e) {
//            System.out.println("Not able to accept: " + e.getMessage());
//            return false;
//        }
//    }

    //Keyword bỏ qua hộp thoại
//    public static Boolean dismissAlert() throws InterruptedException {
//        Thread.sleep(5000);
//        try {
//            System.out.println("Dismissing current alert");
//            driver.switchTo().alert().dismiss();
//            return true;
//        } catch (Exception e) {
//            System.out.println("Not able to dismiss alert: " + e.getMessage());
//            return false;
//        }
//    }

    //Keyword lấy url hiện tại
    public static boolean getUrl(String value) throws InterruptedException {
        Log.info("Executing: |Get Url|");
        waitForPageLoaded();
        String ResultUrl = driver.getCurrentUrl();
        Log.info("Result Url -->" + ResultUrl);
        Log.info("Expected Url -->" + value);
        if (value.equals(ResultUrl))
        {
            return true;
        }
        else return false;
    }

    public static boolean verifyAlertEmailofLoginPage(String value) throws InterruptedException {
        Log.info("Executing: |Verify alert of email|");
        WebElement email = driver.findElement(By.id("customer_email"));
        String expect= email.getAttribute("validationMessage");
        waitForPageLoaded();
        //Assert.assertTrue(Boolean.parseBoolean(email.getAttribute("required"))); //kết quả thực tế mà lấy được
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + email.getAttribute("validationMessage"));
        if (value.equals(expect))
        {
            return true;
        }
        else return false;
      //  Assert.assertEquals(email.getAttribute("validationMessage"), value); //kết quả mong đợi của mình
    }

    public static boolean verifyAlertPasswordofLoginPage(String value) throws InterruptedException {
        Log.info("Executing: |Verify alert of password|");
        WebElement password = driver.findElement(By.id("customer_password"));
        String expect= password.getAttribute("validationMessage");
        waitForPageLoaded();
      //  Assert.assertTrue(Boolean.parseBoolean(password.getAttribute("required"))); //kết quả thực tế mà lấy được
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + password.getAttribute("validationMessage"));
        if (value.equals(expect))
        {
            return true;
        }
        else return false;
       // Assert.assertEquals(password.getAttribute("validationMessage"), value); //kết quả mong đợi của mình
    }

    public static boolean verifyAlertlastname(String value) throws InterruptedException {
        Log.info("Executing: Verify alert of last name");
        WebElement lastname = driver.findElement(By.xpath("//input[@id='last_name']"));
        String expect= lastname.getAttribute("validationMessage");
        waitForPageLoaded();
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + lastname.getAttribute("validationMessage"));
        if (value.equals(expect))
        {
            return true;
        }
        else return false;
    }
    public static boolean verifyAlertfistname(String value) throws InterruptedException {
        Log.info("Executing: Verify alert of fist name");
        WebElement fistname = driver.findElement(By.xpath("//input[@id='first_name']"));
        String expect= fistname.getAttribute("validationMessage");
        waitForPageLoaded();
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + fistname.getAttribute("validationMessage"));
        if (value.equals(expect))
        {
            return true;
        }
        else return false;
    }
    public static boolean verifyAlertemail(String value) throws InterruptedException {
        Log.info("Executing: Verify alert of email");
        WebElement email = driver.findElement(By.xpath("//input[@id='email']"));
        String expect= email.getAttribute("validationMessage");
        waitForPageLoaded();
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + email.getAttribute("validationMessage"));
        if (value.equals(expect))
        {
            return true;
        }
        else return false;
    }
    public static boolean verifyAlertsdt(String value) throws InterruptedException {
        Log.info("Executing: Verify alert of phone number");
        WebElement sdt = driver.findElement(By.xpath("//input[@id='phone_ac']"));
        String expect= sdt.getAttribute("validationMessage");
        waitForPageLoaded();
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + sdt.getAttribute("validationMessage"));
        if (value.equals(expect))
        {
            return true;
        }
        else return false;
    }
    public static boolean verifyAlertPasswordofRegisterpage(String value) throws InterruptedException {
        Log.info("Executing: Verify alert password of register page");
        WebElement passwordRG = driver.findElement(By.xpath("//input[@id='password']"));
        String expect= passwordRG.getAttribute("validationMessage");
        waitForPageLoaded();
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + passwordRG.getAttribute("validationMessage"));
        if (value.equals(expect))
        {
            return true;
        }
        else return false;
    }
    public static boolean verifyAlertdatefield(String value) throws InterruptedException {
        Log.info("Executing: Verify alert of date birth");
        WebElement DateBirth = driver.findElement(By.xpath("//input[@id='birthdate']"));
        String expect= DateBirth.getAttribute("validationMessage");
        waitForPageLoaded();
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + DateBirth.getAttribute("validationMessage"));
        if (value.equals(expect))
        {
            return true;
        }
        else return false;
    }
    public static boolean verifyAlertcheckbox(String value) throws InterruptedException {
        Log.info("Executing: Verify checkbox alert");
        WebElement checkbox = driver.findElement(By.xpath("//input[@type='checkbox']"));
        String expect= checkbox.getAttribute("validationMessage");
        waitForPageLoaded();
        Log.info("Expected Result: " + value);
        Log.info("Actual Result: " + checkbox.getAttribute("validationMessage"));
        if (value.equals(expect))
        {
            return true;
        }
        else return false;
    }
    public static void skipQC() throws InterruptedException {
        WebElement alert=driver.findElement(By.id("normal-slidedown"));
        WebElement skip=driver.findElement(By.id("onesignal-slidedown-cancel-button"));
        alert.isDisplayed();
        skip.click();
        Log.info("Skip ad");


    }
    public static void screenshot(String CaseName) throws IOException {
        try {
            // Tạo tham chiếu của TakesScreenshot với driver hiện tại
            TakesScreenshot ts = (TakesScreenshot) driver;
            // Gọi hàm capture screenshot - getScreenshotAs
            File source = ts.getScreenshotAs(OutputType.FILE);
            //Kiểm tra folder tồn tại. Nêu không thì tạo mới folder
            File theDir = new File("./Screenshots/");
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            // result.getName() lấy tên của test case xong gán cho tên File chụp màn hình luôn
            FileHandler.copy(source, new File("./Screenshots/" + CaseName + ".png"));
            Log.info("Executing: Screenshot taken: " + CaseName);
        }
        catch (Exception e)
        {
            Log.error("Executing: Screenshot taken: " + CaseName + "FAIL");
        }
    }
}
