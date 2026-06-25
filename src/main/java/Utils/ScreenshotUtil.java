package Utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static void takeScreenshot(WebDriver driver, String fileName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);

        // Unique timestamp per screenshot so previous runs are never
        // overwritten -- every screenshot is kept in reports/.
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
        String finalFileName = fileName + "_" + timestamp + ".png";

        try {
            File destDir = new File("reports");
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            FileUtils.copyFile(src, new File(destDir, finalFileName));
            System.out.println("Screenshot saved: reports/" + finalFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}