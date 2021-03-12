package com.wyndham.nextgen.core.entity;

import java.util.ArrayList;
import java.util.List;

public class ResortEntity {

	private String checkinTime;
	
	private String checkoutTime;
	
	private String nearestAirport;
	
	private String prodNumber;
	
	private String prodName;
	
	private String prodType;
	
	private AddressEntity resortAddrCheckin;
	
	private AddressEntity resortAddrMain;
	
	private String resortLat;
	
	private String resortLong;
	
	private String resortNum;
	
	private String resortPhone;
	
	private List<String> amenities;
	
	private List<String> attractions;
	
	private String accessible;
	
	private List<String> activities;
	
	private List<String> facts;
	
	private List<String> features;
	
	private List<String> vacationExperience;
	
	private List<RoomType> roomTypes;
	
	public static class RoomType {
		
		private String roomType;
		
		private boolean hideThisRoomType;
		
		private List<RoomCategory> roomCategories;

		public String getRoomType() {
			return roomType;
		}

		public void setRoomType(String roomType) {
			this.roomType = roomType;
		}

		public boolean isHideThisRoomType() {
			return hideThisRoomType;
		}

		public void setHideThisRoomType(boolean hideThisRoomType) {
			this.hideThisRoomType = hideThisRoomType;
		}

		public List<RoomCategory> getRoomCategories() {
			return new ArrayList<RoomCategory>(roomCategories);
		}

		public void setRoomCategories(List<RoomCategory> roomCategories) {
			this.roomCategories = new ArrayList<RoomCategory>(roomCategories);
		}
	}
	
	public static class RoomCategory {
		
		private String towerName;
		
		private String roomCategory;
		
		private boolean hideThisRoomCategory;
		
		private List<String> roomFeatures;
		
		private String minimumSqFt;
		
		private String maximumOccupancy;
		
		private String bathCount;
		
		private List<String> beds;
		
		private List<MediaGallery> galleries;

		public String getTowerName() {
			return towerName;
		}

		public void setTowerName(String towerName) {
			this.towerName = towerName;
		}

		public String getRoomCategory() {
			return roomCategory;
		}

		public void setRoomCategory(String roomCategory) {
			this.roomCategory = roomCategory;
		}

		public boolean isHideThisRoomCategory() {
			return hideThisRoomCategory;
		}

		public void setHideThisRoomCategory(boolean hideThisRoomCategory) {
			this.hideThisRoomCategory = hideThisRoomCategory;
		}

		public List<String> getRoomFeatures() {
			return new ArrayList<String>(roomFeatures);
		}

		public void setRoomFeatures(List<String> roomFeatures) {
			this.roomFeatures = new ArrayList<String>(roomFeatures);
		}

		public String getMinimumSqFt() {
			return minimumSqFt;
		}

		public void setMinimumSqFt(String minimumSqFt) {
			this.minimumSqFt = minimumSqFt;
		}

		public String getMaximumOccupancy() {
			return maximumOccupancy;
		}

		public void setMaximumOccupancy(String maximumOccupancy) {
			this.maximumOccupancy = maximumOccupancy;
		}

		public String getBathCount() {
			return bathCount;
		}

		public void setBathCount(String bathCount) {
			this.bathCount = bathCount;
		}

		public List<String> getBeds() {
			return new ArrayList<String>(beds);
		}

		public void setBeds(List<String> beds) {
			this.beds = new ArrayList<String>(beds);
		}

		public List<MediaGallery> getGalleries() {
			return new ArrayList<MediaGallery>(galleries);
		}

		public void setGalleries(List<MediaGallery> galleries) {
			this.galleries = new ArrayList<MediaGallery>(galleries);
		}
	}
	
	public static class MediaGallery {
		
		private String imageSrc;
		
		private String imageAlt;
		
		private String imageTitle;
		
		private long imageWidth;
		
		private long imageHeight;
		
		private String mediaType;
		
		private String embedVideoCode;

		public String getImageSrc() {
			return imageSrc;
		}

		public void setImageSrc(String imageSrc) {
			this.imageSrc = imageSrc;
		}

