package com.torre.nico.util;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class JsonUtil {

    private JsonUtil() {
        //default constructor for util class
    }

    public static JsonObject convertFromString(String jsonString) {
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
        return object;
    }

    public static String getString (JsonObject jsonObject, String parameter) {
        if (jsonObject.isNull(parameter)) {
            return null;
        } else {
            return jsonObject.getString(parameter);
        }
    }
}
