package com.xelda.service;

import com.google.gson.Gson;
import com.xelda.configuration.HeadersConfig;
import com.xelda.model.Page;
import com.xelda.model.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class WikipediaArticleService {
    private static final Logger LOG = LoggerFactory.getLogger(WikipediaArticleService.class);
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HeadersConfig headersConfig;

    @Value("${articles.random.url}")
    private String randomArticlesUrl;

    @Autowired
    private MongoTemplate mongoTemplate;
    public List<Page> search(String query) {
        TextCriteria c = TextCriteria.forDefaultLanguage().matching(query);
        Query q = TextQuery.queryText(c).sortByScore();
        List<Page> pages = this.mongoTemplate.find(q, Page.class);

        return pages;
    }
    public void getNRandomWikipediaArticles() {
        LOG.info("RestTemplate exchanging");
        final String response = this.restTemplate.exchange(
                this.randomArticlesUrl,
                HttpMethod.GET,
                this.getHeader(),
                new ParameterizedTypeReference<String>() {}).getBody();
        LOG.info("RestTemplate finished exchanging");

        Root root = new Gson().fromJson(response, Root.class);
        this.mongoTemplate.insertAll(root.getQuery().getPages().values());
    }

    private HttpEntity<Page> getHeader() {
        final HttpHeaders httpHeaders = this.headersConfig.getHeaders();

        return new HttpEntity<>(httpHeaders);
    }
}