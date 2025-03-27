package Utility;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class parameterization {

    /**
     * Retrieves data from an Excel file based on the specified sheet name, row, and cell index.
     * 
     * @param sheetname The name of the Excel sheet.
     * @param row The row number (0-based index).
     * @param cell The column index (0-based index).
     * @return The data from the specified cell as a String.
     * @throws EncryptedDocumentException If the Excel file is encrypted or corrupted.
     * @throws IOException If there is an issue reading the file.
     */
    public static String getData(String sheetname, int row, int cell) throws EncryptedDocumentException, IOException {
        
        // Open the Excel file from the specified path
        FileInputStream file = new FileInputStream("E:\\zygalAutomation\\zygal_connect_application\\src\\test\\resources\\data.xlsx");

        // Read and retrieve the cell value from the specified sheet, row, and column
        String value = WorkbookFactory.create(file).getSheet(sheetname).getRow(row).getCell(cell).getStringCellValue();

        return value;
    }
}
