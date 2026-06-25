package Tests;

import Base.BaseTest;
import Pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {


    @Test
    public void verifyHomePageAndNavigation() {

        HomePage home =
                new HomePage(driver);

        // Step 1 → Verify title
        Assert.assertTrue(
                home.getPageTitle()
                        .contains("InMotion Hosting"),
                "Page title validation failed"
        );

        // Step 1 → Verify Domains menu
        Assert.assertTrue(
                home.getMenuItems()
                        .stream()
                        .anyMatch(
                                x ->
                                        x.toLowerCase()
                                                .contains("domain")
                        ),
                "Domains menu not found"
        );

        // Step 1 → Verify Web Hosting menu
        Assert.assertTrue(
                home.getMenuItems()
                        .stream()
                        .anyMatch(
                                x ->
                                        x.toLowerCase()
                                                .contains("web hosting")
                        ),
                "Web Hosting menu not found"
        );

        // Step 2 → Navigate to domain search
        home.openDomainSearch();

        Assert.assertTrue(
                driver.getCurrentUrl()
                        .toLowerCase()
                        .contains("domain"),
                "Failed to navigate to domain page"
        );
    }


}
