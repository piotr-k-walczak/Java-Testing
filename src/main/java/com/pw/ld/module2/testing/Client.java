package com.pw.ld.module2.testing;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Client.
 */
public class Client {
    private String addresses;

    private Map<String, String> placeholders;

    public Client(String addresses) {
        this.addresses = addresses;
        this.placeholders = new HashMap<>();
    }

    public Map<String, String> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(Map<String, String> placeholders) {
        this.placeholders = placeholders;
    }

    public void addPlaceholder(String placeholder, String value) {
        this.placeholders.put(placeholder, value);
    }

    /**
     * Gets addresses.
     *
     * @return the addresses
     */
    public String getAddresses() {
        return addresses;
    }

    /**
     * Sets addresses.
     *
     * @param addresses the addresses
     */
    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }
}
