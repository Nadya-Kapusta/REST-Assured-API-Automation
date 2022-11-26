package REST_Assured_Automation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class API_TEST_Automation {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    public void Create() { //post
        String requestParams = "{\"name\":\n" +
                "\"morpheus\",\n" +
                "\"job\":\n" +
                "\"leader\"}";
        RequestSpecification request = RestAssured.given();
        request.header("Content-type", "application/json"); // Add the Json to the body of the request
        request.body(requestParams); // Post the request and check the response

        Response response = request.post("/api/users");

        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test
    public void Register_success() { //post
//          String requestParams = "{\n" +
//                "    \"email\": \"eve.holt@reqres.in\",\n" +
//                "    \"password\": \"pistol\"\n" +
//                "}";
        JSONObject requestParams = new JSONObject();     //safer to convert data into JSONObject to
        // not to confuse with strings
        requestParams.put("email", "eve.holt@reqres.in");
        requestParams.put("password", "pistol");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestParams.toJSONString())
                .when()
                .post("/api/register")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void Register_Unsuccess() { //post
        String requestParams = "{\n" +
                "\n" +
                "\"email\":\n" +
                "\"sydney@fife\"\n" +
                "}";
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestParams)
                .when()
                .post("/api/register")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), 400);
    }

    @Test
    public void Login_Success() { //post

        JSONObject requestParams = new JSONObject();     //safer to convert data into JSONObject to
        // not to confuse with strings
        requestParams.put("email", "eve.holt@reqres.in");
        requestParams.put("password", "cityslicka");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestParams.toJSONString())
                .when()
                .post("/api/login")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void Login_Unsuccess() { //post
        String requestParams = "{\"email\":\n" +
                "\"peter@klaven\"\n" +
                "}";

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestParams)
                .when()
                .post("/api/login")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), 400);
    }

    @Test
    public void List_Users() { //get
        Response response = given()
                .contentType(ContentType.JSON)
                .param("postId", "2")
                .when()
                .get("/api/users?page=2")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void List_Resource() { //get
        Response response = given()
                .contentType(ContentType.JSON)
                .param("postId", "2")
                .when()
                .get("/api/unknown")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void Update_PUT() { //PUT
        String requestParams = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestParams)
                .when()
                .put("/api/users/2")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void Update_PATCH() { //PATCH
        String requestParams = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestParams)
                .when()
                .patch("/api/users/2")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void Delete() { //DELETE
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/users/2")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), 204);
    }


}
