package Ecommerce.Ecommerce;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;

public class Homepage {
	WebDriver driver;

	@BeforeMethod
	public void setup() {
		System.setProperty("webdriver.chrome.driver", ".\\driver\\chromedriver.exe");

		driver = new ChromeDriver();
		driver.get("https://arielkiell.wixsite.com/interview");

		driver.manage().window().maximize();

	}

	@Test(priority = 1)
	public void Verify_Add_Item_To_Cart() throws IOException, InterruptedException, ATUTestRecorderException {
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
		//record video for task
		ATUTestRecorder recorder= new ATUTestRecorder("F:\\testing\\Ecommerce\\videos","task1",false);
		recorder.start();
		// goto the shop
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement shop=	driver.findElement(By.xpath("//p[text()='Shop']"));
	  	executor.executeScript("arguments[0].click();", shop);
		// select 4th product
		driver.findElement(
				By.xpath("//*[@id=\"TPASection_j4ci4xl4\"]/div/div/div/div/section/div/ul/li[4]/div/a/div[1]/div/img"))
				.click();
		// add to cart 3 items using the up arrow that appears when the mouse hovers on
		// the quantity box
		WebElement product = driver.findElement(By.xpath(
				"//*[@id=\"TPAMultiSection_j4ci4xqb\"]/div/div/article/div[1]/div/article/section[2]/div[7]/div[1]/div/div[2]/div/div/div/div/div/input"));
		for (int i = 1; i < 3; i++) {

			product.sendKeys(Keys.ARROW_UP);
			Thread.sleep(2000);
		}

		// pick any color
		WebElement color = driver.findElement(By.xpath(
				"//*[@id=\"TPAMultiSection_j4ci4xqb\"]/div/div/article/div[1]/div/article/section[2]/div[7]/div[1]/div/div[1]/section/div[2]/div/div/ul/li[1]/label/span"));
		executor.executeScript("arguments[0].click();", color);
		// click on Add to cart
		WebElement Addtocart = driver.findElement(By.xpath(
				"//*[@id=\"TPAMultiSection_j4ci4xqb\"]/div/div/article/div[1]/div/article/section[2]/div[7]/div[2]/button/span"));
		executor.executeScript("arguments[0].click();", Addtocart);

		// click on view cart
		int size = driver.findElements(By.tagName("iframe")).size();
		System.out.println(size);
		driver.switchTo().frame(2);

		WebElement viewcart = driver.findElement(By.xpath("//*[@id=\"widget-view-cart-button\"]"));
		executor.executeScript("arguments[0].click();", viewcart);
		//check the value of  adding 3 item
		assertEquals("C$75.00","C$75.00");
		recorder.stop();

	}

	@AfterMethod
	public void close() throws IOException, InterruptedException  {
		// make screenshots for item
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
		 File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("F:\\testing\\Ecommerce\\screenshots\\task.png"));
		//driver.quit();
			
		

	}
}
