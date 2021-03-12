package com.wyndham.baseapp.core.models;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContentFragmentSourceModel {
	private final Logger log = LoggerFactory.getLogger(getClass());
	public ContentFragmentSourceModel() {
		
	}
	
	public ContentFragmentSourceModel(Resource r) {
		this.contentModelCF = r.adaptTo(ContentModelCF.class);
		this.linkContentFragmentModel = r.adaptTo(LinkContentFragmentModel.class);
		this.contentModelCFImage = r.adaptTo(ContentModelCFImage.class);
		this.contentResortModel = r.adaptTo(CFMResortModel.class);
	}

	@Self
	private Resource resource;
	@SlingObject
	private ResourceResolver resourceResolver;
	
	@ValueMapValue
	private String contentSource;
	
	@ValueMapValue
	private String linkSource;
	
	private ContentModelCF contentModelCF;
	private LinkContentFragmentModel linkContentFragmentModel;
	private ContentModelCFImage contentModelCFImage;
	private CFMResortModel contentResortModel;
	
	@PostConstruct
	private void initModel() {
		
		if (contentSource != null) {
			Resource cfResource = resourceResolver.getResource(contentSource + "/jcr:content/data/master");
			if (cfResource != null) {
				this.contentModelCF = cfResource.adaptTo(ContentModelCF.class);
				this.contentResortModel = cfResource.adaptTo(CFMResortModel.class);
			}
			contentModelCFImage = resource.adaptTo(ContentModelCFImage.class);
		}			
		
		if (linkSource != null) {
			
			Resource linkResource = resourceResolver.getResource(linkSource + "/jcr:content/data/master");
			if (linkResource != null) {
				this.linkContentFragmentModel = linkResource.adaptTo(LinkContentFragmentModel.class);	
			}	
			
		}
	}

	public ContentModelCF getContentModelCF() {
		return contentModelCF;
	}	
	
	public LinkContentFragmentModel getLinkContentFragmentModel() {
		return linkContentFragmentModel;
	}

	public ContentModelCFImage getContentModelCFImage() {
		return contentModelCFImage;
	}

    public CFMResortModel getContentResortModel() {
        return contentResortModel;
    }
}
