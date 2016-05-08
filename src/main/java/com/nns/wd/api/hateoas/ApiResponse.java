package com.nns.wd.api.hateoas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Nirav on 09/05/2016.
 */
public class ApiResponse extends ResourceSupport {

    String message;
    String details;
    Map<String, Collection<String>> siteMap;

    @JsonCreator
    public ApiResponse(@JsonProperty("message") String msg, @JsonProperty("details") String details) {
        this.message = msg;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Map<String, Collection<String>> getSiteMap() {
        return siteMap;
    }

    public void setSiteMap(Map<String, Collection<String>> siteMap) {
        this.siteMap = siteMap;
    }
}
