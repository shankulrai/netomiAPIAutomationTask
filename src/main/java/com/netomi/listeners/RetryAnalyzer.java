package com.netomi.listeners;
import static com.netomi.utlis.ExtentTestManager.getTest;
import com.aventstack.extentreports.Status;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;



public class RetryAnalyzer implements IRetryAnalyzer {
    private        int count  = 0;
    private static int maxTry = 1; //Run the failed test 2 times
    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {
            if (count < maxTry) {
                count++;
                iTestResult.setStatus(ITestResult.FAILURE);
                extendReportsFailOperations(iTestResult);
                return true;
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);
        }
        return false;
    }
    public void extendReportsFailOperations(ITestResult iTestResult) {
        Object testClass = iTestResult.getInstance();

        getTest().log(Status.FAIL, "Test Failed After Retry");
    }
}
