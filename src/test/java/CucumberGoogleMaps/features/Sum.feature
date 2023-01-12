Feature: Validating Place API's
  @AddPlace @Regression
  Scenario Outline: Verify if Place is being Succesfully added using AddPlaceAPI

    Given Add Place Payload with "<name>"  "<language>" "<address>"
    When user calls "AddPlaceAPI" with "POST" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_Id created maps to "<name>" using "getPlaceAPI"

    Examples:
      |name  | language |address|
      |House |  English |Poland |

  @UpdatePlace @Regression
  Scenario: Verify if Update Place functionality is working

    Given Update Place Payload
    When user calls "updatePlaceAPI" with "PUT" http request
    Then the API call got success with status code 200
    And "msg" in response body is "Address successfully updated"

  @DeletePlace @Regression
  Scenario: Verify if Delete Place functionality is working

    Given Delete Place Payload
    When user calls "deletePlaceAPI" with "DELETE" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"














