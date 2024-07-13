package br.com.kenzley.fiap.service.order.bdd;

import br.com.kenzley.fiap.service.order.api.response.OrderResponseDTO;
import br.com.kenzley.fiap.service.order.utils.OrderHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class StepDefinition {

    private Response response;
    private OrderResponseDTO orderResponseDTO;
    private final String ENDPOINT_API_ORDER = "http://localhost:8081/orders";

    @When("register a new order")
    public OrderResponseDTO register_a_new_order() {
        var orderRequest = OrderHelper.gerarOrderRequestDTO();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequest)
                .when()
                .post(ENDPOINT_API_ORDER);

        return response.then().extract().as(OrderResponseDTO.class);

    }

    @Then("the order is registered successfully")
    public void the_order_is_registered_successfully() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @Then("must be presented")
    public void must_be_presented() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/order.schema.json"));
    }

    @Given("that a order has already been published")
    public void that_a_order_has_already_been_published() {
         orderResponseDTO = register_a_new_order();
    }

    @When("search for the message")
    public void search_for_the_message() {
       response = when()
                 .get(ENDPOINT_API_ORDER + "/{id}", orderResponseDTO.getId());
    }

    @Then("the order is displayed successfully")
    public void the_order_is_displayed_successfully() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/order.schema.json"));
    }

    @When("make request to change message")
    public void make_request_to_change_message() {
        response = given()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(orderResponseDTO)
                .when()
                    .put(ENDPOINT_API_ORDER + "/{id}", orderResponseDTO.getId());
    }
    @Then("the order is successfully shown")
    public void the_order_is_successfully_shown() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }
}
