Feature: API endpoints work

  Background:
    Given the API is up

  Scenario: List items
    When I GET "/items"
    Then the response status is 200
    And the response is JSON
    And the JSON array has at least 0 elements

  Scenario: Create an item
    When I POST "/items" with JSON:
      """
      { "name": "salary", "wage": 12.34 }
      """
    Then the response status is 201
    And the response is JSON
    And the JSON at "$.name" equals "widget"
    And I save the JSON at "$.id" as "createdId"

  Scenario: Fetch created item by id
    Given I have a saved value "createdId"
    When I GET "/items/{id}" using path vars:
      | id | {{createdId}} |
    Then the response status is 200
    And the JSON at "$.name" equals "widget"
