package com.pw.ld.module2.testing.loader;

import java.util.Map;

public class Message {
    private String address;
    private Map<String, String> placeholders;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, String> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(Map<String, String> placeholders) {
        this.placeholders = placeholders;
    }
}
