package Pages;

import Base.BasePage;
import Utils.ScreenshotUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // Cart icon
    private By cartIcon =
            By.xpath("(//span[@class='cartitems'])[2]");

    // Domain displayed inside cart
    private By domainNameInCart =
            By.xpath("(//div[contains(@class,'ctw-italic')])[2]");

    // Total cart price
    private By totalCartPrice =
            By.xpath("//div[contains(text(),'Total')]//span[contains(text(),'$')]");

    // Hosting plan name
    private By cartPlanName =
            By.xpath("//table//tr[1]//td[1]//div[contains(@class,'notranslate')]");

    // Hosting plan price
    private By cartPlanPrice =
            By.xpath("//table//tr[1]//td[2]//span[contains(@class,'ctw-font-bold')]");

    // Remove item button
    private By deleteBtn =
            By.cssSelector("mat-icon.remove-cart-item");

    // Open cart page
    public void openCart() {

        waitForPageToLoad();

        click(cartIcon);

        ScreenshotUtil.takeScreenshot(
                driver,
                "FinalCartView"
        );
    }

    // Get selected domain
    public String getDomain() {
        return getText(domainNameInCart).trim();
    }

    // Get total price
    public String getTotalPrice() {
        return getText(totalCartPrice).trim();
    }

    // Get selected hosting plan
    public String getPlanName() {
        return getText(cartPlanName).trim();
    }

    // Get hosting plan price
    public String getPlanPrice() {
        return getText(cartPlanPrice).trim();
    }

    // Remove item from cart
    public void removeItem() {

        click(deleteBtn);

        waitForPageToLoad();
    }

    // Refresh cart
    public void refreshCart() {

        driver.navigate().refresh();

        waitForPageToLoad();
    }

    // Validate item removed
    public boolean isDomainRemoved() {

        return driver.findElements(
                domainNameInCart
        ).isEmpty();
    }
}