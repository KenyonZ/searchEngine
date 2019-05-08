package com.kenyon.searchEngine.service.impl;

import com.kenyon.searchEngine.pojo.Article;
import com.kenyon.searchEngine.service.EsService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
public class EsServiceImpl implements EsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsServiceImpl.class);

    @Autowired
    private JestClient jestClient;
    @Override
    public void saveArticle(Article entity) {
        Index index = new Index.Builder(entity).index(Article.INDEX_NAME).type(Article.TYPE).build();
        try {
            jestClient.execute(index);
            LOGGER.info("ES 插入完成");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void saveArticle(List<Article> entityList) {
        Bulk.Builder bulk = new Bulk.Builder();
        for(Article entity : entityList) {
            Index index = new Index.Builder(entity).index(Article.INDEX_NAME).type(Article.TYPE).build();
            bulk.addAction(index);
        }
        try {
            jestClient.execute(bulk.build());
            LOGGER.info("ES 插入完成");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public List<Article> searchArticle(String searchContent) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.query(QueryBuilders.queryStringQuery(searchContent));
        //searchSourceBuilder.field("name");
        searchSourceBuilder.query(QueryBuilders.matchQuery("name",searchContent));
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(Article.INDEX_NAME).addType(Article.TYPE).build();
        try {
            JestResult result = jestClient.execute(search);
            return result.getSourceAsObjectList(Article.class);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
