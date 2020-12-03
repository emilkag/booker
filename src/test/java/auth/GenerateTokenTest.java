package auth;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.User;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.*;
import static org.apache.http.HttpStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.collection.IsMapContaining.hasKey;

public class GenerateTokenTest extends BaseTest {

    private static User user;

    @BeforeAll
    static void setUpUser(){
        user = new User();
    }

    @Test
    void generateValidToken() {

        user.setUsername("admin");
        user.setPassword("password123");

        given()
                .contentType(JSON)
                .body(user)
                .when()
                .post(BASE_URL + AUTH)
                .then()
                .statusCode(SC_OK)
                .body("$", hasKey("token"));
    }


    @ParameterizedTest
    @MethodSource("userCredentialsData")
    void tryToCreateUserWithInvalidCredentials(String username, String password){

        user.setUsername(username);
        user.setPassword(password);

        Response response = given()
                .contentType(JSON)
                .body(user)
                .when()
                .post(BASE_URL + AUTH)
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();

        JsonPath jsonResponse=  response.jsonPath();
        assertThat(jsonResponse.getString("reason")).contains("Bad credentials");
    }

    private static Stream<Arguments> userCredentialsData() {

        return Stream.of(
                Arguments.of("test", "test"),
                Arguments.of("", ""),
                Arguments.of("test$!", "test$!")
        );
    }
}
