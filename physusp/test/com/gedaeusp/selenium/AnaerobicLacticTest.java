package com.gedaeusp.selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AnaerobicLacticTest extends SeleniumTest {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:9090";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testAnaerobicLactic() throws Exception {
    driver.get(baseUrl + "/Physusp/3NHytBzdfBqSxD2xhnYD9L4evaR4DF");
    driver.findElement(By.id("parameters.calculateAnaerobicLactic")).click();
    driver.findElement(By.id("btnNext")).click();
    driver.findElement(By.id("anaerobicLacticParameters.peakLactateConcentration")).clear();
    driver.findElement(By.id("anaerobicLacticParameters.peakLactateConcentration")).sendKeys("4");
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
}
