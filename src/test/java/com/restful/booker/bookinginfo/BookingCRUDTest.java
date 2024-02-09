package com.restful.booker.bookinginfo;

import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SerenityRunner.class)
public class BookingCRUDTest extends TestBase {
    static String username = "admin";
    static String password = "password123";

    static String firstname = TestUtils.getRandomValue() + "Mother";
    static String lastname = TestUtils.getRandomValue() + "Teresa";
    static int totalprice = 987;
    static boolean depositpaid = true;
    static String additionalneeds = "Dinner";

    public static String token;

    static int id;

    @Steps
    BookingSteps steps;                                                                                    //Creating the object
    @Steps
    AuthSteps step;

    @Title("This will create Token")
    @Test
    public void test001() {
        ValidatableResponse response = step.createToken(username, password);                             //Verification & Extraction
        token = response.extract().path("token");
        System.out.println(token);
    }

    @Title("Getting all booking Id")
    @Test
    public void test002() {
        steps.getAllBookingIds().statusCode(200);                                        //Status code verification
    }

    @Title("This will create new booking")
    @Test
    public void test003() {
        ValidatableResponse response = steps.createBooking(firstname, lastname, totalprice, depositpaid, additionalneeds).statusCode(200);
        id = response.extract().path("bookingid");
        System.out.println(id);
    }

    @Title("Verify the booking added to the application")
    @Test
    public void test004() {
        ValidatableResponse response = steps.getBookingById(id).statusCode(200);
        response.body("firstname", equalTo(firstname), "lastname", equalTo(lastname), "totalprice", equalTo(totalprice));
    }

    @Title("Update and verify booking information")
    @Test
    public void test005() {
        firstname = firstname + "_updated";
        lastname = lastname + "_updated";
        totalprice = 852;
        depositpaid = false;
        additionalneeds = "Breakfast";
        ValidatableResponse response = steps.updateBookingWithId(firstname, lastname, totalprice, depositpaid, additionalneeds, token, id).statusCode(200);
        response.body("firstname", equalTo(firstname), "lastname", equalTo(lastname), "totalprice", equalTo(totalprice));

    }

    @Title("Partially update and verify booking information")
    @Test
    public void test006() {
        firstname = firstname + "Modiji";
        lastname = lastname + "Ramar";
        ValidatableResponse response = steps.partiallyUpdateBooking(firstname, lastname, totalprice, depositpaid, additionalneeds, token, id).statusCode(200);
        response.body("firstname", equalTo(firstname), "lastname", equalTo(lastname));
    }

    @Title("Delete the booking and verify if the booking deleted")
    @Test
    public void test007() {
        steps.deleteBooking(token, id).statusCode(201);
        steps.getBookingById(id).statusCode(404);                                    //verifying deletion
    }
}