package com.restful.booker.cucumber.steps;

import com.restful.booker.bookinginfo.BookingSteps;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;

public class MyStepdefs {

    static ValidatableResponse response;

    @Steps
    BookingSteps bookingSteps;

    @When("User sends the GET request for booking endpoints")
    public void userSendsTheGETRequestForBookingEndpoints()
    {
        response = bookingSteps.getAllBookingIds();
    }

    @Then("User must get back with a valid status code {int}")
    public void userMustGetBackWithAValidStatusCode(int statusCode) {
        response.statusCode(statusCode);
    }
}


