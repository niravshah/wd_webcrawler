package com.nns.wd.crawler;

import com.nns.wd.services.RequestService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by Nirav on 09/05/2016.
 */
public class CrawlerImpl  implements Crawler {

    private final Map<String, Integer> visitedLinks = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, Collection<String>> siteMap = Collections.synchronizedMap(new HashMap<>());
    private ForkJoinPool mainPool;

    @Autowired
    RequestService service;

    @Autowired
    BeanFactory factory;

    @Autowired
    public CrawlerImpl(int maxThreads) {
        mainPool = new ForkJoinPool(maxThreads);
    }

    public void startCrawling(String requestId, String url, int maxDepth) {
        LinkIndexer linkIndexer = (LinkIndexer) factory.getBean("linkIndexer", requestId, url, this, 0, maxDepth, url);
        mainPool.invoke(linkIndexer);
        if (mainPool.isQuiescent()) {
            service.setResult(requestId, siteMap);
        }
    }

    public int size() {
        return visitedLinks.size();
    }

    public void addVisited(String s, Integer depth) {
        visitedLinks.put(s, depth);
    }

    public boolean visited(String s) {
        return visitedLinks.containsKey(s);
    }

    public void addNewLinkToSitemap(String s) {
        siteMap.put(s, Collections.synchronizedSet(new HashSet<String>()));
    }

    public void addToChildSitemap(String parent, String child) {
        siteMap.get(parent).add(child);
    }

    public ForkJoinPool getMainPool() {
        return mainPool;
    }

    public Map<String, Collection<String>> getSiteMap() {
        return siteMap;
    }
}