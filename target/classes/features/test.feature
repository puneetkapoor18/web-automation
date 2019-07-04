Feature: Search for the Cheapest Fare
  The purpose of this feature is to test the search for cheapest fare.

  Scenario: FLight Search scenario
    Given a user is on home page
    And user enter the details
    And user click on the search
    Then search result should be in ascending order for Departure page
    And user select the "0" price for Departing Flight
    Then search result should be in ascending order for Arrival page
    And user select the "0" price for Inbound Flight
    Then user total price should be correct
    Then user session time out with message "Your session has expired" 
    

    

