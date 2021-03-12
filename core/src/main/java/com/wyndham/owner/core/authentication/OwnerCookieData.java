package com.wyndham.owner.core.authentication;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class OwnerCookieData {
    private String username;
    private String apiResponse;
    private long creationTime;

    public OwnerCookieData(String username, String apiResponse, long creationTime) {
        this.username = username;
        this.apiResponse = apiResponse;
        this.creationTime = creationTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(String apiResponse) {
        this.apiResponse = apiResponse;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public static boolean isValid(OwnerCookieData cookieData) {
        try {
            if (cookieData.getUsername() != null && !cookieData.getUsername().isEmpty()) {
                new JsonParser().parse(cookieData.getApiResponse()).getAsJsonObject(); // test parsing the cookie, no-throw is valid
                return true;
            }
        } catch (JsonSyntaxException e) {
        }
        return false;
    }
}

