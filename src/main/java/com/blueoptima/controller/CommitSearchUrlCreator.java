package com.blueoptima.controller;

import com.blueoptima.bean.HttpRequest;
import com.blueoptima.bean.MethodType;
import com.blueoptima.common.ApplicationException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suranjay on 13/07/18.
 */
public class CommitSearchUrlCreator {

    private static final String BASE_URL = "https://api.github.com/search/commits?q=";
    private static final String COMMITTER_SUFFIX = "committer:%s";
    private static final String DATE_SUFFIX = "&committer-date:<REPLACE";
    private static final String SORT_SUFFIX = "&sort=committer-date&order=desc";
    private static final String PAGESIZE_SUFFIX = "&per_page=100";

    public static HttpRequest getCommitSearchRequest(String loginId) throws ApplicationException {
        StringBuilder sb = new StringBuilder(BASE_URL);
        try {
            sb.append(URLEncoder.encode(String.format(COMMITTER_SUFFIX, loginId), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new ApplicationException("Error while encoding URL", e);
        }
        sb.append(DATE_SUFFIX);
        sb.append(SORT_SUFFIX);
        sb.append(PAGESIZE_SUFFIX);

        HttpRequest request = new HttpRequest();
        request.setUrlString(sb.toString());
        request.setMethodType(MethodType.GET);
        request.setRequestType(HttpRequest.RequestType.SEARCH);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Accept", "application/vnd.github.cloak-preview");
        request.setHeaderMap(headerMap);
        return request;
    }
}
