package pepperfry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import java.lang.reflect.Method;
import static org.testng.Assert.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;

public class Main {
  
  WebDriver driver=null;
  static ExtentTest test;
  static ExtentReports report;
  ArrayList<String> Tabs;
  WebElement buyNow=null,existingUser=null;
  
  @BeforeClass
	public void initializeReport() {
		report= new ExtentReports("D:\\softwares\\Selenium\\Reports\\extent2.html",true);
  }
  
  @BeforeTest
  public void launch() {
	    System.setProperty("webdriver.chrome.driver", "D:\\softwares\\chromedriver_win32\\chromedriver.exe");
		driver=new ChromeDriver(); 
		
		assertNotNull(driver);
		driver.manage().window().maximize(); //maximize the window
		driver.navigate().to("https://www.pepperfry.com/");  //navigate to amazon
  }
  
  @BeforeMethod
  public static void startReport(Method result) {
	  test = report.startTest("ExtentDemo - " + result.getName());
  }
	 
 @Test(dataProvider="loginTest",dataProviderClass=DataProviders.class,priority=1)
 public void login(String user, String pass) {
	 driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
     test.log(LogStatus.INFO, "Starting with login");
     
	 for(int retry=0;retry<5;retry++) {
		  try { existingUser = driver.findElement(By.xpath("//*[@id=\"regPopUp\"]/div/div[2]/div[2]/div[1]")); }
		  catch(StaleElementReferenceException e) { System.out.println(e.toString()); }
	 } 
	 if(existingUser.isDisplayed()) {
	        test.log(LogStatus.PASS, "Existing user  Button located");
	        existingUser.click();
	 }
	 else{test.log(LogStatus.FAIL, "Button could not be located");}    
	  
	  WebElement username = driver.findElement(By.xpath("//*[@id=\"signIn-form-username\"]/div[2]/div/input"));
	  
	  if(username != null) {
	        test.log(LogStatus.PASS, "Username textbox located");
	        username.click();
	  	    username.sendKeys(user);
	  }
	  else{test.log(LogStatus.FAIL, "Username textbox could not be located");}    
	  
	  WebElement password = driver.findElement(By.id("password"));
	  
	  if(password != null) {
	        test.log(LogStatus.PASS, "Password textbox located");
	        password.click();
	  	    password.sendKeys(pass);
	  }
	  else{test.log(LogStatus.FAIL, "Password textbox could not be located");}    
	  
      WebElement subbtn=null;
	  
	  for(int retry=0;retry<5;retry++) {
		  try { subbtn = driver.findElement(By.xpath("//*[@id=\"formSubmit-popup_login_username_form\"]"));}
		  catch(StaleElementReferenceException e) { System.out.println(e.toString()); }
	  }
	  if(subbtn != null) {
	        test.log(LogStatus.PASS, "Submit button located");
	        subbtn.click();
	      }
	  else{test.log(LogStatus.FAIL, "Submit button could not be located");}      
  }
 
 @Test(priority=2)
 public void validateBasics() throws InterruptedException{
	 
	  //driver.navigate().refresh();
	  
	  WebElement logo = driver.findElement(By.className("head-pepperfry-logo"));
	  
	  if(logo != null) { test.log(LogStatus.PASS, "Website logo located");}
	  else{test.log(LogStatus.FAIL, "Website logo could not be located");}  
	 
      WebElement trackOrder = driver.findElement(By.className("trackyourorderPopup"));
	  
	  if(trackOrder != null) { test.log(LogStatus.PASS, "Track button located");}
	  else{test.log(LogStatus.FAIL, "Track button could not be located");}  
	  
	  WebElement wishlist = driver.findElement(By.cssSelector("a.wishlist_bar"));
	  
	  if(wishlist != null) { test.log(LogStatus.PASS, "Wishlist button located");}
	  else{test.log(LogStatus.FAIL, "Wishlist button could not be located");}  
	  
	  WebElement cart = driver.findElement(By.id("mini-usercart-tab"));
	  
	  if(cart != null) { test.log(LogStatus.PASS, "Cart button located"); }
	  else{test.log(LogStatus.FAIL, "Cart button could not be located");} 	 
 }
  
  @Test(priority=3)
  public void search() {
	 
	  driver.navigate().refresh();
	  WebElement searchbar1=driver.findElement(By.xpath("//*[@id=\"search\"]"));
	 if(searchbar1 != null) {
	        test.log(LogStatus.PASS, "Search box located");
	        searchbar1.sendKeys("wardrobe");
	  	    searchbar1.submit();
	      }
	  else{test.log(LogStatus.FAIL, "Search box could not be located");} 
	 
	 WebElement product = driver.findElement(By.xpath("//*[@id=\"p_2_1_1655146\"]/div/div[3]/div/h2/a"));
	  
	  if(product != null) {
	        test.log(LogStatus.PASS, "Product located");
	        product.click();  
	      }
	  else{test.log(LogStatus.FAIL, "Product could not be located");}
  }
  
