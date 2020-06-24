package com.vi.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class HomeTest extends AbstractTest {

  @Test
//  №1
//  https://www.vseinstrumenti.ru/
//  Автотест взятия заголовка страницы (то, что в заголовке вкладки/окна)
  public void verifyTitle() {
    Assert.assertEquals(page.getTitle(), "Интернет-магазин ВсеИнструменты.ру - электроинструмент и оборудование: климатическое, садовое, клининговое, автогаражное");
  }

  @Test
//  №2
//  https://www.vseinstrumenti.ru/
//  Автотест нажатия на ссылку вызова формы выбора города
  public void selectCityOpen() throws InterruptedException, IllegalMonitorStateException {
    driver.findElement(By.xpath("//div[@class = 'region']//a")).click();
    Thread.sleep(2000);
    Assert.assertTrue(driver.findElement(By.xpath("//div[@rel='showChooseRepr']")).isDisplayed());
  }

  @Test
//  №3
//  https://www.vseinstrumenti.ru/stanki/sverlilnye/stalex/sverlilnyj-stanok-stalex-sdi-25t-industrial-
//  z4125b1/ (или любой другой товар из «распродажи»)
//  Написать Автотест нажатия кнопки "В корзину" - по распродажной цене
  public void itemSaleAddCart() throws InterruptedException, IllegalMonitorStateException {
    page.navigate("https://www.vseinstrumenti.ru/silovaya-tehnika/svarochnoe-oborudovanie/mma/svarochnyj-apparat-invertor/kalibr/svarochnyj-invertor-mini-kalibr-svi-180ap-61901/");
    driver.findElement(By.cssSelector(".action-block a.addItemToBasket")).click();
    Thread.sleep(4000);
    Assert.assertTrue(driver.findElement(By.cssSelector("div.-cart-notify-window")).isDisplayed());
  }

  @Test
//  №4
//  https://www.vseinstrumenti.ru/
//  В хедере вызвать форму выбора города. Написать автотест для выбора случайного региона, в
//  котором возможна курьерская доставка (присутствует иконка грузовика) и который не выделен
//  полужирным шрифтом (не Москва, не Санкт-Петербург, ..., не Казань)
  public void selectCityDelivery() throws InterruptedException, IllegalMonitorStateException {
    driver.findElement(By.xpath("//div[@class = 'region']//a")).click();
    Thread.sleep(2000);
    List<WebElement> regionsList = driver.findElements(By.xpath("//div[@class = ' deliveryCourierConteiner ']//a[not(contains(@class, 'fs-14'))]"));
    int countRegions = regionsList.size();
    Random random = new Random();
    int randomCity = random.nextInt(countRegions);
    regionsList.get(randomCity).click();
    Thread.sleep(2000);
  }

  @Test
//  №5
//  https://www.vseinstrumenti.ru/instrument/gravery/pryamoshlifovalnye-mashiny/
//  Написать автотест нажатия кнопки "В корзину" для случайного товара с признаком "Лучшая цена"
  public void itemBestPriceAddCart() throws InterruptedException, IllegalMonitorStateException {
    page.navigate("https://www.vseinstrumenti.ru/instrument/gravery/pryamoshlifovalnye-mashiny/");
    driver.findElement(By.xpath("//span[.='Лучшая цена']/ancestor::div[contains(@class, 'product')]/div[@class='tile-content']/div[contains(@class, 'buttons')]/a")).click();
    Thread.sleep(2000);
  }

  @Test
//  №6
//  https://www.vseinstrumenti.ru/contacts/1.html
//  Для страницы "Контакты" написать автотест, который узнаёт телефон отдела подбора персонала
  public void getHrPhone() throws InterruptedException, IllegalMonitorStateException {
    page.navigate("https://www.vseinstrumenti.ru/contacts/1.html");
    WebElement phoneEl = driver.findElement(By.xpath("//td[@class='contact_info_table_name'][.=' Отдел подбора персонала ']/../td[@class='contact_info_table_telefon']"));
    String phone = phoneEl.getText();
    System.out.println("телефон отдела подбора персонала = " + phone);
    Assert.assertEquals(phone, "+7 (495) 647-60-00");
    Thread.sleep(2000);
  }

  @Test
//  https://www.vseinstrumenti.ru/
//  Добавление в корзину и проверка цены
  public void testCart() throws InterruptedException {
    Thread.sleep(5000);
    List<WebElement> itemsList = driver.findElements(By.xpath("//div[@data-algorithm='popular']//li[contains(@class, 'retailrocket-item')][@aria-hidden='false']"));
    int countRegions = itemsList.size();
    Random random = new Random();
    int randomItem = random.nextInt(countRegions);
    WebElement item = itemsList.get(randomItem);
    //add to cart
    item.findElement(By.cssSelector(".retailrocket-actions-buy")).click();
    Thread.sleep(2000);
    //get price
    String itemPrice = driver.findElement(By.cssSelector(".-cart-notify-window .cart-sum")).getText();
    driver.findElement(By.cssSelector(".go-to-cart")).click();
    Thread.sleep(2000);
    String totalPrice = driver.findElement(By.cssSelector("aside.cart-total strong[data-behaviour='total-price']")).getText();
    //cur Rubles signs
    totalPrice = totalPrice.substring(0, totalPrice.length() - 3);

    Assert.assertEquals(itemPrice, totalPrice);
  }

  @Test
//  https://www.vseinstrumenti.ru/
//  Проверим что выбирается город
  public void selectCity() throws InterruptedException {
    String city = "Москва";

    driver.findElement(By.xpath("//div[@class = 'region']//a")).click();
    Thread.sleep(2000);

    //В идеале рандомизировать выбор города (и магазина)
    driver.findElement(By.xpath("//div[@rel='showChooseRepr']//*[contains(text(), '" + city + "')]")).click();
    Thread.sleep(2000);
    Assert.assertEquals(driver.findElement(By.cssSelector(".region a span")).getText(), city);
  }
}
