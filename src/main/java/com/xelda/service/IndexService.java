package com.xelda.service;


import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IndexService {
    private Trie<String, Set<Integer>> trie= new PatriciaTrie();

    public void insert(String key, int pageId) {
        Set<Integer> pageIds = new HashSet<>();
        if (this.trie.containsKey(key)) {
            pageIds = this.trie.get(key);
        }

        pageIds.add(pageId);

        this.trie.put(key, pageIds);
    }

    public Set<Integer> search(String key) {
        Set<Integer> pageIds = new HashSet<>();

        String[] terms = key.split(" ");
        for (String s : terms) {
            if (this.trie.containsKey(s)) {
                Set<Integer> ids = this.trie.prefixMap(s).get(s);

                pageIds.addAll(ids);
            }
        }

        return pageIds;
    }
}
