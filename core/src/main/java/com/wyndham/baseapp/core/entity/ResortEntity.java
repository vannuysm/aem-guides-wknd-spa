package com.wyndham.baseapp.core.entity;

import java.util.List;

public class ResortEntity {

	public String checkinTime;
	
	public String checkoutTime;
	
	public String nearestAirport;
	
	public String prodNumber;
	
	public String prodName;
	
	public String prodType;
	
	public AddressEntity resortAddrCheckin;
	
	public AddressEntity resortAddrMain;
	
	public String resortLat;
	
	public String resortLong;
	
	public String resortNum;
	
	public String resortPhone;
	
	public List<String> amenities;
	
	public List<String> attractions;
	
	public String accessible;
	
	public List<String> activities;
	
	public List<String> facts;
	
	public List<String> features;
	
	public List<String> vacationExperience;
	
	public List<RoomType> roomTypes;

	public List<String> accessibilityFeatures;
	
	public static class RoomType {
		
		public String roomType;
		
		public boolean hideThisRoomType;

		public boolean hideTowerNames;
		
		public List<RoomCategory> roomCategories;
	}
	
	public static class RoomCategory implements Comparable<RoomCategory> {
		
		public String towerName;
		
		public String roomCategory;
		
		public boolean hideThisRoomCategory;
		
		public List<String> roomFeatures;
		
		public String minimumSqFt;
		
		public String maximumOccupancy;
		
		public String bathCount;

		public String kitchenType;
		
		public List<String> beds;
		
		public List<MediaGallery> galleries;
		
		public List<String> roomAmenities;
		
		public List<String> accessibilityFeatures;
		
		public List<String> notGuaranteedAccessibleFeatures;

		public String floorPlanImgSrc;

        @Override
        public int compareTo(RoomCategory o) {
            // Comparable doesn't protect for null; roomCategory is required (provided by ImportResortsDataService)
            int towerVal = 0; // equal
            if (this.towerName != null) {
                if (o.towerName != null) {
                    towerVal = this.towerName.compareTo(o.towerName);
                } else {
                    // I'm more important
                    towerVal = 1;
                }
            } else {
                if (o.towerName != null) {
                    // Other is more sorted higher
                    towerVal = -1;
                }
            }

            // Weight roomCategory more (i.e. group by roomCategory THEN tower
            return (this.roomCategory.compareTo(o.roomCategory) * 10) + towerVal;
        }
    }
	
	public static class MediaGallery {
		
		public String imageSrc;
		
		public String imageAlt;
		
		public String imageTitle;
		
		public long imageWidth;
		
		public long imageHeight;
		
		public String mediaType;
		
		public String embedVideoCode;
	}
}


