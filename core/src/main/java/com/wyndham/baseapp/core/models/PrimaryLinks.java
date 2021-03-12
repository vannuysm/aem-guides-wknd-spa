package com.wyndham.baseapp.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PrimaryLinks {
	@ValueMapValue
	private String linkLabel;
	@ValueMapValue
	private String linkPath;
	@ValueMapValue
	private String linkTarget;
	@ChildResource
	private List<PrimaryLinks> primaryLinks;

	public String getLinkLabel() {
		return linkLabel;
	}
	public String getLinkPath() {
		return linkPath;
	}
	public String getLinkTarget() {
		return linkTarget;
	}
	public void setLinkPath(String linkPath) {
		this.linkPath = linkPath;
	}
	public List<PrimaryLinks> getPrimaryLinks() {
		return primaryLinks;
	}
}
