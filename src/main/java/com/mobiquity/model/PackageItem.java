package com.mobiquity.model;

import java.math.BigDecimal;

/**
 * package item is representation of item to be added in the package
 */
public class PackageItem {
    private int number;
    private double weight;
    private BigDecimal cost;

    public PackageItem(Integer number, Double weight, BigDecimal cost) {
        this.number = number;
        this.weight = weight;
        this.cost = cost;
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
