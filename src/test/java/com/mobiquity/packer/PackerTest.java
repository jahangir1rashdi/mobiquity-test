package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mobiquity.util.Constant.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PackerTest {

    @Test
    void givenIncorrectDataFormatThenThrowsException() {
        Exception exception = assertThrows(APIException.class, ()->{
            Packer.pack("src/test/resources/incorrect_input");
        });
        assertEquals(INCORRECT_DATA_FORMAT_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void givenFileNotExistsThenThrowsException() {
        Exception exception = assertThrows(APIException.class, ()->{
            Packer.pack("incorrect/file/path");
        });
        String expectedExceptionMessage = "File not found";
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    void givenItemCostExceedsThenThrowsException() {
        Exception exception = assertThrows(APIException.class, ()->{
            Packer.pack("src/test/resources/input_with_cost_exceed_max_cost");
        });
        assertEquals(COST_VALIDATION_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void givenMaxWeightExceedsThenThrowsException() {
        Exception exception = assertThrows(APIException.class, ()->{
            Packer.pack("src/test/resources/input_with_cost_exceed_max_weight");
        });
        assertEquals(WEIGHT_VALIDATION_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void givenItemNumbersExceedsThenThrowsException() {
        Exception exception = assertThrows(APIException.class, ()->{
            Packer.pack("src/test/resources/input_with_items_exceed_max_item_numbers");
        });
        assertEquals(ITEM_NUMBER_VALIDATION_EXCEPTION_MESSAGE, exception.getMessage());
    }


    @Test
    void pack_ShouldGetValidItemNumberPerPackage() throws APIException, IOException {
        Stream<String> stream = Files.lines(Paths.get("src/test/resources/example_output"));
        String expectedOutput = String.join("\n", stream.collect(Collectors.toList()));
        String output = Packer.pack("src/test/resources/example_input");
        assertEquals(expectedOutput, output);
    }
}