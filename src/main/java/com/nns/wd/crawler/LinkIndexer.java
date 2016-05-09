package com.nns.wd.crawler;

import com.nns.wd.services.HtmlParserService;
import com.nns.wd.services.RequestService;
import com.nns.wd.services.ValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Created by Nirav on 09/05/2016.
 */
public class LinkIndexer  extends RecursiveAction {

    private String requestId;
    private String url;
    private String seedUrl;
    private Crawler cr;
    private int currentDepth;
    private int maxDepth;

    @Autowired
    RequestService service;

    @Autowired
    BeanFactory factory;

    @Autowired
    HtmlParserService htmlParser;

    @Autowired
    ValidatorService validator;

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkIndexer.class);

    public static final String STATUS_PROCESSING = "Processing. Number of links indexed till now: %s";

    public LinkIndexer(String requestid, String url, Crawler cr, int depth, int maxDepth, String seed) {
        this.requestId = requestid;
        this.url = url;
        this.cr = cr;
        this.currentDepth = depth;
        this.maxDepth = maxDepth;
        this.seedUrl = seed;
    }

    @Override
    public void compute() {
        if (!cr.visited(url)) {
            try {
                List<RecursiveAction> actions = new ArrayList<RecursiveAction>();
                cr.addVisited(url, currentDepth);
                cr.addNewLinkToSitemap(url);
                List<String> linkTags = htmlParser.getLinkTags(url);

                for (int i = 0; i < linkTags.size(); i++) {
                    String newUrl = linkTags.get(i);
                    cr.addToChildSitemap(url, newUrl);
                    if(!cr.visited(newUrl)) {
                        if (validator.isCurrentDepthLessThanMaxDepth(currentDepth, maxDepth)){
                            if (validator.checkNewUrl(url, newUrl)) {
                                LinkIndexer linkIndexer = (LinkIndexer) factory.getBean("linkIndexer", requestId, newUrl, cr, currentDepth + 1, maxDepth, seedUrl);
                                actions.add(linkIndexer);
                            }
                        }
                    }
                }

                LOGGER.info("Parent: " + url + " Children: " + linkTags.size() + " Visited Size: " + cr.size());
                service.setStatus(requestId, String.format(STATUS_PROCESSING, cr.size()));
                invokeAll(actions);
            } catch (Exception e) {
            }
        }
    }
}