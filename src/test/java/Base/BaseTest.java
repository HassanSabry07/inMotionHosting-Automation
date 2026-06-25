package Base;

import Utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    public WebDriver driver;

    @BeforeMethod
    public void setup() {

        driver = DriverFactory.initDriver();

        driver.get("https://www.inmotionhosting.com/");
    }

    @AfterMethod
    public void teardown() {

        if (driver != null) {

            ScreenshotUtil.takeScreenshot(
                    driver,
                    "Admin_LastScreenshot"
            );

            driver.quit();
        }
    }
}