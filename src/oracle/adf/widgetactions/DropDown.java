package oracle.adf.widgetactions;

import java.util.List;

import oracle.adf.widgets.WidgetInfo;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DropDown extends GUIWidget implements IGUIWidget {

	public DropDown(WidgetInfo widgetInfo) {
		super(widgetInfo);
	}
	
	@Override
	public void setDisplayValue(String value) {
		WebElement element = managerHelper.getWebElement(widgetInfo);
		if(!value.equalsIgnoreCase(element.getAttribute("value")))
		{
			String name = element.getAttribute("name");
			String base = element.getAttribute("name")+"::";
			String dropdown_Icon = base+"lovIconId";
			String searchLink = base+"dropdownPopup::popupsearch";
			String input_Text = base+"_afrLovInternalQueryId:value00::content";
//			String searchButton = base+"_afrLovInternalQueryId::search";
			String recordID = name+"_afrLovInternalTableId::db";
			String okButton = base+"lovDialogId::ok";
			String cancelButton = base+"lovDialogId::cancel";
			WebDriver driver = manager.getCurrentWebDriver();
			
			manager.waitForWidget(By.id(dropdown_Icon), 20, 1);
			driver.findElement(By.id(dropdown_Icon)).click();
			manager.waitForAjaxToLoad();
			manager.waitForWidget(By.id(searchLink), 20, 1);
			managerHelper.sleep(1);
			driver.findElement(By.id(searchLink)).click();
			manager.waitForAjaxToLoad();
			manager.waitForWidget(By.id(input_Text), 20, 1);
			driver.findElement(By.id(input_Text)).clear();
			managerHelper.sleep(1);
			driver.findElement(By.id(input_Text)).sendKeys(value+Keys.ENTER);
			managerHelper.sleep(1);
			/*driver.switchTo().activeElement().sendKeys(Keys.TAB);
			managerHelper.sleep(.5);
			manager.getJSExecutor().executeScript("arguments[0].click();", driver.findElement(By.id(searchButton)));
			managerHelper.sleep(1);*/
			manager.waitForAjaxToLoad();
			manager.waitForWidget(By.xpath("//div[@id='"+recordID+"']"), 20, 1);
			if(manager.widgetNotExists(new WidgetInfo("xpath=//div[text()='No rows to display']", GUIWidget.class), 50, 1))
			{
				List<WebElement> records = driver.findElements(By.xpath("//div[@id='"+recordID+"']/table/tbody/tr"));
				int noOfRecords = records.size();
				WebElement currentRecord;
				String currentRecordValue;
				for(int i=0; i<noOfRecords ;i++)
				{
					currentRecord = records.get(i);
					currentRecord.click();
					currentRecordValue = currentRecord.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(0).getText().trim();
					if(value.equals(currentRecordValue))
					{
						managerHelper.sleep(1);
						driver.findElement(By.id(okButton)).click();
						managerHelper.sleep(1);
						return;
					}
				}
				managerHelper.sleep(1);
				driver.findElement(By.id(cancelButton)).click();
				logger.info("Unable to select LOV value : "+value );
				managerHelper.sleep(1);
			}
			}
		
	}

	@Override
	public String getDisplayValue() {
		WebElement element = managerHelper.getWebElement(widgetInfo);
		return element.getAttribute("value").trim();
	}

}
