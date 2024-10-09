package api.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {

    private Workbook workbook;

    // Constructor to open Excel file
    public ExcelUtility(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        workbook = WorkbookFactory.create(fileInputStream);
    }

    // Method to get all data from Excel sheet (excluding header row)
    public Object[][] getSheetData(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getLastCellNum(); // Number of columns based on the header row

        // Create 2D array to hold data (excluding the header row)
        Object[][] data = new Object[rowCount - 1][colCount];

        // Iterate through rows and cells (skipping header row)
        for (int i = 1; i < rowCount; i++) {  // Start from row 1 (row 0 is the header)
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);
                data[i - 1][j] = getCellValue(cell);
            }
        }
        return data;
    }

    // Method to return all values under a particular header
    public Object[] getColumnDataByHeader(String sheetName, String columnHeader) {
        Sheet sheet = workbook.getSheet(sheetName);
        Row headerRow = sheet.getRow(0);  // Get the header row
        int colIndex = -1;

        // Find the index of the column with the specified header
        for (Cell cell : headerRow) {
            if (cell.getStringCellValue().equalsIgnoreCase(columnHeader)) {
                colIndex = cell.getColumnIndex();
                break;
            }
        }

        // If column not found, return an empty array
        if (colIndex == -1) {
            return new Object[0];
        }

        // Collect all the values under that column
        List<Object> columnData = new ArrayList<>();
        int rowCount = sheet.getPhysicalNumberOfRows();

        // Start from row 1 to skip the header
        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(colIndex);
                columnData.add(getCellValue(cell));
            }
        }

        return columnData.toArray();
    }

    // Helper method to get the value of a cell as an Object
    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return null;
        }
    }

    // Close the workbook to free resources
    public void close() throws IOException {
        workbook.close();
    }
}
