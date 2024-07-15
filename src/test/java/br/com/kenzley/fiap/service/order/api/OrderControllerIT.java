package br.com.kenzley.fiap.service.order.api;

import br.com.kenzley.fiap.service.order.utils.OrderHelper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OrderControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() throws IOException {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class RegisterOrder{
        @Test
        void mustAllowRegisterOrder() {

            var order = OrderHelper.gerarOrderRequestDTO();

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(order)
                    .when()
                    .post("/orders")
                    .then()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void mustGenerateExceptionWhenUpdateProductPayloadXML(){
            String xmlPayload =
                    "<orderRequestDTO>   <customer>\n" +
                            "      <cpf>11111111111</cpf>\n" +
                            "      <name>teste</name>\n" +
                            "   </customer>\n" +
                            "   <productsId>\n" +
                            "      <element>a0d94841-8ac4-4346-af6d-dbb82bd316cc</element>\n" +
                            "      <element>a0d94841-8ac4-4346-af6d-dbb82bd316cc</element>\n" +
                            "   </productsId>" +
                            "</orderRequestDTO>";

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(xmlPayload)
                    .when()
                    .post("/orders")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class FindOrder{
        @Test
        void mustAllowFindOrder() {

            when()
                    .get("/orders/{id}", 1L)
                    .then()
                    .statusCode(HttpStatus.OK.value());
        }
        @Test
        void mustGenerateExceptionWhenIdNotFound() {
            when()
                    .get("/orders/{id}", 60L)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }


    @Nested
    class ListOrders{
        @Test
        void mustAllowListOrders() {

            when()
                    .get("/orders")
                    .then()
                    .statusCode(HttpStatus.OK.value());
        }
    }


}