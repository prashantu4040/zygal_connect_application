package Utility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class parameterization {

	public static String getData(String sheetname, int row, int cell) throws EncryptedDocumentException, IOException {

		FileInputStream file = new FileInputStream("E:\\zygalAutomation\\zygal_connect_application\\src\\test\\resources\\data.xlsx");

		String value = WorkbookFactory.create(file).getSheet(sheetname).getRow(row).getCell(cell).getStringCellValue();

		return value;
	}
}