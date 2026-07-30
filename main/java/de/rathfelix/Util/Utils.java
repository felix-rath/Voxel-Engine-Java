package de.rathfelix.Util;

import de.rathfelix.exceptions.ShaderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static String loadResource(String shaderPath) {
        try {
            String file = Files.readString(Paths.get("src/main/resources/shaders" + shaderPath));
            return file;
        } catch (IOException e) {
            System.err.println("Utils Error: Cant read resources.");
            return "";
        }
    }

    public static List<String> readAllLines(String fileName) {
        List<String> list = Collections.emptyList();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Utils.class.getResourceAsStream(fileName)))) {
            list = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
