package com.dell.automation;

public class PayLoad {

    public static String addCategories(String name, String id) {
        String payload =
                "{\r\n"
                        + "  \"name\": \"" + name + "\",\r\n"
                        + "  \"id\": \"" + id + "\"\r\n"
                        + "}";
        return payload;
    }

    public static String updateCategoriesname(String name) {
        String updatename =
                "{\r\n"
                        + "  \"name\": \"" + name + "\"\r\n"
                        + "  \r\n"
                        + "}";
        return updatename;
    }
}
