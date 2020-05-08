//appium
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
//junit
import org.junit.*;
//selenium
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.w3c.dom.Document;
//other
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

//http://junit.sourceforge.net/javadoc/org/junit/Assert.html -junit info

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Main {
    @Rule
    public TestName name = new TestName();

    private static AndroidDriver<MobileElement> driver;
    Home home = new Home(driver);

    private static ExtentReports extent;
    private static ExtentTest liorTests;
    String imagePath = "C:\\Users\\טניה וליאור\\IdeaProjects\\MiniProjectAppium\\screenshots";

    public Main() throws Exception {
    }

    @BeforeClass
    public static void setUp() throws Exception {
        extent=new ExtentReports("C:\\Users\\טניה וליאור\\IdeaProjects\\MiniProjectAppium\\miniProReport.html");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android Device");
        capabilities.setCapability("appPackage", "com.sec.android.app.popupcalculator");
        capabilities.setCapability("appActivity", "com.sec.android.app.popupcalculator.Calculator");
        capabilities.setCapability("newCommandTimeout", 120);
        capabilities.setCapability("unicodekeyboard", true);  //נטרול מקלדת
        capabilities.setCapability("resetkeyboard", true); //נטרול מקלדת
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub/"), capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    } //

    @AfterClass
    public static void afterClass() {
        driver.closeApp();
        extent.flush();
    }

    @Before
    public void before() {
        startTest();
        System.out.println("start test " + name.getMethodName());
    }

    @After
    public void after() {
        endTest();
        System.out.println("end test " + name.getMethodName());
    }

    @Test
    public void test01_Add() throws Exception {
        int exp = Integer.parseInt(readFromFile("num1")) + Integer.parseInt(readFromFile("num2"));
        number_1or2("num1");
        home.add.click();
        number_1or2("num2");
        home.equal.click();
        String expected = String.valueOf(exp);
        String actual = home.screen.getText().replace(",", ""); //remove (,)

        try {
            Assert.assertEquals(expected, actual);
            liorTests.log(LogStatus.PASS, "The actual result: "+actual+" equals to the expected result: "+expected);
            printScreen();
        } catch (AssertionError asert) {
            liorTests.log(LogStatus.FAIL, "The actual result: "+actual+" no equals to the expected result: "+expected);
            printScreen();
        }
        home.clear.click();
    }

    @Test
    public void test02_minus() throws Exception {
        int number1 = Integer.parseInt(readFromFile("num1"));
        int number2 = Integer.parseInt(readFromFile("num2"));
        int exp = number1-number2;

        number_1or2("num1");
        home.minus.click();
        number_1or2("num2");
        home.equal.click();
        String expected = String.valueOf(exp);
        String actual = home.screen.getText().replace(",", ""); //remove (,)

        if(number2>number1){   //solves the minus (-) problem
            expected=expected.substring(1);
            actual=actual.substring(1);
        }

        try {
            Assert.assertEquals(expected, actual);
            liorTests.log(LogStatus.PASS, "The actual result equals to the expected result");
            printScreen();
        } catch (AssertionError asert) {
            liorTests.log(LogStatus.FAIL, "The actual result no equals to the expected result: "+asert);
            printScreen();
        }
        home.clear.click();
    }

    @Test
    public void test03_multiply() throws Exception {
        int exp = Integer.parseInt(readFromFile("num1")) * Integer.parseInt(readFromFile("num2"));
        number_1or2("num1");
        home.multiply.click();
        number_1or2("num2");
        home.equal.click();
        String expected = String.valueOf(exp);
        String actual = home.screen.getText().replace(",", ""); //remove (,)

        try {
            Assert.assertEquals(expected, actual);
            liorTests.log(LogStatus.PASS, "The actual result: "+actual+" equals to the expected result: "+expected);
            printScreen();
        } catch (AssertionError asert) {
            liorTests.log(LogStatus.FAIL, "The actual result: "+actual+" no equals to the expected result: "+expected);
            printScreen();
        }
        home.clear.click();
    }

    @Test
    public void test04_divide() throws Exception {
        long number1 = Long.parseLong(readFromFile("num1"));
        long number2 = Long.parseLong(readFromFile("num2"));
        double a = number1;
        double b = number2;
        if (number2 != 0) {
            double exp = a / b;
            exp = Double.parseDouble(new DecimalFormat("##.####").format(exp));
            number_1or2("num1");
            home.divide.click();
            number_1or2("num2");
            home.equal.click();
            String expected = String.valueOf(exp);
            String resultFromScreen = home.screen.getText().replace(",", ""); //remove ,

            double resultInDouble = Double.parseDouble(resultFromScreen);   //take only 4 digits after dot
            resultInDouble = Double.parseDouble(new DecimalFormat("##.####").format(resultInDouble));
            String actual = String.valueOf(resultInDouble);

            try {
                Assert.assertEquals(expected, actual);
                liorTests.log(LogStatus.PASS, "The actual result: "+actual+" equals to the expected result: "+expected);
                printScreen();
            } catch (AssertionError asert) {
                liorTests.log(LogStatus.FAIL, "The actual result: "+actual+" no equals to the expected result: "+expected);
                printScreen();
            }
            home.clear.click();

        } else {
            liorTests.log(LogStatus.FAIL, "The diveded number can not be zero! ("+number2+")");
            printScreen();
        }
    }

    public void number_1or2(String number) throws Exception {
        String num = readFromFile(number);
        for (int i = 0; i < num.length(); i++) {
            String a = num.substring(i, i + 1);
            switch (a) {
                case "0":
                    home.zero.click();
                    break;
                case "1":
                    home.one.click();
                    break;
                case "2":
                    home.two.click();
                    break;
                case "3":
                    home.tree.click();
                    break;
                case "4":
                    home.four.click();
                    break;
                case "5":
                    home.five.click();
                    break;
                case "6":
                    home.six.click();
                    break;
                case "7":
                    home.seven.click();
                    break;
                case "8":
                    home.eight.click();
                    break;
                case "9":
                    home.nine.click();
                    break;
            }
        }
    }

    public static String readFromFile(String keyData) throws Exception {
        File xmlFile = new File("C:\\Users\\טניה וליאור\\IdeaProjects\\MiniProjectAppium\\calculator.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        Document doc = dbBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(keyData).item(0).getTextContent();
    }

    public static String takeScreenShot(String ImagesPath, WebDriver driver) {   //take screenshot
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(ImagesPath + ".png");
        try {
            FileHandler.copy(screenShotFile, destinationFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ImagesPath + ".png";
    }

    private void printScreen() {
        liorTests.log(LogStatus.INFO, "details", liorTests.addScreenCapture(takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
    }

    private void startTest() {
        liorTests = extent.startTest(name.getMethodName());
        liorTests.log(LogStatus.INFO, name.getMethodName() + ": start test");
    }

    private void endTest() {
        extent.endTest(liorTests);
        liorTests.log(LogStatus.INFO, name.getMethodName() + ": end test");
    }
}


