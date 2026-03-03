package com.bdg.api.stepdefinitions;

import com.bdg.api.interactions.GenericRest;
import com.bdg.api.models.UserData;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import static net.serenitybdd.screenplay.actors.OnStage.*;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.*;

public class UserManagementStepDefinitions {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("the actor has access to the url {string}")
    public void setBaseUrl(String baseUrl) {
        theActorCalled("Actor").whoCan(CallAnApi.at(baseUrl));
    }

    @When("he consults the list of users")
    public void getListUsers() {
        theActorInTheSpotlight().attemptsTo(
                GenericRest.execute("GET", "/users", null) // JSONPlaceholder
        );
    }

    @Then("the response should contain at least one user with id {int}")
    public void verifyUserInList(int id) {
        theActorInTheSpotlight().should(
                seeThatResponse("Verify user ID in list", res ->
                        res.statusCode(200)
                                .body("id", hasItem(id))
                )
        );
    }

    @When("he registers a user with name {string} and email {string}")
    public void registerUser(String name, String email) {
        UserData user = UserData.builder()
                .name(name)
                .email(email)
                .build();

        theActorInTheSpotlight().attemptsTo(
                GenericRest.execute("POST", "/users", user)
        );
    }

    @Then("the response body should contain the email {string}")
    public void verifyEmailInResponse(String email) {
        theActorInTheSpotlight().should(
                seeThatResponse("Verify created user email", res ->
                        res.statusCode(201)
                                .body("email", is(email))
                )
        );
    }

    @When("he updates the name to {string} and the username to {string}")
    public void updateUserInfo(String name, String username) {
        UserData updateData = UserData.builder()
                .name(name)
                .username(username)
                .build();

        theActorInTheSpotlight().attemptsTo(
                GenericRest.execute("PUT", "/users/2", updateData)
        );
    }

    @Then("the response body should contain the updated name {string}")
    public void verifyNameInResponse(String name) {
        theActorInTheSpotlight().should(
                seeThatResponse("Verify updated name", res ->
                        res.statusCode(200)
                                .body("name", is(name))
                )
        );
    }

    @When("he deletes the user with id {int}")
    public void deleteUser(int id) {
        theActorInTheSpotlight().attemptsTo(
                GenericRest.execute("DELETE", "/users/" + id, null)
        );
    }

    @Then("the response code should be {int}")
    public void verifyStatusCode(int code) {
        theActorInTheSpotlight().should(
                seeThatResponse("Verify HTTP status code", res -> res.statusCode(code))
        );
    }
}