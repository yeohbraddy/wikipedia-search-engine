package com.xelda.model;

import lombok.Data;

@Data
public class Root {
    private String batchcomplete;
    private Query query;

    @Override
    public String toString() {
        return "Batch: " + this.batchcomplete + "\n " + this.query.toString();
    }
}
