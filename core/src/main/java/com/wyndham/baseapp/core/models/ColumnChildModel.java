package com.wyndham.baseapp.core.models;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ColumnChildModel 
{
	@Inject
	private String largeColumn;
	
	@Inject
	private String smallColumn;
	
	@Inject
	private String mediumColumn;	
	
	public String getLargeColumn() {
		return largeColumn;
	}	
	public String getMediumColumn() {
		return mediumColumn;
	}
	public String getSmallColumn() {
		return smallColumn;
	}
}