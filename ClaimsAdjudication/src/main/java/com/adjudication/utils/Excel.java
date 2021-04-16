package com.adjudication.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
	FileInputStream fis;
	XSSFWorkbook wb;
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;

	/**
	 * Instantiates a excel.
	 *
	 * @param path the path
	 */
	public Excel(String path) {
		try {
			File src = new File(path);
			fis = new FileInputStream(src);
			wb = new XSSFWorkbook(fis);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Read data from the sheet
	 *
	 * @param sheetName the sheet name
	 * @param row       the row
	 * @param col       the col
	 * @return the string
	 */
	public String readData(String sheetName, int row, int col) {
		String data="";
		try {
			sheet = wb.getSheet(sheetName);
			//data=sheet.getRow(row).getCell(col).getStringCellValue();
			DataFormatter formatter = new DataFormatter();
			data = formatter.formatCellValue(sheet.getRow(row).getCell(col));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return data;
	}

	/**
	 * Gets the row count of sheet
	 *
	 * @param sheetName the sheet name
	 * @return the row count
	 */
	public int getRowCount(String sheetName) {
		sheet = wb.getSheet(sheetName);

		return sheet.getLastRowNum() + 1;
	}

	/**
	 * Gets the column count of sheet.
	 *
	 * @param sheetName the sheet name
	 * @return the columns count
	 */
	public int getColumnCount(String sheetName) {
		sheet = wb.getSheet(sheetName);
		XSSFRow row = sheet.getRow(0);
		return row.getLastCellNum();
	}

	/**
	 * Read data from the sheet.
	 *
	 * @param sheetName the sheet name
	 * @param colName   the col name
	 * @param rowNum    the row num
	 * @return the string
	 */
	public String readData(String sheetName, String colName, int rowNum) {
		try {
			int colNum = -1;
			sheet = wb.getSheet(sheetName);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().equals(colName)) {
					colNum = i;
				}
			}

			DataFormatter formatter = new DataFormatter();
			String val = formatter.formatCellValue(sheet.getRow(rowNum).getCell(colNum));

			/*
			 * row= sheet1.getRow(rowNum); cell= row.getCell(colNum);
			 * 
			 * return cell.getStringCellValue();
			 */
			return val;

		} catch (Exception e) {
			System.out.println("Error is:" + e.getMessage());
			e.printStackTrace();
			return "Some issue";

		}

	}

	/**
	 * Gets the row count based on column.
	 *
	 * @param sheetName the sheet name
	 * @param colName   the col name
	 * @return the row count based on column
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public int getRowCountBasedOnColumn(String sheetName, String colName) throws IOException {
		int colNum = -1;
		sheet = wb.getSheet(sheetName);
		row = sheet.getRow(0);
		int n = row.getLastCellNum();
		for (int i = 0; i < n; i++) {
			Cell cell = row.getCell(i);
			if (cell != null && row.getCell(i).getStringCellValue().equals(colName)) {
				colNum = i;
			}
		}
		DataFormatter df = new DataFormatter();
		Iterator rowIter = sheet.rowIterator();
		rowIter.next();
		int count = 0;
		while (rowIter.hasNext()) {
			String data = df.formatCellValue(((Row) rowIter.next()).getCell(colNum));
			if (data == null || data == "")
				break;
			count++;
		}
		return count;

	}
	
	/**
	 * Get Entire Data of Sheet
	 *
	 * @param sheetName the sheet name
	 * @return Object[][] having Map(ColName, Value) within it
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Object[][] readData(String sheetName) {
		int numOfcols = getColumnCount(sheetName);
		Map<String, String> map = null; 
		Object[][] obj = new Object[getRowCount(sheetName)-1][1];
		for (int i = 1; i<getRowCount(sheetName); i++) {
			map = new HashMap<String, String>();
			for (int j = 0; j<numOfcols; j++) {
				if(readData(sheetName,i, j).isEmpty()){
					map.put(readData(sheetName,0, j),"");	
				}
				else
				{
					map.put(readData(sheetName,0, j),readData(sheetName,i, j));
				}
				
			}
			obj[i-1][0] = map;
		}
		return obj;
	}
 
}
