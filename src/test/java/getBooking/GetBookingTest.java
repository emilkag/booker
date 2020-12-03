package getBooking;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.collection.IsMapContaining.hasKey;

public class GetBookingTest extends BaseTest {

    static int bookingId = 1;

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
        bookingId = bookings.get(bookings.size()-2);

    }

    @Test
    void


}
