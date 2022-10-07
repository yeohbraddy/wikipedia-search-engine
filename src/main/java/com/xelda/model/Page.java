package com.xelda.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "articles")
public class Page {
    @Id
    private int pageid;
    private int ns;
    private String title;
    private String extract;

    @Override
    public String toString() {
        return "pageid: " + this.pageid + "\n" +
                "ns: " + this.ns + "\n" +
        "title: " + this.title + "\n" +
                "extract: " + this.extract + "\n";
    }
}
