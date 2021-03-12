package com.wyndham.baseapp.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.day.cq.dam.api.DamConstants;
import com.day.cq.wcm.api.NameConstants;

/**
 * The Class ImageMetaData.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ImageMetaData {
	
	/** The title. */
	@Inject
	@Named(DamConstants.DC_TITLE)
	private String title;
	
	/** The description. */
	@Inject
	@Named(DamConstants.DC_DESCRIPTION)
	private String description;
	
	/** The type. */
	@Inject
	@Named(DamConstants.DC_FORMAT)
	private String type;
	
	/** The tags. */
	@Inject
	@Named(NameConstants.PN_TAGS)
	private String tags;
	
	/** The asset type. */
	private String assetType;
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Gets the tags.
	 *
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}
	
	
	/**
	 * Gets the asset type.
	 *
	 * @return the asset type
	 */
	public String getAssetType() {
		return assetType;
	}

	/**
	 * updating type value.
	 */
	@PostConstruct
	public void init() {
		assetType = type.substring(type.lastIndexOf("/") + 1);
	}
}