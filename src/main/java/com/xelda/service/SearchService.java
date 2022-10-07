package com.xelda.service;

import com.xelda.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.validation.constraints.Size;
import java.util.List;

@ShellComponent
public class SearchService {
    private static final Logger LOG = LoggerFactory.getLogger(SearchService.class);

    private final String commandKey = "xelda";

    @Autowired
    private WikipediaArticleService wikipediaArticleService;


    @ShellMethod(key = commandKey, value = "search query")
    public void search(@ShellOption(value = "-s") @Size(min = 1) String query) {
        LOG.info("Searching for: " + query);

        List<Page> res = this.wikipediaArticleService.search(query);

        if (res.size() == 0) {
            LOG.info("No results");
        }

        for (Page p : res) {
            LOG.info("\n\n=================================================\n" +
                    p.toString() +
                    "\n=================================================\n" );
        }
    }
}
