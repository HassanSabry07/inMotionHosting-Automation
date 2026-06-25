package Tests;

import Base.BaseTest;
import Pages.DomainSearchPage;
import Pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DomainSearchTest extends BaseTest {

    // Step 3
    @Test(priority = 1)
    public void validateAvailableDomain() {

        String domain =
                "myautomationtest123.com";

        HomePage home =
                new HomePage(driver);

        DomainSearchPage search =
                new DomainSearchPage(driver);

        home.openDomainSearch();

        search.searchDomain(domain);

        // Validate searched domain
        Assert.assertTrue(
                search.getSearchedDomain()
                        .contains(domain),
                "Domain mismatch"
        );

        // Validate price displayed
        Assert.assertTrue(
                search.getAvailablePrice()
                        .contains("$"),
                "Price not displayed"
        );
    }

    // Step 9
    @Test(priority = 2)
    public void validateDomainAlreadyTaken() {

        String domain =
                "google.com";

        HomePage home =
                new HomePage(driver);

        DomainSearchPage search =
                new DomainSearchPage(driver);

        home.openDomainSearch();

        search.searchDomain(domain);

        // Validate unavailable message
        Assert.assertTrue(
                search.isDomainTaken(domain),
                "Domain should be unavailable"
        );

        // Validate suggestions
        Assert.assertTrue(
                search.alternativesDisplayed(),
                "No alternatives displayed"
        );
    }
}