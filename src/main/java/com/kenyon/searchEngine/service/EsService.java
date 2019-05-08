package com.kenyon.searchEngine.service;

import com.kenyon.searchEngine.pojo.Article;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EsService {
    void saveArticle(Article entity);

    void saveArticle(List<Article> entityList);

    List<Article> searchArticle(String searchContent);
}
