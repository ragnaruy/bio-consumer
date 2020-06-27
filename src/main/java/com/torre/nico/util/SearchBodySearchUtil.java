package com.torre.nico.util;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class SearchBodySearchUtil {

    private final static String AND = "and";
    private final static String TERM = "term";
    private final static String SKILL_TERM = "Software Development";
    private final static String SKILL_EXPERIENCE = "potential-to-develop";

    private SearchBodySearchUtil() {
        //default constructor for util class
    }

    public static JsonObject searchCriteriaForDevelopers (String name) {
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add(AND, Json.createArrayBuilder()
                        .add(createNameCondition(name))
                        .add(developerCondition())
                );

       return builder.build();
    }

    private static JsonObjectBuilder createNameCondition(String name) {
        JsonObjectBuilder termCondition = Json.createObjectBuilder().add(TERM, name);
        return Json.createObjectBuilder().add("name", termCondition);
    }

    private static JsonObjectBuilder developerCondition() {
        JsonObjectBuilder condition = Json.createObjectBuilder()
                .add(TERM, SKILL_TERM)
                .add("experience", SKILL_EXPERIENCE);
        return Json.createObjectBuilder().add("skill", condition);
    }
}
