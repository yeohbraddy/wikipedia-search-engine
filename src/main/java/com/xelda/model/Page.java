package com.xelda.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.TextScore;

@Data
@Document(collection = "articles")
public class Page {
    @Id
    private int pageid;
    private int ns;
    @TextIndexed(weight = 5)
    @Field
    private String title;
    @TextIndexed(weight = 4)
    @Field
    private String extract;

    @TextScore
    private Float score;

    @Override
    public String toString() {
        return "pageid: " + this.pageid + "\n" +
                "ns: " + this.ns + "\n" +
        "title: " + this.title + "\n" +
                "extract: " + this.extract + "\n";
    }
}
