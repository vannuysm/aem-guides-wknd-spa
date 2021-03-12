package com.wyndham.baseapp.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.wcm.resource.details.AssetDetails;
import com.wyndham.baseapp.core.utils.MediaUtil;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ImageModel {

	@Self
	private Resource resource;

	@SlingObject
	private ResourceResolver resourceResolver;

	@Inject
	@Named("fileReference")
	@Default(values = "")
	private String imageSrc;

	@Inject
	@Default(values = "")
	private String imageAltText;

	@Inject
	@Default(values = "")
	private String imageLinkUrl;

	@PostConstruct
	protected void init() {


	}

	/**
	 * @return the imageSrc
	 */
	public String getImageSrc() {
		return imageSrc;
	}

	/**
	 * @return the imageAltText
	 */
	public String getImageAltText() {
		return MediaUtil.getImageAlt(imageSrc, resourceResolver);
	}

	/**
	 * @return the imageLinkUrl
	 */
	public String getImageLinkUrl() {
		return imageLinkUrl;
	}

	/**
	 * @return the contentBlockImageRendition
	 */
	public String getContentBlockImageRendition() {
		
		Resource imageRes = resourceResolver.getResource(imageSrc);
		if (imageRes != null) {
			Asset asset = imageRes.adaptTo(Asset.class);
			if (asset != null) {
				Rendition rendition = asset.getRendition("cq5dam.thumbnail.762.565.jpeg");
				if(rendition != null) {
				  return rendition.getPath();
				}
			}
		}
		
		return imageSrc;
	}

	/**
	 * @return the defaultCardImageRendition
	 */
	public String getDefaultCardImageRendition() {
		Resource imageRes = resourceResolver.getResource(imageSrc);
		if (imageRes != null) {
			Asset asset = imageRes.adaptTo(Asset.class);
			if (asset != null) {
				Rendition rendition = asset.getRendition("cq5dam.thumbnail.300.322.jpeg");
				if(rendition != null) {
				  return rendition.getPath();
				}
			}
		}
		
		return imageSrc;
	}

	/**
	 * @return the rectangleImageRendition
	 */
	public String getRectangleImageRendition() {
		Resource imageRes = resourceResolver.getResource(imageSrc);
		if (imageRes != null) {
			Asset asset = imageRes.adaptTo(Asset.class);
			if (asset != null) {
				Rendition rendition = asset.getRendition("cq5dam.thumbnail.300.200.jpeg");
				if(rendition != null) {
				  return rendition.getPath();
				}
			}
		}
		
		return imageSrc;
	}

	/**
	 * @return the imageHeight
	 * @throws RepositoryException 
	 */
	public long getImageHeight() throws RepositoryException {
		Resource imageRes = resourceResolver.getResource(imageSrc);
		if (imageRes != null) {
			AssetDetails assetDetails = new AssetDetails(imageRes);
			return assetDetails.getHeight();
		}
		
		return 0;
	}

	/**
	 * @return the imageWidth
	 * @throws RepositoryException 
	 */
	public long getImageWidth() throws RepositoryException {
		Resource imageRes = resourceResolver.getResource(imageSrc);
		if (imageRes != null) {
			AssetDetails assetDetails = new AssetDetails(imageRes);
			return assetDetails.getWidth();
		}
		
		return 0;
	}

	/**
	 * @param imageSrc the imageSrc to set
	 */
	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}
}
