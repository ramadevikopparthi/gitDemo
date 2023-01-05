Feature: Validating Place API's
@AddPlace
Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
	Given Add Place Payload with "<name>" "<language>" "<address>"
	When user calls "AddPlaceAPI" with "post" http request
	Then the API call got success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify place_Id created maps to "<name>" using "getPlaceAPI"
	
	Examples:
		|name   |language|address						|
		|AAhouse|English |World cross center|
		#|BBhouse|Spanish |Sea cross center  |
		

@DeletePlace		
Scenario:
   Given DeletePlace Payload
   When user calls "deletePlaceAPI" with "post" http request
   Then the API call got success with status code 200
   And "status" in response body is "OK"  		