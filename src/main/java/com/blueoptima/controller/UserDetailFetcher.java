package com.blueoptima.controller;

import com.blueoptima.bean.HttpRequest;
import com.blueoptima.bean.HttpResponse;
import com.blueoptima.bean.MethodType;
import com.blueoptima.bean.UserDetail;
import com.blueoptima.common.ApplicationException;
import com.blueoptima.util.JsonReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Suranjay on 11/07/18.
 */
public class UserDetailFetcher extends AbstractDataFetcher<List<UserDetail>> {

    public UserDetailFetcher() {
        super();
        data = new ArrayList<>();
    }

    @Override
    protected void parseResponse(HttpResponse response) throws ApplicationException {
        JSONArray items = JsonReader.getJsonArrayFromJson(response.getBody(), "items");
        items.stream().forEach(item-> {
            String userDetailUrl = (String) ((JSONObject) item).get("url");
            HttpRequest httpRequest = new HttpRequest();
            httpRequest.setUrlString(userDetailUrl);
            httpRequest.setMethodType(MethodType.GET);
            httpRequest.setRequestType(HttpRequest.RequestType.CORE);
            try {
                HttpResponse userDataResponse = DataFetcherWithRateLimit.getInstance().fetchDataFromUrl(httpRequest);
                String body = userDataResponse.getBody();
                UserDetail userDetail = new UserDetail();
                userDetail.setLoginId(JsonReader.<String>getValueFromJson(body,"login"));
                userDetail.setCompany(JsonReader.<String>getValueFromJson(body,"company"));
                userDetail.setEmail(JsonReader.<String>getValueFromJson(body,"email"));
                userDetail.setId(JsonReader.<Long>getValueFromJson(body,"id"));
                userDetail.setFollowering(JsonReader.<Long>getValueFromJson(body,"following"));
                userDetail.setFollowers(JsonReader.<Long>getValueFromJson(body,"followers"));
                userDetail.setLocation(JsonReader.<String>getValueFromJson(body,"location"));
                data.add(userDetail);
                dateFetchCount++;
            } catch (ApplicationException e) {
                e.printStackTrace();
            }
        });
    }
}
