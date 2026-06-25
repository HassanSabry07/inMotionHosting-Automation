package Tests;

import Base.BaseTest;
import Pages.EndToEnd;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class EndToEndTest extends BaseTest {

    @Test
    public void validateDomainAndPrice() {

        EndToEnd endToEnd = new EndToEnd(driver);
        SoftAssert soft = new SoftAssert();

        String myDomain = "myautomationtest123.com";
        String expectedPrice = "$19.99 USD";
        String powerPlanPrice = "$69.48";
        String powerPlanName = "Power";

        Assert.assertTrue(
                endToEnd.getpagetitle().contains("InMotion Hosting"),
                "Title validation failed"
        );
        soft.assertTrue(
                endToEnd.getMenuItems().contains("Domains"),
                "Domains not available"
        );
        soft.assertTrue(
                endToEnd.getMenuItems()
                        .stream()
                        .anyMatch(x -> x.contains("Web Hosting")),
                "Web Hosting not available"
        );

        // 1. Search (captures a screenshot of the search result)
        endToEnd.searchForDomain(myDomain);


        // 3. Go to Cart (captures a screenshot of the final cart view)
        endToEnd.goToCart();

        // 4. Validate Domain
        String actualDomain = endToEnd.getDomainFromCart();

        Assert.assertTrue(actualDomain.contains(myDomain),
                "Domain mismatch! Expected: " + myDomain + ", but found: " + actualDomain);

        // 5. Validate Price (domain-only cart, before adding any hosting plan)
        String actualPrice = endToEnd.getTotalPrice();

        soft.assertTrue(actualPrice.contains(expectedPrice),
                "Price mismatch! Expected: " + expectedPrice + ", but found: " + actualPrice);

        endToEnd.AddHostingPowerPlan(myDomain);

        // 6. Validate hosting plan inclusion (by name, not just by price)
        String planNameInCart = endToEnd.getPlanNameFromCart();
        soft.assertTrue(planNameInCart.contains(powerPlanName),
                "Expected cart to include '" + powerPlanName + "' plan, but found: " + planNameInCart);

        // 7. Validate the Power plan's own line-item price.
        //    NOTE: this is intentionally NOT the same as getTotalPrice(),
        //    because the cart grand total also includes add-ons (e.g.
        //    Backup Manager) attached alongside the plan.
        String planPriceInCart = endToEnd.getPlanPriceFromCart();
        soft.assertTrue(planPriceInCart.contains(powerPlanPrice),
                "Price mismatch! Expected plan price: " + powerPlanPrice + ", but found: " + planPriceInCart);

        endToEnd.goToCartAndRemove();

        soft.assertAll();
        System.out.println("Test passed successfully ✅");
    }

    /**
     * Step 2: Search for a Domain.
     * Verifies the domain IS available (not taken) and that a price
     */
    @Test
    public void validateDomainAvailable() {

        String myDomain = "myautomationtest123.com";

        EndToEnd endToEnd = new EndToEnd(driver);
        SoftAssert soft = new SoftAssert();

        endToEnd.searchForDomain(myDomain);

        String availablePrice = endToEnd.getAvailableDomainPrice();

        soft.assertTrue(
                availablePrice.contains("$"),
                "Expected a price to be displayed, but found: " + availablePrice
        );

        soft.assertEquals(
                endToEnd.getSearchedDomainName(),
                myDomain,
                "Displayed domain does not match searched domain"
        );

        soft.assertAll();
    }

    @Test
    public void addHostingPlans() {
        String myDomain = "myautomationtest123.com";
        String powerPlanPrice = "$69.48";
        String powerPlanName = "Power";

        EndToEnd endToEnd = new EndToEnd(driver);
        SoftAssert soft = new SoftAssert();

        // 1. Add the Power hosting plan
        endToEnd.AddHostingPowerPlan(myDomain);

        // 2. Re-read domain, plan name, and plan price AFTER the plan was
        //    added, since adding the plan changes the cart contents.
        String actualDomain = endToEnd.getDomainFromCart();
        String planNameInCart = endToEnd.getPlanNameFromCart();
        String planPriceInCart = endToEnd.getPlanPriceFromCart();

        soft.assertTrue(actualDomain.contains(myDomain),
                "Domain mismatch! Expected: " + myDomain + ", but found: " + actualDomain);
        soft.assertTrue(planNameInCart.contains(powerPlanName),
                "Expected cart to include '" + powerPlanName + "' plan, but found: " + planNameInCart);
        soft.assertTrue(planPriceInCart.contains(powerPlanPrice),
                "Price mismatch! Expected plan price: " + powerPlanPrice + ", but found: " + planPriceInCart);

        // 3. Remove the plan and verify it's gone from the cart
        endToEnd.goToCartAndRemove();

        soft.assertAll();
    }

    /**
     * Step 6: Validate Cart Persistence After Page Refresh.
     * Adds the domain + Power plan, refreshes the cart page, then
     * verifies the domain, plan, and prices are all still present
     * and unchanged after the refresh.
     */
    @Test
    public void validateCartPersistenceAfterRefresh() {
        String myDomain = "myautomationtest123.com";
        String powerPlanPrice = "$69.48";
        String powerPlanName = "Power";

        EndToEnd endToEnd = new EndToEnd(driver);
        SoftAssert soft = new SoftAssert();

        // 1. Search for the domain and add the Power hosting plan
        endToEnd.searchForDomain(myDomain);
        endToEnd.AddHostingPowerPlan(myDomain);

        // 2. Capture state BEFORE refresh
        String domainBefore = endToEnd.getDomainFromCart();
        String planNameBefore = endToEnd.getPlanNameFromCart();
        String planPriceBefore = endToEnd.getPlanPriceFromCart();
        String totalPriceBefore = endToEnd.getTotalPrice();

        // 3. Refresh the cart page
        endToEnd.refreshPage();

        // 4. Capture state AFTER refresh
        String domainAfter = endToEnd.getDomainFromCart();
        String planNameAfter = endToEnd.getPlanNameFromCart();
        String planPriceAfter = endToEnd.getPlanPriceFromCart();
        String totalPriceAfter = endToEnd.getTotalPrice();

        // 5. Verify domain and plan are still listed
        soft.assertTrue(domainAfter.contains(myDomain),
                "Expected domain '" + myDomain + "' to persist after refresh, but found: " + domainAfter);
        soft.assertTrue(planNameAfter.contains(powerPlanName),
                "Expected plan '" + powerPlanName + "' to persist after refresh, but found: " + planNameAfter);

        // 6. Verify prices remain unchanged
        soft.assertEquals(domainAfter, domainBefore,
                "Domain changed after refresh! Before: " + domainBefore + ", After: " + domainAfter);
        soft.assertEquals(planNameAfter, planNameBefore,
                "Plan name changed after refresh! Before: " + planNameBefore + ", After: " + planNameAfter);
        soft.assertEquals(planPriceAfter, planPriceBefore,
                "Plan price changed after refresh! Before: " + planPriceBefore + ", After: " + planPriceAfter);
        soft.assertEquals(totalPriceAfter, totalPriceBefore,
                "Total price changed after refresh! Before: " + totalPriceBefore + ", After: " + totalPriceAfter);
        soft.assertTrue(planPriceAfter.contains(powerPlanPrice),
                "Price mismatch after refresh! Expected plan price: " + powerPlanPrice + ", but found: " + planPriceAfter);

        soft.assertAll();
    }

    /**
     * Step 7: Remove Domain from Cart.
     *
     * NOTE: in the current cart layout, the domain and the hosting plan
     * share the SAME row, and the only available "remove" icon removes
     * that entire row (domain + plan together). As a result, this test
     * verifies that the domain is removed, but cannot also verify the
     * plan remains afterward, since removing one removes both.
     */
    @Test
    public void removeDomainFromCart() {
        String myDomain = "myautomationtest123.com";

        EndToEnd endToEnd = new EndToEnd(driver);
        SoftAssert soft = new SoftAssert();

        // 1. Search for the domain and add the Power hosting plan
        endToEnd.searchForDomain(myDomain);
        endToEnd.AddHostingPowerPlan(myDomain);

        // 2. Remove the domain/plan row from the cart
        endToEnd.removeDomainFromCart();

        // 3. Verify the domain is no longer in the cart
        soft.assertTrue(endToEnd.isDomainRemovedFromCart(),
                "Expected domain '" + myDomain + "' to be removed from the cart, but it is still present.");

        soft.assertAll();
    }

    /**
     * Step 8: Update Hosting Plan.
     * Adds the domain + Power plan, removes the Power plan from the
     * cart, goes back to the Web Hosting page, and selects the Launch
     * plan as a replacement. Verifies the new plan replaces the old one
     * in the cart and the total price updates accordingly.
     */
    @Test
    public void updateHostingPlan() {
        String myDomain = "myautomationtest123.com";
        String oldPlanName = "Power";
        String oldPlanPrice = "$69.48";
        String newPlanName = "Launch";
        String newPlanPrice = "$57.48";

        EndToEnd endToEnd = new EndToEnd(driver);
        SoftAssert soft = new SoftAssert();

        // 1. Search for the domain and add the Power hosting plan
        endToEnd.searchForDomain(myDomain);
        endToEnd.AddHostingPowerPlan(myDomain);

        // 2. Confirm Power plan is in the cart before updating
        endToEnd.goToCart();
        String planNameBeforeUpdate = endToEnd.getPlanNameFromCart();
        soft.assertTrue(planNameBeforeUpdate.contains(oldPlanName),
                "Expected '" + oldPlanName + "' plan in cart before update, but found: " + planNameBeforeUpdate);

        // 3. Remove the Power plan, go back to Web Hosting, select Launch
        endToEnd.updateHostingPlanFromPowerToLaunch(myDomain);

        // 4. Verify the new plan (Launch) replaced the old one (Power) in the cart
        endToEnd.goToCart();
        String planNameAfterUpdate = endToEnd.getPlanNameFromCart();
        soft.assertTrue(planNameAfterUpdate.contains(newPlanName),
                "Expected cart to show '" + newPlanName + "' plan after update, but found: " + planNameAfterUpdate);
        soft.assertFalse(planNameAfterUpdate.contains(oldPlanName),
                "Expected '" + oldPlanName + "' plan to be replaced, but it is still present: " + planNameAfterUpdate);

        // 5. Verify the total price updated accordingly (no longer the old plan's price)
        String planPriceAfterUpdate = endToEnd.getPlanPriceFromCart();
        soft.assertTrue(planPriceAfterUpdate.contains(newPlanPrice),
                "Expected updated plan price: " + newPlanPrice + ", but found: " + planPriceAfterUpdate);
        soft.assertFalse(planPriceAfterUpdate.contains(oldPlanPrice),
                "Expected old plan price (" + oldPlanPrice + ") to no longer apply, but found: " + planPriceAfterUpdate);

        soft.assertAll();
    }

    /**
     * Step 9: Validate Invalid Domain Search.
     * Searches for a domain known to already be taken (google.com) and
     * verifies the "unavailable" message and alternative suggestions
     * are displayed.
     */
    @Test
    public void validateDomainAlreadyTaken() {

        EndToEnd endToEnd = new EndToEnd(driver);
        SoftAssert soft = new SoftAssert();
        String takenDomain = "google.com";

        // 1. Search for a domain that is known to be already registered
        //    (captures a screenshot of the search result)
        endToEnd.searchForDomain(takenDomain);

        // 2. Verify the "is taken" message appears for that domain
        soft.assertTrue(
                endToEnd.isDomainTaken(takenDomain),
                "Expected domain '" + takenDomain + "' to be marked as taken, but it wasn't."
        );

        // 3. Verify suggested alternative domains are displayed below the message
        soft.assertTrue(
                endToEnd.areAlternativeDomainsDisplayed(),
                "Expected alternative domain suggestions to be displayed, but none were found."
        );
        soft.assertAll();
    }
}
