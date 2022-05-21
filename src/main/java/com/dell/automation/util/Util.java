package com.dell.automation.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Util {

    public static JSONObject getJsonObject(final String path) throws IOException, ParseException {
        return (JSONObject) new JSONParser().parse(new FileReader(path));
    }
}
