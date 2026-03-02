Feature: User management in JSONPlaceholder

  @ListarUsuarios
  Scenario: List users and validate that the response contains at least one user
    Given the actor has access to the url "https://jsonplaceholder.typicode.com"
    When he consults the list of users
    Then the response code should be 200
    And the response should contain at least one user with id 7

  @RegistrarUsuarios
  Scenario: Register a new user and validate the response
    Given the actor has access to the url "https://jsonplaceholder.typicode.com"
    When he registers a user with name "Elmer Kraken" and email "elmer@testing.com"
    Then the response code should be 201
    And the response body should contain the email "elmer@testing.com"

  @ActualizarUsuarios
  Scenario: Update existing user information
    Given the actor has access to the url "https://jsonplaceholder.typicode.com"
    When he updates the name to "Kraken Master" and the username to "kraken.qa"
    Then the response code should be 200
    And the response body should contain the updated name "Kraken Master"

  @EliminarUsuarios
  Scenario: Delete a user
    Given the actor has access to the url "https://jsonplaceholder.typicode.com"
    When he deletes the user with id 7
    Then the response code should be 200