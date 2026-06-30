package com.demo.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(io.qameta.allure.testng.AllureTestNg.class)
public class SimpleAllureTest {
    @Test
    public void basic() {
        Assert.assertTrue(true);
    }
}
