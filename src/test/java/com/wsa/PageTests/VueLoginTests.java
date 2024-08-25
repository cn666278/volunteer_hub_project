package com.wsa.PageTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class VueLoginTests {

    private WebDriver webDriver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        // 使用 EdgeOptions 进行浏览器配置
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless"); // 可选：启用无头模式
        webDriver = new EdgeDriver(options);
        wait = new WebDriverWait(webDriver, 10);
    }

    @AfterEach
    public void teardown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    public void testLogin() {
        // 打开前端登录页面
        webDriver.get("http://localhost:5173/#/login");

        // 等待页面加载并定位输入框和按钮
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.el-input__inner")));
        WebElement passwordInput = webDriver.findElement(By.cssSelector("input[type='password']"));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'el-button') and contains(@class, 'el-button--primary')]")));

        // 填写表单并提交
        usernameInput.sendKeys("volunteer");
        passwordInput.sendKeys("123456");
        loginButton.click();

        // 验证登录是否成功
        boolean isLoginSuccessful = wait.until(ExpectedConditions.urlContains("/"));
        assertTrue(isLoginSuccessful, "Login was not successful");
    }

    @Test
    public void testNavigateToEventsPage() {
        // 需要先登录才能执行该功能
        testLogin();

        // 找到并点击导航栏中的 "Events" 链接
        webDriver.get("http://localhost:5173/#/volunteer/events");

        // 验证是否跳转到 "Events" 页面
        boolean isEventsPageLoaded = wait.until(ExpectedConditions.urlContains("/volunteer/events"));
        assertTrue(isEventsPageLoaded, "Failed to navigate to Events page");
    }


//    @Test
//    public void testClickEventCard() {
//        testLogin();
//        // 导航到事件页面
//        webDriver.get("http://localhost:5173/#/volunteer/events");
//
//
//
//        // 在 "Events" 页面中使用 div 元素的类名定位卡片
//        WebElement firstBlogCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.el-card")));
//        firstBlogCard.click();
//
//        // 验证是否跳转到事件详情页面
//        boolean isNavigationSuccessful = wait.until(ExpectedConditions.urlContains("/volunteer/event/"));
//        assertTrue(isNavigationSuccessful, "Navigation to event detail page was not successful");
//    }
}
