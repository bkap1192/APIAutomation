package com.hotelogix.ApiGeneric;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelUtils {

	
	
	
	
	
	private  HashMap<String, HashMap<String, String>> TTDMap;
	private  HashMap<String, String> TDMap;
	private static ExcelUtils EU=null;
	
	private ExcelUtils(){
		
	}
	
	public static ExcelUtils UI(){
		if(EU==null){
			EU=new ExcelUtils();
		}
		return EU;
	}

	
	
	public  HashMap<String,HashMap<String,String>> getTestCaseDataMap(String filePath, String sheetName) throws IOException{
		 Workbook wBook=getWorkBook(filePath);
		 TDMap=new HashMap<String, String>();
		 TTDMap=new HashMap<String, HashMap<String, String>>();
		 Sheet sheetObj=wBook.getSheet(sheetName);
		 int rowcount=sheetObj.getLastRowNum();
		    for(int j=1; j<=rowcount-1; j++){
		    	Row reqRowObj=sheetObj.getRow(j);
		    	String tcname=reqRowObj.getCell(0).getStringCellValue();
		    int cellCount=reqRowObj.getLastCellNum()-1;
		    for(int i=2; i<=cellCount-1; i+=2){
		    	Cell cellObj=reqRowObj.getCell(i, Row.CREATE_NULL_AS_BLANK);
		    	String dataKey=cellObj.getStringCellValue();
		    	cellObj=reqRowObj.getCell(i+1, Row.CREATE_NULL_AS_BLANK);
		    	String dataValue=cellObj.getStringCellValue();
		    	TDMap.put(dataKey, dataValue);
		    }
		    TTDMap.put(tcname, TDMap);
		    }
		    return  TTDMap;
	        }
	
	public  HashMap<String, String> getTestCaseDataMap(String filePath, String sheetName, String TCID) throws IOException{
		 Workbook wBook=getWorkBook(filePath);
		 TDMap=new HashMap<String, String>();
		 Sheet sheetObj=wBook.getSheet(sheetName);
		 int rowNumber=getTDRowNumber(sheetObj, TCID);
		    Row reqRowObj=sheetObj.getRow(rowNumber);
		    int cellCount=reqRowObj.getLastCellNum()-1;
		    for(int i=1; i<=cellCount; i+=2){
		    	Cell cellObj=reqRowObj.getCell(i, Row.CREATE_NULL_AS_BLANK);
		    	String dataKey=cellObj.getStringCellValue();
		    	cellObj=reqRowObj.getCell(i+1, Row.CREATE_NULL_AS_BLANK);
		    	String dataValue=cellObj.getStringCellValue();
		    	TDMap.put(dataKey, dataValue);
		    }
			return TDMap;
	        }

	
	private Workbook getWorkBook(String filePath) throws IOException{	
		Workbook wBook=null;
		FileInputStream fis=new FileInputStream(filePath);
		if(filePath.endsWith("xlsx")){
			wBook=new XSSFWorkbook(fis);
		}else{
			wBook=new HSSFWorkbook(fis);
		}
		return wBook;
	}
	
	
	private int getTDRowNumber(Sheet sheetObj, String TestCaseID){
		int rowCount=sheetObj.getLastRowNum();
		int dataRowNumber=0;
		for(int i=0; i<=rowCount; i++){
			Row rowObj=sheetObj.getRow(i);
			Cell cellObj=rowObj.getCell(0);
			String TCID_cellValue=cellObj.getStringCellValue();
			if(TestCaseID.equalsIgnoreCase(TCID_cellValue)){
				dataRowNumber=i;
				break;
			}
	        }
		     return dataRowNumber;
	        }
	
	
	
	
}
