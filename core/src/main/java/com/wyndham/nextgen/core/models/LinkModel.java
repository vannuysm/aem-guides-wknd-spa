package com.wyndham.nextgen.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.wyndham.nextgen.core.utils.*;
import com.wyndham.nextgen.core.constants.WyndhamConstants;
 
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LinkModel {
 
	private static final Logger log = LoggerFactory.getLogger(LinkModel.class);
	
	@ScriptVariable
    private PageManager pageManager;
	
	@SlingObject
    private ResourceResolver resourceResolver;
	
	@Inject
	@Default(values = "")
	private String linkText;
	
	@Inject
	@Default(values = "")
	private String linkUrl;
 
	@Inject
	@Default(booleanValues = false)
	private boolean linkOpenInNewWindow;
	
	@Inject
	@Named("icon")
	@Default(values = "")
	private String linkIcon;
	
	private boolean isLinkExternal = false;
	private boolean isLinkEmail = false;
	
	@PostConstruct
	protected void init() {		
		
		if(pageManager == null) {
			pageManager = resourceResolver.adaptTo(PageManager.class);
		}
		if(StringUtils.isBlank(linkText) && pageManager != null) {
			Page page = pageManager.getPage(linkUrl);
			if(page != null) {
				linkText = StringUtils.isNotBlank(page.getNavigationTitle()) ? page.getNavigationTitle() : page.getTitle();
			}
		}
		if(!StringUtils.isBlank(linkUrl)) {
			// Check for external Link
			if(linkUrl.startsWith(WyndhamConstants.DEFAULT_HTTP_STRING)) {
				isLinkExternal = true;
			}
			
			// Check for email link
			if(linkUrl.contains("@")) {
				isLinkEmail = true;
			}
			
			// Add .html extension for internal links
			linkUrl = WyndhamUtil.getInternalPageUrl(linkUrl, pageManager);
		}
			
	}
 
	public String getLinkText() {
		return linkText;
	}
	
	public String getLinkUrl() {
		return linkUrl;
	}
 
	public String getLinkIcon() {
		return linkIcon;
	}
	
	public boolean isLinkExternal() {
		return isLinkExternal;
	}

	public boolean isLinkEmail() {
		return isLinkEmail;
	}
	
	public boolean getLinkOpenInNewWindow() {
		return linkOpenInNewWindow;
	}

}
