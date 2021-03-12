package com.wyndham.nextgen.core.models;

import java.util.List;

import com.day.cq.wcm.api.Page;

public class NavigationItemModel {
	
	LinkModel link;
	Page currentPage;
	Page page;
    List<NavigationItemModel> childNavigationItems;
    
    public NavigationItemModel(Page page, LinkModel link) {
        this.link = link;
        this.page = page;
    }

    public NavigationItemModel(Page currentPage, Page page, List<NavigationItemModel> childNavigationItems) {
    	this.currentPage = currentPage;
        this.page = page;
        this.childNavigationItems = childNavigationItems;
    }
    
    public LinkModel getLink() {
    	return link;
    }
    
	public boolean isActive() {
		if(link != null && link.getLinkUrl() != null) {
			if(link.getLinkUrl().startsWith("/content")) {
				return page.getPath().equals(link.getLinkUrl().replace(".html", "")) ? true: false;
			}
		} else if(page != null && page.getPath() != null) {
			if(page.getPath().startsWith("/content")) {
				return currentPage.getPath().equals(page.getPath()) ? true: false;
			}
		}
		return false;
	}

	public boolean isHierarchyActive() {
		if(link != null && link.getLinkUrl() != null) {
			if(link.getLinkUrl().startsWith("/content")) {
				return page.getPath().startsWith(link.getLinkUrl()) ? true : false;
			}
		}
		return false;
	}
	
	public List<NavigationItemModel> getChildNavigationItems() {
    	return childNavigationItems;
    }
	
	public Page getPage() {
		return page;
	}
}

