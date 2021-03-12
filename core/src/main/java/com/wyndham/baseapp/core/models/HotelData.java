package com.wyndham.baseapp.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.google.gson.annotations.SerializedName;
import com.wyndham.baseapp.core.pojos.RoomTypes;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HotelData {
	@ValueMapValue
	@Default(values = "")
	private String resortNumber;
	@ValueMapValue
	@Default(values = "")
	private String prodName;
	@ValueMapValue
	@Default(values = "")
	private String address;
	@ValueMapValue
	@Default(values = "")
	private String city;
	@ValueMapValue
	@Default(values = "")
	private String stateFull;
	@ValueMapValue
	@Default(values = "")
	private String zipcode;
	@ValueMapValue
	@Default(values = "")
	private String countryFull;
	@ValueMapValue
	@Default(values = "")
	private String lat;
	@SerializedName("long")
	@ValueMapValue(name = "long")
	@Default(values = "")
	private String longitude;
	@ValueMapValue
	@Default(values = "")
	private String phone;
	@ValueMapValue
	@Default(values = "")
	private String description;
	@ValueMapValue
	@Default(values = "")
	private String metaDescription;
	@ValueMapValue
	@Default(values = "")
	private String vacationExperiences;
	@ValueMapValue
	private List<String> amenities;
	private List<RoomTypes> roomTypes;
	@ValueMapValue
	@Default(values = "")
	private String primaryImage;
	@ValueMapValue
	@Default(values = "")
	private String canonicalUrl;

	public String getResortNumber() {
		return resortNumber;
	}

	public String getProdName() {
		return prodName;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getStateFull() {
		return stateFull;
	}

	public String getZipcode() {
		return zipcode;
	}

	public String getCountryFull() {
		return countryFull;
	}

	public String getLat() {
		return lat;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getPhone() {
		return phone;
	}

	public String getDescription() {
		return description;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public String getVacationExperiences() {
		return vacationExperiences;
	}

	public List<String> getAmenities() {
		return amenities;
	}

	public String getPrimaryImage() {
		return primaryImage;
	}

	public String getCanonicalUrl() {
		return canonicalUrl;
	}

	public List<RoomTypes> getRoomTypes() {
		return roomTypes;
	}

	public void setRoomTypes(List<RoomTypes> roomTypes) {
		this.roomTypes = roomTypes;
	}
}