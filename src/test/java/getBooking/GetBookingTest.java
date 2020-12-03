package getBooking;

import base.BaseTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Booking;
import model.Bookingdates;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.collection.IsMapContaining.hasKey;

public class GetBookingTest extends BaseTest {

    static int bookingId = 2;
    static Booking booking;
    static Bookingdates bookingdates;


    @BeforeAll
    static void getTestData() {

        bookingdates = new Bookingdates();
        booking = new Booking();

        Response response = given()
                .when()
                .get(BASE_URL + BOOKING + "/" + bookingId)
                .then()
                .extract()
                .response();

        JsonPath jsonResponse = response.jsonPath();
        booking.setFirstname(jsonResponse.get("firstname"));
        booking.setLastname(jsonResponse.get("lastname"));
        bookingdates.setCheckin(jsonResponse.get("bookingdates.checkin"));
        bookingdates.setCheckout(jsonResponse.get("bookingdates.checkout"));
        booking.setBookingdates(bookingdates);

    }

    @Test
    void getIdsOfAllBookings() {

        Response response = given()
                .when()
                .get(BASE_URL + BOOKING)
                .then()
                .statusCode(200)
                .body("$", hasKey("bookingid"))
                .extract()
                .response();

        JsonPath jsonResponse = response.jsonPath();
        List<Integer> bookings = jsonResponse.getList("bookingid");
        bookingId = bookings.get(bookings.size() - 2);

        assertThat(bookings.size()).isEqualTo(10);

    }

    @ParameterizedTest
    @MethodSource("bookingData")
    void getBookingIdByFiltering(String queryParam, String booking) {

        given()
                .queryParam(queryParam, booking)
                .when()
                .get(BASE_URL + BOOKING)
                .then()
                .statusCode(200)
                .body("[0]", hasKey("bookingid"));

    }

    private static Stream<Arguments> bookingData() {

        return Stream.of(
                Arguments.of("firstname", booking.getFirstname()),
                Arguments.of("lastname", booking.getLastname()),
                Arguments.of("checkin", bookingdates.getCheckin()),
                Arguments.of("checkout", bookingdates.getCheckout())
        );
    }

}
