package com.teg.tryout.pages;

import com.teg.tryout.utils.ElementFinder;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class PremierTicketekPage {

    private WebDriver driver;
    private ElementFinder finder;

    public PremierTicketekPage(WebDriver driver) {
        this.driver = driver;
        this.finder = new ElementFinder(driver);
    }

    private static final String URL = "https://preview.ticketek.com.au/shows/show.aspx?sh=ESPLOAD2"; //"https://premier.ticketek.com.au";

    /**
     * Open the Ticketek event page and wait until the menu button is present.
     */
    public void open() {
        driver.get(URL);
		
            System.out.println("Page1");
try {
    Thread.sleep(1000); // 20 seconds
} catch (InterruptedException e) {
    e.printStackTrace();
}
            System.out.println("Page2");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//a[@title='Show menu options']")
            ));
            System.out.println("Page opened successfully, menu button present.");
        } catch (TimeoutException e) {
            System.out.println("Warning: menu button not visible after 5s — JS may still be loading.");
        }
    }



    public WebElement findElementByText(String text) {
		
		waitForPageReady();
		
  		List<String> xpaths = List.of(
		    "//span[normalize-space()='" + text + "']/ancestor::*[self::button or @role='button']",
		    "//span[normalize-space(.)='" + text + "']/ancestor::*[self::button or @role='button']",
			"//li[contains(@class,'menu-parent')]//a[@title='" + text + "']",
			"//li[.//label[contains(normalize-space(.), '" + text + "')]]",
			"//a[@title='" + text + "']",
			"//a[normalize-space(@title)='" + text + "']",
			"//a[normalize-space(text())='" + text + "']",
			"//button[normalize-space(text())='" + text + "']",
			"//span[normalize-space(text())='" + text + "']",
			"//input[@id=//label[normalize-space()='" + text + "']/@for]",
			"//input[@type='submit' and @value='" + text + "']",
			"//input[@type='button' and @value='" + text + "']",
			"//label[normalize-space(.)='" + text + "']",
			"//div[normalize-space(.)='" + text + "']",
			"//label[contains(normalize-space(.), '" + text + "')]",
			//"//input[@id=//label[contains(normalize-space(.),'" + text + "')]/@for]"
			"//li[.//label[contains(normalize-space(.),'" + text + "')]]//input[@type='radio']"
		);


        boolean foundElement = false;
        long endTime = System.currentTimeMillis() + 5000; // total 5s wait

        for (String xpath : xpaths) {
            while (System.currentTimeMillis() < endTime) {
                try {
									
                    List<WebElement> candidates = driver.findElements(By.xpath(xpath));
                    if (candidates.isEmpty()) {
                        System.out.println("No candidates found for XPATH: " + xpath);
                        break;
                    }

                    for (WebElement el : candidates) {
                        if (el.isDisplayed() && el.isEnabled()) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
                            Thread.sleep(100); // allow JS animations
                            // el.click();
                            System.out.println("Found element '" + text + "' using XPATH: " + xpath);
                            foundElement = true;
							highlight(el);
							return el;
                            // return;
                        } else {
                            System.out.println("Candidate found but not clickable (displayed="
                                    + el.isDisplayed() + ", enabled=" + el.isEnabled() + "): " + xpath);
                        }
                    }

                    break; // done with this xpath, move to next if nothing clicked

                } catch (StaleElementReferenceException | InterruptedException e) {
                    System.out.println("Stale element for XPATH: " + xpath + " — retrying...");
                }

            }
        }
		
		System.out.println("Looking for element '" + text + "' inside Shadow DOM");
		WebElement shadowEl = findInShadowDomByText(text);
		if (shadowEl != null) {
			System.out.println("Found element '" + text + "' inside Shadow DOM");
			highlight(shadowEl);
			return shadowEl;
		}

        if (!foundElement) {
            throw new RuntimeException("Could not find clickable element for text: " + text);
        }
		return null;
    }

    public void clickElementByText(String text) {
		System.out.println("clickElementByText '" + text + "' clickElementByText");
        WebElement element = findElementByText(text);
		
		String href = element.getAttribute("href");
		if (href != null && href.startsWith("javascript")) {
			((JavascriptExecutor) driver).executeScript( "arguments[0].click();", element    );
		} else {
			element.click();
		}
		
    }

	private WebElement findInShadowDomByTextxx(String text) {
		
		try {
			Thread.sleep(3000); // 20 seconds
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Looking for element '" + text + "' inside Shadow DOM 2");
		
		WebElement host = driver.findElement(By.cssSelector("stx-button.showtime-button.hydrated"));

		SearchContext shadowRoot = host.getShadowRoot();

		WebElement button = shadowRoot.findElement(
			By.xpath(".//span[contains(normalize-space(.), '" + text + "')]/ancestor::button")
		);

		button.click();

		return null;
	}


    public void clickEnter() {
        WebElement menuToggle = driver.findElement(
           By.cssSelector("li.menu-parent > a")
        );
        menuToggle.click(); // ensures focus
        menuToggle.sendKeys(Keys.ENTER);
	}
	
    public void clickEscape() {
        driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
    }
	
    public void updateTextElement(String text, String newText) {

        WebElement foundElement = findElementByText(text);
		if (foundElement == null)
			            throw new RuntimeException("Could not find clickable element for text: " + text);
		
		foundElement.click();
		foundElement.sendKeys(newText);
    }

	private void waitForPageReady() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

   		System.out.println("waitForPageReady");	
		// If spinner is present, wait for it to disappear
		List<By> spinners = List.of(
			By.cssSelector(".spinner"),
			By.cssSelector(".loading"),
			By.cssSelector(".loader"),
			By.cssSelector(".is-loading")
		);

		for (By spinner : spinners) {
			if (!driver.findElements(spinner).isEmpty()) {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(spinner));
			}
		}
   		System.out.println("waitForPageReady");	
	}
	

	public void i_sleep(int number) {
		try {
			Thread.sleep(number * 1000); // 20 seconds
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	private WebElement findInShadowDomByText(String text) {

   		System.out.println("waitForPageReady" + text);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		/* Layer 1 */
		WebElement calendarHost =
			wait.until(ExpectedConditions.presenceOfElementLocated(
				By.cssSelector("stx-calendar")
			));

		SearchContext calendarShadow =
			calendarHost.getShadowRoot();

		/* Layer 2 */
		WebElement innerHost =
			calendarShadow.findElement(
				By.cssSelector("stx-calendar-inner#calendar-inner")
			);

		SearchContext innerShadow = innerHost.getShadowRoot();

		/* Find all buttons inside the calendar */
		List<WebElement> buttons =
			innerShadow.findElements(By.cssSelector("button"));

		for (WebElement button : buttons) {
			if (button.getText().contains(text)) {

				((JavascriptExecutor) driver)
					.executeScript("arguments[0].scrollIntoView({block:'center'});", button);
				return button;
			}
		}

		throw new NoSuchElementException("No button found with text: " + text);
	
	}	

public void highlight(WebElement element) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    String originalStyle = element.getAttribute("style");

    js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
            element, "border: 3px solid red; border-style: dashed;");

    // pause briefly to see it
    try { Thread.sleep(700); } catch (InterruptedException e) {}

    js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
            element, originalStyle);
}	

}

