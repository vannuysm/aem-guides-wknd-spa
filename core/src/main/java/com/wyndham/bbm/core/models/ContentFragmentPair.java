package com.wyndham.bbm.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
public class ContentFragmentPair {
    public static final String PROPERTY_NAME_KEY = "key";
    public static final String PROPERTY_NAME_VALUE = "value";
    private String key;
    private String value;

    public ContentFragmentPair(Resource resource) {
        adaptFromResource(resource);
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private void adaptFromResource(Resource resource) {
        ValueMap valueMap = resource.getValueMap();

        if (valueMap.containsKey(PROPERTY_NAME_KEY) && valueMap.containsKey(PROPERTY_NAME_VALUE)) {
            this.key = valueMap.get(PROPERTY_NAME_KEY, String.class);
            this.value = valueMap.get(PROPERTY_NAME_VALUE, String.class);

            return;
        }

        Resource masterResource = resource.getChild("jcr:content/data/master");

        if (masterResource == null) {
            throw new IllegalArgumentException("Resource is " + resource.getPath() + " is not a valid key value pair");
        }

        valueMap = masterResource.getValueMap();

        if (!valueMap.containsKey(PROPERTY_NAME_KEY)) {
            throw new IllegalArgumentException("No property 'key' found for key value pair " + resource.getPath());
        }

        if (!valueMap.containsKey(PROPERTY_NAME_VALUE)) {
            throw new IllegalArgumentException("No property 'value' found for key value pair " + resource.getPath());
        }

        this.key = valueMap.get(PROPERTY_NAME_KEY, String.class);
        this.value = valueMap.get(PROPERTY_NAME_VALUE, String.class);
    }
}
