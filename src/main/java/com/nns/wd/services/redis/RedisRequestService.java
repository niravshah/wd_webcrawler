package com.nns.wd.services.redis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nns.wd.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Nirav on 09/05/2016.
 */
@Service
public class RedisRequestService implements RequestService {

    public static final String NEW_REQUEST_KEY = "new-request";
    public static final String STATUS_PROCESSING = "Processing";
    public static final String STATUS_COMPLETE = "Complete. Total Links indexed: %s";

    @Autowired
    RedisTemplate<String, Object> template;

    public String startNewRequest(String url, int maxD) {
        String uuid = UUID.randomUUID().toString();
        String newRequest = uuid + "&&" + url + "&&" + maxD;
        template.convertAndSend(NEW_REQUEST_KEY, newRequest);
        setStatus(uuid, STATUS_PROCESSING);
        return uuid;
    }

    public String getRequestStatus(String id) {
        return (String) template.opsForValue().get(id + "&&status");
    }

    public String getResult(String id){
        return (String) template.opsForValue().get(id+"&&result");
    }

    public void setResult(String id, Map<String, Collection<String>> siteMap) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        template.opsForValue().set(id + "&&status", String.format(STATUS_COMPLETE,siteMap.size()));
        template.opsForValue().set(id + "&&result", gson.toJson(siteMap));
    }

    public void setStatus(String id, String result) {
        template.opsForValue().set(id + "&&status", result);
    }
}
