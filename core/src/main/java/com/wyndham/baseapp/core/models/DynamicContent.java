package com.wyndham.baseapp.core.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import com.google.gson.Gson;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DynamicContent {
	@ChildResource
	private List<Resource> dynamicContent;

	private String customData;

	public String getCustomData() {
		return customData;
	}

	@PostConstruct
	public void init() {
		if (dynamicContent != null && dynamicContent.size() > 0) {
			Map<String, String> map = new HashMap<>();
			for (Resource r : dynamicContent) {
				map.put(r.getValueMap().get("key", String.class), r.getValueMap().get("value", String.class));
			}
			customData = new Gson().toJson(map);
		}
	}
}
