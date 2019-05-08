package com.kenyon.searchEngine.controller;

import com.kenyon.searchEngine.pojo.Article;
import com.kenyon.searchEngine.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("elastic")
public class EsController {
    @Autowired
    EsService esService;
    @GetMapping("save")
    public boolean save(String content){
        Article article = new Article(System.currentTimeMillis(),
                "商品"+System.currentTimeMillis(),"这是一个测试商品");
        esService.saveArticle(article);
        return true;
    }

}
