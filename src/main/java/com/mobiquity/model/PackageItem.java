package com.mobiquity.model;

import java.math.BigDecimal;

public class PackageItem {
    public static final int COST_INDEX = 2;
    public static final int WEIGHT_INDEX = 1;
    public static final int NUMBER_INDEX = 0;
    private int number;
    private double weight;
    private BigDecimal cost;

    public PackageItem(String packageItem) {
        String[] fields = packageItem.substring(1, packageItem.length() - 1).split(",");
        number = Integer.valueOf(fields[NUMBER_INDEX]);
        weight = Double.valueOf(fields[WEIGHT_INDEX]);
        cost = new BigDecimal(fields[COST_INDEX].substring(1));
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
