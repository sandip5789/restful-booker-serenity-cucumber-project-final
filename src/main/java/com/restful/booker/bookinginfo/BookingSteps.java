package com.restful.booker.bookinginfo;

import com.restful.booker.constants.EndPoints;
import com.restful.booker.model.BookingDates;
import com.restful.booker.model.BookingPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

public class BookingSteps {
    @Step("Creating booking with FirstName : {0},lastname : {1},totalprice : {2},depositpaid : {3},additionalneeds : {4}")
    public ValidatableResponse createBooking(String firstname, String lastname, int totalprice, boolean depositpaid, String additionalneeds)
    {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);                                                       //Post - Payload
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setAdditionalneeds(additionalneeds);

        BookingDates bookingDates = new BookingDates();
        bookingPojo.setBookingdates(bookingDates.getBookingDates());

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(bookingPojo)
                .when()
                .post(EndPoints.POST_CREATE_BOOKING)
                .then().log().all();
    }
    @Step("Getting all booking Id")
    public ValidatableResponse getAllBookingIds(){

        return SerenityRest.given().log().all()                                                      //No payload
                .contentType(ContentType.JSON)
                .when()
                .get(EndPoints.GET_ALL_BOOKING)
                .then().log().all();
    }
    @Step("Updating booking with FirstName : {0}, LastName : {1}, TotalPrice : {2}, DepositPaid : {3}, AdditionalNeeds : {4}, Token : {5}, BookingId : {6}")
    public ValidatableResponse updateBookingWithId(String firstname, String lastname, int totalprice, boolean depositpaid, String additionalneeds, String token, int bookingId ){

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setAdditionalneeds(additionalneeds);                                    //Put,Patch,Delete use token and id
        BookingDates bookingDates = new BookingDates();
        bookingPojo.setBookingdates(bookingDates.getBookingDates());

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("cookie","token=" + token )                                //After Post Auth, token is generated
                .pathParam("bookingId", bookingId)
                .body(bookingPojo)
                .when()
                .put(EndPoints.UPDATE_BOOKING_BY_Id)
                .then().log().all();
    }
    @Step("Partially update and verifying booking with FirstName : {0}, LastName : {1}, TotalPrice : {2}, DepositPaid : {3}, AdditionalNeeds : {4}, Token : {5}, BookingId : {6}")
    public ValidatableResponse partiallyUpdateBooking(String firstname, String lastname, int totalprice, boolean depositpaid, String additionalneeds, String token, int bookingId ){

        BookingDates bookingDates = new BookingDates();
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);                                      //Put,Patch,Delete has token and id
        bookingPojo.setAdditionalneeds(additionalneeds);
        bookingPojo.setBookingdates(bookingDates.getBookingDates());

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("cookie","token=" + token )
                .pathParam("bookingId", bookingId)                                //pathParam
                .body(bookingPojo)
                .when()
                .patch(EndPoints.UPDATE_BOOKING_BY_Id)
                .then().log().all();
    }
    @Step("Deleting booking information with Token : {0}, BookingId : {1}")
    public ValidatableResponse deleteBooking(String token, int bookingId){

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("cookie","token=" + token )
                .pathParam("bookingId", bookingId)
                .when()
                .delete(EndPoints.DELETE_BOOKING_BY_ID)
                .then().log().all();
    }
    @Step("verifying booking information with BookingId : {0}")
    public ValidatableResponse getBookingById(int bookingId){

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("bookingId", bookingId)
                .when()
                .get(EndPoints.GET_SINGLE_BOOKING_BY_Id)
                .then().log().all();
    }

}
