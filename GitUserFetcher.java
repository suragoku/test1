package com.blueoptima;

import com.blueoptima.common.ApplicationException;
import com.blueoptima.controller.CSVInputReader;
import com.blueoptima.controller.RateLimitProcessor;

import java.util.Map;

/**
 * Created by Suranjay on 13/07/18.
 */
public class GitUserFetcher {

    public static void main(String[] args) throws ApplicationException {
        RateLimitProcessor rateLimitProcessor = new RateLimitProcessor(new CSVInputReader());
        Map<String, Map<String, Integer>> userCommitMap = rateLimitProcessor.process("src/main/resources/RateLimiterInput.csv");
        System.out.println(userCommitMap);
    }
}
