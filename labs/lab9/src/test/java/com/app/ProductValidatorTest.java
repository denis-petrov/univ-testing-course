package com.app;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertThrows;
import static org.testng.Assert.assertTrue;

public class ProductValidatorTest {

    private static final Product VALID_PRODUCT = new Product()
            .setId(1)
            .setCategoryId(2)
            .setStatus(1)
            .setHit(1);


    /* CATEGORY ID */

    @DataProvider(name = "invalid-category-id")
    public Object[][] invalidCategoryId() {
        return new Object[][]{
                {new Product().clone(VALID_PRODUCT).setCategoryId(null)},
                {new Product().clone(VALID_PRODUCT).setCategoryId(-1)},
                {new Product().clone(VALID_PRODUCT).setCategoryId(16)},
        };
    }

    @Test(dataProvider = "invalid-category-id")
    public void should_ThrowIllegalArgumentException_When_CategoryIdIsInvalid(Product product) {
        assertThrows(IllegalArgumentException.class, () -> ProductValidator.ensureProductIsValid(product));
    }

    @DataProvider(name = "valid-category-id")
    public Object[][] validCategoryId() {
        return new Object[][]{
                {new Product().clone(VALID_PRODUCT).setCategoryId(1)},
                {new Product().clone(VALID_PRODUCT).setCategoryId(15)},
        };
    }

    @Test(dataProvider = "valid-category-id")
    public void should_ReturnTrue_When_CategoryIdIsValid(Product product) {
        assertTrue(ProductValidator.ensureProductIsValid(product));
    }



    /* STATUS */

    @DataProvider(name = "invalid-status")
    public Object[][] invalidStatus() {
        return new Object[][]{
                {new Product().clone(VALID_PRODUCT).setStatus(null)},
                {new Product().clone(VALID_PRODUCT).setStatus(-1)},
                {new Product().clone(VALID_PRODUCT).setStatus(2)},
        };
    }

    @Test(dataProvider = "invalid-status")
    public void should_ThrowIllegalArgumentException_When_StatusIsInvalid(Product product) {
        assertThrows(IllegalArgumentException.class, () -> ProductValidator.ensureProductIsValid(product));
    }

    @DataProvider(name = "valid-status")
    public Object[][] validStatus() {
        return new Object[][]{
                {new Product().clone(VALID_PRODUCT).setStatus(0)},
                {new Product().clone(VALID_PRODUCT).setStatus(1)},
        };
    }

    @Test(dataProvider = "valid-status")
    public void should_ReturnTrue_When_StatusIsValid(Product product) {
        assertTrue(ProductValidator.ensureProductIsValid(product));
    }



    /* HIT */

    @DataProvider(name = "invalid-hit")
    public Object[][] invalidHit() {
        return new Object[][]{
                {new Product().clone(VALID_PRODUCT).setHit(null)},
                {new Product().clone(VALID_PRODUCT).setHit(-1)},
                {new Product().clone(VALID_PRODUCT).setHit(3)},
        };
    }

    @Test(dataProvider = "invalid-hit")
    public void should_ThrowIllegalArgumentException_When_HitIsInvalid(Product product) {
        assertThrows(IllegalArgumentException.class, () -> ProductValidator.ensureProductIsValid(product));
    }

    @DataProvider(name = "valid-hit")
    public Object[][] validHit() {
        return new Object[][]{
                {new Product().clone(VALID_PRODUCT).setHit(0)},
                {new Product().clone(VALID_PRODUCT).setHit(2)},
        };
    }

    @Test(dataProvider = "valid-hit")
    public void should_ReturnTrue_When_HitIsValid(Product product) {
        assertTrue(ProductValidator.ensureProductIsValid(product));
    }
}
