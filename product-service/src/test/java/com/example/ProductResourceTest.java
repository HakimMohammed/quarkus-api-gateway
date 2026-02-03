package com.example;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

@QuarkusTest
class ProductResourceTest {

    @Test
    void testListProducts() {
        given().when().get("product")
                .then()
                .statusCode(200)
                .body("$.size()", greaterThanOrEqualTo(3));
    }

    @Test
    void testGetProduct() {
        given()
                .when().get("product/1")
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
                .when().post("product")
                .then()
                .statusCode(201)
                .body("name", is("New Product"));
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
                .when().put("product/2")
                .then()
                .statusCode(204);

        given()
                .when().get("product/2")
                .then()
                .statusCode(200)
                .body("name", is("Produit Modifié"));
    }

    @Test
    @Transactional
    void testDeleteProduct() {
        given()
                .when().delete("product/3")
                .then()
                .statusCode(204);

        given()
                .when().get("product/3")
                .then()
                .statusCode(404);
    }
}