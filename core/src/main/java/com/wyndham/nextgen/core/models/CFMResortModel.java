package com.wyndham.nextgen.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CFMResortModel extends CFMContentModel {
	
	@SlingObject
	private Resource resource;
	
	@Inject
	private String yextId;
	
	@Inject
	private String productHubId;
	
	@Inject
	private String streetAddress;
	
	@Inject
	private String city;
	
	@Inject
	private String state;
	
	@Inject
	private String zipcode;
	
	@Inject
	private String phoneNumber;
	
	@Inject
	private String resortDescription;
	
	@PostConstruct
	protected void init() {
		
	}

	/**
	 * @return the yextId
	 */
	public String getYextId() {
		return yextId;
	}

	/**
	 * @return the productHubId
	 */
	public String getProductHubId() {
		return productHubId;
	}

	/**
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @return the resortDescription
	 */
	public String getResortDescription() {
		return resortDescription;
	}
	
}
