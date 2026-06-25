package Pages;

import Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private By domainNameTab = By.xpath("//a[contains(text(), 'Domain')]");
    private By webHostingBtn = By.xpath("//a[text()='Web Hosting']");
    private By inMotionLogo = By.xpath("//img[@alt='InMotion Hosting Logo' and contains(@class,'amp-brand-logo')]");
    private By topMenuItems = By.cssSelector("#navbarNavDropdown .nav1 > li");

    public void openDomainSearch() {
        click(domainNameTab);
    }

    public void openWebHosting() {
        click(webHostingBtn);
    }

    public void clickLogo() {
        click(inMotionLogo);
    }

    public String getPageTitle() {
        waitForPageToLoad();
        return driver.getTitle();
    }

    public List<String> getMenuItems() {

        List<String> menu = new ArrayList<>();

        driver.findElements(topMenuItems)
                .forEach(item -> menu.add(item.getText().trim()));

        return menu;
    }
}