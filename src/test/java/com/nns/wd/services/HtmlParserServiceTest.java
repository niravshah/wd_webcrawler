package com.nns.wd.services;

import org.hamcrest.Matchers;
import org.htmlparser.util.ParserException;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Nirav on 09/05/2016.
 */
public class HtmlParserServiceTest {

    @Test
    public void getLinkTagsTest() throws IOException, ParserException {
        HtmlParserService service = new HtmlParserService();
        List<String> linkTags = service.getLinkTags("http://movewithin.org");
        assertThat(linkTags.size(), Matchers.is(20));
    }
}
