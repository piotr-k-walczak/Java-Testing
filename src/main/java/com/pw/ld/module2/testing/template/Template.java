package com.pw.ld.module2.testing.template;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Template.
 */
public class Template {

    private String templateString;

    public Template() {
        this("Subject #{subject}: #{message}");
    }

    public Template(String templateString) {
        this.templateString = templateString;
    }

    public String getTemplateString() {
        return templateString;
    }

    public void setTemplateString(String templateString) {
        this.templateString = templateString;
    }

    public int getNumberOfExpectedValues() {
        Pattern pattern = Pattern.compile("\\#\\{[^#]*\\}");
        Matcher matcher = pattern.matcher(templateString);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    public List<String> getExpectedValues() {
        Pattern pattern = Pattern.compile("\\#\\{[^#]*\\}");
        Matcher matcher = pattern.matcher(templateString);
        ArrayList<String> matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }
}
