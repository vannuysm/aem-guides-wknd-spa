package com.wyndham.baseapp.core.models;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.LoggerFactory;

/**
 * The Class ImageDataModel.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContentModelCFImage {
	// private final Logger //logger = LoggerFactory.getLogger(getClass());
	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/** The file reference. */
	@ValueMapValue
	private String contentSource;

	/** The file reference. */
	@ValueMapValue
	private String fileReference;

	/** The image meta data. */
	private ImageMetaData imageMetaData;

	/**
	 * Gets the image meta data.
	 *
	 * @return the image meta data
	 */
	public ImageMetaData getImageMetaData() {
		return imageMetaData;
	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	private void initModel() {
		if (contentSource != null) {
			Resource cfResource = resourceResolver.getResource(contentSource + "/jcr:content/data/master");
			//logger.info("Inside Content fragment");
			if (cfResource != null) {
				ContentModelCF contentModelCF = cfResource.adaptTo(ContentModelCF.class);
				if (contentModelCF != null) {
                    // Getting image meta data from file reference
                    Resource imageMetaDataResource = resourceResolver.getResource(contentModelCF.getImageSrc() + "/jcr:content/metadata");
                    if (imageMetaDataResource != null) {
                        //logger.info("(contentModelCF.getImageSrc()" + contentModelCF.getImageSrc());
                        imageMetaData = imageMetaDataResource.adaptTo(ImageMetaData.class);
                        //logger.info("imageMetaData.getTitle()" + imageMetaData.getTitle());
                    }
                }
			}
		}
		if (fileReference != null) {
			//logger.info("Inside Inline content");
			Resource imageMetaDataResource = resourceResolver.getResource(fileReference + "/jcr:content/metadata");
			if (imageMetaDataResource != null) {
				//logger.info("(imageMetaDataResource.getPath()" + imageMetaDataResource.getPath());
				imageMetaData = imageMetaDataResource.adaptTo(ImageMetaData.class);
				//logger.info("imageMetaData.getTitle()" + imageMetaData.getTitle());
			}
		}
	}
}
