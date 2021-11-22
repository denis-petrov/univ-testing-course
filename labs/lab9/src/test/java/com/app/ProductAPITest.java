package com.app;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.*;

public class ProductAPITest {

    private final static String BASE_URL = "http://91.210.252.240:9010/api/";
    private final static String PAYLOADS_DIR = "src/test/resources/payloads/";
    private final static String PRODUCTS_DIR = PAYLOADS_DIR + "products/";
    private final static String PRODUCTS_VALID_DIR = PRODUCTS_DIR + "valid/";
    private final static String PRODUCTS_INVALID_DIR = PRODUCTS_DIR + "invalid/";
    private final static String PRODUCTS_INVALID_ADDABLE_DIR = PRODUCTS_INVALID_DIR + "addable/";
    private final static String PRODUCTS_INVALID_NOT_ADDABLE_DIR = PRODUCTS_INVALID_DIR + "notAddable/";

    private final static String GET_ENDPOINT = "products";
    private final static String ADD_ENDPOINT = "addproduct";
    private final static String UPDATE_ENDPOINT = "editproduct";
    private final static String DELETE_BY_ID_ENDPOINT = "deleteproduct?id=";

    private static final String RESPONSE_STATUS = "status";
    private static final Integer SUCCESS_STATUS = 1;
    private static final String ID = "id";


    /* GET */

    @Test
    public void should_ReturnProducts_When_RequestToProducts() {
        Response resp = given().
            baseUri(BASE_URL).
        when().
            get(GET_ENDPOINT).
        andReturn();

        assertEquals(resp.statusCode(), SC_OK);
        ensureProductsAreValid(resp.body().asString());
    }



    /* ADD */

    @DataProvider(name = "valid-product")
    public Object[][] validProduct() {
        return new Object[][]{
                {new File(PRODUCTS_VALID_DIR + "defaultProduct1.json")},
                {new File(PRODUCTS_VALID_DIR + "defaultProduct2.json")},
        };
    }

    @Test(dataProvider = "valid-product")
    public void givenValidProduct_whenRequestIsExecuted_thenResponseStatusAndCodeAreSuccess(File product) {
        Integer receivedId = given().
            baseUri(BASE_URL).
            contentType(ContentType.JSON).
            body(product).
        when().
            post(ADD_ENDPOINT).
        then().
            assertThat().
            statusCode(SC_OK).
            and().
            assertThat().
            body(RESPONSE_STATUS, Matchers.is(SUCCESS_STATUS)).
        extract().
            path(ID);

        var newProduct = productById(receivedId);
        assertNotNull("A new Product from file: " + product.getName() + "; have not been added.", newProduct);
        ensureProductIsValid(newProduct);
    }

    @DataProvider(name = "not-addable-invalid-product")
    public Object[][] notAddableInvalidProduct() {
        return new Object[][]{
                {new File(PRODUCTS_INVALID_NOT_ADDABLE_DIR + "invalidFieldsType.json")},
                {new File(PRODUCTS_INVALID_NOT_ADDABLE_DIR + "categoryIdLessThan1.json")},
                {new File(PRODUCTS_INVALID_NOT_ADDABLE_DIR + "categoryIdMoreThan15.json")},
        };
    }

    @Test(dataProvider = "not-addable-invalid-product")
    public void givenProductWithChangeInRequiredFields_whenRequestIsExecuted_thenResponseStatusAndCodeAreFailed(File product) {
        assertThrows(() -> {
            Response res = given().
               baseUri(BASE_URL).
               contentType(ContentType.JSON).
               body(product).
            when().
               post(ADD_ENDPOINT).
            then().
               extract().response();

            Integer id = res.then().
                    extract().
                    path(ID);

            ensureProductByIdExist(id);
        });
    }


    @DataProvider(name = "addable-invalid-product")
    public Object[][] addableInvalidProduct() {
        return new Object[][]{
                {new File(PRODUCTS_INVALID_ADDABLE_DIR + "hitLessThan0.json")},
                {new File(PRODUCTS_INVALID_ADDABLE_DIR + "hitMoreThan2.json")},
                {new File(PRODUCTS_INVALID_ADDABLE_DIR + "statusLessThan0.json")},
                {new File(PRODUCTS_INVALID_ADDABLE_DIR + "statusMoreThan1.json")},
        };
    }

