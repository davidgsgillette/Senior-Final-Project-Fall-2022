package edu.sru.group3.WebBasedEvaluations.excel;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edu.sru.group3.WebBasedEvaluations.excel.ExcelRead_group;
/*
 * This class provides the test suite for all methods in the Excel package
 * 
 * @author J Abbigail Rowe
 */

/*
 * This test is kind of obsolete. A new version of this test will be when i get implementation
 * tests working and it will actually test this method
 */

//https://spring.io/guides/gs/testing-web/

@Suite
@SuiteDisplayName("Excel Methods")
@IncludeClassNamePatterns(".*Tests")
public class ExcelTest {
	private static XSSFWorkbook wb;
	private static XSSFWorkbook wbTest;
	private static XSSFSheet sheet;
	private static XSSFSheet sheetTest;
	//for loading in test excel file
	@BeforeAll
	public static void newSheet() throws IOException {
		MockMultipartFile file = new MockMultipartFile("Multipart File", "thisFile.xlsx", 
				"application/x-xls", new ClassPathResource("test.xlsx").getInputStream());
		wb = new XSSFWorkbook(file.getInputStream());
		sheet = wb.getSheetAt(0);		
	}
	
	@Test
    public void loadFileTest() throws IOException {
		MockMultipartFile file = new MockMultipartFile("Multipart File", "thisOtherFile.xlsx", 
				"application/x-xls", new ClassPathResource("test.xlsx").getInputStream());
		wbTest = ExcelRead_group.loadFile(file);
		sheetTest = ExcelRead_group.loadFile(file).getSheetAt(0);
		assertNotNull(wbTest);
		assertNotNull(sheetTest);
		//Note: Does not work because each object is different, need to extract data and compare
    }
	
	@Test
    public void checkStringTest() {
		String expected = "1";
		XSSFCell cellLocation = sheet.getRow(0).getCell(0);
		String actual;
		actual = ExcelRead_group.checkStringType(cellLocation);
		
		assertEquals(expected, actual);
    }
	
	@Test
    public void checkIntTest() {
		int expected = 1;
		XSSFCell cellLocation = sheet.getRow(0).getCell(0);
		int actual;
		actual = ExcelRead_group.checkIntType(cellLocation);
		
		assertSame(expected, actual);
    }
	
	@Test
    public void checkLongTest() {
		long expected = 1;
		XSSFCell cellLocation = sheet.getRow(0).getCell(0);
		long actual;
		actual = ExcelRead_group.checkLongType(cellLocation);
		
		assertSame(expected, actual);

    }
	
}