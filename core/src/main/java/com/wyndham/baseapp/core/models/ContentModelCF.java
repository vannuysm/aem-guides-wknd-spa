package com.wyndham.baseapp.core.models;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("deprecation")
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContentModelCF {

//	@SlingObject
//	private ResourceResolver resourceResolver;
//	
//	@SlingObject
//	private Resource resource;

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
    private String videoLink;

    @Inject
    private String videoId;

    @Inject
    private String videoType;

    @Inject
    private String videoImage;

    @Inject
    private String fileReference;

    @Inject
    private String imageIcon;

    @Inject
    private String imageCaption;

    @Inject
    private String imageBackgroundColor;

    @Inject
    private String iconListTitle;

    @Inject
    private String iconListImageOne;

    @Inject
    private String iconListTitleOne;

    @Inject
    private String iconListDescriptionOne;

    @Inject
    private String iconListImageTwo;

    @Inject
    private String iconListTitleTwo;

    @Inject
    private String iconListDescriptionTwo;

    @Inject
    private String iconListImageThree;

    @Inject
    private String iconListTitleThree;

    @Inject
    private String iconListDescriptionThree;


//	@Inject
//	@Default(booleanValues = false)
//	private boolean isCFMFeature;
//	
//	@Inject
//	@Default(booleanValues = false)
//	private boolean isInlineFeature;

    @Inject
    @Default(values = "initial")
    private String fontColor;

    @Inject
    private String customFontColor;

    @Inject
    @Default(values = "initial")////adding for banner multi field
    private String fontColorTitle;

    @Inject////adding for banner multi field
    private String customTitleFontColor;

    @Inject
    @Default(values = "initial")////adding for banner multi field
    private String fontColorSubtitle;

    @Inject////adding for banner multi field
    private String customSubtitleFontColor;

    @Inject
    @Default(values = "initial")////adding for banner multi field
    private String fontColorText;

    @Inject////adding for banner multi field
    private String customTextFontColor;
//	@Inject
//	@Named("fileReference2")
//	@Default(values = "")
//	private String secondaryImageSrc;
//	
//	@Inject
//	@Default(values = "")
//	private String secondaryImageAltText;

    @Inject
    @Default(booleanValues = false)
    private boolean flipContent;

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
     * @return the videoId
     */
    public String getVideoId() {
        return StringEscapeUtils.unescapeHtml4(videoId);
    }

    /**
     * @param videoId the caption to set
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * @return the videoLink
     */
    public String getVideoLink() {
        return StringEscapeUtils.unescapeHtml4(videoLink);
    }

    /**
     * @param videoLink the caption to set
     */
    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    /**
     * @return the videoType
     */
    public String getVideoType() {
        return StringEscapeUtils.unescapeHtml4(videoType);
    }

    /**
     * @param videoType the caption to set
     */
    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    /**
     * @return the videoImage
     */
    public String getVideoImage() {
        return StringEscapeUtils.unescapeHtml4(videoImage);
    }

    /**
     * @param videoImage the caption to set
     */
    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
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

    public boolean getFlipContent() {
        return flipContent;
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
     * @return backgrond image
     */
    public String getFileReference() {
        return fileReference;
    }

//	/**
//	 * @return the isCFMFeature
//	 */
//	public boolean isFeature() {
//		return isCFMFeature || isInlineFeature;
//	}
//
//	/**
//	 * @param isCFMFeature the isCFMFeature to set
//	 */
//	public void setFeature(boolean isFeature) {
//		this.isCFMFeature = isFeature;
//	}

    /**
     * @return the fontColor
     */
    public String getFontColor() {
        return fontColor;
    }

    /**
     * @return the customFontColor
     */
    public String getCustomFontColor() {
        return customFontColor;
    }

    public String getFontColorTitle() {
        return fontColorTitle;
    }

    public String getCustomTitleFontColor() {
        return customTitleFontColor;
    }

    public String getFontColorSubtitle() {
        return fontColorSubtitle;
    }

    public String getCustomSubtitleFontColor() {
        return customSubtitleFontColor;
    }

    public String getFontColorText() {
        return fontColorText;
    }

    public String getCustomTextFontColor() {
        return customTextFontColor;
    }

//	/**
//	 * @return the fontColor
//	 */
//	public String getSecondaryImageSrc() {
//		return secondaryImageSrc;
//	}
//	
//	/**
//	 * @return the imageAltText
//	 */
//	public String getSecondaryImageAltText() {
//		return MediaUtil.getImageAlt(secondaryImageSrc, resourceResolver);
//	}
//	
//	/**
//	 * @return the contentBlockImageRendition
//	 */
//	public String getContentBlockSecondaryImageRendition() {
//		
//		Resource imageRes = resourceResolver.getResource(secondaryImageSrc);
//		if (imageRes != null) {
//			Asset asset = imageRes.adaptTo(Asset.class);
//			if (asset != null) {
//				Rendition rendition = asset.getRendition("cq5dam.thumbnail.762.565.jpeg");
//				if(rendition != null) {
//				  return rendition.getPath();
//				}
//			}
//		}
//		
//		return secondaryImageSrc;
//	}


    @Inject
    @Named("fileReference")
    @Default(values = "")
    private String imageSrc;

    public String getImageIcon() {
        return imageIcon;
    }

    public String getImageCaption() {
        return imageCaption;
    }

    public String getImageBackgroundColor() {
        return imageBackgroundColor;
    }


    public String getIconListTitle() {
        return iconListTitle;
    }

    public String getIconListImageOne() {
        return iconListImageOne;
    }

    public String getIconListTitleOne() {
        return iconListTitleOne;
    }

    public String getIconListDescriptionOne() {
        return iconListDescriptionOne;
    }

    public String getIconListImageTwo() {
        return iconListImageTwo;
    }

    public String getIconListTitleTwo() {
        return iconListTitleTwo;
    }

    public String getIconListDescriptionTwo() {
        return iconListDescriptionTwo;
    }

    public String getIconListImageThree() {
        return iconListImageThree;
    }

    public String getIconListTitleThree() {
        return iconListTitleThree;
    }

    public String getIconListDescriptionThree() {
        return iconListDescriptionThree;
    }

    @Inject
    @Default(values = "")
    private String imageAltText;

    public String getImageSrc() {
        return imageSrc;
    }

    public String getImageAltText() {
        return imageAltText;
    }
}



