package com.wyndham.baseapp.core.models;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.wyndham.baseapp.core.utils.MediaUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("deprecation")
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CFMContentModel extends ImageModel {
 
	@SlingObject
	private ResourceResolver resourceResolver;
	
	@SlingObject
	private Resource resource;
	
	@Inject
	private String caption;
	
	@Inject
	private String title;
	
	@Inject
	private String subtitle;
	
	@Inject
	private String text;
	
	@Inject
	@Default(values = "Left")
	private String textAlignment;
	
	@Inject	
	private String videoTitle;
	
	@Inject	
	private String videoUrl;
	
	@Inject
	@Default(booleanValues = false)
	private boolean isCFMFeature;
	
	@Inject
	@Default(booleanValues = false)
	private boolean isInlineFeature;
	
	@Inject	
	private String fontColor;
	
	@Inject
	@Named("fileReference2")
	@Default(values = "")
	private String secondaryImageSrc;
	
	@Inject
	@Default(values = "")
	private String secondaryImageAltText;
	
	@PostConstruct
	protected void init() {

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
	public String getSubTitle() {
		return StringEscapeUtils.unescapeHtml4(subtitle);
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return StringEscapeUtils.unescapeHtml4(text);
	}

	/**
	 * @return the subtitle
	 */
	public String getCaption() {
		return caption;
	}
	
	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the textAlignment
	 */
	public String getTextAlignment() {
		return textAlignment;
	}

	/**
	 * @return the videoTitle
	 */
	public String getVideoTitle() {
		return videoTitle;
	}

	/**
	 * @return the videoUrl
	 */
	public String getVideoUrl() {
		return videoUrl;
	}

	/**
	 * @return the subtitle
	 */
	public String getSubtitle() {
		return subtitle;
	}
	
	/**
	 * @param title the subtitle to set
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	/**
	 * @return the isCFMFeature
	 */
	public boolean isFeature() {
		return isCFMFeature || isInlineFeature;
	}

	/**
	 * @param isCFMFeature the isCFMFeature to set
	 */
	public void setFeature(boolean isFeature) {
		this.isCFMFeature = isFeature;
	}
	
	/**
	 * @return the fontColor
	 */
	public String getFontColor() {
		return fontColor;
	}
	
	/**
	 * @return the fontColor
	 */
	public String getSecondaryImageSrc() {
		return secondaryImageSrc;
	}
	
	/**
	 * @return the imageAltText
	 */
	public String getSecondaryImageAltText() {
		return MediaUtil.getImageAlt(secondaryImageSrc, resourceResolver);
	}
	
	/**
	 * @return the contentBlockImageRendition
	 */
	public String getContentBlockSecondaryImageRendition() {
		
		Resource imageRes = resourceResolver.getResource(secondaryImageSrc);
		if (imageRes != null) {
			Asset asset = imageRes.adaptTo(Asset.class);
			if (asset != null) {
				Rendition rendition = asset.getRendition("cq5dam.thumbnail.762.565.jpeg");
				if(rendition != null) {
				  return rendition.getPath();
				}
			}
		}
		
		return secondaryImageSrc;
	}
}



