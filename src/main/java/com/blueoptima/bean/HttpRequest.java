package com.blueoptima.bean;

import java.util.Map;

/**
 * Created by Suranjay on 11/07/18.
 */
public class HttpRequest {
    public static enum RequestType {
        SEARCH, CORE;
    }
    private String urlString;
    private Map<String, String> headerMap;
    private MethodType methodType;
    private RequestType requestType;

    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public MethodType getMethodType() {
        return methodType;
    }

    public void setMethodType(MethodType methodType) {
        this.methodType = methodType;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "urlString='" + urlString + '\'' +
                ", headerMap=" + headerMap +
                ", methodType=" + methodType +
                ", requestType=" + requestType +
                '}';
    }
}
