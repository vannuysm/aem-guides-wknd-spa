package com.wyndham.baseapp.core.models;

import java.util.Date;
import java.util.Iterator;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;

import com.day.cq.commons.jcr.JcrConstants;

public class IndexCardPojo implements Comparable<IndexCardPojo>{

	private String cardTitle;

	private String cardTag;

	private String cardLinkText;

	private String cardImagePath;

	private Date cardDate;

	private String cardCTALink;

	private String cardDescription;
	
	private String dateAsString ;

	private boolean hidden;
	


	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getDateAsString() {
		return dateAsString;
	}

	public String getCardTitle() {
		return cardTitle;
	}

	public String getCardTag() {
		return cardTag;
	}

	public String getCardLinkText() {
		return cardLinkText;
	}

	public String getCardImagePath() {
		return cardImagePath;
	}

	public Date getCardDate() {
		return new Date(cardDate.getTime());
	}

	public String getCardCTALink() {
		return cardCTALink;
	}

	public String getCardDescription() {
		return cardDescription;
	}

	public IndexCardPojo(Resource pageResource,String rendition) {
		ValueMap jcrContentDataMap = pageResource.getChild(JcrConstants.JCR_CONTENT).getValueMap();
		cardCTALink = jcrContentDataMap.get("cardCTALink", pageResource.getPath());
		cardTitle = jcrContentDataMap.get("cardTitle", " ");
		cardTag = jcrContentDataMap.get("cardTag", " ");
		cardLinkText = jcrContentDataMap.get("cardLinkText", " ");
		String imagePath=  jcrContentDataMap.get("cardImagePath", "#");
		if(rendition!=null) {
			cardImagePath=getRenditionedPath(pageResource.getResourceResolver(),imagePath,rendition);
		}else {
			cardImagePath = imagePath;
		}
		
		cardDescription = jcrContentDataMap.get("cardDescription", "");
		cardDate = jcrContentDataMap.get("cardDate", Date.class);
	}

	private String getRenditionedPath(ResourceResolver resourceResolver, String imagePath, String rendition) {
		if("#" != imagePath) {				
			Resource imgResource = resourceResolver.getResource(imagePath);
			if(imgResource != null) {
				Asset imageAsset = imgResource.adaptTo(Asset.class);
				if(imageAsset != null) {
					Iterator<Rendition> renditionsIterator=imageAsset.listRenditions();
					while(renditionsIterator.hasNext()) {
						Rendition currentRendition = renditionsIterator.next();
						String renditionName=currentRendition.getName();
						if(renditionName.startsWith("cq5dam.thumbnail."+rendition)) {
							return currentRendition.getPath();
						}
					}
				}
			}
		}
		return imagePath;
	}

	@Override
	public int compareTo(IndexCardPojo o) {
		return o.getCardDate().compareTo(getCardDate());
	}

}
