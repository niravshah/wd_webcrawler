package com.nns.wd.services;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Nirav on 09/05/2016.
 */
public interface RequestService {

    String startNewRequest(String url, int maxD);
    String getRequestStatus(String id);
    String getResult(String id);
    void setResult(String id, Map<String, Collection<String>> siteMap);
    void setStatus(String id, String result);
}
