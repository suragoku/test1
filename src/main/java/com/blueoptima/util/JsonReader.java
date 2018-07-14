package com.blueoptima.util;

import com.blueoptima.common.ApplicationException;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by Suranjay on 11/07/18.
 */
public class JsonReader {

    private static final Logger LOGGER = Logger.getLogger(JsonReader.class.getName());

    private static final JSONParser jsonParser = new JSONParser();

    public static <T> T getValueFromJson(String json, String attribute) throws ApplicationException {

        Object object;
        try {
            object = jsonParser.parse(json);
        } catch (ParseException e) {
            LOGGER.error("Error while parsing json", e);
            throw new ApplicationException("Error while parsing json", e);
        }

        JSONObject jsonObject = (JSONObject) object;

        return (T) jsonObject.get(attribute);
    }

    public static JSONArray getJsonArrayFromJson(String json, String attribute) throws ApplicationException {

        Object object;
        try {
            object = jsonParser.parse(json);
        } catch (ParseException e) {
            LOGGER.error("Error while parsing json", e);
            throw new ApplicationException("Error while parsing json", e);
        }

        JSONObject jsonObject = (JSONObject) object;

        return (JSONArray) jsonObject.get(attribute);
    }

    /*public static void main(String[] args) throws ApplicationException {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Accept", "application/vnd.github.cloak-preview");
        HttpResponse dataFromURL = HttpUrlDataFetcher.getDataFromURL("https://api.github.com/search/commits?q=committer%3AKirinDave&per_page=100", headerMap, HttpUrlDataFetcher.MethodType.GET);
        Map<String, Integer> contributingReposMap = new HashMap<>();
        JSONArray items = JsonReader.getJsonArrayFromJson(dataFromURL.getBody(), "items");
        items.stream().forEach(item-> {
            System.out.println(((JSONObject)((JSONObject)item).get("repository")).get("full_name"));
            String o = (String) ((JSONObject) ((JSONObject) item).get("repository")).get("full_name");
            Integer count = contributingReposMap.get(o);

            if (count == null) {
                contributingReposMap.put(o, 1);
            } else {
                contributingReposMap.put(o, ++count);
            }
        });
        System.out.println(contributingReposMap);
    }*/
}
