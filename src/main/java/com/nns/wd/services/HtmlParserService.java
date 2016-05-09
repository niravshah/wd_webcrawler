package com.nns.wd.services;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nirav on 09/05/2016.
 */
@Service
public class HtmlParserService {

    public List<String> getLinkTags(String url) throws ParserException, IOException {
        URL urlLink = new URL(url);
        List<String> linkTags = new ArrayList<>();
        Parser parser = new Parser(urlLink.openConnection());
        NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
        for (int i = 0; i < list.size(); i++) {
            LinkTag extracted = (LinkTag) list.elementAt(i);
            linkTags.add(extracted.extractLink());
        }
        return linkTags;
    }
}
