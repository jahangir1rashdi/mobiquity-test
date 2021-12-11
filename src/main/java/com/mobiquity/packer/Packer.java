package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.PackageItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Packer {

    private static final Logger logger = LogManager.getLogger(Packer.class);

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {
        final StringBuilder sb = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            logger.info("packing started");
            stream.forEach(packageDetail -> {
                String[] details = packageDetail.split(":");
                double maxWeight = Double.valueOf(details[0].trim());
                String[] packageItemsStr = details[1].trim().split(" ");
                List<PackageItem> packageItems = Arrays
                        .stream(packageItemsStr)
                        .parallel()
                        .map(PackageItem::new)
                        .collect(Collectors.toList());
                sb.append(getPackageItemIndexNumbers(maxWeight, packageItems));
                sb.append("\n");
            });
            logger.info("packing finished successfully");
        } catch (IOException e) {
            logger.error("Incorrect file path {}", filePath);
            throw new APIException("File not found", e);

        } catch (NumberFormatException nfe) {
            logger.error("Incorrect data in file path {}", filePath);
            throw new APIException("Incorrect data format", nfe);
        }
        return sb.isEmpty() ? "-" : sb.toString().trim();
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
