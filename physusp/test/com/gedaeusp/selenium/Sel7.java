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

public class Sel7 {
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
  public void testE9() throws Exception {
    driver.get(baseUrl + "/physusp/#options");
    driver.findElement(By.id("parameters.calculateAnaerobicAlactic")).click();
    driver.findElement(By.id("btnNext")).click();
    driver.findElement(By.cssSelector("textarea.copyPaste")).clear();
    driver.findElement(By.cssSelector("textarea.copyPaste")).sendKeys("00:28:51\n00:28:52\n00:28:54\n00:28:55\n00:28:56\n00:28:57\n00:28:58\n00:28:59\n00:29:00\n00:29:02\n00:29:03\n00:29:04\n00:29:05\n00:29:06\n00:29:07\n00:29:08\n00:29:09\n00:29:10\n00:29:12\n00:29:13\n00:29:15\n00:29:16\n00:29:17\n00:29:18\n00:29:19\n00:29:21\n00:29:22\n00:29:23\n00:29:24\n00:29:25\n00:29:26\n00:29:28\n00:29:29\n00:29:31\n00:29:33\n00:29:34\n00:29:35\n00:29:37\n00:29:38\n00:29:39\n00:29:41\n00:29:42\n00:29:43\n00:29:44\n00:29:46\n00:29:47\n00:29:48\n00:29:49\n00:29:51\n00:29:52\n00:29:53\n00:29:54\n00:29:55\n00:29:57\n00:29:58\n00:29:59\n00:30:01\n00:30:02\n00:30:03\n00:30:04\n00:30:06\n00:30:07\n00:30:09\n00:30:10\n00:30:12\n00:30:13\n00:30:14\n00:30:15\n00:30:17\n00:30:18\n00:30:19\n00:30:20\n00:30:22\n00:30:23\n00:30:24\n00:30:25\n00:30:26\n00:30:28\n00:30:29\n00:30:30\n00:30:32\n00:30:33\n00:30:34\n00:30:35\n00:30:37\n00:30:38\n00:30:40\n00:30:41\n00:30:43\n00:30:44\n00:30:45\n00:30:47\n00:30:49\n00:30:50\n00:30:52\n00:30:53\n00:30:55\n00:30:56\n00:30:58\n00:31:00\n00:31:02\n00:31:03\n00:31:05\n00:31:07\n00:31:08\n00:31:10\n00:31:11\n00:31:13\n00:31:14\n00:31:15\n00:31:17\n00:31:18\n00:31:20\n00:31:21\n00:31:22\n00:31:24\n00:31:26\n00:31:27\n00:31:29\n00:31:30\n00:31:32\n00:31:33\n00:31:35\n00:31:36\n00:31:38\n00:31:39\n00:31:41\n00:31:42\n00:31:44\n00:31:46\n00:31:47\n00:31:49\n00:31:51\n00:31:53\n00:31:54\n00:31:56\n00:31:58\n00:31:59\n00:32:01\n00:32:03\n00:32:04\n00:32:06\n00:32:07\n00:32:09\n00:32:11\n00:32:12\n00:32:14\n00:32:16\n00:32:17\n00:32:20\n00:32:21\n00:32:23\n00:32:26\n00:32:28\n00:32:29\n00:32:31\n00:32:32\n00:32:34\n00:32:36\n00:32:37\n00:32:38\n00:32:40\n00:32:42\n00:32:44\n00:32:46\n00:32:48\n00:32:49\n00:32:51\n00:32:53\n00:32:55\n00:32:56\n00:32:57\n00:33:00\n00:33:02\n00:33:03\n00:33:07\n00:33:08\n00:33:10\n00:33:12\n00:33:13\n00:33:14\n00:33:16\n00:33:18\n00:33:19\n00:33:21\n00:33:22\n00:33:24\n00:33:26\n00:33:27\n00:33:29\n00:33:30\n00:33:32\n00:33:34\n00:33:36\n00:33:38\n00:33:39\n00:33:41\n00:33:43\n00:33:45\n00:33:47\n00:33:48\n00:33:50\n00:33:52\n00:33:54\n00:33:56\n00:33:57\n00:33:59\n00:34:01\n00:34:03\n00:34:04\n00:34:06\n00:34:08\n00:34:09\n00:34:11\n00:34:13\n00:34:14\n00:34:16\n00:34:18\n00:34:21\n00:34:23\n00:34:25\n00:34:27\n00:34:28\n00:34:30\n00:34:32\n00:34:34\n00:34:36\n00:34:39\n00:34:41\n00:34:43\n00:34:45\n00:34:48\n00:34:50\n00:34:51\n00:34:53\n00:34:56\n00:34:57\n00:34:59\n00:35:01\n00:35:03\n00:35:04\n00:35:06\n00:35:08\n00:35:10\n00:35:12\n00:35:14\n00:35:16\n00:35:18\n00:35:20\n00:35:22\n00:35:25\n00:35:27\n00:35:29\n00:35:31\n00:35:33\n00:35:34\n00:35:36\n00:35:38\n00:35:40\n00:35:42\n00:35:44\n00:35:46\n00:35:48\n00:35:51\n00:35:53\n00:35:55\n00:35:58\n00:36:00\n00:36:02\n00:36:04\n00:36:06\n00:36:09\n00:36:11\n00:36:13");
    driver.findElement(By.cssSelector("textarea.copyPaste")).clear();
    driver.findElement(By.cssSelector("textarea.copyPaste")).sendKeys("4108\n3238\n2243\n3275\n3081\n3075\n2949\n2938\n3078\n2889\n2926\n2981\n2764\n2866\n2595\n2630\n2726\n2824\n1600\n2640\n2333\n2045\n2082\n1993\n2273\n1994\n1907\n2034\n1964\n1965\n1849\n1683\n1121\n1337\n1169\n2084\n1774\n1604\n1131\n1433\n1286\n1106\n1276\n1043\n1220\n1115\n1186\n1178\n1121\n1114\n1107\n1101\n1146\n1223\n1085\n1108\n1174\n1127\n1131\n1055\n1047\n940\n756\n1190\n897\n1010\n1005\n956\n938\n941\n897\n915\n872\n985\n915\n873\n807\n808\n773\n791\n813\n828\n763\n741\n880\n735\n719\n850\n843\n835\n750\n688\n550\n653\n671\n812\n776\n760\n468\n486\n854\n850\n800\n821\n674\n728\n749\n717\n789\n595\n744\n731\n652\n666\n647\n598\n776\n650\n783\n637\n694\n1281\n978\n719\n682\n740\n639\n745\n633\n679\n512\n623\n568\n621\n836\n567\n533\n508\n784\n598\n627\n461\n855\n715\n472\n555\n604\n695\n641\n897\n864\n476\n482\n493\n712\n1093\n835\n638\n569\n298\n565\n671\n361\n874\n629\n732\n841\n717\n463\n532\n623\n368\n647\n580\n449\n521\n933\n896\n730\n649\n545\n605\n485\n923\n732\n800\n645\n645\n914\n724\n637\n729\n1007\n574\n426\n480\n579\n624\n525\n521\n515\n622\n643\n558\n589\n614\n646\n613\n609\n662\n636\n633\n636\n585\n716\n729\n719\n854\n492\n459\n668\n615\n961\n734\n768\n592\n498\n320\n552\n506\n567\n387\n567\n485\n447\n566\n549\n372\n509\n530\n448\n517\n557\n471\n586\n582\n589\n680\n498\n460\n467\n428\n377\n550\n307\n402\n511\n455\n512\n510\n487\n514\n483\n371\n666\n548\n441\n627\n615\n414\n536\n382\n516\n531");
    driver.findElement(By.cssSelector("textarea.copyPaste")).clear();
    driver.findElement(By.cssSelector("textarea.copyPaste")).sendKeys("375\n347\n504\n403\n302\n342\n338\n371\n389\n354\n351\n370\n330\n276\n389\n330\n255\n352\n346\n344\n380\n353\n331\n355\n321\n291\n349\n307\n322\n322\n295\n331\n396\n362\n319\n373\n415\n392\n432\n420\n381\n405\n386\n359\n338\n296\n425\n380\n370\n309\n289\n335\n360\n253\n472\n486\n532\n527\n462\n507\n449\n461\n516\n444\n425\n400\n388\n374\n383\n369\n368\n459\n382\n371\n390\n390\n362\n369\n356\n252\n359\n356\n276\n222\n350\n359\n261\n322\n371\n427\n632\n615\n450\n567\n504\n515\n232\n239\n182\n303\n225\n80\n228\n190\n248\n266\n219\n322\n271\n414\n375\n496\n208\n420\n296\n320\n530\n337\n332\n362\n346\n520\n440\n343\n300\n253\n360\n339\n320\n272\n259\n349\n392\n504\n498\n374\n389\n320\n316\n302\n242\n434\n598\n459\n372\n339\n424\n322\n365\n391\n330\n377\n324\n307\n346\n317\n325\n300\n273\n258\n252\n366\n607\n496\n433\n364\n287\n384\n331\n300\n303\n309\n372\n485\n338\n273\n372\n395\n328\n330\n408\n418\n412\n373\n358\n518\n363");
    driver.findElement(By.cssSelector("textarea.copyPaste")).clear();
    driver.findElement(By.cssSelector("textarea.copyPaste")).sendKeys("00:00:17\n00:00:20\n00:00:25\n00:00:28\n00:00:31\n00:00:35\n00:00:38\n00:00:41\n00:00:44\n00:00:48\n00:00:51\n00:00:54\n00:00:57\n00:01:00\n00:01:04\n00:01:07\n00:01:10\n00:01:13\n00:01:17\n00:01:20\n00:01:23\n00:01:26\n00:01:29\n00:01:33\n00:01:37\n00:01:41\n00:01:45\n00:01:48\n00:01:52\n00:01:55\n00:01:59\n00:02:02\n00:02:05\n00:02:09\n00:02:12\n00:02:15\n00:02:18\n00:02:21\n00:02:24\n00:02:27\n00:02:30\n00:02:32\n00:02:35\n00:02:39\n00:02:42\n00:02:46\n00:02:49\n00:02:52\n00:02:55\n00:02:58\n00:03:02\n00:03:05\n00:03:08\n00:03:13\n00:03:16\n00:03:19\n00:03:22\n00:03:24\n00:03:27\n00:03:30\n00:03:33\n00:03:36\n00:03:39\n00:03:42\n00:03:45\n00:03:49\n00:03:53\n00:03:57\n00:04:00\n00:04:03\n00:04:06\n00:04:09\n00:04:12\n00:04:15\n00:04:18\n00:04:21\n00:04:24\n00:04:27\n00:04:30\n00:04:33\n00:04:37\n00:04:40\n00:04:43\n00:04:47\n00:04:51\n00:04:54\n00:04:58\n00:05:01\n00:05:04\n00:05:07\n00:05:10\n00:05:13\n00:05:15\n00:05:18\n00:05:21\n00:05:26\n00:05:30\n00:05:33\n00:05:39\n00:05:53\n00:06:03\n00:06:09\n00:06:13\n00:06:17\n00:06:20\n00:06:24\n00:06:28\n00:06:32\n00:06:36\n00:06:39\n00:06:43\n00:06:46\n00:06:49\n00:06:53\n00:06:56\n00:06:59\n00:07:02\n00:07:06\n00:07:09\n00:07:11\n00:07:14\n00:07:17\n00:07:20\n00:07:23\n00:07:26\n00:07:30\n00:07:34\n00:07:38\n00:07:41\n00:07:44\n00:07:48\n00:07:52\n00:07:55\n00:07:58\n00:08:01\n00:08:04\n00:08:07\n00:08:10\n00:08:13\n00:08:17\n00:08:20\n00:08:23\n00:08:26\n00:08:29\n00:08:33\n00:08:36\n00:08:39\n00:08:43\n00:08:47\n00:08:50\n00:08:54\n00:08:57\n00:09:01\n00:09:04\n00:09:08\n00:09:11\n00:09:14\n00:09:18\n00:09:21\n00:09:25\n00:09:28\n00:09:31\n00:09:34\n00:09:37\n00:09:40\n00:09:44\n00:09:47\n00:09:51\n00:09:54\n00:09:58\n00:10:01\n00:10:04\n00:10:07\n00:10:10\n00:10:14\n00:10:17\n00:10:21\n00:10:24\n00:10:27\n00:10:30\n00:10:33\n00:10:36\n00:10:39\n00:10:43\n00:10:46\n00:10:50\n00:10:53");
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
