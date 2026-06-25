package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {
    public static WebDriver initDriver() {

        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();
        return driver;
    }
}
