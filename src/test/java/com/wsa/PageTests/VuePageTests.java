package com.wsa.PageTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VuePageTests {

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
//        WebDriverManager.edgedriver().setup(); // 自动管理EdgeDriver
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless"); // 启用无头模式
        webDriver = new EdgeDriver(options);
    }

    @AfterEach
    public void teardown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }



    @Test
    public void testEventDetailsNavigation() {
        webDriver.get("http://localhost:" + port + "/#/volunteer/events");
        WebElement firstBlogCard = webDriver.findElement(By.cssSelector(".blog-card"));
        firstBlogCard.click();
        assertTrue(webDriver.getCurrentUrl().contains("/EventDetail"));
    }

    @Test
    public void testDateFormatting() {
        webDriver.get("http://localhost:" + port + "/#/volunteer/events");
        WebElement dateElement = webDriver.findElement(By.cssSelector(".date-details"));
        assertTrue(dateElement.getText().matches("\\w+ \\d{1,2}, \\d{4}"));
    }
}
