package Pages;

import Base.BasePage;
import Utils.ScreenshotUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DomainSearchPage extends BasePage {

    public DomainSearchPage(WebDriver driver) {
        super(driver);
    }

    // Search field
    private By searchField =
            By.id("domain_search_domain");

    // Search button
    private By searchBtn =
            By.id("domain_submit");

    // Available domain price
    private By availableDomainPrice =
            By.xpath("//div[@id='tld0']//span[contains(@class,'ctw-col-span-2')]");

    // Add selected domain
    private By addAndContinueBtn =
            By.xpath("//div[@id='tld0']//button[contains(., 'Add')]");

    // Search result domain
    private By searchedDomainResult =
            By.xpath("//div[contains(@class,'ctw-italic')]");

    // Domain taken message
    private By domainTakenMessage =
            By.xpath("(//span[contains(@class,'ctw-text-red-700')])[1]");

    // Suggested domains
    private By alternativeDomains =
            By.xpath("//div[starts-with(@id,'tld')]");

    // Search for domain
    public void searchDomain(String domain) {

        type(searchField, domain);

        click(searchBtn);

        ScreenshotUtil.takeScreenshot(
                driver,
                "DomainSearchResult"
        );
    }

    // Get available price
    public String getAvailablePrice() {

        return getText(
                availableDomainPrice
        ).trim();
    }

    // Add domain to cart
    public void addToCart() {

        click(addAndContinueBtn);

        waitForPageToLoad();
    }

    // Get searched domain
    public String getSearchedDomain() {

        return getText(
                searchedDomainResult
        ).trim();
    }

    // Check unavailable domain
    public boolean isDomainTaken(
            String domain
    ) {

        return getText(
                domainTakenMessage
        ).contains(
                domain + " is taken"
        );
    }

    // Validate suggestions displayed
    public boolean alternativesDisplayed() {

        return !driver.findElements(
                alternativeDomains
        ).isEmpty();
    }
}