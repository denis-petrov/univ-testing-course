package com.app;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testng.Assert.assertNotEquals;
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
    private final static String DELETE_BY_ID_ENDPOINT = "deleteproduct?id=ID";

    private static final String RESPONSE_STATUS = "status";
    private static final Integer SUCCESS_STATUS = 1;
    private static final String ID = "id";

    @BeforeMethod
    public void beforeMethod(Method method) {
        Test test = method.getAnnotation(Test.class);
        System.out.println("Test name is " + test.testName());
        System.out.println("Test description is " + test.description());
    }


    /* GET */

    /**
     * Get request to GET_ENDPOINT and validate that response status is SC_OK
     * Ensure that received products are valid
     */
    @Test(description = "Should return products when request to products")
    public void getAllProducts() {
        Response resp = given()
                .baseUri(BASE_URL)
                .when()
                .get(GET_ENDPOINT)
                .andReturn();

        assertEquals("Response status code = " + resp.statusCode() + "; does not equals to OK = " + SC_OK + "\n",
                resp.statusCode(), SC_OK
        );
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

    /**
     * Execute POST request to ADD_ENDPOINT and validate that response status is SC_OK
     * and RESPONSE_STATUS in equals to SUCCESS_STATUS
     * Extract id from response and ensure that product by this id was added
     *
     * @param product json for POST
     */
    @Test(
            dataProvider = "valid-product",
            description = "Given valid product when request is executed then response status and code must be success"
    )
    public void ableToAddValidProduct(File product) {
        Integer receivedId = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(product)
                .when()
                .post(ADD_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body(RESPONSE_STATUS, Matchers.is(SUCCESS_STATUS))
                .extract()
                .path(ID);

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

    /**
     * Should throw exception in next chain of responsibility:
     * Execute POST request to ADD_ENDPOINT, extract id from request and ensure that product was added
     * Able to throw exception in post execution or if server API has been changed and not addable product
     * now should be addable
     *
     * @param product json for POST
     */
    @Test(
            dataProvider = "not-addable-invalid-product",
            description = "Given product with changes in required fields API must not add this product"
    )
    public void notAbleToAddNotAddableProduct(File product) {
        assertThrows(Throwable.class, () -> {
            Response res = given()
                    .baseUri(BASE_URL)
                    .contentType(ContentType.JSON)
                    .body(product)
                    .when()
                    .post(ADD_ENDPOINT)
                    .then()
                    .extract()
                    .response();

            Integer id = res
                    .then()
                    .extract()
                    .path(ID);

            ensureProductByIdExist(id);
        }, "Not addable invalid product was added, looks like API has been changed");
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

    /**
     * Execute POST request to ADD_ENDPOINT and validate that response status is SC_OK
     * and RESPONSE_STATUS in equals to SUCCESS_STATUS
     * Extract id from response and ensure that product by this id was added
     *
     * @param product json for POST
     */
    @Test(
            dataProvider = "addable-invalid-product",
            description = "Given addable invalid product than API should add this product"
    )
    public void ableToAddAddableInvalidProduct(File product) {
        Response res = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(product)
                .when()
                .post(ADD_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body(RESPONSE_STATUS, Matchers.is(SUCCESS_STATUS))
                .extract()
                .response();


        Integer id = res
                .then()
                .extract()
                .path(ID);

        var newProduct = productById(id);
        assertNotNull("A new Product from file: " + product.getName() + "; have not been added.", newProduct);
        ensureProductIsValid(newProduct);
    }


    @DataProvider(name = "product-for-test-alias")
    public Object[][] productForTestAlias() {
        return new Object[][]{
                {new File(PRODUCTS_VALID_DIR + "productForAliasTest.json"),}
        };
    }

    /**
     * Execute 2 POST requests to ADD_ENDPOINT with equals product
     * should add second product with different alias
     *
     * @param product json for POST
     */
    @Test(
            dataProvider = "product-for-test-alias",
            description = "Given valid products than API should add second product with different alias"
    )
    public void aliasChangeInAPI(File product) {
        Response resFirst = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(product)
                .when()
                .post(ADD_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body(RESPONSE_STATUS, Matchers.is(SUCCESS_STATUS))
                .extract()
                .response();


        Integer idFirst = resFirst
                .then()
                .extract()
                .path(ID);

        var firstProduct = productById(idFirst);

        Response resSecond = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(product)
                .when()
                .post(ADD_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body(RESPONSE_STATUS, Matchers.is(SUCCESS_STATUS))
                .extract()
                .response();


        Integer idSecond = resSecond
                .then()
                .extract()
                .path(ID);

        var secondProduct = productById(idSecond);

        var firstAlias = firstProduct.getAlias();
        var secondAlias = secondProduct.getAlias();

        assertNotEquals(firstAlias, secondAlias,
                "Alias of first product and of second, should be different, looks like API has been changed"
        );
        var secondAliasPostfix = secondAlias.substring(secondAlias.length() - 2);
        var secondAliasPrefix = secondAlias.substring(0, secondAlias.length() - 2);
        assertEquals(
                "Second alias has not been appended with -0 as API should, looks like API has been changed",
                "-0", secondAliasPostfix
        );
        assertEquals(
                "Second alias prefix does not equals to first one, looks like API has not been added postfix to second alias",
                firstAlias, secondAliasPrefix
        );
    }




    /* UPDATE */

    @DataProvider(name = "valid-product-for-update")
    public Object[][] validProductForUpdate() {
        return new Object[][]{
                {new File(PRODUCTS_VALID_DIR + "defaultProduct1Update.json")},
        };
    }


    /**
     * Execute POST request to UPDATE_ENDPOINT and validate that response status is SC_OK
     * and RESPONSE_STATUS in equals to SUCCESS_STATUS
     * <p>
     * Extract id from response and ensure that product by this id was updated
     * by compare product before update and after, there are must be different
     *
     * @param productFile File with json for POST
     */
    @Test(
            dataProvider = "valid-product-for-update",
            description = "Given valid product for update after request product before update and after must be different"
    )
    public void ableToUpdateProduct(File productFile) throws IOException {
        String productFileContent = Files.asCharSource(productFile, Charsets.UTF_8).read();
        var productBeforeUpdate = getProductFromString(productFileContent);
        var productId = productBeforeUpdate.getId();

        given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(productFile)
                .when()
                .post(UPDATE_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body(RESPONSE_STATUS, Matchers.is(SUCCESS_STATUS));

        var productAfterUpdate = productById(productId);
        assertNotNull(
                "A new Product from file: " + productFile.getName() + "; have not been updated.",
                productAfterUpdate
        );
        ensureProductIsValid(productAfterUpdate);
        assertNotEquals(
                productBeforeUpdate, productAfterUpdate,
                "Product before update and after are equals, that means the API has not been updated product"
        );
    }




    /* DELETE */

    @DataProvider(name = "product-for-delete-by-id")
    public Object[][] productForDeleteById() {
        return new Object[][]{
                {330},
                {331},
        };
    }

    /**
     * Execute Get request to DELETE_ENDPOINT with id of a product for delete
     * and validate that response status is SC_OK
     * and RESPONSE_STATUS in equals to SUCCESS_STATUS
     * <p>
     * After request ensure that product with given id no longer exist
     *
     * @param id Integer id of product for delete
     */
    @Test(
            dataProvider = "product-for-delete-by-id",
            description = "Given id of existing product the response status and code are must be valid"
    )
    public void givenIdOfExistingProduct_whenRequestIsExecuted_thenResponseStatusAndCodeAreValid(Integer id) {
        ensureProductByIdExist(id);

        given()
                .baseUri(BASE_URL)
                .when()
                .get(DELETE_BY_ID_ENDPOINT.replace("ID", id.toString()))
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .assertThat()
                .body(RESPONSE_STATUS, Matchers.is(SUCCESS_STATUS));

        assertNull("Product by id = " + id + "; Have not been deleted", productById(id));
    }


    /* PRIVATE */

    /**
     * Ensure that all products from response body,
     * if one of a product is invalid than throw IllegalArgumentException
     *
     * @param body String body from response
     * @throws IllegalArgumentException if a product is invalid
     */
    private void ensureProductsAreValid(String body) throws IllegalArgumentException {
        List<Product> products = new Gson().fromJson(body, new TypeToken<ArrayList<Product>>() {
        }.getType());
        products.forEach(this::ensureProductIsValid);
    }

    /**
     * Ensure that a product is valid,
     * if the product is invalid than throw IllegalArgumentException
     *
     * @param product Product
     * @throws IllegalArgumentException if a product is invalid
     */
    private void ensureProductIsValid(Product product) throws IllegalArgumentException {
        ProductValidator.ensureProductIsValid(product);
    }

    /**
     * @param id Integer
     * @throws IllegalArgumentException if the product is not found by id
     */
    private void ensureProductByIdExist(Integer id) {
        var productForTest = productById(id);
        if (productForTest == null) {
            throw new IllegalArgumentException("Product with id = " + id + "; does not exist");
        }
    }


    /**
     * Request to server and get body as a json
     * Search in json for product by id
     *
     * @param id Integer
     * @return Product if product was found and null if not
     */
    private Product productById(Integer id) {
        String body = given()
                .baseUri(BASE_URL)
                .when()
                .get(GET_ENDPOINT)
                .then()
                .extract()
                .body().asString();
        return findProductByIdFromBody(body, id);
    }


    /**
     * Find product by id from body as a json
     *
     * @param body String as json
     * @param id   Integer
     * @return Product if product was found and null if not
     */
    private Product findProductByIdFromBody(String body, Integer id) {
        List<Product> products = new Gson().fromJson(body, new TypeToken<ArrayList<Product>>() {
        }.getType());
        return products.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }


    /**
     * Parse product from source from received json
     *
     * @param source Product as json
     * @return Product from source
     */
    private Product getProductFromString(String source) {
        return new Gson().fromJson(source, Product.class);
    }
}