		public String getImageAlt() {
			return imageAlt;
		}

		public void setImageAlt(String imageAlt) {
			this.imageAlt = imageAlt;
		}

		public String getImageTitle() {
			return imageTitle;
		}

		public void setImageTitle(String imageTitle) {
			this.imageTitle = imageTitle;
		}

		public long getImageWidth() {
			return imageWidth;
		}

		public void setImageWidth(long imageWidth) {
			this.imageWidth = imageWidth;
		}

		public long getImageHeight() {
			return imageHeight;
		}

		public void setImageHeight(long imageHeight) {
			this.imageHeight = imageHeight;
		}

		public String getMediaType() {
			return mediaType;
		}

		public void setMediaType(String mediaType) {
			this.mediaType = mediaType;
		}

		public String getEmbedVideoCode() {
			return embedVideoCode;
		}

		public void setEmbedVideoCode(String embedVideoCode) {
			this.embedVideoCode = embedVideoCode;
		}
	}

	public String getCheckinTime() {
		return checkinTime;
	}

	public void setCheckinTime(String checkinTime) {
		this.checkinTime = checkinTime;
	}

	public String getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(String checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	public String getNearestAirport() {
		return nearestAirport;
	}

	public void setNearestAirport(String nearestAirport) {
		this.nearestAirport = nearestAirport;
	}

	public String getProdNumber() {
		return prodNumber;
	}

	public void setProdNumber(String prodNumber) {
		this.prodNumber = prodNumber;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	public AddressEntity getResortAddrCheckin() {
		return resortAddrCheckin;
	}

	public void setResortAddrCheckin(AddressEntity resortAddrCheckin) {
		this.resortAddrCheckin = resortAddrCheckin;
	}

	public AddressEntity getResortAddrMain() {
		return resortAddrMain;
	}

	public void setResortAddrMain(AddressEntity resortAddrMain) {
		this.resortAddrMain = resortAddrMain;
	}

	public String getResortLat() {
		return resortLat;
	}

	public void setResortLat(String resortLat) {
		this.resortLat = resortLat;
	}

	public String getResortLong() {
		return resortLong;
	}

	public void setResortLong(String resortLong) {
		this.resortLong = resortLong;
	}

	public String getResortNum() {
		return resortNum;
	}

	public void setResortNum(String resortNum) {
		this.resortNum = resortNum;
	}

	public String getResortPhone() {
		return resortPhone;
	}

	public void setResortPhone(String resortPhone) {
		this.resortPhone = resortPhone;
	}

	public List<String> getAmenities() {
		return new ArrayList<String>(amenities);
	}

	public void setAmenities(List<String> amenities) {
		this.amenities = new ArrayList<String>(amenities);
	}

	public List<String> getAttractions() {
		return new ArrayList<String>(attractions);
	}

	public void setAttractions(List<String> attractions) {
		this.attractions = new ArrayList<String>(attractions);
	}

	public String getAccessible() {
		return accessible;
	}

	public void setAccessible(String accessible) {
		this.accessible = accessible;
	}

	public List<String> getActivities() {
		return new ArrayList<String>(activities);
	}

	public void setActivities(List<String> activities) {
		this.activities = new ArrayList<String>(activities);
	}

	public List<String> getFacts() {
		return new ArrayList<String>(facts);
	}

	public void setFacts(List<String> facts) {
		this.facts = new ArrayList<String>(facts);
	}

	public List<String> getFeatures() {
		return new ArrayList<String>(features);
	}

	public void setFeatures(List<String> features) {
		this.features = new ArrayList<String>(features);
	}

	public List<String> getVacationExperience() {
		return new ArrayList<String>(vacationExperience);
	}

	public void setVacationExperience(List<String> vacationExperience) {
		this.vacationExperience = new ArrayList<String>(vacationExperience);
	}

	public List<RoomType> getRoomTypes() {
		return new ArrayList<RoomType>(roomTypes);
	}

	public void setRoomTypes(List<RoomType> roomTypes) {
		this.roomTypes = new ArrayList<RoomType>(roomTypes);
	}
}


