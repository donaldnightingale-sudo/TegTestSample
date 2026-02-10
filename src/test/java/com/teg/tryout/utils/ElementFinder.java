package com.teg.tryout.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class ElementFinder {

    private WebDriver driver;

    public ElementFinder(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Tries multiple XPATHs to find an element.
     * Waits up to totalTimeout seconds once.
     */
    public WebElement findElementWithFallback(List<String> xpaths, int totalTimeoutSeconds) {

        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(totalTimeoutSeconds))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(org.openqa.selenium.NoSuchElementException.class);

        for (String xpath : xpaths) {
            try {
                WebElement element = wait.until(new Function<WebDriver, WebElement>() {
                    @Override
                    public WebElement apply(WebDriver driver) {
                        return driver.findElement(By.xpath(xpath));
                    }
                });
                // found it, return immediately
                return element;
            } catch (TimeoutException e) {
                // try next xpath
            }
        }

        throw new RuntimeException("Element not found for any of the provided XPATHs");
    }
}
