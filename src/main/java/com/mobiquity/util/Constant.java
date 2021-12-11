package com.mobiquity.util;

import java.math.BigDecimal;

public interface Constant {
    String PACKAGE_DETAIL_FORMAT_REGEX = "\\d+ : [ ?\\(\\d+,\\d+.?\\d+,€\\d+\\)]+";
    Double MAX_WEIGHT_ALLOWED = 100d;
    BigDecimal MAX_COST_ALLOWED = BigDecimal.valueOf(100);
    int MAX_NUMBER_OF_ITEMS = 15;
    int COST_INDEX = 2;
    int WEIGHT_INDEX = 1;
    int NUMBER_INDEX = 0;
    String COST_VALIDATION_EXCEPTION_MESSAGE = "Max cost cannot exceed 100";
    String WEIGHT_VALIDATION_EXCEPTION_MESSAGE = "Max weight cannot exceed 100";
    String ITEM_NUMBER_VALIDATION_EXCEPTION_MESSAGE = "Max number of cannot exceed 15 per package";
    String INCORRECT_DATA_FORMAT_EXCEPTION_MESSAGE = "Incorrect data format";
}
