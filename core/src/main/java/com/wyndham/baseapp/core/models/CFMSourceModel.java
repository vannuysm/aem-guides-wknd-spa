package com.wyndham.baseapp.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CFMSourceModel {
 
	@SlingObject
    private ResourceResolver resourceResolver;
	
	@Inject
	private String contentSource;
	
	@Inject
	private String linkSource;
 
	@PostConstruct
	protected void init() {
		
	}

	/**
	 * @return the contentSource
	 */
	public String getContentSource() {
		return contentSource;
	}

	/**
	 * @return the linkSource
	 */
	public String getLinkSource() {
		return linkSource;
	}
}



