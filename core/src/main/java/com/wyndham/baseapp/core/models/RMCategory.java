package com.wyndham.baseapp.core.models;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RMCategory {
	@ChildResource
	private List<RMContentFragmentData> rmContentSource;

	public List<RMContentFragmentData> getRmContentSource() {
		return rmContentSource;
	}
}