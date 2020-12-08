package auth;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import requests.PostRequest;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class GenerateTokenTest extends BaseTest {

    private static User user;
    static PostRequest postRequest;

    @BeforeAll
    static void setUpUser(){

        user = new User();
        postRequest = new PostRequest();
    }

    @Test
    void generateValidToken() {

        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);

        JsonPath jsonResponse = postRequest.sendPostRequest(AUTH, user);

        TOKEN = jsonResponse.getString("token");
        assertThat(jsonResponse.getString("token")).isNotEmpty();

    }

    @ParameterizedTest
    @MethodSource("userCredentialsData")
    void tryToCreateUserWithInvalidCredentials(String username, String password){

        user.setUsername(username);
        user.setPassword(password);

        JsonPath jsonResponse = postRequest.sendPostRequest(AUTH, user);

        assertThat(jsonResponse.getString("reason")).contains("Bad credentials");

    }

    private static Stream<Arguments> userCredentialsData() {

        return Stream.of(
                Arguments.of("test", PASSWORD),
                Arguments.of(USERNAME, "test"),
                Arguments.of("test$!", "test$!"),
                Arguments.of("", PASSWORD),
                Arguments.of(USERNAME, "")
        );
    }
}
