package com.blueoptima.controller;

import com.blueoptima.bean.HttpRequest;
import com.blueoptima.bean.HttpResponse;
import com.blueoptima.bean.MethodType;
import com.blueoptima.common.ApplicationException;
import com.blueoptima.util.HttpUrlDataFetcher;
import com.blueoptima.util.JsonReader;
import org.json.simple.JSONObject;


/**
 * Created by Suranjay on 11/07/18.
 */
public class DataFetcherWithRateLimit {

    private static DataFetcherWithRateLimit INSTANCE = null;
    private static final HttpRequest initRequest;
    static {
        initRequest = new HttpRequest();
        initRequest.setUrlString("https://api.github.com/rate_limit");
        initRequest.setMethodType(MethodType.GET);
    }

    public static DataFetcherWithRateLimit getInstance() throws ApplicationException {
        if (INSTANCE == null) {
            INSTANCE = new DataFetcherWithRateLimit();
        }
        return INSTANCE;
    }

    private DataFetcherWithRateLimit() throws ApplicationException {
        HttpResponse initResponse = HttpUrlDataFetcher.getDataFromURL(initRequest);
        if (initResponse!= null) {
            limitRemaining = ((Long) ((JSONObject)(JsonReader.<JSONObject>getValueFromJson(initResponse.getBody(), "resources")).get("search")).get("remaining")).intValue();
            limitReset = ((Long) ((JSONObject)(JsonReader.<JSONObject>getValueFromJson(initResponse.getBody(), "resources")).get("search")).get("reset")).intValue();
            coreRemaining = ((Long) ((JSONObject)(JsonReader.<JSONObject>getValueFromJson(initResponse.getBody(), "resources")).get("core")).get("remaining")).intValue();
            coreReset = ((Long) ((JSONObject)(JsonReader.<JSONObject>getValueFromJson(initResponse.getBody(), "resources")).get("core")).get("reset")).intValue();
            System.out.print("Init: ");
            System.out.print(" limitRemaining:" +limitRemaining);
            System.out.print(" limitReset:" + limitReset);
            System.out.print(" coreRemaining" + coreRemaining);
            System.out.println(" coreReset" + coreReset);
        }
    }

    private int limitRemaining;
    private long limitReset;
    private int coreRemaining;
    private long coreReset;

    public HttpResponse fetchDataFromUrl(HttpRequest request) throws ApplicationException {
        System.out.println("Fetching for:"+request.getUrlString());

        HttpResponse response;

        long timeToReset = 0;
        int countRemaining = 1;

        if (request.getRequestType() == HttpRequest.RequestType.SEARCH) {
            countRemaining = limitRemaining;
            timeToReset = limitReset;
        } else if (request.getRequestType() == HttpRequest.RequestType.CORE) {
            countRemaining = coreRemaining;
            timeToReset = coreReset;
        }

        if (countRemaining <= 0) {
            System.out.println("Going to wait for.." + (timeToReset - System.currentTimeMillis() / 1000) + " seconds...");
            while (System.currentTimeMillis() / 1000 < timeToReset) {
            }
            System.out.println("Wait complete");
        }
        response = HttpUrlDataFetcher.getDataFromURL(request);
        if (response != null && response.getHeaderMap() != null) {
            if (request.getRequestType() == HttpRequest.RequestType.SEARCH) {
                limitRemaining = Integer.valueOf(response.getHeaderMap().get("X-RateLimit-Remaining").get(0));
                limitReset = Long.valueOf(response.getHeaderMap().get("X-RateLimit-Reset").get(0));
            } else if (request.getRequestType() == HttpRequest.RequestType.CORE){
                coreRemaining = Integer.valueOf(response.getHeaderMap().get("X-RateLimit-Remaining").get(0));
                coreReset = Long.valueOf(response.getHeaderMap().get("X-RateLimit-Reset").get(0));

            }
        }

        /*System.out.print("limitRemaining:" +limitRemaining);
        System.out.print(" limitReset:" + limitReset);
        System.out.print(" coreRemaining:" + coreRemaining);
        System.out.println(" coreReset:" + coreReset);*/
        return response;
    }
}