  @Test(priority=4,dependsOnMethods= {"search"})
  public void validateProductDetails() throws InterruptedException {
	  
	  Tabs =  new ArrayList<String>(driver.getWindowHandles());
	  driver.switchTo().window(Tabs.get(1));
      Thread.sleep(3000);
	 
      WebElement productName = driver.findElement(By.xpath("//*[@id=\"page\"]/div/div[2]/div/div/div[2]/div[1]/h1"));
	  
	  if(productName.isDisplayed()) { test.log(LogStatus.PASS, "Name of product located");}
	  else{test.log(LogStatus.FAIL, "Name of product was not located");}
      
	  WebElement price = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/div[2]/div/div[2]/div/div/div[2]/div[1]/div[3]/span[2]"));
	  
	  if(price.isDisplayed()) {test.log(LogStatus.PASS, "Price of product located");}
	  else{test.log(LogStatus.FAIL, "Price of product was not located");}  
	  
	  WebElement addToCart = driver.findElement(By.id("vipAddToCartButton"));
	  ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",addToCart);
	  
	  if(addToCart.isDisplayed()) {
	        test.log(LogStatus.PASS, "Add to cart button located");
	        addToCart.click();
	      }
	  else{test.log(LogStatus.FAIL, "Add to cart button was not located");}  
	
      WebElement addToWishlist = driver.findElement(By.xpath("//*[@id=\"gallery\"]/div[1]/div[3]/div[3]"));
	  
	  if(addToWishlist.isDisplayed()) {test.log(LogStatus.PASS, "Add to wishlist button located");}
	  else{test.log(LogStatus.FAIL, "Add to wishlist button was not located");}  
	  
      WebElement pinCodeBox = driver.findElement(By.name("pincode"));
	  
	  if(pinCodeBox.isDisplayed()) { test.log(LogStatus.PASS, "Pin code box located");}
	  else{test.log(LogStatus.FAIL, "Pin code box was not located");} 
	 
      WebElement buyNow = driver.findElement(By.id("vipBuyNowButton"));
      ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",buyNow);
    
	  if(buyNow != null) {
	        test.log(LogStatus.PASS, "Proceed button located");
	        buyNow.click();
	        Thread.sleep(3000);
	  }}
  
  @Test(priority=5,dependsOnMethods= {"validateProductDetails"})
  public void paymentGateway() {
	  
	  driver.navigate().refresh();
	  
	  WebElement deliveryDate=driver.findElement(By.xpath("//*[@id=\"cartitem_1655146\"]/div/div[2]/div[3]/span[1]"));
	  
	  if(deliveryDate.isDisplayed()) { test.log(LogStatus.PASS, "Delivery date displayed"); }
	  else{test.log(LogStatus.FAIL, "Delivery date not displayed");}
	  
      WebElement productPrice=driver.findElement(By.xpath("//*[@id=\"cartitem_1655146\"]/div/div[3]/div[4]/span[2]"));
	  
	  if(productPrice.isDisplayed()) { test.log(LogStatus.PASS, "Product price displayed"); }
	  else{test.log(LogStatus.FAIL, "Product price not displayed");}
	  
      WebElement totalPrice=driver.findElement(By.id("total_pay_coupon"));
	  
	  if(totalPrice.isDisplayed()) { test.log(LogStatus.PASS, "Total price of order displayed"); }
	  else{test.log(LogStatus.FAIL, "Total price of order not displayed");}
	  
	  WebElement couponCodeBox=driver.findElement(By.className("inputcoupon"));
	  
	  if(couponCodeBox.isDisplayed()) { test.log(LogStatus.PASS, "Coupon code can be entered"); }
	  else{test.log(LogStatus.FAIL, "Coupon code cannot be entered");}
	  
      WebElement growTreeCheckbox=driver.findElement(By.id("growTree"));
	  
	  if(growTreeCheckbox.isSelected()) { test.log(LogStatus.INFO, "Dontation for tree growing is added"); }
	  else{test.log(LogStatus.INFO, "Dontation for tree growing is not added");}
	  
	  WebElement placeOrderBtn=driver.findElement(By.className("ck-proceed-btn-wrap"));
	  
	  if(placeOrderBtn.isDisplayed()) { 
		  test.log(LogStatus.PASS, "Order can be placed");
		  placeOrderBtn.click();
	  }
	  else{test.log(LogStatus.FAIL, "Order cannot be placed");}
	  
  }
  
  @Test(priority=6)
  public void socialMediaWebsite() throws InterruptedException {
	  
	  WebElement facebook = driver.findElement(By.xpath("//*[@id=\"pre-footer\"]/div/footer/div[2]/div/div/div[3]/div/div/ul/li[1]/a"));
	  
	  if(facebook != null) {
	        test.log(LogStatus.PASS, "Facebook connectivity located");
	        facebook.click();
	        test.log(LogStatus.PASS, "Facebook page opened");
	        Thread.sleep(5000);
	      }
	  else{test.log(LogStatus.FAIL, "Facebook connectivity could not be located");}  
	  
	  WebElement twitter = driver.findElement(By.xpath("//*[@id=\"pre-footer\"]/div/footer/div[2]/div/div/div[3]/div/div/ul/li[3]/a"));
	  
	  if(twitter != null) {
	        test.log(LogStatus.PASS, "Twitter connectivity located");
	        twitter.click();
	        test.log(LogStatus.PASS, "Twitter page opened");
	        Thread.sleep(2000);
	      }
	  else{test.log(LogStatus.FAIL, "Twitter connectivity could not be located");}     
  }
  
  @AfterTest
  public void exit() {
	  driver.quit();  
  }
  
  @AfterClass
  public static void endTest()
  {
    report.endTest(test);
    report.flush();
  }
}

