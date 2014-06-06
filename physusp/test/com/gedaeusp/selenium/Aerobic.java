package com.gedaeusp.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Aerobic {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    Dimension resolution = new Dimension(1280,1024);
    driver.manage().window().setSize(resolution);
    baseUrl = "http://localhost:8080";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testAerobic() throws Exception {
    driver.get(baseUrl + "/physusp/");
    driver.findElement(By.id("parameters.calculateAerobic")).click();
    driver.findElement(By.id("btnNext")).click();
    driver.findElement(By.xpath("(//input[@name='restOxygenParameters.calculateMethod'])[2]")).click();
    driver.findElement(By.xpath("//div[@id='restOptions']/div[2]/label")).click();
    driver.findElement(By.id("restOxygenParameters.fixedValue")).clear();
    driver.findElement(By.id("restOxygenParameters.fixedValue")).sendKeys("50");
    driver.findElement(By.cssSelector("textarea.copyPaste")).clear();
    driver.findElement(By.cssSelector("textarea.copyPaste")).sendKeys("driver.get(baseUrl + \"physusp/\");");
    driver.findElement(By.cssSelector("textarea.copyPaste")).clear();
    
    SeleniumTest.tableInput(driver, "oxygenConsumptionDuringExercise", "00:00:06	896.0504942\n00:00:09	92.66286444\n00:00:13	389.7610792\n00:00:18	1005.87375\n00:00:25	205.7137612\n00:00:30	651.1437949\n00:00:37	447.2992843\n00:00:45	318.1190874\n00:00:51	349.0034122\n00:00:54	23.92124346\n00:00:58	438.0864893\n00:01:02	127.8976187\n00:01:10	335.9368661\n00:01:14	555.3510457\n00:01:20	714.1628899\n00:01:27	340.7419155\n00:01:30	844.5648184\n00:01:34	801.1754718\n00:01:40	612.8634366\n00:01:49	1047.610115\n00:01:53	870.8085807\n00:01:57	899.3041894\n00:02:01	760.4010867\n00:02:04	733.1766183\n00:02:07	540.4897804\n00:02:10	745.4143349\n00:02:16	412.1767871\n00:02:21	379.3860243\n00:02:26	575.9258612\n00:02:31	499.1622578\n00:02:37	561.9783346\n00:02:41	655.895562\n00:02:45	909.0739968\n00:02:49	501.420841\n00:02:56	371.3270269\n00:02:59	586.8032321\n00:03:03	611.9869979\n00:03:06	556.2443688\n00:03:10	512.8278677\n00:03:15	643.9503139\n00:03:20	848.5843018\n00:03:24	781.3855082\n00:03:28	575.1099301\n00:03:34	484.6750375\n00:03:38	622.4319222\n00:03:44	349.85326\n00:03:48	866.2329836\n00:03:53	392.8180866\n00:03:58	562.9796735\n00:04:02	737.7467394\n00:04:07	534.2929366");
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
