package com.blueoptima.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by Suranjay on 10/07/18.
 */
public class OutputDetail {

    private String loginId;
    private String name;
    private Map<String, Integer> contributingReposMap;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getContributingReposMap() {
        return contributingReposMap;
    }

    public void setContributingReposMap(Map<String, Integer> contributingReposMap) {
        this.contributingReposMap = contributingReposMap;
    }

    @Override
    public String toString() {
        return "OutputDetail{" +
                "loginId='" + loginId + '\'' +
                ", name='" + name + '\'' +
                ", contributingReposMap=" + contributingReposMap +
                '}';
    }
}
