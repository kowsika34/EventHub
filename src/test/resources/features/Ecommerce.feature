Feature: EventHub API Automation

  Background: 
    Given User has base URI
    Then User has valid authentication token

  #########################################################
  # AUTHENTICATION APIs
  #########################################################
 #Scenario: Register new user successfully
   Scenario: Register all users
  Given User prepares registration payload
    When User sends POST request for all users
               # Given User prepares registration payload for user 0
               # When User sends POST request for 2 users
    When Response status code should be 201
              #Then Response body is "Created"
              #Then Response should contain success as true
    Then Response should contain token
   
   # Scenario: Login successfully
 #   Given User prepares login payload
   # When User send POST request to "login"
  #  Then Response should contain authentication token
  #  When Responses status code should be 200
  #  Then Response should contain user details

 # Scenario: Get logged in user details
 #   When User sends GET request to "getuser"
  #  Then Response status codes should be 200
  #  When Response should contains user email
  #  Then Response should contain user id

  #########################################################
  # EVENT APIs
  #########################################################
Scenario: Create event successfully
   Given User prepares event payload
    When User sends POST request to "eventcreated"
    Then Response status code should be 201
    And Response should contain success as true
    And Response should contain event id
    And Response should contain event title

  Scenario: Get all events
    When User send GET request to "getallevents"
    Then Response the status code should be 200
    Then Response should contain event list
    
    
  Scenario: Get event details by valid event ID
  
    Given User send GET request to  att "geteventbyid"
    When Response status code should be at 200
    Then Response should contain  at event id
    Then Response should contain  at event title
    
    
  Scenario: Update event by id

  Given User creates event for multiple users
  When User updates event with id
  Then Response status should be 200
  And Response should contain updated event title
  
  #########################################
  #Booking Details
  ############################################
  
   Scenario: Create Booking details
    Given User prepares payload
    When User sends post requested to send "bookingdetails"
    When User got Response code should be 201
    Then User response should contain event id
    Then User response should contain bookingref 
    Then User response should contain bookingid
    
 Scenario: Getting All bookings
    Given User prepares payload
    When User send get request too getallbooking
    Then Response should be 200 at statuscode
    Then Response contain pagination list
  
  
  Scenario: Getting All bookings By id
  When User send get request to get book by "id"
  Then Response should be statuscodes at 200
  Then Response should be contain booking id
  
  
  Scenario: Getting All bookings By Ref
  When User send get request book by "Ref"
  Then Response should be statuscode att- 200
  Then Response should be contain booking Ref
  
  Scenario: Deleted Booking Successfully
  Given User send Bookig id 
  When User send Delete request to cancel the booking
  Then Response should be 200
  Then Response should contain booking cancelled message
  
  Scenario: Cancel Event successfully
    Given Event id is available
    When User sends DELETE request to "cancelthebooking"
    Then Response status code should be  at 200
    And Response should contain cancellation message
