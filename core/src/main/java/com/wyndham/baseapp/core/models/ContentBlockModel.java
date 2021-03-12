package com.wyndham.baseapp.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContentBlockModel {
 
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@SlingObject
	private Resource resource;
	
	@Inject
	private String title;
	
	@Inject
	private String subtitle;
	
	@Inject
	private String text;
 
	@Inject
	private String caption;
 
	private LinkModel link = new LinkModel();
	private ImageModel image = new ImageModel();
	
	@PostConstruct
	protected void init() {
		if(resource != null) {
			try {
				link = resource.adaptTo(LinkModel.class);
				image = resource.adaptTo(ImageModel.class);
			} catch(Exception ex) {
				log.error("ERROR: ContentBlockModel - " + ex.getMessage(), ex);
			}
		}
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the title
	 */
	public String getSubtitle() {
		return subtitle;
	}
	
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the subtitle
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @return the link
	 */
	public LinkModel getLink() {
		return link;
	}

	/**
	 * @return the image
	 */
	public ImageModel getImage() {
		return image;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
}


