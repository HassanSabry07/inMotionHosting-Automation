package Pages;

import Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WebHostingPage extends BasePage {

    public WebHostingPage(WebDriver driver) {
        super(driver);
    }

    // Power hosting plan
    private By powerPlanBtn =
            By.xpath("(//a[@data-plan-name='Power'])[3]");

    // Launch hosting plan
    private By launchPlanBtn =
            By.xpath("(//a[@data-plan-name='Launch'])[1]");

    // Existing domain option
    private By radioBtn =
            By.xpath("//span[contains(.,'Use my existing domain')]");

    // Domain textbox
    private By domainField =
            By.xpath("(//input[@type='text'])[1]");

    // Continue button
    private By continueBtn =
            By.xpath("//span[text()=' Continue ']");

    // Select Power plan
    public void selectPowerPlan(String domain) {

        click(powerPlanBtn);

        waitForPageToLoad();

        click(radioBtn);

        type(domainField, domain);

        click(continueBtn);

        waitForPageToLoad();
    }

    // Select Launch plan
    public void selectLaunchPlan(String domain) {

        click(launchPlanBtn);

        waitForPageToLoad();

        click(radioBtn);

        type(domainField, domain);

        click(continueBtn);

        waitForPageToLoad();
    }

    // Replace current plan
    public void updatePlan(String domain) {

        selectLaunchPlan(domain);
    }
}