package com.wyndham.baseapp.core.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;


@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeaderNavigation {
	
	@ChildResource
	private List<PrimaryLinks> primaryLinks;

	public List<PrimaryLinks> getPrimaryLinks() {
		return new ArrayList<PrimaryLinks>(primaryLinks);
	}
}
