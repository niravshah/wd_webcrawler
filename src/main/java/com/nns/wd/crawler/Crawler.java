package com.nns.wd.crawler;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by Nirav on 09/05/2016.
 */
public interface Crawler {
    int size();
    boolean visited(String link);
    void addNewLinkToSitemap(String link);
    void addToChildSitemap(String parent, String child);
    void addVisited(String s, Integer depth);
    void startCrawling(String requestid, String link, int maxDepth);
    ForkJoinPool getMainPool();
    Map<String, Collection<String>> getSiteMap();
}
