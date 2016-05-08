package com.nns.wd.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nns.wd.api.hateoas.ApiResponse;
import com.nns.wd.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Nirav on 09/05/2016.
 */
@RestController
@RequestMapping("/result")
public class ResultResource extends BaseResource{

    public final static String  WAIT_MESSAGE = "Please comeback in some time for the result";
    public final static String  SITEMAP_MESSAGE = "Sitemap Below";

    @Autowired
    RequestService service;

    @RequestMapping(method = RequestMethod.GET)
    HttpEntity<ApiResponse> getRequestResult(@RequestParam(name="id") String id) {
        String requestStatus = service.getRequestStatus(id);
        String result = service.getResult(id);
        ApiResponse rep = getHateoasRepresentation(
                requestStatus, (result != null) ? SITEMAP_MESSAGE : WAIT_MESSAGE);

        Type type = new TypeToken<Map<String, Collection<String>>>() {
        }.getType();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Collection<String>> siteMap = gson.fromJson(result, type);
        rep.setSiteMap(siteMap);
        return new ResponseEntity<>(rep, HttpStatus.OK);

    }
}