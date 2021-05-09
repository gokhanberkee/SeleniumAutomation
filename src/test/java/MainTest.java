import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import javax.swing.JOptionPane;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainTest {

    protected WebDriver driver;
    Login login;

    @BeforeAll
    public void runTest(){
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.gittigidiyor.com/");

        String expected = "GittiGidiyor - Türkiye'nin Öncü Alışveriş Sitesi";
        Assert.assertEquals(expected,driver.getTitle());

        driver.navigate().to("https://www.gittigidiyor.com/uye-girisi");
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("L-UserNameField"))));

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("L-PasswordField"))));

        WebElement userName = driver.findElement(By.id("L-UserNameField"));
        userName.click();
        userName.sendKeys("gokhanberkee@gmail.com");

        WebElement pass = driver.findElement(By.id("L-PasswordField"));
        pass.click();
        pass.sendKeys("Gokhan123");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS) ;
        driver.findElement(By.id("gg-login-enter")).click();

        String checkLogin = "Hesabım";
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[3]/div/div[1]/div/div[2]"))));
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[3]/div/div[1]/div/div[2]")).getText().contains(checkLogin));

        login = new Login(driver);
    }

    @AfterAll
    public void tearDown(){
//     driver.quit();
    }
}
