package seleniumpack;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.NoSuchElementException;
import static org.testng.Assert.assertTrue;

@SuppressWarnings("unused")
public class NegativeSignUp {

	public static void main (String[] args) throws InterruptedException, Throwable {
		
		File excelFile = new File("C:\\Users\\Asus\\eclipse-workspace\\seleniumjava\\src\\seleniumpack\\Login Details.xlsx");
		System.out.println("Current working directory: " + System.getProperty("user.dir"));
		FileInputStream fis = new FileInputStream(excelFile);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("-SignUp");
		int noOfRows = sheet.getLastRowNum();
		System.out.println(noOfRows);
		
		SoftAssert softAssert = new SoftAssert();
        System.setProperty("webdriver.chrome.driver", "D:\\BrowserDrivers\\chromedriver-win64\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        boolean isHidden;
        
		for(int i=1; i<=noOfRows; i++) {
			Thread.sleep(3000);
			driver.get("https://xaltsocnportal.web.app/");
	        driver.manage().window().maximize();
	        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[h1='Open Capital Network']")));
	        driver.findElement(By.xpath("//div[@class='cta-container']/button[text()='Get Started']")).click();
	        String title = driver.getTitle();
	        Assert.assertEquals(title,"OCN Portal");
	        WebElement legendElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@id='outlined-basic-helper-text'][text()='Password must contain atelast one lowercase letter, uppercase letter, number and special character and be a minimum of 8 characters in length']")));			
	    
				var Email = sheet.getRow(i).getCell(0).getStringCellValue();
				var Pwd = sheet.getRow(i).getCell(1).getStringCellValue();
				var cnfmPwd = sheet.getRow(i).getCell(2).getStringCellValue();
				var ErrMsg = sheet.getRow(i).getCell(3).getStringCellValue();
			       
			       String actualErrorMessage = "";
			       try {
			    	   driver.findElement(By.xpath("//main/div[2]/div[1]//div[1]/input")).sendKeys(Email);
				       driver.findElement(By.xpath("//main/div[2]/div[2]//div[1]/input")).sendKeys(Pwd);
				       driver.findElement(By.xpath("//main/div[2]/div[3]//div[1]/input")).sendKeys(cnfmPwd);
				       var SignUpStatus = driver.findElement(By.xpath("//button[contains(@class,'portal-signin-input')][text()='Sign Up']")).isEnabled();
				       softAssert.assertEquals(SignUpStatus, true);
				       driver.findElement(By.xpath("//button[contains(@class,'portal-signin-input')][text()='Sign Up']")).click();
				       Thread.sleep(1000);
				       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[h1='Open Capital Network']")));
			       } catch (Exception e) {
			           actualErrorMessage = e.getMessage();
			           System.out.println(actualErrorMessage);
			           System.lineSeparator();
			           String expectedErrorMessage = "is not clickable at point (960, 499)";
			        // Check if the actual error message contains the expected error message
			        if (actualErrorMessage.contains(expectedErrorMessage)) {
			            	var Err1 =  driver.findElement(By.xpath("//p[@id='outlined-basic-helper-text'][text()]")).getText();
			            	System.out.println("ErrMsg = "+ErrMsg);
			            	if (Err1.equals(ErrMsg)) {
			            		System.out.println("Err1 = "+Err1);
			            }
			            	else {
					            	var Err2 =  driver.findElement(By.xpath("//p[@id='outlined-basic-helper-text'][text()]")).getText();
					            	System.out.println("Err2 = "+Err2);
					            assert Err2.equals(ErrMsg);
			            		}				         	
			            	}		            
			        else {			        	
			        		assert actualErrorMessage.contains(ErrMsg);
			        		System.out.println(ErrMsg);
			        	} 			    
			        } 
			       //softAssert.assertAll();
		}
		driver.close();
	}
}