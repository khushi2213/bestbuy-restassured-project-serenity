package com.bestbuy.crudtest;


import com.bestbuy.info.StoreSteps;
import com.bestbuy.testbase.StoreTestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)

public class StoreCRUDTest extends StoreTestBase {
    static String name = "Priya" + TestUtils.getRandomValue();
    static String type = "SoftGood";
    static String address = "Flat 54, Hatton road, HA0 4AH";
    static String address2 = "Ghareli park";
    static String city = "Surat";
    static String state = "Gujarat";
    static String zip = "39002";
    static int lat = 10;
    static int lng = 5;
    static String hours = "24";
    static int storeId;



    @Steps
    StoreSteps storeSteps;


    @Title("This will create a new store")
    @Test
    public void test001() {

        ValidatableResponse response = storeSteps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours)
                .log().all().statusCode(201);
        storeId = response.extract().path("id");

    }

    @Title("Verify if the store was added")
    @Test
    public void test002() {
        //verify if the name was added
        HashMap<String, Object> storeMap = storeSteps.getStoreInfoByName(name);
        storeId = (int) storeMap.get("id");
        Assert.assertThat(storeMap, hasValue(name));

    }


    @Title("This will retrieve store with id")
    @Test
    public void test003() {

        //verify that the store has been added
        storeSteps.getStoreById(storeId).log().all().statusCode(200);

    }


    @Title("This will update store with id")
    @Test
    public void test004() {
        //update store
        storeSteps.updateStore(storeId, name).statusCode(200);

        //verify if the store was updated
        HashMap<String, Object> storeMap = storeSteps.getStoreInfoByName(name);
        Assert.assertThat(storeMap, hasValue(name));
    }


    @Title("This will delete store with id")
    @Test
    public void test005() {
        //delete the store
        storeSteps.deleteStore(storeId).statusCode(200);

        //verify that the store is deleted
        storeSteps.getStoreById(storeId).statusCode(404);

    }

}
