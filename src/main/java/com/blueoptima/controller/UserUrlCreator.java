package com.blueoptima.controller;

import com.blueoptima.bean.HttpRequest;
import com.blueoptima.bean.InputDetail;
import com.blueoptima.bean.MethodType;
import com.blueoptima.common.ApplicationException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Suranjay on 12/07/18.
 */
public class UserUrlCreator {

    private static final String BASE_URL = "https://api.github.com/search/users?q=";
    private static final String NAME_SUFFIX = "+in:fullname";
    private static final String LOCATION_SUFFIX = "location:\"%s\"";
    private static final String PAGE_SUFFIX = "&per_page=100";

    public static HttpRequest createUserSearchRequest(InputDetail inputDetail) throws ApplicationException {

        StringBuilder sb = new StringBuilder(BASE_URL);
        if (inputDetail.getFirstName() != null || inputDetail.getLastName() != null) {
            try {
                sb.append(URLEncoder.encode(inputDetail.getFirstName() + " " + inputDetail.getLastName(), "UTF-8")).append(NAME_SUFFIX);
            } catch (UnsupportedEncodingException e) {
                throw new ApplicationException("Error while encoding name suffix", e);
            }
        }

        if (inputDetail.getLocation() != null) {
            try {
                sb.append("+").append(URLEncoder.encode(String.format(LOCATION_SUFFIX, inputDetail.getLocation()),  "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new ApplicationException("Error while encoding location suffix", e);
            }
        }

        sb.append(PAGE_SUFFIX);

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrlString(sb.toString());
        httpRequest.setRequestType(HttpRequest.RequestType.SEARCH);
        httpRequest.setMethodType(MethodType.GET);
        return httpRequest;

    }
}
