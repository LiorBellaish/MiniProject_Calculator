import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Home
{
    public static WebDriver driver;

    @FindBy(id ="com.sec.android.app.popupcalculator:id/calc_edt_formula" )
    WebElement screen;
    @FindBy(id = "com.sec.android.app.popupcalculator:id/calc_keypad_btn_add")
    WebElement add;
    @FindBy(id = "com.sec.android.app.popupcalculator:id/calc_keypad_btn_equal")
    WebElement equal;
    @FindBy(id ="com.sec.android.app.popupcalculator:id/calc_keypad_btn_sub" )
    WebElement minus;
    @FindBy(id = "com.sec.android.app.popupcalculator:id/calc_keypad_btn_mul")
    WebElement multiply;
    @FindBy(id = "com.sec.android.app.popupcalculator:id/calc_keypad_btn_div")
    WebElement divide;
    @FindBy(id = "com.sec.android.app.popupcalculator:id/calc_keypad_btn_clear")
    WebElement clear;
    //numbers
    @FindBy(id="com.sec.android.app.popupcalculator:id/calc_keypad_btn_00")
    WebElement zero;
    @FindBy(id="com.sec.android.app.popupcalculator:id/calc_keypad_btn_01")
    WebElement one;
    @FindBy(id="com.sec.android.app.popupcalculator:id/calc_keypad_btn_02")
    WebElement two;
    @FindBy(id="com.sec.android.app.popupcalculator:id/calc_keypad_btn_03")
    WebElement tree;
    @FindBy(id="com.sec.android.app.popupcalculator:id/calc_keypad_btn_04")
    WebElement four;
    @FindBy(id="com.sec.android.app.popupcalculator:id/calc_keypad_btn_05")
    WebElement five;
    @FindBy(id="com.sec.android.app.popupcalculator:id/calc_keypad_btn_06")
    WebElement six;
    @FindBy(id="com.sec.android.app.popupcalculator:id/calc_keypad_btn_07")
    WebElement seven;
    @FindBy(id="com.sec.android.app.popupcalculator:id/calc_keypad_btn_08")
    WebElement eight;
    @FindBy(id="com.sec.android.app.popupcalculator:id/calc_keypad_btn_09")
    WebElement nine;


    public Home (WebDriver driver) throws Exception {  //constructor
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }




}
