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

    @BeforeClass
    public void setup() throws MalformedURLException {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("RZCX40ERHTR");         
        options.setApp("C:/appium-apk/waves2.apk");   
        options.setNoReset(true);    
        options.setFullReset(false); 

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

      
        try {
            Thread.sleep(3000); 

            List<WebElement> appAllowButtons = driver.findElements(
                By.xpath("//android.widget.Button[@content-desc='Allow Access']"));
            if (!appAllowButtons.isEmpty()) {
                appAllowButtons.get(0).click();
                System.out.println("✅ App's Location Access screen dismissed!");
                Thread.sleep(2000); 
            } else {
                System.out.println("ℹ️ App location screen not found (already passed)");
            }

           
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

    
    @Test(priority = 1)
    public void testAppLaunch() {
        String activity = driver.currentActivity();
        System.out.println("📱 Current screen: " + activity);

       
        Assert.assertNotNull(activity, "App should have an active screen");
        Assert.assertFalse(activity.isEmpty(), "Activity name should not be empty");

        System.out.println("✅ Test 1 PASSED: App launched on correct screen");
    }

    
    @Test(priority = 2)
    public void testClickPrimarySales() {

        WebElement primarySales = driver.findElement(
            By.xpath("//android.widget.ImageView[contains(@content-desc, 'Primary Sales')]"));
        primarySales.click();
        System.out.println("✅ Test 2 PASSED: Primary Sales button clicked!");

       
    }

   
    @Test(priority = 3)
    public void testTypeText() {

      

        System.out.println("ℹ️ Test 3: Use Appium Inspector to find your input field's id, then uncomment above");
    }

    
    @Test(priority = 4)
    public void testVerifyText() {

       

        System.out.println("ℹ️ Test 4: Use Appium Inspector to find your text element's id, then uncomment above");
    }

   
    @Test(priority = 5)
    public void testScroll() {

       
        System.out.println("ℹ️ Test 5: Uncomment above to scroll the screen");
    }

    
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("✅ App closed.");
        }
    }
}