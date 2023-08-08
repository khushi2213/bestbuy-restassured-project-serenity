package com.bestbuy.info;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class StoreSteps {

    @Step("Creating store with name: {0}, type {1}, address {2}, address2 {3}, city {4}, state {5}, zip {6}, lat {7}, lng {8}, hours {9}")
    public ValidatableResponse createStore(String name, String type, String address, String address2, String city, String state,
                                           String zip, int lat, int lng, String hours) {

        StorePojo storesPojo = StorePojo.getStoresPojo(name, type, address, address2, city, state, zip, lat, lng, hours);

        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .body(storesPojo)
                .when()
                .post()
                .then();


    }


    @Step("Getting the product information with storeId: {0}")
    public ValidatableResponse getStoreById(int id) {
        return SerenityRest.given().log().all()
                .pathParam("id", id)
                .when()
                .get(EndPoints.GET_SINGLE_STORE_BY_ID)
                .then();
    }


    @Step("Updating store information with id: {0}, name {1}")
    public ValidatableResponse updateStore(int id, String name) {

        name = name + "_123";

        StorePojo storesPojo = StorePojo.getStorePojo(name);


        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(storesPojo)
                .when()
                .patch(EndPoints.UPDATE_SINGLE_STORE_BY_ID)
                .then().log().all();

    }

    @Step("Deleting store information with id: {0}")
    public ValidatableResponse deleteStore(int id) {
        return SerenityRest.given().log().all()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_SINGLE_STORE_BY_ID)
                .then();
    }

    @Step("Getting the Store information with name : {0}")
    public HashMap<String, Object> getStoreInfoByName(String name) {
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
}
