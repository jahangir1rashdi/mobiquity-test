package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Package;
import com.mobiquity.model.PackageItem;
import com.mobiquity.model.PackageFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Packer {

    private static final Logger logger = LogManager.getLogger(Packer.class);

    private Packer() {
    }

    /**
     * @param filePath file having list of package with item configuration
     * @return list of item index number to be added in the package
     * @throws APIException throws validation exceptions
     */
    public static String pack(String filePath) throws APIException {
        logger.info("packing started");
        List<String> packageDetails = readInput(filePath);
        List<Package> packages = PackageFactory
                .getPackages(packageDetails);
        String output = packages.stream()
                .map(Packer::getPackageItemIndexNumbers)
                .collect(Collectors.joining("\n"));
        logger.info("packing finished successfully");

        return output.isEmpty() ? "-" : output;
    }

    private static List<String> readInput(String filePath) throws APIException {
        Stream<String> stream;
        try {
            stream = Files.lines(Paths.get(filePath));
        } catch (IOException e) {
            logger.error("Incorrect file path {}", filePath);
            throw new APIException("File not found", e);

        }
        return stream.collect(Collectors.toList());
    }

    private static String getPackageItemIndexNumbers(
            Package pack) {
        List<PackageItem> selectedItems = new ArrayList<>();
        pack.getPackageItems()
                .stream()
                .filter(x -> x.getWeight() < pack.getWeight())
                .sorted((p1, p2) -> {
                    int costComparator = p2.getCost().compareTo(p1.getCost());
                    return costComparator != 0 ? costComparator
                            : Double.compare(p1.getWeight(), p2.getWeight());
                })
                .forEach(packageItem -> {
                    Double currentTotalWeight = calculateTotalWeight(selectedItems);
                    if (currentTotalWeight + packageItem.getWeight() < pack.getWeight()) {
                        selectedItems.add(packageItem);
                    }
                });
        return selectedItems.size() > 0 ? toOutputFormatString(selectedItems) : "-";
    }

    private static Double calculateTotalWeight(List<PackageItem> finalItems) {
        return finalItems
                .stream()
                .parallel()
                .mapToDouble(PackageItem::getWeight)
                .sum();
    }

    private static String toOutputFormatString(List<PackageItem> selectedItems) {
        return selectedItems.stream().map(x -> String
                .valueOf(x.getNumber())).collect(Collectors.joining(","));
    }

}
