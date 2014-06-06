package com.gedaeusp.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AnaerobicLactic extends SeleniumTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testAnaerobicLactic() throws Exception {
    driver.get(baseUrl + "/physusp/");
    driver.findElement(By.id("parameters.calculateAnaerobicLactic")).click();
    driver.findElement(By.id("btnNext")).click();
    driver.findElement(By.id("anaerobicLacticParameters.maxLactateConcentration")).clear();
    driver.findElement(By.id("anaerobicLacticParameters.maxLactateConcentration")).sendKeys("4");
    driver.findElement(By.id("anaerobicLacticParameters.restLactateConcentration")).clear();
    driver.findElement(By.id("anaerobicLacticParameters.restLactateConcentration")).sendKeys("2");
    driver.findElement(By.id("anaerobicLacticParameters.weight")).clear();
    driver.findElement(By.id("anaerobicLacticParameters.weight")).sendKeys("70");
    driver.findElement(By.id("btnNext")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
