package com.restful.booker.bookinginfo;

import com.restful.booker.constants.EndPoints;
import com.restful.booker.model.AuthPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

public class AuthSteps {
    @Step("Creating the token")                                                             //annotation in serenitybdd
    public ValidatableResponse createToken(String username, String password)
    {
        AuthPojo authPojo = new AuthPojo();
        authPojo.setUsername(username);                                                  //Setting parameters
        authPojo.setPassword(password);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(authPojo)
                .post(EndPoints.GET_TOKEN)
                .then().log().all();                                             //ValidatableResponse can do statuscode but not for String
    }
}
