package com.hms.test.RepoterAndCustomException;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.IRetryAnalyzer;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.collections.Lists;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Reporter implements IReporter, IRetryAnalyzer, ITestListener {

	private static ExtentReports extent;
	public static ExtentTest test;
	
	
	
	public static void getstarttime(){
	//	test=RoomsTest.test;
		test.setStartedTime(new Date());
	}
	public static ExtentReports getExtent(){
		return extent = new ExtentReports("Report\\" + File.separator + "ExtentReportTestNG.html");
		
	}
	 public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
			//   extent = new ExtentReports("Report\\" + File.separator + "ExtentReportTestNG.html", true);
			    	try{
			    	extent =getExtent();
			        for (ISuite suite : suites) {
			            Map<String, ISuiteResult> result = suite.getResults();
			            for (ISuiteResult r : result.values()) {
			                ITestContext context = r.getTestContext();
			                buildTestNodes(context.getPassedTests(), LogStatus.PASS);
			                buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
			                buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
			            }
			        }
			        extent.flush();
			        extent.close();
			    	}catch(Exception e){
			    		try {
							throw e;
						} catch (Exception e1) {
							e1.printStackTrace();
						}
			    	    }
			           }
	 
    public void buildTestNodes(IResultMap tests, LogStatus status) throws Exception {
         if (tests.size() > 0) {
             for (ITestResult result : tests.getAllResults()) {
                test = extent.startTest(result.getInstanceName().split("\\.")[4]+" :: "+result.getMethod().getMethodName());
                test.getStartedTime();
                test.getEndedTime();
                test.setStartedTime(getTime(result.getStartMillis()));
                test.setEndedTime(getTime(result.getEndMillis()));
                 for (String group : result.getMethod().getGroups())
                     test.assignCategory(group);
                 if (result.getThrowable() != null) {
                	 test.log(status, result.getThrowable());
                 }else{
                     test.log(status, result.getMethod().getDescription());
                 } 
                 extent.endTest(test);
            }
            }
            }
    
    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();        
    }
    
    private int retryCount = 0;
	private int maxRetryCount = 1;
	
	public boolean retry(ITestResult result) {
		  if (retryCount < maxRetryCount) {
	            retryCount++;
	            return true;
	        }return false;
	}
	
	
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		String newLine = System.getProperty("line.separator");
		System.out.println(result.getMethod().getMethodName()+" is Passed."+newLine+result.getMethod().getDescription());
	}
	
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		 System.err.println(result.getMethod().getMethodName()+" is Failed."+" Reason "+result.getThrowable().getMessage());
	}
	
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
	}
	
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}
	
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
	}
	
	public void onFinish(ITestContext context){
		
	}

}
