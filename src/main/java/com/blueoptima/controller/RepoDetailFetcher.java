package com.blueoptima.controller;

import com.blueoptima.bean.HttpResponse;
import com.blueoptima.common.ApplicationException;
import com.blueoptima.util.JsonReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suranjay on 11/07/18.
 */
public class RepoDetailFetcher extends AbstractDataFetcher<Map<String, Integer>> {


    public RepoDetailFetcher() {
        super();
        data = new HashMap<>();
    }

    @Override
    protected void parseResponse(HttpResponse response) throws ApplicationException {
        JSONArray items = JsonReader.getJsonArrayFromJson(response.getBody(), "items");
        items.stream().forEach(item-> {
            String o = (String) ((JSONObject) ((JSONObject) item).get("repository")).get("full_name");
//            oldLastDate = lastDate;
            String lastDateCurrent = ((String) ((JSONObject) (JSONObject) ((JSONObject) ((JSONObject) item).get("commit")).get("committer")).get("date")).substring(0, 10);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date lastDateCurr = dateFormat.parse(lastDateCurrent);
                Date lastDat = dateFormat.parse(lastDate);
                if (lastDat.after(lastDateCurr)) {
                    lastDate = lastDateCurrent;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            dateFetchCount++;
            Integer count = data.get(o);
            if (count == null) {
                data.put(o, 1);
            } else {
                data.put(o, ++count);

            }
        });
    }

    public Long getTotalCount() {
        return totalCount;
    }
}
