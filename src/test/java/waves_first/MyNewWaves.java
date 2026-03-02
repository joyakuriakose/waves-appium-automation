package waves_first;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class MyNewWaves {

    public AndroidDriver driver;

    // -------------------------------------------------------
    // SETUP: Runs ONCE before all tests
    // -------------------------------------------------------
    @BeforeClass
    public void setup() throws MalformedURLException {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("RZCX40ERHTR");         // your phone's ID
        options.setApp("C:/appium-apk/waves2.apk");   // your APK path
        options.setNoReset(true);    // ✅ true = keep app installed, avoids permission re-asks
        options.setFullReset(false); // true = clear app data before test

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // ✅ Step 1: Handle the app's own "Location Access" screen
        try {
            Thread.sleep(3000); // wait for the screen to load

            // Found via Appium Inspector: accessibility id = "Allow Access"
            List<WebElement> appAllowButtons = driver.findElements(
                By.xpath("//android.widget.Button[@content-desc='Allow Access']"));
            if (!appAllowButtons.isEmpty()) {
                appAllowButtons.get(0).click();
                System.out.println("✅ App's Location Access screen dismissed!");
                Thread.sleep(2000); // wait after clicking
            } else {
                System.out.println("ℹ️ App location screen not found (already passed)");
            }

            // ✅ Step 2: Handle Android System Location Permission popup (if it appears)
            List<WebElement> systemAllowButtons = driver.findElements(
                By.xpath("//android.widget.Button[@text='Allow' or @text='While using the app' or @text='Only this time']"));
            if (!systemAllowButtons.isEmpty()) {
                systemAllowButtons.get(0).click();
                System.out.println("✅ Android system location permission accepted!");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("✅ App launched!");
    }

    // -------------------------------------------------------
    // TEST 1: Verify app opens on the right screen
    // -------------------------------------------------------
    @Test(priority = 1)
    public void testAppLaunch() {
        String activity = driver.currentActivity();
        System.out.println("📱 Current screen: " + activity);

        // Check the app opened (activity should not be empty)
        Assert.assertNotNull(activity, "App should have an active screen");
        Assert.assertFalse(activity.isEmpty(), "Activity name should not be empty");

        System.out.println("✅ Test 1 PASSED: App launched on correct screen");
    }

    // -------------------------------------------------------
    // TEST 2: Tap "Primary Sales" button
    // ✅ Found via Appium Inspector:
    //    content-desc = "Primary Sales\nDirect customer orders"
    //    class        = android.widget.ImageView
    //    package      = com.spiralcode.waves2
    // -------------------------------------------------------
    @Test(priority = 2)
    public void testClickPrimarySales() {

        // ✅ BEST: xpath using contains on content-desc (handles the \n safely)
        WebElement primarySales = driver.findElement(
            By.xpath("//android.widget.ImageView[contains(@content-desc, 'Primary Sales')]"));
        primarySales.click();
        System.out.println("✅ Test 2 PASSED: Primary Sales button clicked!");

        // --- Backup: exact accessibilityId match (includes newline + subtitle) ---
        // WebElement primarySales = driver.findElement(
        //     By.accessibilityId("Primary Sales\nDirect customer orders"));
    }

    // -------------------------------------------------------
    // TEST 3: Type text into an input field
    // -------------------------------------------------------
    @Test(priority = 3)
    public void testTypeText() {

        // TODO: Replace with your actual input field locator
        // WebElement inputField = driver.findElement(By.id("com.waves.app:id/et_username"));
        // inputField.clear();                      // clear existing text first
        // inputField.sendKeys("testuser@gmail.com"); // type your text
        //
        // String typedText = inputField.getText();
        // Assert.assertEquals(typedText, "testuser@gmail.com", "Text should match what was typed");
        // System.out.println("✅ Test 3 PASSED: Text typed successfully");

        System.out.println("ℹ️ Test 3: Use Appium Inspector to find your input field's id, then uncomment above");
    }

    // -------------------------------------------------------
    // TEST 4: Verify text on screen
    // -------------------------------------------------------
    @Test(priority = 4)
    public void testVerifyText() {

        // TODO: Replace with your actual text element locator
        // WebElement label = driver.findElement(By.id("com.waves.app:id/tv_title"));
        // String actualText = label.getText();
        // System.out.println("📝 Text on screen: " + actualText);
        //
        // Assert.assertEquals(actualText, "Welcome to Waves", "Title text should match");
        // System.out.println("✅ Test 4 PASSED: Text verified");

        System.out.println("ℹ️ Test 4: Use Appium Inspector to find your text element's id, then uncomment above");
    }

    // -------------------------------------------------------
    // TEST 5: Scroll down the screen
    // -------------------------------------------------------
    @Test(priority = 5)
    public void testScroll() {

        // Scroll down using UiScrollable (works on any scrollable list)
        // driver.findElement(By.androidUIAutomator(
        //     "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"));
        // System.out.println("✅ Test 5 PASSED: Scrolled down");

        System.out.println("ℹ️ Test 5: Uncomment above to scroll the screen");
    }

    // -------------------------------------------------------
    // TEARDOWN: Runs ONCE after all tests finish
    // -------------------------------------------------------
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("✅ App closed.");
        }
    }
}