package com.teg.tryout.stepdefinitions;

import com.teg.tryout.pages.PremierTicketekPage;
import com.teg.tryout.utils.DriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;

public class NavigationSteps {

    private WebDriver driver = DriverFactory.getDriver();
    private PremierTicketekPage ticketekPage = new PremierTicketekPage(driver);

    @Given("I open the Ticketek event page")
    public void i_open_the_ticketek_event_page() {
        ticketekPage.open();
    }

    @Then("I Click {string}")
    public void i_click(String elementText) {
        ticketekPage.clickElementByText(elementText);
    }

    @Then("I Click the Escape button")
    public void i_click_Escape() {
        ticketekPage.clickEscape();
    }
	
    @Then("I Click the Enter button")
    public void i_click_Enter() {
        ticketekPage.clickEnter();
    }

    @Then("I Update {string} to {string}")
    public void i_update(String elementText, String newvalue) {
        ticketekPage.updateTextElement(elementText, newvalue);
    }

    @Then("I sleep for {int} seconds")
    public void i_sleep(int number) {
        ticketekPage.i_sleep(number);
    }


}
