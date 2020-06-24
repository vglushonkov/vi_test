package com.vi.tests;


import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import com.vi.pages.Page;

public abstract class  AbstractTest {
  
  protected static Page page;
  protected static ChromeDriver driver;

  @BeforeMethod
  public void setup() {
    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") +"/drivers/chromedriver");
    driver = new ChromeDriver();

    String url = "https://www.vseinstrumenti.ru";
    page = new Page(driver);
    page.navigate(url);
  }

  @AfterMethod
  public void afterTest() {
    page.tearDown();
  }
}
