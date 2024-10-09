package api.utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name = "Data")
    public Object[][] getAllData() throws IOException {
        String path = System.getProperty("user.dir") + "//testData//Userdata.xlsx";
        ExcelUtility excel = new ExcelUtility(path);

        return excel.getSheetData("Sheet1");
    }

    @DataProvider(name = "UserNames")
    public Object[] getUserNames() throws IOException {
        String path = System.getProperty("user.dir") + "//testData//Userdata.xlsx";
        ExcelUtility excel = new ExcelUtility(path);

        return excel.getColumnDataByHeader("Sheet1", "UserName");
    }
	
}
