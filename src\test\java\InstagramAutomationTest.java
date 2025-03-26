import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class InstagramAutomationTest {

    private WebDriver driver;
    private final String screenshotPath = "YOUR_SCREENSHOT_PATH";  // Replace with your actual path


    @Before
    public void setUp() throws Exception {
        // Initialize WebDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testInstagramUpload() throws AWTException, InterruptedException, IOException {
        // 01) Launch Chrome browser and open Google's homepage
        driver.get("https://www.google.com");
        Thread.sleep(2000);
        takeScreenshot("01_Google_Homepage");

        // 02) Search for Instagram on Google
        driver.findElement(By.name("q")).sendKeys("Instagram" + Keys.ENTER);
        Thread.sleep(2000);
        takeScreenshot("02_Search_Instagram");

        // 03) Navigate directly to Instagram login page
        driver.get("https://www.instagram.com/accounts/login/");
        Thread.sleep(3000);
        takeScreenshot("03_Instagram_Login_Page");

        // 04) Fill in the Username/Email/Phone Number and password
        driver.findElement(By.name("username")).sendKeys("YOUR_USERNAME"); // Replace with your username
        driver.findElement(By.name("password")).sendKeys("YOUR_PASSWORD" + Keys.ENTER); // Replace with your password
        Thread.sleep(5000);
        takeScreenshot("04_Login_Instagram");

        // 05) Navigate to the user's profile page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement profileButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("xpdipgo")));
        profileButton.click();
        Thread.sleep(2000);
        takeScreenshot("05_Go_To_Profile");

        // 06) Click the Profile Picture
        WebElement profilePicture = driver.findElement(By.className("_aadn"));
        profilePicture.click();
        Thread.sleep(3000);
        takeScreenshot("06_Click_Profile_Picture");

        // 07) Click the "Upload Photo" button
        WebElement uploadPhotoButton = driver.findElement(By.xpath("//button[contains(@class, '_a9--') and contains(@class, '_ap36') and contains(@class, '_a9_0')]"));
        uploadPhotoButton.click();
        Thread.sleep(2000);
        takeScreenshot("07_Upload_Image_Button");

        // 08) Select the image from the computer
        String data = "YOUR_IMAGE_PATH";  // Replace with the path of the image to upload
        StringSelection selection = new StringSelection(data);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(4000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(10000);
        takeScreenshot("08_Upload_Profile_Picture");

        // 09) Refresh the profile page and wait for 10 seconds
        driver.navigate().refresh();
        Thread.sleep(10000);
        takeScreenshot("09_Uploaded_Profile_Picture");
    }
    private void takeScreenshot(String fileName)throws IOException {
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        Path destination = Paths.get(screenshotPath + fileName +".png");
        Files.copy(screenshot.toPath(), destination);
    }

    @After
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
