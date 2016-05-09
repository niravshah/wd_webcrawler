package com.nns.wd.crawler;

import com.nns.wd.CrawlerApplication;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Nirav on 09/05/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CrawlerApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class CrawlerTests {
    @Autowired
    BeanFactory factory;

    @Test
    public void testCrawler(){
        String testUid = UUID.randomUUID().toString();
        Crawler crawler = (Crawler) factory.getBean("crawlerImpl", 64);
        crawler.startCrawling(testUid,"http://movewithin.org",0);
        if(crawler.getMainPool().isQuiescent()){
            assertThat(crawler.getSiteMap().size(), Matchers.is(1));
        }
    }
}
