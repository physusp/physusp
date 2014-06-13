package com.gedaeusp.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Sel6 {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	    driver = new FirefoxDriver();
	    Dimension resolution = new Dimension(1280,1024);
	    driver.manage().window().setSize(resolution);
	    baseUrl = Defines.geDomain();
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  //@Test
  public void testE8() throws Exception {
    driver.get(baseUrl + "/physusp/#options");
    driver.findElement(By.id("parameters.calculateAnaerobicLactic")).click();
    driver.findElement(By.id("btnNext")).click();
    driver.findElement(By.id("anaerobicLacticParameters.maxLactateConcentration")).clear();
    driver.findElement(By.id("anaerobicLacticParameters.maxLactateConcentration")).sendKeys("4.5");
    driver.findElement(By.id("anaerobicLacticParameters.restLactateConcentration")).clear();
    driver.findElement(By.id("anaerobicLacticParameters.restLactateConcentration")).sendKeys("0.62");
    driver.findElement(By.id("anaerobicLacticParameters.weight")).clear();
    driver.findElement(By.id("anaerobicLacticParameters.weight")).sendKeys("66.3");
    driver.findElement(By.id("btnNext")).click();
    driver.findElement(By.id("btnPrevious")).click();
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
