package com.wyndham.nextgen.core.models;

import java.util.ArrayList;
import java.util.List;

public class FooterColumnModel {

	private String heading;
	private String headingLink;
	private List<NavigationItemModel> links;
	
	 public FooterColumnModel(String heading,String headingLink, List<NavigationItemModel> links) {
	        this.heading = heading;
	        this.headingLink = headingLink;
	        this.links = new ArrayList<NavigationItemModel>(links);
	 }
   
	public List<NavigationItemModel> getFooterLinks() {
		 return new ArrayList<NavigationItemModel>(links);
	}

	public String getHeading() {
		return heading;
	}

	public String getHeadingLink() {
		return headingLink;
	}
}
