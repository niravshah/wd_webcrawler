package com.nns.wd.services;

import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Nirav on 09/05/2016.
 */
@Service
public class ValidatorService {
    public boolean checkNewUrl(String currUrl, String newUrl) throws MalformedURLException {
        URL currentUrl = new URL(currUrl);
        if (!newUrl.isEmpty()) {
            URL newUrlLink = new URL(newUrl);
            if (checkDomain(currentUrl, newUrlLink)){
                return true;
            }
        }
        return false;
    }

    public boolean isCurrentDepthLessThanMaxDepth(int currentDepth, int maxDepth) {
        return (maxDepth > 0) ? (currentDepth < maxDepth) : true;
    }

    private boolean checkDomain(URL urlLink, URL newUrlLink) {
        return newUrlLink.getHost().equals(urlLink.getHost());
    }
}
