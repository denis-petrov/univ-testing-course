package com.app;

public final class ProductValidator {

    private ProductValidator() {
    }

    public static boolean ensureProductIsValid(Product product) throws IllegalArgumentException {
        String alertMsg = "";

        var categoryId = product.getCategoryId();
        if (!isCategoryIdValid(categoryId)) {
            alertMsg += "\tNot valid category_id = " + categoryId + "; must be from 1 to 15\n";
        }

        var status = product.getStatus();
        if (!isStatusValid(status)) {
            alertMsg += "\tNot valid status = " + product.getStatus() + "; must be equals 0 or 1\n";
        }

        var hit = product.getHit();
        if (!isHitValid(hit)) {
            alertMsg += "\tNot valid hit = " + product.getHit() + "; must be equals from 0 to 2\n";
        }

        if (!alertMsg.isEmpty()) {
            throw new IllegalArgumentException("Product with id = " + product.getId() + "\n" + alertMsg);
        }
        return true;
    }

    private static boolean isHitValid(Integer hit) {
        return hit != null && 0 <= hit && hit <= 2;
    }

    private static boolean isStatusValid(Integer status) {
        return status != null && (0 == status || status == 1);
    }

    private static boolean isCategoryIdValid(Integer categoryId) {
        return categoryId != null && 0 < categoryId && categoryId < 16;
    }
}
