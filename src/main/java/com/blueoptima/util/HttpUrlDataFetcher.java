package com.blueoptima.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blueoptima.bean.HttpRequest;
import com.blueoptima.bean.HttpResponse;
import com.blueoptima.bean.MethodType;
import com.blueoptima.common.ApplicationException;
import org.apache.log4j.Logger;

/**
 * Created by Suranjay on 10/07/18.
 */
public class HttpUrlDataFetcher {
    private static final Logger LOGGER = Logger.getLogger(HttpUrlDataFetcher.class.getName());
    private static final int CONNECTION_TIMEOUT = 4000;
    private static final int READ_TIMEOUT = 20000;
    private static final String OATH_TOKEN = "ba436a40ed58e6f1bad1f764e652d1478a0f93ee";

    public static HttpResponse getDataFromURL(HttpRequest httpRequest) throws ApplicationException {
        return getDataFromURL(httpRequest, CONNECTION_TIMEOUT, READ_TIMEOUT);
    }

    public static HttpResponse getDataFromURL(HttpRequest httpRequest, int connectionTimeout, int readTimeout) throws ApplicationException {
        StringBuilder content = new StringBuilder();

        URL url;
        try {
            url = new URL(httpRequest.getUrlString());
        } catch (MalformedURLException e) {
            LOGGER.error("Invalid URL supplied", e);
            throw new ApplicationException("Invalid URL supplied", e);
        }

        final HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(connectionTimeout);
            urlConnection.setReadTimeout(readTimeout);
            if (httpRequest.getHeaderMap() != null) {
                httpRequest.getHeaderMap().entrySet().stream().forEach(entry -> urlConnection.setRequestProperty(entry.getKey(), entry.getValue()));
            }
            urlConnection.setRequestProperty("Authorization", "token " + OATH_TOKEN);
            urlConnection.setRequestMethod(httpRequest.getMethodType().getType());
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
        } catch (IOException e) {
            LOGGER.error("Exception while connecting to URL:" + httpRequest.getUrlString(), e);
            throw new ApplicationException("Exception while connecting to URL:" + httpRequest.getUrlString(), e);
        }

        HttpResponse response = new HttpResponse();
        response.setHeaderMap(urlConnection.getHeaderFields());

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            for (String line; (line = reader.readLine()) != null; ) {
                content.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Exception while reading output:", e);
            throw new ApplicationException("Exception while reading output", e);
        } finally {
            if (reader!= null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        response.setBody(content.toString());
        return response;
    }

    /*public static void main(String[] args) throws ApplicationException {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Accept", "application/vnd.github.cloak-preview");
        System.out.println(HttpUrlDataFetcher.getDataFromURL("https://api.github.com/search/commits?q=committer%3AKirinDave&type=Commits&page=34", headerMap, MethodType.GET).getBody());
    }*/


}
