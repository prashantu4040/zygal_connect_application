package Utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class testReportListener implements ITestListener {
    private static final String FILE_PATH = "test-output/TestReport.xlsx";
    private static Workbook workbook = new XSSFWorkbook();
    private static Sheet sheet = workbook.createSheet("Test Results");
    private static int rowNum = 0;

    static {
        // Create Header Row
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("Test Case Name");
        headerRow.createCell(1).setCellValue("Status");
        headerRow.createCell(2).setCellValue("Execution Time");
        headerRow.createCell(3).setCellValue("Comment"); 
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        writeResult(result, "PASSED", "Test executed successfully.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String failureReason = result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown Error";
        writeResult(result, "FAILED", failureReason);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        writeResult(result, "SKIPPED", "Test was skipped.");
    }

    @Override
    public void onFinish(ITestContext context) {
        try (FileOutputStream fileOut = new FileOutputStream(new File(FILE_PATH))) {
            workbook.write(fileOut);
            System.out.println("Test Report generated at: " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeResult(ITestResult result, String status, String comment) {
        Row row = sheet.createRow(rowNum++);
        String testName = result.getMethod().getDescription();
        row.createCell(0).setCellValue(testName != null ? testName : result.getName());
        row.createCell(1).setCellValue(status);
        row.createCell(2).setCellValue(new Date().toString());
        row.createCell(3).setCellValue(comment); 
    }

}
