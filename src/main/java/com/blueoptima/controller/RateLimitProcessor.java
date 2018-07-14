package com.blueoptima.controller;

import com.blueoptima.bean.HttpRequest;
import com.blueoptima.bean.InputDetail;
import com.blueoptima.bean.UserDetail;
import com.blueoptima.common.ApplicationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Suranjay on 13/07/18.
 */
public class RateLimitProcessor {

    private InputReader inputReader;

    public RateLimitProcessor(InputReader inputReader) {
        this.inputReader = inputReader;
    }

    public Map<String, Map<String, Integer>> process(String filePath) throws ApplicationException {
        List<InputDetail> inputDetails = inputReader.parseInput(filePath);
        Map<String, Map<String, Integer>> userRepoCommitMap = new HashMap<>();

        for (InputDetail detail : inputDetails) {
            AbstractDataFetcher<List<UserDetail>> dataFetcher1 = new UserDetailFetcher();
            HttpRequest httpRequest = UserUrlCreator.createUserSearchRequest(detail);
            List<UserDetail> userDetails = dataFetcher1.getData(httpRequest);
            System.out.println("Found " + userDetails.size() + " users matching for " + detail.getFirstName() + " "+ detail.getLastName()+ ", " + detail.getLocation());
            for (UserDetail userDetail : userDetails) {
                AbstractDataFetcher<Map<String, Integer>> repoDetailFetcher = new RepoDetailFetcher();
                httpRequest = CommitSearchUrlCreator.getCommitSearchRequest(userDetail.getLoginId());
                Map<String, Integer> repoCommitMap = repoDetailFetcher.getData(httpRequest);
                System.out.println("Total commits count from search: " + ((RepoDetailFetcher)repoDetailFetcher).getTotalCount());
                int count=0;
                for (Integer integer : repoCommitMap.values()) {
                    count = count + integer;
                }
                System.out.println("Totak commits in Map: " + count);
                userRepoCommitMap.put(userDetail.getLoginId(), repoCommitMap);
            }
        }
        return userRepoCommitMap;

    }
}
