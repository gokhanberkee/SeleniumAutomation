import org.junit.Test;
import org.openqa.selenium.*;
import org.junit.Assert;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.RemoteWebElement;

import javax.xml.ws.WebEndpoint;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Login{

    private WebDriver driver ;
    private final By searchKeyword = By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[2]/form/div/div[1]/div[2]/input");

    public Login(WebDriver driver){
        this.driver = driver;
    }

    public void searchKeyword(String keyword){
      WebElement nameSpace = driver.findElement(searchKeyword);
      nameSpace.click();
      nameSpace.sendKeys(keyword);
      driver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[2]/form/div/div[2]/button")).click();
      JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement Element = driver.findElement(By.xpath("//*[@id=\"best-match-right\"]/div[5]/ul/li[2]/a"));
         js.executeScript("arguments[0].scrollIntoView();", Element);

         WebDriverWait wait = new WebDriverWait(driver, 500);
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"best-match-right\"]/div[5]/ul/li")));

         driver.findElements(By.xpath("//*[@id=\"best-match-right\"]/div[5]/ul/li")).get(1).click();

         String checkSecondPage = "sf=2";
         Assert.assertTrue((driver.getCurrentUrl()).endsWith(checkSecondPage));

         Random rand = new Random();
         int randomProduct = rand.nextInt(48)+1;


         driver.findElement(By.cssSelector("*[product-index='"+randomProduct+"']")).click();

         String productPagePrice = driver.findElement(By.id("sp-price-highPrice")).getText();


         WebElement quantity = driver.findElement(By.id("buyitnow_adet"));
         quantity.clear();
         quantity.sendKeys(Keys.ENTER);
         WebElement addToBasket = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div[1]/div[1]/div[2]/div[2]/div[1]/div/div[3]/div[3]/section/div/div/div[2]/form/button"));


         wait.until(ExpectedConditions.elementToBeClickable(addToBasket));
         addToBasket.sendKeys(Keys.ENTER);

         driver.findElement(By.xpath("//*[@id=\"header_wrapper\"]/div[4]/div[3]/a")).click();

         String productBasketPrice = driver.findElement(By.cssSelector("*[class='total-price']")).getText();


         if(productPagePrice.equals(productBasketPrice)){
            Select quantityBasket = new Select (driver.findElement(By.cssSelector("*[class='amount']")));
            quantityBasket.selectByIndex(1);
            WebElement option = quantityBasket.getFirstSelectedOption();
            String selectedAmount = option.getText();
            String expectedAmount = "2";
            if(selectedAmount.equals(expectedAmount)){
                WebElement deleteBasket = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[1]/form/div/div[2]/div[2]/div/div/div[6]/div[2]/div[2]/div[1]/div[3]/div/div[2]/div/a"));
                wait.until(ExpectedConditions.elementToBeClickable((deleteBasket)));
                deleteBasket.click();
            }
            WebElement checkEmptyBasket = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[1]/form/div/div[2]/div[2]/div[1]/div/div[1]/div[1]/div[1]/div/div[2]/h2"));
            String checkTest = checkEmptyBasket.getText();
            String expectedEmptyBasket = "Sepetinizde ürün bulunmamaktadır.";
            if(checkTest.equals(expectedEmptyBasket)){
                System.out.println("Test Başarılı");
            }
         }
         else{
             System.out.println("Test failed since item has discount price.");
             js.executeScript("window.confirm('Test failed since item has discount price.');");
         }
    }
}
