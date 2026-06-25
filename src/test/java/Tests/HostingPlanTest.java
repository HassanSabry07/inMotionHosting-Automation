package Tests;

import Base.BaseTest;
import Pages.CartPage;
import Pages.HomePage;
import Pages.WebHostingPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HostingPlanTest extends BaseTest {

    private final String DOMAIN =
            "myautomationtest123.com";

    // Step 5
    @Test(priority = 1)
    public void verifyPowerPlanAdded() {

        HomePage home =
                new HomePage(driver);

        WebHostingPage hosting =
                new WebHostingPage(driver);

        CartPage cart =
                new CartPage(driver);

        home.openWebHosting();

        hosting.selectPowerPlan(DOMAIN);

        cart.openCart();

        // Validate plan exists
        Assert.assertTrue(
                cart.getPlanName()
                        .contains("Power"),
                "Power plan not found"
        );

        // Validate price displayed
        Assert.assertTrue(
                cart.getPlanPrice()
                        .contains("$"),
                "Plan price missing"
        );
    }

    // Step 8
    @Test(priority = 2)
    public void updateHostingPlan() {

        HomePage home =
                new HomePage(driver);

        WebHostingPage hosting =
                new WebHostingPage(driver);

        CartPage cart =
                new CartPage(driver);

        // Add Power
        home.openWebHosting();

        hosting.selectPowerPlan(DOMAIN);

        cart.openCart();

        String oldPrice =
                cart.getPlanPrice();

        cart.removeItem();

        // Replace with Launch
        home.clickLogo();

        home.openWebHosting();

        hosting.selectLaunchPlan(DOMAIN);

        cart.openCart();

        // Validate replacement
        Assert.assertTrue(
                cart.getPlanName()
                        .contains("Launch"),
                "Launch plan not added"
        );

        // Validate price changed
        Assert.assertNotEquals(
                cart.getPlanPrice(),
                oldPrice,
                "Price did not update"
        );
    }
}