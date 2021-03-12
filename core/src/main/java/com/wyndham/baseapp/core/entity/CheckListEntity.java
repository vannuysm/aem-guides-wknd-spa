package com.wyndham.baseapp.core.entity;

public class CheckListEntity {
	
	private final String title;

	private final String blueIconSource;
	private final String grayIconSource;
	
	private final String experienceFragmentSource;

    public CheckListEntity(String title, String blueIconSource, String grayIconSource, String experienceFragmentSource) {
        this.title = title;
        this.blueIconSource = blueIconSource;
        this.grayIconSource = grayIconSource;
        this.experienceFragmentSource = experienceFragmentSource;
    }

    public String getTitle() {
        return title;
    }

    public String getBlueIconSource() {
        return blueIconSource;
    }
    
    public String getGrayIconSource() {
        return grayIconSource;
    }

    public String getExperienceFragmentSource() {
        return experienceFragmentSource;
    }
}
