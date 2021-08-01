package com.netomi.listeners;

import static com.netomi.utlis.ExtentManager.getExtentReports;
import static com.netomi.utlis.ExtentTestManager.getTest;
import com.aventstack.extentreports.Status;
import java.util.Objects;

import com.netomi.utlis.BaseSteps;

import com.netomi.utlis.Log;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

 public class TestListener extends BaseSteps implements ITestListener {
    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }
    @Override
    public void onStart(ITestContext iTestContext) {
        Log.info("Start Of Test Method " + iTestContext.getName());

    }
    @Override
    public void onFinish(ITestContext iTestContext) {
        Log.info("onFinish of Test method " + iTestContext.getName());
        //Do tier down operations for ExtentReports reporting!
        getExtentReports().flush();
    }
    @Override
    public void onTestStart(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is starting.");
    }
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is succeed.");
        //ExtentReports log operation for passed tests.
        getTest().log(Status.PASS, "Test passed");
    }
    @Override
    public void onTestFailure(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is failed.");
        Object testClass = iTestResult.getInstance();

        //ExtentReports log and screenshot operations for failed tests.
        getTest().log(Status.FAIL, "Test Failed"+iTestResult.getThrowable());
    }
    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is skipped.");
        //ExtentReports log operation for skipped tests.
        getTest().log(Status.SKIP, "Test Skipped");
    }
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        Log.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }
}