    @Test(dataProvider = "addable-invalid-product")
    public void givenAddableInvalidProduct_whenRequestIsExecuted_thenResponseStatusAndCodeAreFailed(File product) {
        Response res = given().
            baseUri(BASE_URL).
            contentType(ContentType.JSON).
            body(product).
        when().
            post(ADD_ENDPOINT).
        then().
            assertThat().
            statusCode(SC_OK).
            and().
            assertThat().
            body(RESPONSE_STATUS, Matchers.is(SUCCESS_STATUS)).
        extract().
            response();


        Integer id = res.then().
        extract().
            path(ID);

        var newProduct = productById(id);
        assertNotNull("A new Product from file: " + product.getName() + "; have not been added.", newProduct);
        ensureProductIsValid(newProduct);
    }




    /* UPDATE */

    @DataProvider(name = "valid-product-for-update")
    public Object[][] validProductForUpdate() {
        return new Object[][]{
                {new File(PRODUCTS_VALID_DIR + "defaultProduct1Update.json")},
                {new File(PRODUCTS_VALID_DIR + "defaultProduct2Update.json")},
        };
    }

    @Test(dataProvider = "valid-product-for-update")
    public void givenValidProductForUpdate_whenRequestIsExecuted_thenProductByIdMustBeEquals(File productFile) throws IOException {
        String productFileContent = Files.asCharSource(productFile, Charsets.UTF_8).read();
        var productBeforeUpdate = getProductFromString(productFileContent);
        var productId = productBeforeUpdate.getId();

        given().
            baseUri(BASE_URL).
            contentType(ContentType.JSON).
            body(productFile).
        when().
            post(UPDATE_ENDPOINT).
        then().
            assertThat().
            statusCode(SC_OK).
            and().
            assertThat().
            body(RESPONSE_STATUS, Matchers.is(SUCCESS_STATUS));

        var productAfterUpdate = productById(productId);
        assertNotNull("A new Product from file: " + productFile.getName() + "; have not been updated.", productAfterUpdate);
        ensureProductIsValid(productAfterUpdate);
        assertNotEquals(productBeforeUpdate, productAfterUpdate);
    }



    /* DELETE */

    @DataProvider(name = "product-for-delete-by-id")
    public Object[][] productForDeleteById() {
        return new Object[][]{
                {306},
                {307},
                {308},
                {309},
                {310},
                {311},
                {312},
                {313},
                {314},
                {315},
        };
    }

    @Test(dataProvider = "product-for-delete-by-id")
    public void givenIdOfExistingProduct_whenRequestIsExecuted_thenResponseStatusAndCodeAreValid(Integer id) {
        ensureProductByIdExist(id);

        given().
            baseUri(BASE_URL).
        when().
            get(DELETE_BY_ID_ENDPOINT + id).
        then().
            assertThat().
            statusCode(SC_OK).
            assertThat().
            body(RESPONSE_STATUS, Matchers.is(SUCCESS_STATUS));

        assertNull("Product by id = " + id + "; Have not been deleted", productById(id));
    }






    /* PRIVATE */
    private void ensureProductsAreValid(String body) throws IllegalArgumentException {
        var products = getProductsFromBody(body);
        products.forEach(this::ensureProductIsValid);
    }

    private void ensureProductIsValid(Product product) {
        ProductValidator.ensureProductIsValid(product);
    }

    private void ensureProductByIdExist(Integer id) {
        var productForTest = productById(id);
        if (productForTest == null) {
            throw new IllegalArgumentException("Product with id = " + id + "; does not exist");
        }
    }

    private Product productById(Integer id) {
        String body = given().
            baseUri(BASE_URL).
        when().
            get(GET_ENDPOINT).
        then().
            extract().
            body().asString();
        return findProductByIdFromBody(body, id);
    }

    private Product findProductByIdFromBody(String body, Integer id) {
        var products = getProductsFromBody(body);
        return products.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private List<Product> getProductsFromBody(String body) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Product>>(){}.getType();
        return gson.fromJson(body, listType);
    }

    private Product getProductFromString(String source) {
        Gson gson = new Gson();
        return gson.fromJson(source, Product.class);
    }
}
