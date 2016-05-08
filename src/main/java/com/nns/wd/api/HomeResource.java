package com.nns.wd.api;

import com.nns.wd.api.hateoas.ApiResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Nirav on 09/05/2016.
 */
@RestController
@RequestMapping("/")
public class HomeResource    extends BaseResource {

    public static final String MESSAGE = "Welcome.";
    public static final String DETAIL_MESSAGE = "Please use one of the below links to navigate.";

    @RequestMapping(method = RequestMethod.GET)
    HttpEntity<ApiResponse> home() {
        ApiResponse rep =  getHateoasRepresentation(MESSAGE, DETAIL_MESSAGE);
        addRequesttLink(rep,"URL",-1);
        addResultLink(rep,"UID");
        return new ResponseEntity<>(rep, HttpStatus.OK);
    }
}
