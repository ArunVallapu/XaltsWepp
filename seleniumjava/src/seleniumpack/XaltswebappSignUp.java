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
public class XaltswebappSignUp {

	public static void main (String[] args) throws InterruptedException, Throwable {
		
		File excelFile = new File("C:\\Users\\Asus\\eclipse-workspace\\seleniumjava\\src\\seleniumpack\\Login Details.xlsx");
		System.out.println("Current working directory: " + System.getProperty("user.dir"));
		FileInputStream fis = new FileInputStream(excelFile);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("SignUp");
		int noOfRows = sheet.getLastRowNum();
		int noOfColumns = sheet.getRow(0).getLastCellNum();
		System.out.println(sheet.getLastRowNum());
		
		SoftAssert softAssert = new SoftAssert();
        System.setProperty("webdriver.chrome.driver", "D:\\BrowserDrivers\\chromedriver-win64\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        
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
		
			       driver.findElement(By.xpath("//main/div[2]/div[1]//div[1]/input")).sendKeys(Email);
			       driver.findElement(By.xpath("//main/div[2]/div[2]//div[1]/input")).sendKeys(Pwd);
			       var SignUpStatus = driver.findElement(By.xpath("//button[contains(@class,'portal-signin-input')][text()='Sign Up']")).isEnabled();
			       
			       softAssert.assertEquals(SignUpStatus, false);
			       driver.findElement(By.xpath("//main/div[2]/div[3]//div[1]/input")).sendKeys(cnfmPwd);
			       softAssert.assertEquals(SignUpStatus, true);
			       driver.findElement(By.xpath("//button[contains(@class,'portal-signin-input')][text()='Sign Up']")).click();
			       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[h1='Open Capital Network']")));
			       var SignInStatus = driver.findElement(By.xpath("//body/div//header//button")).getText();
			       Assert.assertEquals(SignInStatus,"SIGN OUT","Unexpected Page");
			       driver.findElement(By.xpath("//button[text()='Sign Out']")).click();
			       boolean SignOutStatus = driver.findElement(By.xpath("//button[text()='Sign In']")).isDisplayed();
			       softAssert.assertEquals(SignOutStatus, true);
			       System.out.println("Signed Up");
			      
		}

		driver.close();		
	}
}