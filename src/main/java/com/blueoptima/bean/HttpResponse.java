package com.blueoptima.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by Suranjay on 11/07/18.
 */
public class HttpResponse {

    private String body;
    private Map<String, List<String>> headerMap;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, List<String>> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, List<String>> headerMap) {
        this.headerMap = headerMap;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "body='" + body + '\'' +
                ", headerMap=" + headerMap +
                '}';
    }
}
