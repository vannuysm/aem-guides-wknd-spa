package com.wyndham.common.services;

import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;

import java.util.Map;
import java.util.Set;

public interface KeyValueFragmentAdapter {
    JsonObject adaptKeyValuePairsToFragments(KeyValuePairService kvService, Map<String, String> fragmentToKeyValue);

    JsonObject adaptKeyValuePairsToFragments(KeyValuePairService kvService, Map<String, String> fragmentToKeyValue, Set<String> keysToUppercase);

    JsonObject adaptKeyValuePairsToFragments(SlingHttpServletRequest httpRequest, KeyValuePairService kvService, Map<String, String> fragmentToKeyValue);

    JsonObject adaptKeyValuePairsToFragments(SlingHttpServletRequest httpRequest, KeyValuePairService kvService, Map<String, String> fragmentToKeyValue, Set<String> keysToUppercase);
}
