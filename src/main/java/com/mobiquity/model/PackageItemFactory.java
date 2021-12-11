package com.mobiquity.model;

import com.mobiquity.exception.APIException;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class PackageItemFactory {
    public static final int COST_INDEX = 2;
    public static final int WEIGHT_INDEX = 1;
    public static final int NUMBER_INDEX = 0;

    public static Map<Double, List<PackageItem>> mapPackageItemsToWeight(
            List<String> packageDetails
    ) throws APIException {
        Map<Double, List<PackageItem>> packageItemsToWeight = new LinkedHashMap<>();

            for (String packageDetail : packageDetails) {
                String[] details = packageDetail.split(":");
                double maxWeight = Double.valueOf(details[0].trim());
                String[] packageItemsStr = details[1].trim().split(" ");
                List<PackageItem> packageItems = Arrays
                        .stream(packageItemsStr)
                        .parallel()
                        .map(PackageItemFactory::createPackageItem)
                        .collect(Collectors.toList());
                packageItemsToWeight.put(maxWeight, packageItems);
            }
        return packageItemsToWeight;
    }

    private static PackageItem createPackageItem(String packageItem) {

        String[] fields = packageItem.substring(1, packageItem.length() - 1).split(",");
        Integer number = Integer.valueOf(fields[NUMBER_INDEX]);
        Double weight = Double.valueOf(fields[WEIGHT_INDEX]);
        BigDecimal cost = new BigDecimal(fields[COST_INDEX].substring(1));
        return new PackageItem(number, weight, cost);
    }
}
