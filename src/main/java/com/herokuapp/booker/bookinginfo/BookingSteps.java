package com.herokuapp.booker.bookinginfo;


import com.herokuapp.booker.constants.EndPoints;
import com.herokuapp.booker.model.BookingPojo;
import com.herokuapp.booker.params.Headers1;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class BookingSteps {
    @Step("Getting Authentication with username : {0}, password: {1}")
    public ValidatableResponse getAuthentication(String username, String password){
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setUsername(username);
        bookingPojo.setPassword(password);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bookingPojo)
                .when()
                .post("https://restful-booker.herokuapp.com/auth")
                .then();
    }

    @Step("Creating Booking with firstName : {0}, lastName: {1}, totalPrice {2} ,depositPaid {3} ,bookingDate {4}, additionalNeeds {5}")
    public ValidatableResponse createBooking(String firstname, String lastname, int totalprice, Boolean depositpaid, HashMap<Object, Object> bookingsDates, String additionalneeds) {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setUsername("admin");
        bookingPojo.setPassword("password123");
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingdates(bookingsDates);
        bookingPojo.setAdditionalneeds(additionalneeds);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bookingPojo)
                .when()
                .post()
                .then();
    }
    @Step("Get bookings with BookingId: {0}")
    public ValidatableResponse getBookingWithBookingId(int bookingId) {
        return SerenityRest.given().log().ifValidationFails()
                .pathParam("bookingID", bookingId)
                .when()
                .get(EndPoints.GET_SINGLE_USER_BY_ID)
                .then();
    }

    @Step("Update booking with bookingId: {0}, firstName: {1}, lastName: {2}, totalPrice: {3}, depositPaid: {4}, " +
            "checkIn: {5}, checkOut: {6} and additionalNeeds: {7}")
    public ValidatableResponse updateBooking(int bookingId, String firstName, String lastName, int totalPrice,
                                             boolean depositPaid, HashMap<Object, Object> bookingsDates,
                                             String additionalNeeds,String token) {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setUsername("admin");
        bookingPojo.setPassword("password123");
        bookingPojo.setFirstname(firstName);
        bookingPojo.setLastname(lastName);
        bookingPojo.setTotalprice(totalPrice);
        bookingPojo.setDepositpaid(depositPaid);
        bookingPojo.setBookingdates(bookingsDates);
        bookingPojo.setAdditionalneeds(additionalNeeds);
        return SerenityRest.given().log().all()
                .headers(Headers1.getHeaders(token))
                .pathParam("bookingID", bookingId)
                .body(bookingPojo)
                .when()
                .put(EndPoints.UPDATE_USER_BY_ID)
                .then();
    }
    @Step("Delete bookings with BookingId: {0}")
    public ValidatableResponse deleteBookingWithBookingId(int bookingId, String token) {
        return SerenityRest.given().log().ifValidationFails()
                .headers(Headers1.getHeaders(token))
                .pathParam("bookingID", bookingId)
                .when()
                .delete(EndPoints.DELETE_USER_BY_ID)
                .then();
    }

}
