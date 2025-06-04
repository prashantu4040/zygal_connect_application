package Utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TestNG Listener for generating test reports in an Excel file. This class
 * listens to test execution events and logs the results.
 */
public class testReportListener implements ITestListener {
	private static final String FILE_PATH = "test-output/TestReport.xlsx"; // File path for storing the test report
	private static Workbook workbook = new XSSFWorkbook(); // Creating an Excel workbook
	private static Sheet sheet = workbook.createSheet("Test Results"); // Creating a sheet for test results
	private static int rowNum = 0; // Row number tracker for writing data
	private static Map<String, Integer> testCaseIds = new HashMap<>(); // Stores Test Case ID for each unique test case
																		// name
	private static int testCaseCounter = 1; // Counter for assigning Test Case IDs

	// List of methods to exclude from the report
	private static final List<String> EXCLUDED_METHODS = Arrays.asList("loginWithInvalidCredentialsTest"
	// Add more method names as needed
	);

	// Static block to initialize the header row in the Excel sheet with bold text
	static {
		Row headerRow = sheet.createRow(rowNum++);
		CellStyle boldStyle = workbook.createCellStyle();
		Font boldFont = workbook.createFont();
		boldFont.setBold(true);
		boldStyle.setFont(boldFont);

		String[] headers = { "Test Case ID", "Test Case Name", "Status", "Execution Time", "Comment" };
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(boldStyle);
		}
	}

	/**
	 * Deletes the previous test report before starting a new test execution.
	 */
	@Override
	public void onStart(ITestContext context) {
		File reportFile = new File(FILE_PATH);
		if (reportFile.exists()) {
			if (reportFile.delete()) {
				System.out.println("Previous test report deleted successfully.");
			} else {
				System.out.println("Failed to delete previous test report.");
			}
		}
	}

	/**
	 * This method is invoked when a test case passes.
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		if (!EXCLUDED_METHODS.contains(methodName)) {
			writeResult(result, "PASSED", "Test executed successfully.");
		}
	}

	/**
	 * This method is invoked when a test case fails.
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		if (!EXCLUDED_METHODS.contains(methodName)) {
			String failureReason = result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown Error";
			writeResult(result, "FAILED", failureReason);
		}
	}

	/**
	 * This method is invoked when a test case is skipped.
	 */
	@Override
	public void onTestSkipped(ITestResult result) {
		String methodName = result.getMethod().getMethodName();

		if (!EXCLUDED_METHODS.contains(methodName)) {
			Throwable throwable = result.getThrowable();
			String skipReason = (throwable instanceof SkipException && throwable.getMessage() != null)
					? throwable.getMessage()
					: "Test was skipped.";
			writeResult(result, "SKIPPED", skipReason);
		}
	}

	/**
	 * This method is invoked at the end of the test execution.
	 */
	@Override
	public void onFinish(ITestContext context) {
		try (FileOutputStream fileOut = new FileOutputStream(new File(FILE_PATH))) {
			// Auto-size all columns for better readability
			int totalColumns = sheet.getRow(0).getPhysicalNumberOfCells(); // Assumes header row exists
			for (int i = 0; i < totalColumns; i++) {
				sheet.autoSizeColumn(i);
			}

			workbook.write(fileOut); // Now write the formatted workbook
			System.out.println("✅ Test Report generated at: " + FILE_PATH);
		} catch (IOException e) {
			System.err.println("❌ Failed to write the test report:");
			e.printStackTrace();
		}
	}

	/**
	 * Writes the test result details into the Excel sheet.
	 */
	private void writeResult(ITestResult result, String status, String comment) {
		String testCaseName = result.getMethod().getDescription() != null ? result.getMethod().getDescription()
				: result.getName();
		int testCaseId = getTestCaseId(testCaseName);

		Row row = sheet.createRow(rowNum++);
		row.createCell(0).setCellValue(testCaseId); // Test Case ID
		row.createCell(1).setCellValue(testCaseName); // Test Case Name
		row.createCell(2).setCellValue(status); // Status (PASSED, FAILED, SKIPPED)
		row.createCell(3).setCellValue(getFormattedExecutionTime()); // Execution Time
		row.createCell(4).setCellValue(comment); // Comment or failure reason
	}

	/**
	 * Retrieves or assigns a unique Test Case ID for each test case name using
	 * HashMap.
	 */
	private static int getTestCaseId(String testCaseName) {
		return testCaseIds.computeIfAbsent(testCaseName, k -> testCaseCounter++);
	}

	/**
	 * Formats the execution time in 12-hour format (MM/dd/yyyy hh:mm:ss a) with
	 * AM/PM in uppercase.
	 */
	private static String getFormattedExecutionTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH);
		return dateFormat.format(new Date()).toUpperCase(); // Convert AM/PM to uppercase
	}
}