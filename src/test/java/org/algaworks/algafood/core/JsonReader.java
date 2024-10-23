package org.algaworks.algafood.core;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JsonReader {

    public static String read(Path path) {
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            return lines.collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
