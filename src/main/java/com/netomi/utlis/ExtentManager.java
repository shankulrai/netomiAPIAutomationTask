package com.netomi.utlis;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static final ExtentReports extentReports = new ExtentReports();
    public synchronized static ExtentReports getExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("./extent-reports/extent-report.html");
        reporter.config().setReportName("Weather API Test Report");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("User Name", "Shankul Rai");
        extentReports.setSystemInfo("Author", "Shankul Rai");
        return extentReports;
    }
}
