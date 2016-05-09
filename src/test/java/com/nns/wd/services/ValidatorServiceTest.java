package com.nns.wd.services;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.net.MalformedURLException;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Nirav on 09/05/2016.
 */
public class ValidatorServiceTest {

    @Test(expected = MalformedURLException.class)
    public void checkEmptyOldUrlTest() throws MalformedURLException {
        ValidatorService service = new ValidatorService();
        service.checkNewUrl("", "http://google.com");
    }


    @Test
    public void checkEmptyNewUrlTest() throws MalformedURLException {
        ValidatorService service = new ValidatorService();
        boolean b = service.checkNewUrl("http://google.com", "");
        assertThat(b, Matchers.is(false));
    }

    @Test
    public void checkNewUrlTest() throws MalformedURLException {
        ValidatorService service = new ValidatorService();
        boolean b = service.checkNewUrl("http://google.com", "http://google.com/blah");
        assertThat(b, Matchers.is(true));
    }

    @Test
    public void checkCurrentDepthGreaterTest(){
        ValidatorService service = new ValidatorService();
        boolean b = service.isCurrentDepthLessThanMaxDepth(2, 1);
        assertThat(b,Matchers.is(false));
    }


    @Test
    public void checkCurrentDepthSmallerTest(){
        ValidatorService service = new ValidatorService();
        boolean b = service.isCurrentDepthLessThanMaxDepth(1, 2);
        assertThat(b,Matchers.is(true));
    }

    @Test
    public void checkMaxDepthAllTest(){
        ValidatorService service = new ValidatorService();
        boolean b = service.isCurrentDepthLessThanMaxDepth(2, -1);
        assertThat(b,Matchers.is(true));
    }
}
