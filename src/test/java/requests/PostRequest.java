package requests;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.*;

public class PostRequest{

    public JsonPath sendPostRequest(String path, Object object){

        JsonPath jsonResponse = given()
                .contentType(JSON)
                .body(object)
                .when()
                .post(BaseTest.BASE_URL + path)
                .then()
                .statusCode(SC_OK)
                .extract()
                .response()
                .jsonPath();

        return jsonResponse;
    }
}
