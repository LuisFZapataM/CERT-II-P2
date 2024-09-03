import io.github.bonigarcia.wdm.WebDriverManager;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {


    protected WebDriver driver;

    @BeforeEach
    public void Setup()
    {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.get("https://saucedemo.com");

        //Maximizar el tamaĆ±o de la pantalla del browser
        driver.manage().window().maximize();


        //Login
        WebElement usernametextbox = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
        usernametextbox.sendKeys(("standard_user"));

        WebElement passwordtextbox = driver.findElement(By.id("password"));
        passwordtextbox.sendKeys("secret_sauce");

        WebElement loginbutton = driver.findElement(By.id("login-button"));
        loginbutton.click();
    }

    @AfterEach
    public void cleanUp()
    {
        driver.quit();
    }
}
