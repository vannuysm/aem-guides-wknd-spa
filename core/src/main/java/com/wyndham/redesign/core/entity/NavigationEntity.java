package com.wyndham.redesign.core.entity;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * @Type Class
 * This is a class about global navigation data model
 */
public class NavigationEntity extends AbstractEntity{
	
	private String title;
	private String link;
	private List<NavigationEntity> navList;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public List<NavigationEntity> getNavList() {
		return new ArrayList<>(navList);
	}
	public void setNavList(List<NavigationEntity> navList) {
		this.navList = ImmutableList.copyOf(navList);
	}

}
