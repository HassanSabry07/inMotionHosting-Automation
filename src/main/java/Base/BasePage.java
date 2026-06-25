package Base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    private By spinner =
            By.cssSelector("mat-spinner, mat-progress-spinner");

    private By acceptCookiesBtn =
            By.id("onetrust-accept-btn-handler");

    public BasePage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    // ------------------------------------------------------------------
    // Accept Cookies
    // ------------------------------------------------------------------
    protected void acceptCookiesIfPresent() {

        try {

            WebElement cookieBtn = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            acceptCookiesBtn
                    )
            );

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            cookieBtn
                    );

        } catch (Exception ignored) {
        }
    }

    // ------------------------------------------------------------------
    // Spinner Wait
    // ------------------------------------------------------------------
    protected void waitForSpinnerToDisappear() {

        WebDriverWait shortWait =
                new WebDriverWait(driver, Duration.ofMillis(300));

        try {

            shortWait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            spinner
                    )
            );

        } catch (TimeoutException ignored) {

            return;
        }

        try {

            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(
                            spinner
                    )
            );

        } catch (TimeoutException ignored) {
        }
    }

    // ------------------------------------------------------------------
    // Page Load Wait
    // ------------------------------------------------------------------
    protected void waitForPageToLoad() {

        wait.until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript(
                                "return document.readyState"
                        )
                        .equals("complete")
        );
    }

    // ------------------------------------------------------------------
    // Click
    // ------------------------------------------------------------------
    protected void click(By locator) {

        acceptCookiesIfPresent();

        waitForSpinnerToDisappear();

        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );

        scrollToElement(element);

        element.click();

        waitForSpinnerToDisappear();
    }

    // ------------------------------------------------------------------
    // Type
    // ------------------------------------------------------------------
    protected void type(By locator, String text) {

        acceptCookiesIfPresent();

        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );

        element.clear();
        element.sendKeys(text);
    }

    // ------------------------------------------------------------------
    // Get Text
    // ------------------------------------------------------------------
    protected String getText(By locator) {

        waitForSpinnerToDisappear();

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).getText();
    }

    // ------------------------------------------------------------------
    // Element Present
    // ------------------------------------------------------------------
    protected boolean isElementPresent(By locator) {

        return !driver.findElements(locator).isEmpty();
    }

    // ------------------------------------------------------------------
    // Scroll
    // ------------------------------------------------------------------
    protected void scrollToElement(WebElement element) {

        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                element
        );
    }
}