package com.wyndham.baseapp.core.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TableCell {
	@ValueMapValue
	private String contentReference;
	@SlingObject
	private ResourceResolver resourceResolver;
	private Resource cfData;

	private String linkType;

	@PostConstruct
	public void init() {
		if (contentReference != null) {
			cfData = resourceResolver.getResource(contentReference + "/jcr:content/data/master");
			if(contentReference.startsWith("/content/dam")) {
				linkType = "cf";
			} else if(contentReference.startsWith("http") || contentReference.startsWith("https")) {
				linkType = "externalLink";
			} else {
				linkType = "internalLink";
			}
		}
	}

	public String getContentReference() {
		return contentReference;
	}

	public String getLinkType() {
		return linkType;
	}

	public Resource getCfData() {
		return cfData;
	}
}
