package Tests;

import Base.BaseTest;
import Pages.CartPage;
import Pages.DomainSearchPage;
import Pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    private final String DOMAIN =
            "myautomationtest123.com";

    // Step 4 → Add domain and verify cart
    @Test(priority = 1)
    public void verifyDomainAddedToCart() {

        HomePage home =
                new HomePage(driver);

        DomainSearchPage search =
                new DomainSearchPage(driver);

        CartPage cart =
                new CartPage(driver);

        home.openDomainSearch();

        search.searchDomain(DOMAIN);

        String expectedPrice =
                search.getAvailablePrice();

        search.addToCart();

        cart.openCart();

        Assert.assertTrue(
                cart.getDomain()
                        .contains(DOMAIN),
                "Domain not added to cart"
        );

        Assert.assertTrue(
                cart.getTotalPrice()
                        .contains("$"),
                "Price not displayed"
        );
    }

    // Step 6 → Verify cart persistence
    @Test(priority = 2)
    public void validateCartPersistenceAfterRefresh() {

        HomePage home =
                new HomePage(driver);

        DomainSearchPage search =
                new DomainSearchPage(driver);

        CartPage cart =
                new CartPage(driver);

        home.openDomainSearch();

        search.searchDomain(DOMAIN);

        search.addToCart();

        cart.openCart();

        String domainBefore =
                cart.getDomain();

        String priceBefore =
                cart.getTotalPrice();

        cart.refreshCart();

        Assert.assertEquals(
                cart.getDomain(),
                domainBefore,
                "Domain changed after refresh"
        );

        Assert.assertEquals(
                cart.getTotalPrice(),
                priceBefore,
                "Price changed after refresh"
        );
    }

    // Step 7 → Remove domain
    @Test(priority = 3)
    public void removeDomainFromCart() {

        HomePage home =
                new HomePage(driver);

        DomainSearchPage search =
                new DomainSearchPage(driver);

        CartPage cart =
                new CartPage(driver);

        home.openDomainSearch();

        search.searchDomain(DOMAIN);

        search.addToCart();

        cart.openCart();

        cart.removeItem();

        Assert.assertTrue(
                cart.isDomainRemoved(),
                "Domain still exists in cart"
        );
    }
}