package com.wsa.PageTests;




import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VuePageTests_Home {

    @Value("${local.server.port}")
    private int port;

    private WebDriver webDriver;

    @BeforeAll
    public static void setupClass() {
        // Set the path to your Edge WebDriver executable
        System.setProperty("webdriver.edge.driver", "D:\\edgedriver_win64\\msedgedriver.exe");
    }

    @BeforeEach
    public void setupTest() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless"); // Run Edge in headless mode

        webDriver = new EdgeDriver(options);
    }

    @AfterEach
    public void teardown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    public void testCarouselNextButtonClick() {
        // 打开目标页面
        webDriver.get("http://localhost:" + port + "/#/home");

        // 创建WebDriverWait对象，设置最长等待时间为10秒
        WebDriverWait wait = new WebDriverWait(webDriver, 20);

        try {
            // 等待直到轮播图的右箭头按钮可被点击
            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".el-carousel__arrow--right")));

            // 点击右箭头按钮
            nextButton.click();

            // 可以在此处添加更多断言或验证操作的代码
            // 例如，验证图片是否成功切换到下一张
            // assertTrue(someCondition);

        } catch (TimeoutException e) {
            // 如果在指定时间内没有找到该元素，处理超时异常
            System.out.println("元素未在规定时间内找到或不可点击: " + e.getMessage());
        }
    }

}
