package com.nns.wd.api;

import com.nns.wd.api.hateoas.ApiResponse;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Nirav on 09/05/2016.
 */
public class BaseResource {

    public ApiResponse getHateoasRepresentation(String message, String details) {
        return new ApiResponse(message, details);
    }

    public void addRequesttLink(ResourceSupport rep, String url, int maxD) {
        rep.add(linkTo(methodOn(RequestResource.class).createNewRequest(url,maxD)).withRel("new_request"));
    }

    public void addResultLink(ResourceSupport rep, String id) {
        rep.add(linkTo(methodOn(ResultResource.class).getRequestResult(id)).withRel("result"));
    }

}
