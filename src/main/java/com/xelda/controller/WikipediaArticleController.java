package com.xelda.controller;

import com.xelda.service.WikipediaArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(value = "/")
public class WikipediaArticleController {

    private static final Logger LOG = LoggerFactory.getLogger(WikipediaArticleController.class);

    @Autowired
    private WikipediaArticleService wikipediaArticleService;

    @Value("${articles.limit}")
    private int limit;

    @PostConstruct
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getWikipediaArticles() {
        int numTimesToRequest = this.limit / 20;
        for (int i=0; i<numTimesToRequest; i++) {
            this.wikipediaArticleService.getNRandomWikipediaArticles();
        }

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
