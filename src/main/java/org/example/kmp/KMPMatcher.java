package org.example.kmp;

import java.util.ArrayList;
import java.util.List;

public class KMPMatcher {

    private final String pattern;
    private final int[] lps;

    public KMPMatcher(String pattern) {
        if (pattern == null) throw new IllegalArgumentException("pattern must not be null");

        this.pattern = pattern;

        // prepare lps to make matching faster
        this.lps = buildLpsArray(pattern);
    }

    public int searchFirst(String text) {
        List<Integer> list = searchAll(text);
        return list.isEmpty() ? -1 : list.get(0);
    }

    public List<Integer> searchAll(String text) {
        if (text == null) throw new IllegalArgumentException("text must not be null");

        List<Integer> out = new ArrayList<>();

        // empty pattern matches everywhere
        if (pattern.isEmpty()) {
            for (int i = 0; i <= text.length(); i++) out.add(i);
            return out;
        }

        int i = 0; // text index
        int j = 0; // pattern index

        while (i < text.length()) {

            // characters match → move both
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            // full match found
            if (j == pattern.length()) {
                out.add(i - j);
                j = lps[j - 1]; // reuse prefix info
            }

            // mismatch case
            else if (i < text.length() && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) j = lps[j - 1]; // fallback inside pattern
                else i++;                  // no prefix fallback
            }
        }

        return out;
    }

    private int[] buildLpsArray(String pattern) {
        // lps holds longest prefix-suffix lengths
        int[] lps = new int[pattern.length()];
        int len = 0;
        int i = 1;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                lps[i] = ++len;
                i++;
            } else {
                if (len != 0) len = lps[len - 1];
                else lps[i++] = 0;
            }
        }
        return lps;
    }
}