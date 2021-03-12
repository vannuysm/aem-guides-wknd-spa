package com.wyndham.common.services.impl;

import com.google.gson.JsonObject;
import com.wyndham.common.services.KeyValueFragmentAdapter;
import com.wyndham.common.services.KeyValuePairService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@Component(service = KeyValueFragmentAdapter.class)
public class KeyValueFragmentAdapterImpl implements KeyValueFragmentAdapter {
    private static final String numberUppercase = "{NUMBER}";
    private static final String numberLowercase = "{number}";

    @Override
    public JsonObject adaptKeyValuePairsToFragments(KeyValuePairService kvService, Map<String, String> fragmentToKeyValue) {
        return adaptKeyValuePairsToFragments(null, kvService, fragmentToKeyValue, new HashSet<>());
    }

    @Override
    public JsonObject adaptKeyValuePairsToFragments(KeyValuePairService kvService, Map<String, String> fragmentToKeyValue, Set<String> keysToUppercase) {
        return adaptKeyValuePairsToFragments(null, kvService, fragmentToKeyValue, new HashSet<>());
    }

    @Override
    public JsonObject adaptKeyValuePairsToFragments(SlingHttpServletRequest httpRequest, KeyValuePairService kvService, Map<String, String> fragmentToKeyValue) {
        return adaptKeyValuePairsToFragments(httpRequest, kvService, fragmentToKeyValue, new HashSet<>());
    }

    @Override
    public JsonObject adaptKeyValuePairsToFragments(SlingHttpServletRequest httpRequest, KeyValuePairService kvService, Map<String, String> fragmentToKeyValue, Set<String> keysToUppercase) {
        Map<String, String> jcrKeyToValue = kvService.getKeyValuePairs(httpRequest, new ArrayList<>(fragmentToKeyValue.values()));
        JsonObject json = new JsonObject();

        for (Entry<String, String> entry : fragmentToKeyValue.entrySet()) {
            String fragmentKey = entry.getKey();
            String jcrValue = jcrKeyToValue.get(entry.getValue());
            jcrValue = applyCasing(fragmentKey, jcrValue, keysToUppercase);
            json.addProperty(fragmentKey, jcrValue);
        }
        
        return json;
    }

    // Uppercase if desired
    private String applyCasing(String key, String value, Set<String> keysToUppercase) {
        String correctlyCasedValue = value;
        if (keysToUppercase.contains(key) && correctlyCasedValue != null) {
            correctlyCasedValue = correctlyCasedValue.toUpperCase();
            correctlyCasedValue = correctlyCasedValue.replace(numberUppercase, numberLowercase);
        }
        return correctlyCasedValue;
    }
}
