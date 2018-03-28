package oracle.adf.widgetactions;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import oracle.adf.core.Verify;
import oracle.adf.widgets.WidgetInfo;

public class Table extends GUIWidget implements IGUIWidget {

	public Table(WidgetInfo widgetInfo) {
		super(widgetInfo);
	}

	@Override
	public void setDisplayValue(String value) {
		
	}

	@Override
	public String getDisplayValue() {
		return null;
	}
	
	public int getRecordsCount()
	{
		manager.waitForWidget(widgetInfo, 60, 2);
		String parentID = managerHelper.getWebElement(widgetInfo).getAttribute("id");
		WebElement childRecords = manager.getCurrentWebDriver().findElement(By.id(parentID+"::db"));
		return childRecords.findElements(By.xpath("table/tbody/tr")).size();
	}
	
	public void selectRow(String column, String value)
	{
		WebDriver driver = manager.getCurrentWebDriver();
		String parentID = managerHelper.getWebElement(widgetInfo).getAttribute("id");
		WebElement header = driver.findElement(By.id(parentID+"::ch"));
		WebElement childRecords = driver.findElement(By.id(parentID+"::db"));
		
		List<String> headers = new ArrayList<String>();
		for(WebElement ele : header.findElements(By.xpath("descendant::th[@class='af_column_column-header-cell']")))
			headers.add(ele.getText().trim());
		
		String currentRecordValue;
		
		for(WebElement record : childRecords.findElements(By.xpath("table/tbody/tr")))
		{
			currentRecordValue = record.findElements(By.xpath("descendant::td[contains(@class,'af_column_data-cell')]")).get(headers.indexOf(column)).getText().trim();
			if(currentRecordValue.equals(value) || currentRecordValue.matches(value+"([\\s][(][1][)]){0,1}"))
			{
				record.click();
				managerHelper.sleep(1);
				Verify.verifyEquals("Selected Record matching with criteria : "+column+" = "+value, true);
				return;
			}
		}
		Verify.verifyEquals("No Record found matching with criteria : "+column+" = "+value, false);
		
	}
	
	public WebElement returnRow(String column, String value)
	{
		WebDriver driver = manager.getCurrentWebDriver();
		String parentID = managerHelper.getWebElement(widgetInfo).getAttribute("id");
		WebElement header = driver.findElement(By.id(parentID+"::ch"));
		WebElement childRecords = driver.findElement(By.id(parentID+"::db"));
		
		List<String> headers = new ArrayList<String>();
		for(WebElement ele : header.findElements(By.xpath("descendant::th[@class='af_column_column-header-cell']")))
			headers.add(ele.getText().trim());
		String currentRecordValue;
		for(WebElement record : childRecords.findElements(By.xpath("table/tbody/tr")))
		{
			currentRecordValue = record.findElements(By.xpath("descendant::td[contains(@class,'af_column_data-cell')]")).get(headers.indexOf(column)).getText().trim();
			if(currentRecordValue.equals(value) || currentRecordValue.matches(value+"([\\s][(][1][)]){0,1}"))
			{
//				Verify.verifyEquals("Returned Record matching with criteria : "+column+" = "+value, true);
				return record;
			}
		}
		Verify.verifyEquals("No Record found matching with criteria : "+column+" = "+value, false);
		return null;
		
	}
	
	public boolean recordExists(String column, String value)
	{
		WebDriver driver = manager.getCurrentWebDriver();
		String parentID = managerHelper.getWebElement(widgetInfo).getAttribute("id");
		WebElement header = driver.findElement(By.id(parentID+"::ch"));
		WebElement childRecords = driver.findElement(By.id(parentID+"::db"));
		
		List<String> headers = new ArrayList<String>();
		for(WebElement ele : header.findElements(By.xpath("descendant::th[@class='af_column_column-header-cell']")))
			headers.add(ele.getText().trim());
		String currentRecordValue;
		for(WebElement record : childRecords.findElements(By.xpath("table/tbody/tr")))
		{
			currentRecordValue = record.findElements(By.xpath("descendant::td[contains(@class,'af_column_data-cell')]")).get(headers.indexOf(column)).getText().trim();
			if(currentRecordValue.equals(value) || currentRecordValue.matches(value+"([\\s][(][1][)]){0,1}"))
			{
//				Verify.verifyEquals("Selected Record matching with criteria : "+column+" = "+value, true);
				return true;
			}
		}
//		Verify.verifyEquals("No Record found matching with criteria : "+column+" = "+value, false);
		return false;
		
	}
	
	public WebElement getCell(String cell, String column, String value)
	{
		WebDriver driver = manager.getCurrentWebDriver();
		String parentID = managerHelper.getWebElement(widgetInfo).getAttribute("id");
		WebElement header = driver.findElement(By.id(parentID+"::ch"));
		WebElement childRecords = driver.findElement(By.id(parentID+"::db"));
		
		List<String> headers = new ArrayList<String>();
		for(WebElement ele : header.findElements(By.xpath("descendant::th[@class='af_column_column-header-cell']")))
			headers.add(ele.getText().trim());
		String currentRecordValue;
		for(WebElement record : childRecords.findElements(By.xpath("table/tbody/tr")))
		{
			currentRecordValue = record.findElements(By.xpath("descendant::td[contains(@class,'af_column_data-cell')]")).get(headers.indexOf(column)).getText().trim();
			if(currentRecordValue.equals(value) || currentRecordValue.matches(value+"([\\s][(][1][)]){0,1}"))
			{
				return record.findElements(By.xpath("descendant::td[contains(@class,'af_column_data-cell')]")).get(headers.indexOf(cell));
			}
		}
		Verify.verifyEquals("No Record found matching with criteria : "+column+" = "+value, false);
		return null;
		
	}
	
	public void selectRow(int row)
	{
		int count = getRecordsCount();
		if(count>=row)
		{
			manager.waitForWidget(widgetInfo, 60, 2);
			String parentID = managerHelper.getWebElement(widgetInfo).getAttribute("id");
			WebElement childRecords = manager.getCurrentWebDriver().findElement(By.id(parentID+"::db"));
			childRecords.findElement(By.xpath("table/tbody/tr["+row+"]")).click();
			Verify.verifyEquals("Selected Record No : "+row, true);
		}
		else
			Verify.verifyEquals("Only "+count+" records are fetched, unable to select record No : "+row, false);
	}
	
	public WebElement returnRow(int row)
	{
		int count = getRecordsCount();
		if(count>=row)
		{
			manager.waitForWidget(widgetInfo, 60, 2);
			String parentID = managerHelper.getWebElement(widgetInfo).getAttribute("id");
			WebElement childRecords = manager.getCurrentWebDriver().findElement(By.id(parentID+"::db"));
//			Verify.verifyEquals("Returned Record No : "+row, true);
			return childRecords.findElement(By.xpath("table/tbody/tr["+row+"]"));
		}
		else
			Verify.verifyEquals("Only "+count+" records are fetched, unable to return record No : "+row, false);
		return null;
	}

}
