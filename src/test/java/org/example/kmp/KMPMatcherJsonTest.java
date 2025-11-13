package org.example.kmp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

// small test to verify json-driven test cases
public class KMPMatcherJsonTest {

    @Test
    void testJsonCases() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // read cases.json from resources
        InputStream is = Main.class.getClassLoader().getResourceAsStream("cases.json");
        assertNotNull(is);

        InputConfig config = mapper.readValue(is, InputConfig.class);
        assertNotNull(config.cases);
        assertFalse(config.cases.isEmpty());

        // compare expected vs found matches
        for (InputConfig.Case c : config.cases) {
            KMPMatcher matcher = new KMPMatcher(c.pattern);
            assertEquals(c.expectedMatches, matcher.searchAll(c.text));
        }
    }
}