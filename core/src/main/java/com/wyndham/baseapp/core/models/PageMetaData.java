package com.wyndham.baseapp.core.models;

import com.wyndham.baseapp.core.utils.WyndhamUtil;
import com.wyndham.common.WyndhamConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.text.DateFormat;
import java.util.Date;

/**
 * @Type Page MetaData Class This class will be used to inject all metadata from
 *       page properties
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PageMetaData {

	@Named("@context")
	@Default(values = "")
	@ValueMapValue
	private String context;

	@Named("@type")
	@Default(values = "")
	@ValueMapValue
	private String ldJsonType;

	private String type;

	@Default(values = "")
	@ValueMapValue
	private String name;

	@Default(values = "")
	@ValueMapValue
	private String telephone;

	@Default(values = "")
	@ValueMapValue
	private String description;

	@Default(values = "")
	@ValueMapValue
	private String logo;

	@ValueMapValue
	@Named("foundingDate")
	private Date foundDate;

	private String foundingDate;

	@Default(values = "")
	@ValueMapValue
	private String addressType;

	@Default(values = "")
	@ValueMapValue
	private String streetAddress;

	@Default(values = "")
	@ValueMapValue
	private String addressLocality;

	@Default(values = "")
	@ValueMapValue
	private String addressRegion;

	@Default(values = "")
	@ValueMapValue
	private String postalCode;

	@Default(values = "")
	@ValueMapValue
	private String addressCountry;

	@Default(values = "")
	@ValueMapValue
	private String contactPointType;

	@Default(values = "")
	@ValueMapValue
	private String contactPointTel;

	@Default(values = "")
	@ValueMapValue
	private String contactType;

	@Default(values = "")
	@ValueMapValue
	private String primaryImage;

	@Default(values = "")
	@ValueMapValue
	private String siteName;

	@Default(values = "")
	@ValueMapValue
	private String ogTitle;

	@Default(values = "")
	@ValueMapValue
	private String ogDesc;

	private String ogUrl;

	@Default(values = "")
	@ValueMapValue
	private String twitterCard;

	@Default(values = "")
	@ValueMapValue
	private String twitterSite;

	@Default(values = "")
	@ValueMapValue
	private String twitterImage;

	@Default(values = "")
	@ValueMapValue
	private String index;

	@Default(values = "")
	@ValueMapValue
	private String follow;

	private String robots;

	private String generatedLDJson;

	public String getContext() {
		return context;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getDescription() {
		return description;
	}

	public String getLogo() {
		return logo;
	}

	public String getFoundingDate() {
		return foundingDate;
	}

	public String getAddressType() {
		return addressType;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public String getAddressLocality() {
		return addressLocality;
	}

	public String getAddressRegion() {
		return addressRegion;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getAddressCountry() {
		return addressCountry;
	}

	public String getContactPointType() {
		return contactPointType;
	}

	public String getContactPointTel() {
		return contactPointTel;
	}

	public String getContactType() {
		return contactType;
	}

	public String getPrimaryImage() {
		return primaryImage;
	}

	public String getSiteName() {
		return siteName;
	}

	public String getOgTitle() {
		return ogTitle;
	}

	public String getOgDesc() {
		return ogDesc;
	}

	public String getTwitterCard() {
		return twitterCard;
	}

	public String getTwitterSite() {
		return twitterSite;
	}

	public String getTwitterImage() {
		return twitterImage;
	}

	public String getRobots() {
		return robots;
	}

	public String getLdJsonType() {
		return ldJsonType;
	}

	public String getOgUrl() {
		return ogUrl;
	}

	public String getGeneratedLDJson() {
		return generatedLDJson;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public void setPrimaryImage(String primaryImage) {
		this.primaryImage = primaryImage;
	}

	public void setOgUrl(String ogUrl) {
		this.ogUrl = ogUrl;
	}

	public void setTwitterImage(String twitterImage) {
		this.twitterImage = twitterImage;
	}

	public void setOgTitle(String ogTitle) {
		this.ogTitle = ogTitle;
	}

	public void setOgDesc(String ogDesc) {
		this.ogDesc = ogDesc;
	}

	@PostConstruct
	public void init() {
		// Hard Coded String
		type = "website";

		// formating the founding Date.
		DateFormat df = WyndhamUtil.getDateFormat(WyndhamConstants.DATE_FORMAT_8);
		if (foundDate != null) {
			foundingDate = df.format(foundDate);
		}

		// Generating index, noindex, follow, nofollow string conditionally.
		if (!index.isEmpty() && !follow.isEmpty()) {
			robots = index + "," + follow;
		} else if (!index.isEmpty()) {
			robots = index;
		} else if (!follow.isEmpty()) {
			robots = follow;
		} else {
			robots = StringUtils.EMPTY;
		}
	}
}