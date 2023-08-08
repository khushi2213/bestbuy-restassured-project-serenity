package com.bestbuy.info;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ProductSteps {

    @Step("Creating product with name: {0}, type {1}, price {2}, upc {3}, shipping {4}, description {5}, manufacturer {6}, model {7} and url {8}")
    public ValidatableResponse createProduct(String name, String type, double price, String upc, int shipping, String description,
                                             String manufacturer, String model, String url) {

        ProductPojo productsPojo = ProductPojo.getProductsPojo(name, type, price, upc, shipping, description, manufacturer, model, url);

        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .body(productsPojo)
                .when()
                .post()
                .then();


    }

    @Step("Getting the Product information with name : {0}")
    public HashMap<String, Object> getProductInfoByName(String name) {
        String s1 = "data.findAll{it.name == '";
        String s2 = "'}.get(0)";
        return SerenityRest.given()
                .queryParam("name", name)
                .when()
                .get()
                .then().log().all().statusCode(200)
                .extract()
                .path(s1 + name + s2);


    }


    @Step("Getting the product information with productId: {0}")
    public ValidatableResponse getProductById(int id) {
        return SerenityRest.given().log().all()
                .pathParam("id", id)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }


    @Step("Updating product information with id: {0}, name {1}, type {2}")
    public ValidatableResponse updateProduct(int id, String name, String type) {

        ProductPojo productsPojo = ProductPojo.getProductPojo(name, type);

        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(productsPojo)
                .when()
                .patch(EndPoints.UPDATE_SINGLE_PRODUCT_BY_ID)
                .then().log().all();

    }

    @Step("Deleting product information with id: {0}")
    public ValidatableResponse deleteProduct(int id) {
        return SerenityRest.given().log().all()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_SINGLE_PRODUCT_BY_ID)
                .then();
    }


}
