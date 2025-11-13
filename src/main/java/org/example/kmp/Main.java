package org.example.kmp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        // use direct path to resources so output lands in the project folder
        Path resources = Paths.get("src/main/resources");
        Path input = resources.resolve("cases.json");
        Path output = resources.resolve("output.json");

        if (!Files.exists(input)) {
            System.err.println("cases.json not found");
            return;
        }

        // load test cases
        InputConfig config;
        try (InputStream is = Files.newInputStream(input)) {
            config = mapper.readValue(is, InputConfig.class);
        }

        List<Map<String, Object>> results = new ArrayList<>();

        // run kmp on every case
        for (InputConfig.Case c : config.cases) {
            KMPMatcher m = new KMPMatcher(c.pattern);
            List<Integer> matches = m.searchAll(c.text);

            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("case", c.name);
            entry.put("pattern", c.pattern);
            entry.put("matches", matches);
            results.add(entry);

            System.out.println("case: " + c.name);
            System.out.println("matches: " + matches);
            System.out.println();
        }

        // write output.json in the same folder
        try (Writer w = new OutputStreamWriter(Files.newOutputStream(output), StandardCharsets.UTF_8)) {
            mapper.writeValue(w, results);
        }

        System.out.println("output.json updated");
    }
}