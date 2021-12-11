package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PackerTest {

    @Test
    void givenIncorrectDataFormatThenThrowsException() {
        Exception exception = assertThrows(APIException.class, ()->{
            Packer.pack("src/test/resources/incorrect_input");
        });
        String expectedExceptionMessage = "Incorrect data format";
        assertEquals(expectedExceptionMessage, exception.getMessage());
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
    void pack_ShouldGetValidItemNumberPerPackage() throws APIException, IOException {
        Stream<String> stream = Files.lines(Paths.get("src/test/resources/example_output"));
        String expectedOutput = String.join("\n", stream.collect(Collectors.toList()));
        String output = Packer.pack("src/test/resources/example_input");
        assertEquals(expectedOutput, output);
    }
}