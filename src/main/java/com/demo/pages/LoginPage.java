package com.demo.pages;

import com.demo.locators.LoginPageLocators;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public void open() {
        page.navigate("https://www.saucedemo.com/");
    }

    public void login(String username, String password) {
        Locator usernameInput = page.locator(LoginPageLocators.USERNAME_INPUT);
        Locator passwordInput = page.locator(LoginPageLocators.PASSWORD_INPUT);
        Locator loginButton = page.locator(LoginPageLocators.LOGIN_BUTTON);

        usernameInput.fill(username);
        passwordInput.fill(password);
        loginButton.click();
    }

    public boolean isErrorVisible() {
        return page.locator(LoginPageLocators.ERROR_MESSAGE).isVisible();
    }

    public boolean isInventoryPageVisible() {
        return page.url().contains("inventory.html");
    }
}
