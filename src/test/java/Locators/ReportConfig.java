package Locators;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ReportConfig {

    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;

    public void reportSetup(String reportName , String testSuiteName) {
        htmlReporter = new ExtentHtmlReporter(reportName);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle(testSuiteName);
        htmlReporter.config().setReportName(testSuiteName);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    public void tearDown(String reportName) throws IOException {
        extent.flush();
        File htmlFile = new File(reportName);
        Desktop.getDesktop().open(htmlFile);
    }

}
