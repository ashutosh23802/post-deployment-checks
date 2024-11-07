package io.cucumber.skeleton.tests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.skeleton.setup.RequestInitializer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;

public class PostDeploymentCheck extends RequestInitializer {

    public PostDeploymentCheck(){
        super();
    }

    @Then("Api green version response and blue version response should match")
    public void apiGreenVersionResponseAndBlueVersionResponseShouldMatch() {
        Object greenVersionResponse = RestAssured.given()
                .baseUri(this.getApiEndpoint())
                .header("debug_tiaa_bg","green")
                .get()
                .asString();

        Object blueVersionResponse = RestAssured.given()
                .baseUri(this.getApiEndpoint())
                .header("debug_tiaa_bg","blue")
                .get()
                .asString();

        Assertions.assertEquals(greenVersionResponse,blueVersionResponse);
    }
}
