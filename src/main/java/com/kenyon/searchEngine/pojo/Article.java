package com.kenyon.searchEngine.pojo;

import lombok.Data;

@Data
public class Article {
    private static final long serialVersionUID = -763638353551774166L;

    public static final String INDEX_NAME = "index_article";

    public static final String TYPE = "esType";
    public Article(Long id,String title,String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }
    private Long id;
    private String title;
    private String content;
}
