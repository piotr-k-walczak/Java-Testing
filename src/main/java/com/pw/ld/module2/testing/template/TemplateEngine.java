package com.pw.ld.module2.testing.template;

import com.pw.ld.module2.testing.Client;

import java.util.Map;

/**
 * The type Template engine.
 */
public class TemplateEngine {
    /**
     * Generate message string.
     *
     * @param template the template
     * @param client   the client
     * @return the string
     */
    public String generateMessage(Template template, Client client) throws MissingPlaceholderException {
        String templateString = template.getTemplateString();
        if (checkIfAllPlaceholdersArePresent(templateString, client.getPlaceholders())) {
            for (Map.Entry<String, String> entry : client.getPlaceholders().entrySet()) {
                templateString = templateString.replace("#{" + entry.getKey() + "}", entry.getValue());
            }
        }
        return templateString;
    }

    public boolean checkIfAllPlaceholdersArePresent(String templateString, Map<String, String> placeholders) throws MissingPlaceholderException {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            templateString = templateString.replace("#{" + entry.getKey() + "}", "");
        }

        if (templateString.contains("#")) {
            throw new MissingPlaceholderException();
        }

        return true;
    }
}
