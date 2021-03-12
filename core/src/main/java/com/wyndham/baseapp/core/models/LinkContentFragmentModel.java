package com.wyndham.baseapp.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.day.cq.wcm.api.PageManager;
import com.wyndham.redesign.core.services.CommonService;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LinkContentFragmentModel {	
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@SlingObject
	private ResourceResolver resourceResolver;	
	
	@ScriptVariable
    private PageManager pageManager;

	@Inject
	//@Default(values = "")
	private String linkText;
	
	@Inject
	//@Default(values = "")
	private String linkUrl;
	  
	@Inject////adding for banner multi field
	private String fontColor;

	@Inject
	private String customFontColor;

	@Inject
	@Optional
	@Default(booleanValues = false)
	private boolean linkOpenInNewWindow;
	
	@OSGiService
    CommonService commonService;
	
	@Inject
    @Via("request")
    SlingHttpServletRequest request;


	@PostConstruct
	protected void init() {
		
		if(pageManager == null) {
			pageManager = resourceResolver.adaptTo(PageManager.class);
		}
		log.error("Something else");
		log.error(this.linkUrl);
		this.linkUrl = commonService.processLinkPath(resourceResolver, request, linkUrl); 
				
	}

	public String getLinkText() {
		return linkText;
	}

	public String getLinkUrl() {
		log.error(linkUrl);
		return linkUrl;
	}
	
	public String getFontColor() {
		return fontColor;
	}

	public String getCustomFontColor() {
		return customFontColor;
	}

	public boolean isLinkOpenInNewWindow() {
		return linkOpenInNewWindow;
	}

}