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
public class NegativeSignIn {

	public static void main (String[] args) throws InterruptedException, Throwable {
		
		File excelFile = new File("C:\\Users\\Asus\\eclipse-workspace\\seleniumjava\\src\\seleniumpack\\Login Details.xlsx");
		System.out.println("Current working directory: " + System.getProperty("user.dir"));
		FileInputStream fis = new FileInputStream(excelFile);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("-SignIn");
		int noOfRows = sheet.getLastRowNum();
		System.out.println(noOfRows);
		
		SoftAssert softAssert = new SoftAssert();
        System.setProperty("webdriver.chrome.driver", "D:\\BrowserDrivers\\chromedriver-win64\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        boolean isHidden;
        
		for(int i=1; i<noOfRows; i++) {
			Thread.sleep(3000);
			driver.get("https://xaltsocnportal.web.app/");
	        driver.manage().window().maximize();
	        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[h1='Open Capital Network']")));
	        driver.findElement(By.xpath("//div[@class='cta-container']/button[text()='Get Started']")).click();
	        String title = driver.getTitle();
	        Assert.assertEquals(title,"OCN Portal");
	        WebElement legendElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Already have an account? Click here to sign in.']")));
	        driver.findElement(By.xpath("//button[text()='Already have an account? Click here to sign in.']")).click();
	    	try {
	            WebElement divElement = driver.findElement(By.xpath("//main/div[2]/div[3]"));
	            isHidden = !divElement.isDisplayed();
	        } catch (NoSuchElementException e) {
	            isHidden = true;
	        }
				var Email = sheet.getRow(i).getCell(0).getStringCellValue();
				var Pwd = sheet.getRow(i).getCell(1).getStringCellValue();				 		
			       
			       String actualErrorMessage = "";
			       try {
			    	   driver.findElement(By.xpath("//main/div[2]/div[1]//div[1]/input")).sendKeys(Email);
				       driver.findElement(By.xpath("//main/div[2]/div[2]//div[1]/input")).sendKeys(Pwd);
				       var SignInStatus = driver.findElement(By.xpath("//button[contains(@class,'portal-signin-input')][text()='Sign In']")).isEnabled();
				       softAssert.assertEquals(SignInStatus, true);
				       driver.findElement(By.xpath("//button[contains(@class,'portal-signin-input')][text()='Sign In']")).click();
				       Thread.sleep(1000);
				       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[h1='Open Capital Network']")));
			       } catch (Exception e) {
			           actualErrorMessage = e.getMessage();
			           System.out.println(actualErrorMessage);
			           System.lineSeparator();
			           String expectedErrorMessage = " Incorrect E-Mail or Password";

			        // Check if the actual error message contains the expected error message
			        if (actualErrorMessage.contains(expectedErrorMessage)) {
			            System.out.println("The error message contains the expected text.");
			        } else {
			            Assert.fail("The error message does not contain the expected text.");
			        }
			       }
			       softAssert.assertAll();
		}	
		driver.close();
	}

}