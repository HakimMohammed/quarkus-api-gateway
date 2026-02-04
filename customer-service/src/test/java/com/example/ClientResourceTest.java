package com.example;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientResourceTest {

    @Test
    @Order(1)
    void testListClients() {
        given().when().get("")
                .then()
                .statusCode(200)
                .body("$.size()", greaterThanOrEqualTo(3));
    }

    @Test
    void testGetClient() {
        given()
                .when().get("/1")
                .then()
                .statusCode(200)
                .body("name", is("Client A"));
    }

    @Test
    void testCreateClient() {
        Client client = new Client();
        client.name = "New Client";
        client.email = "new@example.com";
        client.address = "New Address";

        given()
                .contentType(ContentType.JSON)
                .body(client)
                .when().post("")
                .then()
                .statusCode(201)
                .body("name", is("New Client"));
    }

    @Test
    void testUpdateClient() {
        Client client = new Client();
        client.name = "Updated Client B";
        client.email = "clientb@example.com";
        client.address = "Addresse du client B";

        given()
                .contentType(ContentType.JSON)
                .body(client)
                .when().put("/2")
                .then()
                .statusCode(204);

        given()
                .when().get("/2")
                .then()
                .statusCode(200)
                .body("name", is("Updated Client B"));
    }

    @Test
    @Order(2)
    void testDeleteClient() {
        given()
                .when().delete("/3")
                .then()
                .statusCode(204);

        given()
                .when().get("/3")
                .then()
                .statusCode(404);
    }
}
