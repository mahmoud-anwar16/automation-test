package com.dell.automation;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class APICategoryTest {

    @BeforeMethod
    public void baseURI() {
        baseURI = "http://localhost:3030";
    }

    @Test(priority = 1, description = " Check that default limit is 10")
    public void defaultLimitSkip() {
        String response = given().queryParam("$limit", "10").queryParam("$skip", "0").when().get("/categories")
                .then().assertThat().statusCode(200).extract().asString();

        JsonPath js = new JsonPath(response);
        int actualLimit = js.getInt("data.size()");
        Assert.assertEquals(actualLimit, 10);
    }

    @Test(priority = 2, description = "validate that maximum limit is 25")
    public void maximLimit() {
        String r1 = given().queryParams("$limit", "25").queryParams("$skip", "0").when().get("/categories")
                .then().assertThat().statusCode(200).extract().asString();

        JsonPath js = new JsonPath(r1);
        int actualLimit = js.getInt("data.size()");
        Assert.assertEquals(actualLimit, 25);
    }

    @Test(priority = 3, description = "validate that limited number of categories = 1")
    public void minimumLimit() {
        String r2 = given().queryParams("$limit", "1").queryParams("$skip", "0").when().get("/categories")
                .then().assertThat().statusCode(200).extract().asString();

        JsonPath js = new JsonPath(r2);
        int actualLimit = js.getInt("data.size()");
        Assert.assertEquals(actualLimit, 1);
    }

    @Test(priority = 4, description = "verify that limit for numbers of categories cannot be negative numbers")
    public void negativeLimit() {
        String r4 = given().queryParams("$limit", "-1").queryParams("$skip", "0").when().get("/categories")
                .then().assertThat().statusCode(200).extract().asString();

        JsonPath js = new JsonPath(r4);
        int actualLimit = js.getInt("data.size()");
        Assert.assertEquals(actualLimit, 1);
    }

    @Test(priority = 5, description = "verify that limit for  numbers of categories cannot exceed 25")
    public void overLimit() {
        String r5 = given().queryParams("$limit", "30").queryParams("$skip", "0").when().get("/categories")
                .then().assertThat().statusCode(200).extract().asString();

        JsonPath js = new JsonPath(r5);
        int actualLimit = js.getInt("data.size()");
        Assert.assertEquals(actualLimit, 25);
    }

    @DataProvider(name = "categories")
    public Object[][] getData() {
        return new Object[][]{{"Category", "12345"}};
    }

    @DataProvider(name = "setname")
    public Object[] setname() {
        return new Object[]{"New Category"};
    }

    private String response_ID;

    public void setResponseID(String respID) {
        response_ID = respID;
    }

    @Test(priority = 6, dataProvider = "categories", description = " Adding categories by sending ID only")
    public void createCategoryWithoutName(String name, String id) {
       given().log().all().header("Content-Type", "application/json")
                .body(PayLoad.addCategories("", id))
                .when().post("/categories")
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST).extract().response().asString();
    }

    @Test(priority = 7, dataProvider = "categories", description = "Add a new  category")
    public void createCategory(String name, String id) {
        String r7 = given().log().all().header("Content-Type", "application/json")
                .body(PayLoad.addCategories(name, id))
                .when().post("/categories")
                .then().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = new JsonPath(r7);
        String ID = js.get("id");
        setResponseID(ID);
    }

    @Test(priority = 8, description = "Get the new added category details ")
    public void getCategoryById() {
        given().log().all()
                .when().get("/categories/" + response_ID)
                .then().log().all().assertThat().statusCode(200).contentType(ContentType.JSON).extract().response().asString();
    }

    @Test(priority = 9, dataProvider = "setname", description = "Update the category name")
    public void updateRequestById(String name) {
        String r9 = given().log().all().header("Content-Type", "application/json")
                .body(PayLoad.updateCategoriesname(name))
                .when().patch("/categories/" + response_ID)
                .then().assertThat().statusCode(200).contentType(ContentType.JSON).log().all().extract().response().asString();

        JsonPath jsonPath = new JsonPath(r9);
        String actualName = jsonPath.get("name");
        Assert.assertNotNull(actualName);
    }

   

    @Test(priority = 10, description = "Delete the add category ")
    public void deleteCategoryById() {
        given().log().all()
                .when().delete("/categories/" + response_ID)
                .then().assertThat().statusCode(200).log().all();
    }

    @Test(priority = 11, description = "Get the Deleted category by ID ")
    public void getCategoryWithInvalidID() {
        given().log().all()
                .when().get("/categories/" + response_ID)
                .then().log().all().assertThat().statusCode(404).contentType(ContentType.JSON).log().all();
    }
    @Test(priority = 12, description = "Delete the add category ")
    public void deleteCategoryByInvalidId() {
        given().log().all()
                .when().delete("/categories/" + response_ID)
                .then().assertThat().statusCode(404).log().all();
    }

}
