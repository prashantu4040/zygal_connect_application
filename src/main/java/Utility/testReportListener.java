package Utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * TestNG Listener for generating test reports in an Excel file.
 * This class listens to test execution events and logs the results.
 */
public class testReportListener implements ITestListener {
    private static final String FILE_PATH = "test-output/TestReport.xlsx"; // File path for storing the test report
    private static Workbook workbook = new XSSFWorkbook(); // Creating an Excel workbook
    private static Sheet sheet = workbook.createSheet("Test Results"); // Creating a sheet for test results
    private static int rowNum = 0; // Row number tracker for writing data

    // Static block to initialize the header row in the Excel sheet
    static {
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("Test Case Name");
        headerRow.createCell(1).setCellValue("Status");
        headerRow.createCell(2).setCellValue("Execution Time");
        headerRow.createCell(3).setCellValue("Comment"); 
    }

    /**
     * This method is invoked when a test case passes.
     * It logs the test case as "PASSED" with a success comment.
     *
     * @param result The test result object
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        writeResult(result, "PASSED", "Test executed successfully.");
    }

    /**
     * This method is invoked when a test case fails.
     * It logs the test case as "FAILED" along with the failure reason.
     *
     * @param result The test result object
     */
    @Override
    public void onTestFailure(ITestResult result) {
        String failureReason = result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown Error";
        writeResult(result, "FAILED", failureReason);
    }

    /**
     * This method is invoked when a test case is skipped.
     * It logs the test case as "SKIPPED" with a relevant message.
     *
     * @param result The test result object
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        writeResult(result, "SKIPPED", "Test was skipped.");
    }

    /**
     * This method is invoked at the end of the test execution.
     * It writes the data to the Excel file and saves the report.
     *
     * @param context The test execution context
     */
    @Override
    public void onFinish(ITestContext context) {
        try (FileOutputStream fileOut = new FileOutputStream(new File(FILE_PATH))) {
            workbook.write(fileOut); // Writing data to the file
            System.out.println("Test Report generated at: " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the test result details into the Excel sheet.
     *
     * @param result  The test result object
     * @param status  The test execution status (PASSED, FAILED, SKIPPED)
     * @param comment A comment describing the test result
     */
    private void writeResult(ITestResult result, String status, String comment) {
        Row row = sheet.createRow(rowNum++); // Creating a new row
        String testName = result.getMethod().getDescription(); // Fetching test case description
        row.createCell(0).setCellValue(testName != null ? testName : result.getName()); // Test case name
        row.createCell(1).setCellValue(status); // Status (PASSED, FAILED, SKIPPED)
        row.createCell(2).setCellValue(new Date().toString()); // Execution time
        row.createCell(3).setCellValue(comment); // Comment or failure reason
    }
}
