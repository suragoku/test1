package com.blueoptima.controller;

import com.blueoptima.bean.InputDetail;
import com.blueoptima.common.ApplicationException;

import java.util.List;

/**
 * Created by Suranjay on 13/07/18.
 */
public interface InputReader {
    List<InputDetail> parseInput(String filePath) throws ApplicationException;
}
