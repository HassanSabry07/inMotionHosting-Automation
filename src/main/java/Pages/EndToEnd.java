package Pages;

import Base.BasePage;
import Utils.ScreenshotUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class EndToEnd extends BasePage {

    public EndToEnd(WebDriver driver) {
        super(driver);
    }

    private By domainNameTab = By.xpath("//a[contains(text(), 'Domain')]");
    private By searchField = By.id("domain_search_domain");
    private By searchBtn = By.id("domain_submit");

    private By inMotionLogo =
            By.xpath("//img[@alt='InMotion Hosting Logo' and contains(@class,'amp-brand-logo')]");

    private By cartIcon =
            By.xpath("(//span[@class=\"cartitems\"])[2]");

    private By totalCartPrice =
            By.xpath("//div[contains(text(), 'Total')]//span[contains(text(), '$')]");

    private By domainNameInCart =
            By.xpath("(//div[contains(@class, 'ctw-italic')])[2]");

    private By powerPlanBtn =
            By.xpath("(//a[@href=\"/shared-hosting\"])[4]");

    private By webHoistingBtn =
            By.xpath("//a[text()='Web Hosting']");

    private By powerpricebtn =
            By.xpath("(//a[@data-plan-name='Power' and normalize-space()='Select'])[3]");

    private By launchBtnPrice =
            By.xpath("(//a[@data-plan-name='Launch' and @data-term='12' and normalize-space()='Select'])[1]");

    private By radiorbtn =
            By.xpath("//span[contains(.,'Use my existing domain')]");

    private By textfield =
            By.xpath("(//input[@type='text'])[1]");

    private By deleteCartBtn =
            By.cssSelector("mat-icon.remove-cart-item");

    private By continuebtn =
            By.xpath("//span[text()=' Continue ']");

    private By domainTakenMessage =
            By.xpath("(//span[contains(@class, 'ctw-text-red-700')])[1]");

    private By alternativeDomainsList =
            By.xpath("//div[starts-with(@id, 'tld')]");

    private By alternativeDomainUnavailableButtons =
            By.xpath("//div[starts-with(@id, 'tld')]//button[contains(., 'Unavailable')]");

    private By cartPlanName =
            By.xpath("//table//tr[1]//td[1]//div[contains(@class,'notranslate') and contains(@class,'ctw-max-w-xs')]");

    private By cartPlanPrice =
            By.xpath("//table//tr[1]//td[2]//span[contains(@class,'ctw-font-bold')]");

    private By cartTable =
            By.cssSelector("table.mat-table");

    private By topMenuItems =
            By.cssSelector("#navbarNavDropdown .nav1 > li");

    private By domainRemoveBtn =
            By.xpath("//table//tr[1]//mat-icon[contains(@class,'remove-cart-item')]");

    private By availableDomainPrice =
            By.xpath("//div[@id='tld0']//span[contains(@class,'ctw-col-span-2')]");

    private By domainPrivacyCheckbox =
            By.cssSelector("mat-checkbox[name='domainPrivacy'] input[type='checkbox']");

    private By addAndContinueBtn =
            By.xpath("//div[@id='tld0']//button[contains(., 'Add')]");

    private By domainMenu =
            By.xpath("//span[text()='DOMAIN']");

    // ============================================================
    // Domain Search
    // ============================================================

    public void searchForDomain(String domainName) {

        click(domainNameTab);
        type(searchField, domainName);
        click(searchBtn);

        ScreenshotUtil.takeScreenshot(
                driver,
                "DomainSearchResult"
        );
    }

    // ============================================================
    // Cart
    // ============================================================

    public void goToCart() {

        click(inMotionLogo);

        waitForPageToLoad();

        click(cartIcon);

        waitForCartTableToLoad();

        ScreenshotUtil.takeScreenshot(
                driver,
                "FinalCartView"
        );
    }

    private void waitForCartTableToLoad() {

        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(
                        ExpectedConditions.presenceOfElementLocated(cartTable)
                );
    }

    public String getDomainFromCart() {
        return getText(domainNameInCart).trim();
    }

    public String getTotalPrice() {
        return getText(totalCartPrice);
    }

    public String getPlanNameFromCart() {
        return getText(cartPlanName).trim();
    }

    public String getPlanPriceFromCart() {
        return getText(cartPlanPrice).trim();
    }

    // ============================================================
    // Home Page
    // ============================================================

    public String getpagetitle() {

        waitForPageToLoad();

        return driver.getTitle();
    }

    public List<String> getMenuItems() {

        waitForPageToLoad();

        List<WebElement> items =
                driver.findElements(topMenuItems);

        List<String> menu =
                new ArrayList<>();

        for (WebElement item : items) {

            String text = item.getText().trim();

            if (!text.isEmpty()) {
                menu.add(text);
            }
        }

        return menu;
    }

    // ============================================================
    // Hosting Plans
    // ============================================================

    public void AddHostingPowerPlan(String myDomain) {

        click(inMotionLogo);

        click(webHoistingBtn);

        click(powerPlanBtn);

        waitForPageToLoad();

        scrollToElement(
                driver.findElement(powerpricebtn)
        );

        click(powerpricebtn);

        waitForPageToLoad();

        click(radiorbtn);

        type(textfield, myDomain);

        click(continuebtn);
    }

    public void AddHostingLaunchPlan(String myDomain) {

        click(inMotionLogo);

        click(webHoistingBtn);

        click(powerPlanBtn);

        waitForPageToLoad();

        scrollToElement(
                driver.findElement(launchBtnPrice)
        );

        click(launchBtnPrice);

        waitForPageToLoad();

        click(radiorbtn);

        type(textfield, myDomain);

        click(continuebtn);
    }

    // ============================================================
    // Remove Items
    // ============================================================

    public void goToCartAndRemove() {

        click(inMotionLogo);

        click(cartIcon);

        click(deleteCartBtn);
    }

    public void removeDomainFromCart() {

        click(domainRemoveBtn);
    }

    public boolean isDomainRemovedFromCart() {

        return driver.findElements(domainNameInCart).isEmpty();
    }

    public void removePlanFromCart() {

        click(domainRemoveBtn);

        waitForPageToLoad();
    }

    // ============================================================
    // Refresh
    // ============================================================

    public void refreshPage() {

        driver.navigate().refresh();

        waitForPageToLoad();
    }

    // ============================================================
    // Update Hosting Plan
    // ============================================================

    public void goToWebHostingPlans() {

        click(inMotionLogo);

        click(webHoistingBtn);

        click(powerPlanBtn);

        waitForPageToLoad();
    }

    public void updateHostingPlanFromPowerToLaunch(String myDomain) {

        goToCart();

        removePlanFromCart();

        goToWebHostingPlans();

        scrollToElement(
                driver.findElement(launchBtnPrice)
        );

        click(launchBtnPrice);

        waitForPageToLoad();

        click(radiorbtn);

        type(textfield, myDomain);

        click(continuebtn);
    }

    // ============================================================
    // Domain Availability
    // ============================================================

    public String getDomainTakenMessage() {

        return getText(domainTakenMessage).trim();
    }

    public boolean isDomainTaken(String domainName) {

        String resultText =
                getDomainTakenMessage();

        String expectedText =
                domainName + " is taken";

        return resultText.contains(expectedText);
    }

    public boolean isDomainAvailable(String domainName) {

        click(domainMenu);

        return !isDomainTaken(domainName);
    }

    public String getAvailableDomainPrice() {

        return getText(availableDomainPrice).trim();
    }

    // ============================================================
    // Domain Privacy
    // ============================================================

    public boolean isDomainPrivacyChecked() {

        return driver.findElement(
                domainPrivacyCheckbox
        ).isSelected();
    }

    public void uncheckDomainPrivacy() {

        if (isDomainPrivacyChecked()) {

            click(domainPrivacyCheckbox);
        }
    }

    public void addDomainToCart() {

        click(addAndContinueBtn);

        waitForPageToLoad();
    }

    // ============================================================
    // Alternative Domains
    // ============================================================

    public boolean areAlternativeDomainsDisplayed() {

        List<WebElement> alternatives =
                driver.findElements(alternativeDomainsList);

        return !alternatives.isEmpty();
    }

    public int getAlternativeDomainsCount() {

        return driver.findElements(
                alternativeDomainsList
        ).size();
    }

    public boolean areAllAlternativeDomainsUnavailable() {

        List<WebElement> rows =
                driver.findElements(alternativeDomainsList);

        List<WebElement> unavailableButtons =
                driver.findElements(alternativeDomainUnavailableButtons);

        return !rows.isEmpty()
                && rows.size() == unavailableButtons.size();
    }


    private By searchedDomainResult =
            By.xpath("//div[contains(@class,'ctw-italic') and contains(@class,'notranslate')]");
    public String getSearchedDomainName() {
        return getText(searchedDomainResult).trim();
    }
}