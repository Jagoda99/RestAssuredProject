package GoogleMapsAPI;

import Files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class BasicsAPITests {

    @Test
    public  void baseTest() {

//Add Place

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(payload.addPlace())
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).extract().response().asString();

        JsonPath js = new JsonPath(response);
        String placeId = js.getString("place_id");


//Update place
        String newAddress = "70 Summer walk, USA";

        String r = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when().put("maps/api/place/update/json")
                .then().assertThat().statusCode(200).extract().response().asString();
        JsonPath js2 = new JsonPath(r);
        String res = js2.getString("msg");
        //.body("msg", equalTo("Address successfully updated"));
        System.out.println(res);


//Get place
       String getPlaceResponse = given().log().all().queryParam("key","qaclick123").queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().statusCode(200).extract().response().asString();
       JsonPath js1 = new JsonPath(getPlaceResponse);
       String actualAddress = js1.getString("address");

        Assert.assertEquals(actualAddress, newAddress);
    }
}
