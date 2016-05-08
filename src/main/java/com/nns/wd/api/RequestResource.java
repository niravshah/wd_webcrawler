package com.nns.wd.api;

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

/**
 * Created by Nirav on 09/05/2016.
 */
@RestController
@RequestMapping("/request")
public class RequestResource extends BaseResource{

    public static String MESSAGE = "New Request Started";
    public static String DETAIL_MESSAGE = "Request ID: %s";

    @Autowired
    RequestService service;

    @RequestMapping(method = RequestMethod.GET)
    HttpEntity<ApiResponse> createNewRequest(@RequestParam(name = "url") String url, @RequestParam(name = "md") int maxD) {
        String s = service.startNewRequest(url, maxD);
        ApiResponse rep =  getHateoasRepresentation(MESSAGE, String.format(DETAIL_MESSAGE,s));
        addResultLink(rep,s);
        return new ResponseEntity<>(rep, HttpStatus.OK);
    }

}