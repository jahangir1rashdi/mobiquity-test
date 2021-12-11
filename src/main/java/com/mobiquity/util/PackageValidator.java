package com.mobiquity.util;

import com.mobiquity.exception.APIException;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

import static com.mobiquity.util.Constant.*;

public class PackageValidator {

    public static void validatePackageDetails(List<String> packageDetails) throws APIException {
        Pattern p = Pattern.compile(PACKAGE_DETAIL_FORMAT_REGEX);
        for (String packageDetail : packageDetails) {
            if (!p.matcher(packageDetail).matches()) {
                throw new APIException(INCORRECT_DATA_FORMAT_EXCEPTION_MESSAGE);
            }
            String[] details = packageDetail.split(":");
            double maxWeight = Double.valueOf(details[0].trim());
            validateMaxWeight(maxWeight);
            String[] packageItems = details[1].trim().split(" ");
            validateNumberOfItems(packageItems);
            for (String packageItem : packageItems) {
                String[] fields = packageItem.substring(1, packageItem.length() - 1).split(",");
                validateMaxCost(new BigDecimal(fields[COST_INDEX].substring(1)));
            }
        }
    }

    public static void validateMaxCost(BigDecimal maxCost) throws APIException {
        if (maxCost.compareTo(MAX_COST_ALLOWED) > 0) {
            throw new APIException(COST_VALIDATION_EXCEPTION_MESSAGE);
        }
    }

    private static void validateMaxWeight(Double maxWeight) throws APIException {
        if (maxWeight > MAX_WEIGHT_ALLOWED) {
            throw new APIException(WEIGHT_VALIDATION_EXCEPTION_MESSAGE);
        }
    }

    private static void validateNumberOfItems(String[] packageItems) throws APIException {
        if (packageItems.length > MAX_NUMBER_OF_ITEMS) {
            throw new APIException(ITEM_NUMBER_VALIDATION_EXCEPTION_MESSAGE);
        }
    }
}
