package com.gedaeusp.selenium;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class SeleniumTest {

	static public void tableInput(WebDriver driver, String table, String data) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(data), null);
	    Actions builder = new Actions(driver);
	    builder.moveToElement(driver.findElement(By.id(table)), 0, 50).click().sendKeys(Keys.chord(Keys.LEFT_CONTROL,"v"));
	    builder.perform();
	}


  }
