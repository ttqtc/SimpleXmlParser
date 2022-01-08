package com.xml.util;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class SimpleXmlParser {    
    //4
    private static final int NOTE_L = "<!--".length();
    //9
    private static final int CDATA_L = "<![CDATA[".length();
    //3
    private static final int CDATA_R = "]]>".length();
    //2
    private static final int TAG_END_L = "</".length();
    //1
    private static final int TAG_END_R = ">".length();

    private static int read(char[] s, int i, Map<String, Object> m) {
        i = trim(s, i);
        StringBuilder k = new StringBuilder();
        if (s[i] != '<') {
            return -1;
        }
        i++;
        int n_len = 0;
        boolean skip = false;
        while (s[i] != '>') {
            if (s[i] == ' ' || skip) {
                skip = true;
                i++;
                continue;
            }
            k.append(s[i]);
            n_len++;
            i++;
        }
        i++;
        i = trim(s, i);
        if (isCdataBegin(s, i)) {
            i = i + CDATA_L;
            StringBuilder v = new StringBuilder();
            while (!isCdataEnd(s, i)) {
                v.append(s[i]);
                i++;
            }
            i = i + CDATA_R + 1;
            i = trim(s, i);
            i = i + TAG_END_L + n_len + TAG_END_R;
            m.put(k.toString(), v.toString());
        } else if (s[i] == '<') {
            Map<String, Object> km = new HashMap<>();
            m.put(k.toString(), km);
            while (i < s.length && !isClosePrefix(s, i)) {
                i = read(s, i, km);
            }
        } else {
            StringBuilder v = new StringBuilder();
            while (s[i] != '<') {
                v.append(s[i]);
                i++;
            }
            i = i + TAG_END_L + n_len + TAG_END_R;
            m.put(k.toString(), v.toString());
        }
        i = trim(s, i);
        return i;
    }

    private static boolean isClosePrefix(char[] s, int i) {
        return s[i] == '<' && s[i + 1] == '/';
    }

    private static boolean isCdataEnd(char[] s, int i) {
        return s[i] == ']' && s[i + 1] == ']' && s[i + 2] == '>';
    }

    private static boolean isCdataBegin(char[] s, int i) {
        return s[i] == '<' && s[i + 1] == '!' && s[i + 2] == '[';
    }


    private static int trim(char[] s, int i) {
        if (i >= s.length - 1) {
            return i;
        }
        while (s[i] == '\n' || s[i] == ' ') {
            i++;
            if (i >= s.length - 1) {
                return i;
            }
        }
        if (isNoteBegin(s, i)) {
            i = i + NOTE_L;
            while (!isNoteEnd(s, i)) {
                i++;
            }
            i++;
            if (i >= s.length - 1) {
                return i;
            }
            i = trim(s, i);
        }
        return i;
    }

    private static boolean isNoteEnd(char[] s, int i) {
        return s[i - 2] == '-' && s[i - 1] == '-' && s[i] == '>';
    }

    private static boolean isNoteBegin(char[] s, int i) {
        return s[i] == '<' && s[i + 1] == '!' && s[i + 2] == '-' && s[i + 3] == '-';
    }

    public static Map<String, Object> parse(String text) throws ParseException {
        try {
            Map<String, Object> m = new HashMap<>();
            SimpleXmlParser.read(text.toCharArray(), 0, m);
            return m;
        } catch (Exception e) {
            throw new ParseException("parse xml fail", 0);
        }
    }
}
