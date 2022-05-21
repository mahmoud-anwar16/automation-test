package com.dell.automation;

import com.dell.automation.util.Util;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class UserLoginTest extends BaseTest {

    private LoginPage login;
    private JSONObject json;


    @BeforeMethod
    public void setUp() throws IOException, ParseException {
        login = new LoginPage(driver);
        json = Util.getJsonObject("src/test/resources/test-data.json");
    }

    @Test(priority = 1, description = "valid login using correct username and password")
    public void validLogin() {
        login.login(json.get("usermame1").toString(), json.get("password1").toString());
        SoftAssert as = new SoftAssert();
        as.assertEquals(login.title.getText(), "PRODUCTS");
        login.sideMenu.click();
        login.logoutBtn.click();
    }

    @Test(priority = 2, description = "Invalid login using incorrect password")
    public void InvalidLoginWrongPassword() {
        login.login(json.get("usermame2").toString(), json.get("password2").toString());
        String actualTitle = login.errorMessage.getText();
        String expectedTitle = "Epic sadface: Username and password do not match any user in this service";
        Assert.assertEquals(actualTitle, expectedTitle);
        System.out.println(expectedTitle);
    }


    @Test(priority = 3, description = "Invaild login using incorrect username")
    public void InvalidLoginWrongUsername() {
        login.login(json.get("usermame3").toString(), json.get("password3").toString());
        String actualtitle = login.errorMessage.getText();
        String expectedtitle = "Epic sadface: Username and password do not match any user in this service";
        Assert.assertEquals(actualtitle, expectedtitle);
        System.out.println(expectedtitle);
    }


    @Test(priority = 4, description = "Invaild login using incorrect username&password")
    public void InvalidLoginWrongUserandPass() {

        login.login(json.get("usermame4").toString(), json.get("password4").toString());

        String actualtitle = login.errorMessage.getText();
        String expectedtitle = "Epic sadface: Username and password do not match any user in this service";
        Assert.assertEquals(actualtitle, expectedtitle);
        System.out.println(expectedtitle);

    }

    @Test(priority = 5, description = "Invaild login using incorrect username&password")
    public void InvalidLoginwithoutpass() {

        login.login(json.get("usermame4").toString(), "");

        String actualtitle = login.errorMessage.getText();
        String expectedtitle = "Epic sadface: Password is required";
        Assert.assertEquals(actualtitle, expectedtitle);
        System.out.println(expectedtitle);
    }

    @AfterMethod
    public void refresh() {
        driver.navigate().refresh();
    }
}
