package CucumberGoogleMaps.StepDefinitions;

import CucumberGoogleMaps.Resources.APIResources;
import CucumberGoogleMaps.Resources.TestDataBuild;
import CucumberGoogleMaps.Resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class StepDefinition extends Utils {
    RequestSpecification request;
    ResponseSpecification responseSpec;
    Response response;
    TestDataBuild data = new TestDataBuild();
    static String place_id;


    @Given("Add Place Payload with {string}  {string} {string}")
    public void add_Place_Payload(String name, String language, String address) throws IOException {
        request = given().spec(requestSpecification())
                .body(data.addPlacePayLoad(name, language, address));
    }

    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String method) {
        APIResources resourceAPI = APIResources.valueOf(resource);
        System.out.println(resourceAPI.getResource());

        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if (method.equalsIgnoreCase("POST"))
            response = request.when().post(resourceAPI.getResource());
        else if (method.equalsIgnoreCase("GET"))
            response = request.when().get(resourceAPI.getResource());
        else if (method.equalsIgnoreCase("PUT"))
            response = request.when().put(resourceAPI.getResource());
        else if (method.equalsIgnoreCase("DELETE"))
            response = request.when().delete(resourceAPI.getResource());

        System.out.println(response.asString());
    }

    @Then("the API call got success with status code {int}")
    public void the_API_call_got_success_with_status_code(Integer int1) {
        assertEquals(response.getStatusCode(), 200);
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String keyValue, String expectedValue) {
        assertEquals(getJsonPath(response, keyValue), expectedValue);
    }

    @Then("verify place_Id created maps to {string} using {string}")
    public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws IOException {
        place_id = getJsonPath(response, "place_id");
        request = given().spec(requestSpecification()).queryParam("place_id", place_id);
        user_calls_with_http_request(resource, "GET");
        String actualName = getJsonPath(response, "name");
        assertEquals(actualName, expectedName);
    }
    @Given("Update Place Payload")
    public void update_Place_Payload() throws IOException {
        request = given().spec(requestSpecification()).body(data.updatePlacePayload(place_id));
    }
    @Given("Delete Place Payload")
    public void delete_place_Payload() throws IOException {
        request = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
    }

}

