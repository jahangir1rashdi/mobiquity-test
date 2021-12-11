package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.PackageItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.mobiquity.model.PackageItemFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Packer {

    private static final Logger logger = LogManager.getLogger(Packer.class);

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {
        logger.info("packing started");
        final StringBuilder sb = new StringBuilder();
        Stream<String> stream = loadDataStream(filePath);
        List<String> packageDetails = stream.collect(Collectors.toList());
        Map<Double, List<PackageItem>> packageItemsToWeight = PackageItemFactory
                .mapPackageItemsToWeight(packageDetails);
        for (Map.Entry<Double, List<PackageItem>> packageItemToWeight : packageItemsToWeight.entrySet()) {
            sb.append(getPackageItemIndexNumbers(
                    packageItemToWeight.getKey(),
                    packageItemToWeight.getValue())
            );
            sb.append("\n");
        }
        logger.info("packing finished successfully");

        return sb.isEmpty() ? "-" : sb.toString().trim();
    }

    private static Stream<String> loadDataStream(String filePath) throws APIException {
        Stream<String> stream;
        try {
            stream = Files.lines(Paths.get(filePath));
        } catch (IOException e) {
            logger.error("Incorrect file path {}", filePath);
            throw new APIException("File not found", e);

        }
        return stream;
    }

    private static String getPackageItemIndexNumbers(
            double maxWeight,
            List<PackageItem> packages) {
        List<PackageItem> finalItems = new ArrayList<>();
        packages
                .stream()
                .filter(x -> x.getWeight() < maxWeight)
                .sorted((p1, p2) -> {
                    int sComp = p2.getCost().compareTo(p1.getCost());
                    return sComp != 0 ? sComp : Double.valueOf(p1.getWeight()).compareTo(p2.getWeight());
                })
                .forEach(packageItem -> {
                    Double currentTotal = finalItems
                            .stream()
                            .mapToDouble(PackageItem::getWeight)
                            .sum();
                    if (currentTotal + packageItem.getWeight() < maxWeight) {
                        finalItems.add(packageItem);
                    }
                });
        return finalItems.size() > 0 ? formatPackageItemIndexNumbers(finalItems) : "-";
    }

    private static String formatPackageItemIndexNumbers(List<PackageItem> finalItems) {
        return String.join(
                ",",
                finalItems.stream()
                        .map(x -> String
                                .valueOf(x.getNumber()))
                        .collect(Collectors.toList())
        );
    }

}
