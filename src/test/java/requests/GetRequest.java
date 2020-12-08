package requests;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;

public class GetRequest {

    public JsonPath sendGetRequest(String path) {

        JsonPath jsonResponse = given()
                .when()
                .get(BaseTest.BASE_URL + path)
                .then()
                .statusCode(SC_OK)
                .extract()
                .response()
                .jsonPath();

        return jsonResponse;

    }
}
