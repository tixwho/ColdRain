package com.coldrain.backend.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestGsonParse {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        TestNestedJson parsedNest = gson.fromJson(getTestJsonStr(), TestNestedJson.class);
        System.out.println(parsedNest);
        TestSingleJson parseSingle = gson.fromJson(getSingleJsonStr(), TestSingleJson.class);
        System.out.println(parseSingle);
    }

    
    public static String getTestJsonStr() {
        String rtr = "{\r\n"
            + "\"order\":5,\r\n"
            + "\"date\": \"2018-08-31 16:56:18\",\r\n"
            + "  \"thatJson\":{\r\n"
            + "    \"name\":\"aaa\",\r\n"
            + "    \"aLong\":17718817086994,\r\n"
            + "    \"args\":[\"I\",\"see\",\"you\"]\r\n"
            + "  }\r\n"
            + "}";
        return rtr;
    }
    
    public static String getSingleJsonStr() {
        String rtr = "{\r\n"
            + "    \"name\":\"aaa\",\r\n"
            + "    \"aLong\":17718817086994,\r\n"
            + "    \"args\":[\"I\",\"see\",\"you\"]\r\n"
            + "  }\r\n";
        return rtr;
    }
}
