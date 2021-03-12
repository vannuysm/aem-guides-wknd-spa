package com.wyndham.common.services;

import org.apache.sling.api.SlingHttpServletRequest;

import java.util.List;
import java.util.Map;

public interface KeyValuePairService {
    Map<String, String> getKeyValuePairs();

    Map<String, String> getKeyValuePairs(List<String> keysFilter);

    Map<String, String> getKeyValuePairs(List<String> keysFilter, List<String> valuesFilter);

    Map<String, String> getKeyValuePairs(SlingHttpServletRequest httpRequest);

    Map<String, String> getKeyValuePairs(SlingHttpServletRequest httpRequest, List<String> keysFilter);

    Map<String, String> getKeyValuePairs(SlingHttpServletRequest httpRequest, List<String> keysFilter, List<String> valuesFilter);

    String getKeyValuePairsJson();

    String getKeyValuePairsJson(List<String> keysFilter);

    String getKeyValuePairsJson(List<String> keysFilter, List<String> valuesFilter);

    String getKeyValuePairsJson(SlingHttpServletRequest httpRequest);

    String getKeyValuePairsJson(SlingHttpServletRequest httpRequest, List<String> keysFilter);

    String getKeyValuePairsJson(SlingHttpServletRequest httpRequest, List<String> keysFilter, List<String> valuesFilter);
}
