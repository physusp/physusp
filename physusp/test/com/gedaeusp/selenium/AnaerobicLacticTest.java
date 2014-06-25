/*
Copyright 2014 António Miranda, Caio Valente, Igor Topcin, Jorge Melegati, Thales Paiva, Victor Santos

This file is part of PhysUSP.

PhysUSP is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

PhysUSP is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PhysUSP. If not, see <http://www.gnu.org/licenses/>.
*/

package com.gedaeusp.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AnaerobicLacticTest {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = Defines.getDomain();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testAnaerobicLactic() throws Exception {
    driver.get(baseUrl + "/physusp/esc");
    driver.findElement(By.id("parameters.calculateAerobic")).click();
    driver.findElement(By.id("parameters.calculateAnaerobicAlactic")).click();
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
