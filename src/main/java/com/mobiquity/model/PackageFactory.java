package com.mobiquity.model;

import com.mobiquity.exception.APIException;
import com.mobiquity.util.PackageValidator;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.mobiquity.util.Constant.*;

/**
 * this factory holds the responsibility to create list of packages
 */
public class PackageFactory {

    /**
     * @param packageDetails creates list of package objects
     * @return list of Package object
     * @throws APIException when any constraint violation or invalid data format occurs
     */
    public static List<Package> getPackages(
            List<String> packageDetails
    ) throws APIException {
        List<Package> packages = new LinkedList<>();
        PackageValidator.validatePackageDetails(packageDetails);
        for (String packageDetail : packageDetails) {
            String[] details = packageDetail.split(":");
            double maxWeight = Double.parseDouble(details[0].trim());
            String[] packageItemsStr = details[1].trim().split(" ");
            List<PackageItem> packageItems = Arrays
                    .stream(packageItemsStr)
                    .parallel()
                    .map(PackageFactory::createPackageItem)
                    .collect(Collectors.toList());
            packages.add(new Package(maxWeight, packageItems));
        }
        return packages;
    }

    private static PackageItem createPackageItem(String packageItem) {
        String[] fields = packageItem.substring(1, packageItem.length() - 1).split(",");
        Integer number = Integer.valueOf(fields[NUMBER_INDEX]);
        Double weight = Double.valueOf(fields[WEIGHT_INDEX]);
        BigDecimal cost = new BigDecimal(fields[COST_INDEX].substring(1));
        return new PackageItem(number, weight, cost);
    }
}
