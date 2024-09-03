import com.google.common.collect.Ordering;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CompleteTests extends BaseTest
{



    @Test
    // Verify that the  prices can be order in ascending order
    public void SortProductsByPriceAscendanTest()
    {

        WebElement filterComboBox = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.className("product_sort_container")));
        Select filterSelect = new Select(filterComboBox);
        filterSelect.selectByVisibleText("Price (low to high)");

        List<Double> productPrices = new ArrayList<>();

        for(WebElement we : driver.findElements(By.className("inventory_item_price"))) {
            productPrices.add(Double.parseDouble(we.getText().replace("$", "")));
        }

        Boolean ascensdingOrder = Ordering.natural().isOrdered(productPrices);
        Assertions.assertTrue(ascensdingOrder);



    }


    // Verifiy that an empty shopping-cart can't checkout
    @Test
    public void NonEmptyShoppingCartCheckOutTest()
    {


        WebElement shoppingCart = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("shopping_cart_container")));
        shoppingCart.click();

        WebElement checkOutButton = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("checkout")));

        Assertions.assertFalse(checkOutButton.isEnabled());

    }


    // Verify that when a product is added or removed the counter in the shopping cart is modified
    @Test
    public void ProductsAdderCounterShoppingCartTest()
    {
        WebElement backpackAddButton = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("add-to-cart-sauce-labs-backpack")));
        backpackAddButton.click();

        WebElement tshirtAddButton = driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt"));
        tshirtAddButton.click();

        WebElement spProductsCounter = driver.findElement(By.className("shopping_cart_badge"));
        Integer count = Integer.parseInt(spProductsCounter.getText());


        Assertions.assertEquals(2, count);

        WebElement backpackRemoveButton = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("remove-sauce-labs-backpack")));
        backpackRemoveButton.click();

        count = Integer.parseInt(spProductsCounter.getText());

        Assertions.assertEquals(1, count);


    }

    // Verify that the sum of the prices of all products is correct
    @Test
    public void CorrectSumOfProductsTest()
    {

        Double subTotalCalculated = 0.0;


        List<WebElement> products = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("inventory_item")));
        for(WebElement p : products)
        {
            WebElement clickBtn = p.findElement(By.className("btn"));
            clickBtn.click();

            WebElement aux = p.findElement(By.className("inventory_item_price"));
            subTotalCalculated += Double.parseDouble(aux.getText().replace("$",""));
        }


        WebElement shoppingCart = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("shopping_cart_container")));
        shoppingCart.click();

        WebElement checkOutButton = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("checkout")));
        checkOutButton.click();


        WebElement nameTextBox = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("first-name")));
        nameTextBox.sendKeys("Juan");

        WebElement lastNameTextBox = driver.findElement(By.id("last-name"));
        lastNameTextBox.sendKeys("Perez");

        WebElement zipCodeTextBox = driver.findElement(By.id("postal-code"));
        zipCodeTextBox.sendKeys("12124");

        WebElement continueButton = driver.findElement(By.id("continue"));
        continueButton.click();


        WebElement subtotalLabel = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.className("summary_subtotal_label")));

        String auxsubTotal = subtotalLabel.getText().split(":")[1];
        Double subTotalPage =  Double.parseDouble(auxsubTotal.replace("$",""));

        Assertions.assertEquals(subTotalPage, subTotalCalculated);
    }

    //Veriy that a complete purchase is made
    @Test
    public void CompletePurchaseTest()
    {
        WebElement backpackAddButton = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("add-to-cart-sauce-labs-backpack")));
        backpackAddButton.click();

        WebElement tshirtAddButton = driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt"));
        tshirtAddButton.click();

        WebElement shoppingCart = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("shopping_cart_container")));
        shoppingCart.click();

        WebElement checkOutButton = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("checkout")));
        checkOutButton.click();


        WebElement nameTextBox = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("first-name")));
        nameTextBox.sendKeys("Juan");

        WebElement lastNameTextBox = driver.findElement(By.id("last-name"));
        lastNameTextBox.sendKeys("Perez");

        WebElement zipCodeTextBox = driver.findElement(By.id("postal-code"));
        zipCodeTextBox.sendKeys("12124");

        WebElement continueButton = driver.findElement(By.id("continue"));
        continueButton.click();

        WebElement finishButton = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("finish")));
        finishButton.click();

        WebElement backToProductsButton = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("back-to-products")));

        Assertions.assertTrue(backToProductsButton.isDisplayed());

    }





}
