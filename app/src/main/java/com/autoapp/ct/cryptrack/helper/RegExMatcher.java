package com.autoapp.ct.cryptrack.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shwg on 10/8/2017.
 */

public class RegExMatcher {

    private static RegExMatcher sRegExMatcher;

    public static RegExMatcher getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        private static RegExMatcher INSTANCE = new RegExMatcher();
    }

    private RegExMatcher() {};


    private static final String NOT_FOUND = "Not found";

    public String match(String content, String pattern) {
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(content);
        if (m.find()) {
            return m.group(0);
        } else {
            return NOT_FOUND;
        }
    }
}
