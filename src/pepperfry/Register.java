package pepperfry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.concurrent.TimeUnit;

public class Register {
	
  static WebDriver driver=null;	
	
  public static void registerUser(String username, String mobile, String email, String password) {
	 
	  System.setProperty("webdriver.chrome.driver", "D:\\softwares\\chromedriver_win32\\chromedriver.exe");
	      
	  driver=new ChromeDriver(); 
	  driver.manage().window().maximize(); //maximize the window
	  driver.navigate().to("https://www.pepperfry.com/");
      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  
	  WebElement firstname = driver.findElement(By.name("firstname"));
	  firstname.click();
	  firstname.sendKeys(username);
	  
	  WebElement mobno = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/div[2]/div[1]/input"));
	  mobno.click();
	  mobno.sendKeys(mobile);
	  
	  WebElement emailid = driver.findElement(By.name("email"));
	  emailid.click();
	  emailid.sendKeys(email);
	  
	  WebElement passw = driver.findElement(By.name("password1"));
	  passw.click();
	  passw.sendKeys(password);
	  
	  WebElement submit = driver.findElement(By.xpath("//*[@id=\"formSubmit-popup_reg_form\"]"));
	  submit.click();
  }
  
  public static void main(String args[]) throws IOException {
		
		try {
			FileInputStream file= new FileInputStream("C:\\Users\\dell\\OneDrive\\Documents\\RegisterDetails.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook(file);
			XSSFSheet sheet=wb.getSheet("Sheet1");
			
			String firstname,password,mobno,email;
			
			for(int i=1;i<=sheet.getLastRowNum();i++) {
				XSSFRow row=sheet.getRow(i);
				
				firstname=row.getCell(0).getStringCellValue();
				
				if(row.getCell(1).getCellType() == CellType.NUMERIC) {
					mobno=row.getCell(1).getRawValue().toString();
				}
				else {mobno=row.getCell(1).toString();}
				  
				email=row.getCell(2).getStringCellValue();
				password=row.getCell(3).getStringCellValue();
				registerUser(firstname,mobno,email,password);
			}
			
		} catch (FileNotFoundException e) {e.printStackTrace();}
	}
}
