package org.example.kmp;

import java.util.List;

// simple holder for json structure
public class InputConfig {

    public List<Case> cases;

    public static class Case {
        public String name;
        public String pattern;
        public String text;
        public List<Integer> expectedMatches;
    }
}