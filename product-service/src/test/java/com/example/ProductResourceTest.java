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
class ProductResourceTest {

    @Test
    @Order(1)
    void testListProducts() {
        given().when().get("")
                .then()
                .statusCode(200)
                .body("$.size()", greaterThanOrEqualTo(3));
    }

    @Test
    void testGetProduct() {
        given()
                .when().get("/1")
                .then()
                .statusCode(200)
                .body("name", is("Produit A"));
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.name = "Nouveau Produit";
        product.price = 15;
        product.description = "Description du nouveau Produit";

        given()
                .contentType(ContentType.JSON)
                .body(product)
                .when().post("")
                .then()
                .statusCode(201)
                .body("name", is("Nouveau Produit"));
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.name = "Produit Modifié";
        product.price = 15;
        product.description = "Description du Produit Modifié";

        given()
                .contentType(ContentType.JSON)
                .body(product)
                .when().put("/2")
                .then()
                .statusCode(204);

        given()
                .when().get("/2")
                .then()
                .statusCode(200)
                .body("name", is("Produit Modifié"));
    }

    @Test
    @Order(2)
    void testDeleteProduct() {
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