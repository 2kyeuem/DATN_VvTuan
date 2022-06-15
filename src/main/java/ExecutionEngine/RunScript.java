package ExecutionEngine;

import Utilities.ActionKeywords;
import Utilities.ExcelUtils;
import Utilities.Log;
import com.aventstack.extentreports.Status;
import org.junit.Test;
import utils.ExtentTestManager;

import java.util.NoSuchElementException;

import static utils.ExtentManager.getExtentReports;


public class RunScript {
    String sPath = System.getProperty("user.dir") + "//src//main//java//DataEngine//DataDATN1.xlsx";

    @Test //Test script chức năng đăng nhập
        public void Excute_TestScriptLogin() throws Exception {
            ExcelUtils.setExcelFile(sPath, "LoginPage");
            int row = ExcelUtils.getRowCount("LoginPage");
            HamChung(row);
        }
    @Test  //Test script chức năng đăng ký
        public void Excute_TestScriptRegister() throws Exception {
        ExcelUtils.setExcelFile(sPath, "RegisterPage");
        int row = ExcelUtils.getRowCount("RegisterPage");
        HamChung(row);
         }
    @Test  //Test script chức năng giỏ hàng
    public void Excute_TestScriptCart() throws Exception {
        ExcelUtils.setExcelFile(sPath, "CartPage");
        int row = ExcelUtils.getRowCount("CartPage");
        HamChung(row);
            }
    @Test  //Test script chức năng đặt hàng
    public void Excute_TestScriptCheckout() throws Exception {
        ExcelUtils.setExcelFile(sPath, "CheckoutPage");
        int row = ExcelUtils.getRowCount("CheckoutPage");
        HamChung(row);
             }
        //Hàm dùng chung các switch case
        public void HamChung(int row) throws Exception {
        int CasePass = 0;
        int CaseFail = 0;
        int CaseSkip = 0;
        for (int i = 1; i < row; i++) {
            System.out.println("Line:" + i);
            String Description = ExcelUtils.getCellData(i, 1);
            String sActionKeyword = ExcelUtils.getCellData(i, 2);
            String locatorType = ExcelUtils.getCellData(i, 3);
            String locatorValue = ExcelUtils.getCellData(i, 4);
            String testData = ExcelUtils.getCellData(i, 5);
            String CaseName = ExcelUtils.getCellData(i, 0);
            System.out.println(Description);
            switch (sActionKeyword) {
                case "openBrowser":
                    if (CaseName != null)
                    {
                        Log.info("--------------Thực thi Test Case ID: " + CaseName + "--------------");
                    }
                    ExtentTestManager.saveToReport(CaseName,"");
                    try {
                        ActionKeywords.openBrowser(testData);
                        ExtentTestManager.logMessage(Status.PASS, Description);
                        }
                    catch (Exception e)
                        {
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                        }
                    break;
                case "move":
                    try {
                        ActionKeywords.ElementPerfom(locatorValue);
                        ExtentTestManager.logMessage(Status.PASS, Description);
                        }
                    catch (Exception e)
                        {
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                        }
                    break;
                case "navigate":
                    try {
                        ActionKeywords.navigate(testData);
                        ExtentTestManager.logMessage(Status.PASS, Description);
                        }
                    catch (Exception e)
                        {
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                        }
                    break;
                case "setText":
                    try {
                        ActionKeywords.setText(locatorType, locatorValue, testData);
                        ExtentTestManager.logMessage(Status.PASS, Description);
                        }
                    catch (NoSuchElementException e)
                        {
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                        }
                    break;
                case "dismissAlert":
                    ActionKeywords.skipQC();
                    ExtentTestManager.logMessage(Status.INFO, Description);
                    break;
                case "click":
                    try {
                        ActionKeywords.clickElement(locatorType, locatorValue);
                        ExtentTestManager.logMessage(Status.PASS, Description);
                        }
                    catch (NoSuchElementException e)
                        {
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                        }
                    break;
                case "verifyUrl":
                    if (ActionKeywords.getUrl(testData)) {
                        Log.info("Same result ---> pass");
                        CasePass++;
                        ExtentTestManager.logMessage(Status.PASS, Description);
                    } else {
                        Log.error("Different result ---> Fail");
                        CaseFail++;
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                    }
                    break;
                case "AlertofEmail":
                    if (ActionKeywords.verifyAlertEmailofLoginPage(testData)) {
                        Log.info("Same result ---> pass");
                        CasePass++;
                        ExtentTestManager.logMessage(Status.PASS, Description);
                    } else {
                        Log.error("Different result ---> Fail");
                        CaseFail++;
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                    }
                    break;
                case "AlertofPassword":
                    if (ActionKeywords.verifyAlertPasswordofLoginPage(testData)) {
                        Log.info("Same result ---> pass");
                        CasePass++;
                        ExtentTestManager.logMessage(Status.PASS, Description);
                    } else {
                        Log.error("Different result ---> Fail");
                        CaseFail++;
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                    }
                    break;
                case "verifyelementtext":
                    if (ActionKeywords.verifyText(locatorType, locatorValue, testData)) {
                        Log.info("Same result ---> pass");
                        CasePass++;
                        ExtentTestManager.logMessage(Status.PASS, Description);
                    }
                    else
                    {
                        Log.error("Different result ---> Fail");
                        CaseFail++;
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                    }
                    break;
                case "Displayed":
                    try {
                        ActionKeywords.Displayed(locatorType,locatorValue);
                        ExtentTestManager.logMessage(Status.PASS, Description);
                    }
                    catch (Exception e)
                    {
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                    }
                    break;
                case "Sceenshot":
                    try {
                        ActionKeywords.screenshot(CaseName);
                        ExtentTestManager.logMessage(Status.PASS, Description);
                    }
                   catch (Exception e)
                   {
                       ExtentTestManager.logMessage(Status.FAIL, Description);
                   }
                    break;
                case "verifyAlertfistname":
                    if (ActionKeywords.verifyAlertfistname(testData)) {
                        Log.info("Same result ---> pass");
                        CasePass++;
                        ExtentTestManager.logMessage(Status.PASS, Description);
                    } else {
                        Log.error("Different result ---> Fail");
                        CaseFail++;
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                    }
                    break;
                case "verifyAlertemail":
                    if (ActionKeywords.verifyAlertemail(testData)) {
                        Log.info("Same result ---> pass");
                        CasePass++;
                        ExtentTestManager.logMessage(Status.PASS, Description);
                    } else {
                        Log.error("Different result ---> Fail");
                        CaseFail++;
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                    }
                    break;
                case "verifyAlertlastname":
                    if (ActionKeywords.verifyAlertlastname(testData)) {
                        Log.info("Same result ---> pass");
                        CasePass++;
                        ExtentTestManager.logMessage(Status.PASS, Description);
                    } else {
                        Log.error("Different result ---> Fail");
                        CaseFail++;
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                    }
                    break;
                case "verifyAlertsdt":
                    if (ActionKeywords.verifyAlertsdt(testData)) {
                        Log.info("Same result ---> pass");
                        CasePass++;
                        ExtentTestManager.logMessage(Status.PASS, Description);
                    } else {
                        Log.error("Different result ---> Fail");
                        CaseFail++;
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                    }
                    break;
                case "verifyAlertPasswordofRegisterpage":
                    if (ActionKeywords.verifyAlertPasswordofRegisterpage(testData)) {
                        Log.info("Same result ---> pass");
                        CasePass++;
                        ExtentTestManager.logMessage(Status.PASS, Description);
                    } else {
                        Log.error("Different result ---> Fail");
                        CaseFail++;
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                    }
                    break;
                case "verifyAlertdatefield":
                    if (ActionKeywords.verifyAlertdatefield(testData)) {
                        Log.info("Same result ---> pass");
                        CasePass++;
                        ExtentTestManager.logMessage(Status.PASS, Description);
                    } else {
                        Log.error("Different result ---> Fail");
                        CaseFail++;
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                    }
                    break;
                case "verifyAlertcheckbox":
                    if (ActionKeywords.verifyAlertcheckbox(testData)) {
                        Log.info("Same result ---> pass");
                        CasePass++;
                        ExtentTestManager.logMessage(Status.PASS, Description);
                    } else {
                        Log.error("Different result ---> Fail");
                        CaseFail++;
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                    }
                    break;
                case "selectOptionByValue":
                    try  {
                        ActionKeywords.selectOptionByValue(locatorType, locatorValue, testData);
                        ExtentTestManager.logMessage(Status.PASS, Description);
                         }
                    catch (NoSuchElementException e)
                        {
                            ExtentTestManager.logMessage(Status.FAIL, Description);
                        }
                    break;
                case "selectOptionByText":
                    try  {
                        ActionKeywords.selectOptionByText(locatorType, locatorValue, testData);
                        ExtentTestManager.logMessage(Status.PASS, Description);
                    }
                    catch (NoSuchElementException e)
                    {
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                    }
                    break;
                case "ScrollDownAndClick":
                    try {
                        ActionKeywords.clickElementWithJs(locatorType, locatorValue);
                        ExtentTestManager.logMessage(Status.PASS, Description);
                        }
                    catch (NoSuchElementException e)
                        {
                            ExtentTestManager.logMessage(Status.FAIL, Description);
                        }
                    break;
                case "quitBrowser":
                    try
                    {
                    ActionKeywords.quitDriver();
                    ExtentTestManager.logMessage(Status.PASS, Description);
                    }
                    catch (Exception e)
                    {
                        ExtentTestManager.logMessage(Status.FAIL, Description);
                    }
                    break;
            }
        }
        getExtentReports().flush();
        java.util.Date date = new java.util.Date();
        System.out.println("==========================================================");
        System.out.println("-----------" + date + "--------------");
        System.out.println("Total number of Testcases run: " + (CasePass + CaseFail + CaseSkip));
        System.out.println("Total number of passed Testcases: " + CasePass);
        System.out.println("Total number of failed Testcases: " + CaseFail);
        System.out.println("Total number of skip Testcases: " + CaseSkip);
        System.out.println("==========================================================");
    }
}

//        for (int iRow=1;iRow<=19;iRow++)
//        {
//            String Description = ExcelUtils.getCellData(iRow, 1);
//            String sActionKeyword = ExcelUtils.getCellData(iRow, 2);
//            String locatorType = ExcelUtils.getCellData(iRow, 3);
//            String locatorValue = ExcelUtils.getCellData(iRow, 4);
//            String testData = ExcelUtils.getCellData(iRow, 5);
//            String CaseName = ExcelUtils.getCellData(iRow, 0);

