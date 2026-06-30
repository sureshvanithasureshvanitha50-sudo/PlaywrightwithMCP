package com.demo.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(io.qameta.allure.testng.AllureTestNg.class)

public class PlaywrightFormFlowTest {
    private Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
    }

    @Test
    public void fillDataEntryForm() {
        Allure.step("Open form page", () -> {
            page.navigate("https://testautomationpractice.blogspot.com/");
            page.waitForLoadState();
        });

        Allure.step("Verify page title and heading", () -> {
            Assert.assertTrue(page.title().contains("Automation Testing Practice"));
            page.locator("h2, h3, h4").filter(new Locator.FilterOptions().setHasText("Data Entry Form")).first().waitFor();
        });

        Allure.step("Fill form fields", () -> {
            page.locator("input[name='name'], input[id='name'], input[placeholder*='Name' i], input[aria-label*='Name' i]").first().fill("Yamini");
            page.locator("input[name='email'], input[id='email'], input[placeholder*='Email' i], input[aria-label*='Email' i]").first().fill("email");
            page.locator("input[name='phone'], input[id='phone'], input[placeholder*='Phone' i], input[aria-label*='Phone' i]").first().fill("1234567890");
            page.locator("textarea#textarea, textarea[placeholder*='Address' i], textarea[aria-label*='Address' i]").first().fill("pkpuram");
        });

        Allure.step("Select gender and checkboxes", () -> {
            page.locator("input[type='radio'][value='female'], input[type='radio'][name*='female' i], label:has-text('Female')").first().click();
            page.locator("input[type='checkbox'][value='monday'], input[type='checkbox'][name*='monday' i]").first().check();
            page.locator("input[type='checkbox'][value='wednesday'], input[type='checkbox'][name*='wednesday' i]").first().check();
            page.locator("input[type='checkbox'][value='thursday'], input[type='checkbox'][name*='thursday' i]").first().check();
        });

        Allure.step("Choose options", () -> {
            page.locator("select#country").selectOption("france");
            page.locator("select#colors").selectOption(new String[]{"yellow", "red", "white"});
            page.locator("select#animals").selectOption(new String[]{"cat", "dog", "rabbit"});
        });

        Allure.step("Fill dates and submit", () -> {
            page.locator("input[type='text'], input[type='date']").nth(0).fill("06/30/2026");
            page.locator("input[type='text'], input[type='date']").nth(1).fill("30/06/2026");
            page.locator("button.submit-btn").click();
        });

        Allure.addAttachment("Page title", "text/plain", page.title());
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
