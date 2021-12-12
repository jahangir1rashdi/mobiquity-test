package com.mobiquity.model;

import java.util.List;

/**
 * Package is the object representation of input file single line
 * this contains weight and list of items
 */
public class Package {
    private Double weight;
    private List<PackageItem> packageItems;

    public Package(Double weight, List<PackageItem> packageItems) {
        this.weight = weight;
        this.packageItems = packageItems;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public List<PackageItem> getPackageItems() {
        return packageItems;
    }

    public void setPackageItems(List<PackageItem> packageItems) {
        this.packageItems = packageItems;
    }
}
