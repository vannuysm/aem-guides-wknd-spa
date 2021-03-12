package com.wyndham.common.services.impl;

import com.day.cq.search.QueryBuilder;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.wyndham.bbm.core.models.ContentFragmentPair;
import com.wyndham.common.WyndhamUtils;
import com.wyndham.common.services.ContentVariationService;
import com.wyndham.common.services.KeyValuePairService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;

import static com.wyndham.bbm.core.utils.Utils.executeQueryMap;

@Component(service = KeyValuePairService.class)
public class KeyValuePairServiceImpl implements KeyValuePairService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference
    private QueryBuilder queryBuilder;

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    private ContentVariationService contentVariationService;

    @Override
    public Map<String, String> getKeyValuePairs() {
        return getKeyValuePairs(null, new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public Map<String, String> getKeyValuePairs(List<String> keysFilter) {
        return getKeyValuePairs(null, keysFilter, new ArrayList<>());
    }

    @Override
    public Map<String, String> getKeyValuePairs(List<String> keysFilter, List<String> valuesFilter) {
        return getKeyValuePairs(null, keysFilter, valuesFilter);
    }

    @Override
    public Map<String, String> getKeyValuePairs(SlingHttpServletRequest httpRequest) {
        return getKeyValuePairs(httpRequest, new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public Map<String, String> getKeyValuePairs(SlingHttpServletRequest httpRequest, List<String> keysFilter) {
        return getKeyValuePairs(httpRequest, keysFilter, new ArrayList<>());
    }

    @Override
    public Map<String, String> getKeyValuePairs(SlingHttpServletRequest httpRequest, List<String> keysFilter, List<String> valuesFilter) {
        return WyndhamUtils.GetFromJCR(
                resolverFactory,
                new HashMap<>(),
                resourceResolver -> {
                    ArrayList<Resource> resources;

                    if (keysFilter.isEmpty()) {
                        resources = getAllKeyValuePairs(resourceResolver);
                    } else {
                        resources = getFilteredKeyValuePairs(keysFilter, resourceResolver);
                    }

                    Map<String, String> keyValueMap = new HashMap<>();
                    resources.forEach(resource -> {
                        ContentFragmentPair pair = getKeyValuePair(httpRequest, resource, valuesFilter);
                        if (pair != null) {
                            keyValueMap.put(pair.getKey(), pair.getValue());
                        }
                    });
                    return keyValueMap;
                });
    }

    @Override
    public String getKeyValuePairsJson() {
        return getKeyValuePairsJson(null, new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public String getKeyValuePairsJson(List<String> keysFilter) {
        return getKeyValuePairsJson(null, keysFilter, new ArrayList<>());
    }

    @Override
    public String getKeyValuePairsJson(List<String> keysFilter, List<String> valuesFilter) {
        return getKeyValuePairsJson(null, keysFilter, valuesFilter);
    }

    @Override
    public String getKeyValuePairsJson(SlingHttpServletRequest httpRequest) {
        return getKeyValuePairsJson(httpRequest, new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public String getKeyValuePairsJson(SlingHttpServletRequest httpRequest, List<String> keysFilter) {
        return getKeyValuePairsJson(httpRequest, keysFilter, new ArrayList<>());
    }

    @Override
    public String getKeyValuePairsJson(SlingHttpServletRequest httpRequest, List<String> keysFilter, List<String> valuesFilter) {
        TreeMap<String, String> sortedMap = new TreeMap<>(getKeyValuePairs(httpRequest, keysFilter, valuesFilter));
        JsonObject json = new JsonObject();

        for (Entry<String, String> entry : sortedMap.entrySet()) {
            json.addProperty(entry.getKey(), entry.getValue());
        }

        return new GsonBuilder().disableHtmlEscaping().create().toJson(json);
    }

    private ArrayList<Resource> getAllKeyValuePairs(ResourceResolver resourceResolver) {
        return executeQueryMap(
                queryBuilder,
                resourceResolver,
                "/content/dam/wyndham/key-value-pairs",
                "dam:Asset"
        );
    }

    private ArrayList<Resource> getFilteredKeyValuePairs(List<String> keysFilter, ResourceResolver resourceResolver) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("path", "/content/dam/wyndham/key-value-pairs");
        queryMap.put("1_property", "jcr:primaryType");
        queryMap.put("1_property.value", "dam:Asset");
        queryMap.put("p.guessTotal", "true");
        queryMap.put("p.limit", "-1");

        queryMap.put("group.p.or", "true");
        for (int i = 0; i < keysFilter.size(); i++) {
            int currentIndex = i + 1;
            queryMap.put(String.format("group.%s_property", currentIndex), "jcr:content/data/master/key");
            queryMap.put(String.format("group.%s_property.value", currentIndex), keysFilter.get(i));
        }

        return executeQueryMap(resourceResolver, queryBuilder, queryMap);
    }

    private ContentFragmentPair getKeyValuePair(SlingHttpServletRequest httpRequest, Resource resource, List<String> valuesFilter) {
        ContentFragmentPair pair = resource.adaptTo(ContentFragmentPair.class);

        // Short circuit if it's not a KVP or if it should be filtered out
        if (pair == null || (!valuesFilter.isEmpty() && !valuesFilter.contains(pair.getValue()))) {
            return null;
        }

        if (pair.getKey() == null) {
            logger.warn("key is null for path: ".concat(resource.getPath()));
            return null;
        }

        if (pair.getValue() == null) {
            logger.warn("value is null for path: ".concat(resource.getPath()));
            return null;
        }

        if (httpRequest != null) {
        	Resource variationResource = contentVariationService.getVariation(httpRequest, resource);
        	if (variationResource != null) {
	            ContentFragmentPair variation = variationResource.adaptTo(ContentFragmentPair.class);
	
	            if (variation != null && variation.getValue() != null) {
	                pair.setValue(variation.getValue());
	            }
        	}
        }

        return pair;
    }
}
