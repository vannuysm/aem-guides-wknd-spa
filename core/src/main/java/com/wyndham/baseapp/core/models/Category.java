package com.wyndham.baseapp.core.models;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Category {
	@ValueMapValue
	private String title;
	@ValueMapValue
	private String uniqueId;

	public String getTitle() {
		return title;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	@PostConstruct
	public void init() {
		if (!title.isEmpty()) {
			uniqueId = StringUtils.join(title.split(" "), "_");
		}
	}
}
