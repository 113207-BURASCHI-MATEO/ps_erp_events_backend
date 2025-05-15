package com.tup.ps.erpevents.services.utils;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class VariableUtils {

    private static final Pattern PLACEHOLDER_PATTERN =
            Pattern.compile("\\{\\{(.*?)\\}\\}");


    public static Set<String> extractPlaceholders(String template) {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
        return matcher.results()
                .map(match -> match.group(1))
                .collect(Collectors.toSet());
    }
}
