package com.xelda.model;

import lombok.Data;

import java.util.Map;

@Data
public class Query {
    private Map<String, Page> pages;

    @Override
    public String toString() {
        String s = "";
        for (Page a : this.pages.values()) {
            s += a.toString() + "\n";
        }

        return s;
    }
}
