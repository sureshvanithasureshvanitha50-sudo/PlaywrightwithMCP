package com.demo.tests;

import com.demo.data.TestData;
import com.demo.pages.LoginPage;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(io.qameta.allure.testng.AllureTestNg.class)

public class LoginTest {
    private Playwright playwright;
    private Browser browser;
    private Page page;
    private LoginPage loginPage;
    private TestData testData;

    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        page = browser.newPage();
        loginPage = new LoginPage(page);
        testData = new TestData("testdata/login-data.properties");
    }

    @Test
    public void validLoginShouldOpenInventory() {
        loginPage.open();
        loginPage.login(testData.getValue("valid.username"), testData.getValue("valid.password"));
        Assert.assertTrue(loginPage.isInventoryPageVisible(), "Inventory page should be visible after successful login");
    }

    @Test
    public void lockedUserShouldShowError() {
        loginPage.open();
        loginPage.login(testData.getValue("locked.username"), testData.getValue("locked.password"));
        Assert.assertTrue(loginPage.isErrorVisible(), "Error message should be visible for locked user");
    }

    @AfterMethod
    public void tearDown() {
        if (page != null) {
            page.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
