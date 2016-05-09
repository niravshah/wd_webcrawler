package com.nns.wd.config;

import com.nns.wd.crawler.Crawler;
import com.nns.wd.crawler.CrawlerImpl;
import com.nns.wd.crawler.LinkIndexer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by Nirav on 09/05/2016.
 */
@Configuration
public class CrawlerConfig {

    @Bean(name = "linkIndexer")
    @Scope("prototype")
    public LinkIndexer linkFinderAction(String id, String url, Crawler cr, int depth, int maxDepth, String seed) {
        return new LinkIndexer(id, url,cr,depth,maxDepth, seed);
    }

    @Bean(name = "crawlerImpl")
    @Scope("prototype")
    public Crawler crawler(int maxThread){
        return new CrawlerImpl(maxThread);
    }
}
