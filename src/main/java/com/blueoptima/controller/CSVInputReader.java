package com.blueoptima.controller;

import com.blueoptima.bean.InputDetail;
import com.blueoptima.common.ApplicationException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suranjay on 13/07/18.
 */
public class CSVInputReader implements InputReader {

    @Override
    public List<InputDetail> parseInput(String filePath) throws ApplicationException {
        CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');

        CSVParser parser;
        try {
            parser = new CSVParser(new FileReader(filePath), format);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationException("Error while parsing CSV", e);
        }

        List<InputDetail> users = new ArrayList<>();
        for(CSVRecord record : parser){
            InputDetail user = new InputDetail();
            user.setFirstName(record.get("First Name"));
            user.setLastName(record.get("Last Name"));
            user.setLocation(record.get("Location"));
            users.add(user);
        }
        try {
            parser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }
}
