package com.wyndham.baseapp.core.models;

import javax.annotation.PostConstruct;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.Self;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.day.cq.wcm.api.PageManager;
import com.wyndham.baseapp.core.utils.WyndhamUtil;

 
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class IconListModel {
 
	//private static final Logger log = LoggerFactory.getLogger(IconListModel.class);
	
	@ScriptVariable
	private PageManager pageManager;
	
	@Self
	Resource resource;
	
	@SlingObject
    private ResourceResolver resourceResolver;
	
	public List<Map<String,String>> iconList;

	@PostConstruct
	protected void init() {
		try {
			if(pageManager == null) {
				pageManager = resourceResolver.adaptTo(PageManager.class);
			}
			//log.info("IconListModel: Invoked - " );
			Node iconListNode = resource.adaptTo(Node.class);
			iconList = new ArrayList<>();
			for(int i = 1 ; i < 7 ; i++) {
				Map<String,String> iconListProps = new HashMap<>();
				if(iconListNode != null) {
					if(iconListNode.hasProperty("icon"+i)){
						iconListProps.put("icon", iconListNode.getProperty("icon"+i).getString());
					}
					if(iconListNode.hasProperty("iconTitle"+i)){
						iconListProps.put("iconTitle", iconListNode.getProperty("iconTitle"+i).getString());
					}
					if(iconListNode.hasProperty("iconSubtitle"+i)){
						iconListProps.put("iconSubtitle", iconListNode.getProperty("iconSubtitle"+i).getString());
					}
					if(iconListNode.hasProperty("iconLinkText"+i)){
						iconListProps.put("iconLinkText", iconListNode.getProperty("iconLinkText"+i).getString());
					}
					if(iconListNode.hasProperty("iconLinkUrl"+i)){
						iconListProps.put("iconLinkUrl", WyndhamUtil.getInternalPageUrl(iconListNode.getProperty("iconLinkUrl"+i).getString(), pageManager) );
					}
				}
				iconList.add(iconListProps);
			}
        } catch(Exception ex) {
			//log.error("ERROR: LinkModel - " + ex.getMessage(), ex);
		}
	}

	public List<Map<String,String>> getIconList() {
		return iconList;
	}
	
 
}
