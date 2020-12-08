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
import requests.GetRequest;
import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.collection.IsMapContaining.hasKey;

public class GetBookingTest extends BaseTest {

    static Booking booking;
    static Bookingdates bookingdates;
    static GetRequest getRequest;


    @BeforeAll
    static void getTestData() {

        bookingdates = new Bookingdates();
        booking = new Booking();
        getRequest = new GetRequest();

        JsonPath jsonResponse =getRequest.sendGetRequest("booking/2");

        booking.setFirstname(jsonResponse.get("firstname"));
        booking.setLastname(jsonResponse.get("lastname"));
        bookingdates.setCheckin(jsonResponse.get("bookingdates.checkin"));
        bookingdates.setCheckout(jsonResponse.get("bookingdates.checkout"));
        booking.setBookingdates(bookingdates);

    }

    @Test
    void getIdsOfAllBookings() {

        JsonPath jsonResponse = getRequest.sendGetRequest(BOOKING);
        List<Integer> bookings = jsonResponse.getList("bookingid");
        assertThat(bookings.size()).isEqualTo(10);

    }

    @ParameterizedTest
    @MethodSource("bookingDataSingleParameter")
    void getBookingIdByFilteringWithOneParameter(String queryParam, String value) {

        given()
                .queryParam(queryParam, value)
                .when()
                .get(BASE_URL + BOOKING)
                .then()
                .statusCode(200)
                .body("[0]", hasKey("bookingid"));

    }

    @ParameterizedTest
    @MethodSource("bookingDataWithMultipleParameters")
    void getBookingIdFilteringWithMultipleParams(String queryParam1, String value1, String queryParam2, String value2) {

        given()
                .queryParam(queryParam1, value1)
                .queryParam(queryParam2, value2)
                .when()
                .get(BASE_URL + BOOKING)
                .then()
                .statusCode(200)
                .body("[0]", hasKey("bookingid"));

    }

    private static Stream<Arguments> bookingDataSingleParameter() {

        return Stream.of(
                Arguments.of("firstname", booking.getFirstname()),
                Arguments.of("lastname", booking.getLastname()),
                Arguments.of("checkin", bookingdates.getCheckin()),
                Arguments.of("checkout", bookingdates.getCheckout())
        );
    }

    private static Stream<Arguments> bookingDataWithMultipleParameters() {

        return Stream.of(
                Arguments.of("firstname", booking.getFirstname(), "lastname", booking.getLastname()),
                Arguments.of("firstname", booking.getFirstname(), "checkin", bookingdates.getCheckin()),
                Arguments.of("firstname", booking.getFirstname(), "checkout", bookingdates.getCheckout()),
                Arguments.of("lastname", booking.getLastname(), "checkin", bookingdates.getCheckin()),
                Arguments.of("lastname", booking.getLastname(), "checkout", bookingdates.getCheckout()),
                Arguments.of("checkin", bookingdates.getCheckin(), "checkout", bookingdates.getCheckout())
        );
    }

}



