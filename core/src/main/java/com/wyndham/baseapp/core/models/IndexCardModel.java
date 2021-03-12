package com.wyndham.baseapp.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import org.apache.sling.api.resource.ValueMap;

import com.adobe.acs.commons.genericlists.GenericList;
import com.adobe.acs.commons.genericlists.GenericList.Item;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class IndexCardModel {

	@Self
	private Resource resource;
	@SlingObject
	private ResourceResolver resourceResolver;
	@ChildResource
	private Resource pageList;
	@ValueMapValue
	private String displayRows;
	@ValueMapValue
	private String displayColumns;
	@ValueMapValue
	private String desktop;

	private List<Item> sortList;
	private boolean viewMoreCTA;

	public boolean isViewMoreCTA() {
		return viewMoreCTA;
	}

	public void setViewMoreCTA(boolean viewMoreCTA) {
		this.viewMoreCTA = viewMoreCTA;
	}

	private int columnCount = 0;

	private List<IndexCardPojo> cardList;

	public List<IndexCardPojo> getCardList() {
		return new ArrayList<IndexCardPojo>(cardList);
	}

	public List<Item> getSortList() {
		return sortList;
	}

	@PostConstruct
	public void init() {
		List<IndexCardPojo> unsortedCardList = new ArrayList<IndexCardPojo>();
		if (null != pageList) {
			setColumnCount();
			Iterator<Resource> itemListIterator = pageList.listChildren();
			while (itemListIterator.hasNext()) {
				String pagePath = itemListIterator.next().getValueMap().get("pagePath", String.class);
				Resource currentPageResource = resourceResolver.getResource(pagePath);
				if (null != currentPageResource) {
					Iterator<Resource> childPageIterator = currentPageResource.listChildren();
					while (childPageIterator.hasNext()) {
						Resource currentChildPageResource = childPageIterator.next();
						if (!JcrConstants.JCR_CONTENT.equalsIgnoreCase(currentChildPageResource.getName())) {
							ValueMap jcrContentDataMap = currentChildPageResource.getChild(JcrConstants.JCR_CONTENT)
									.getValueMap();
							if (jcrContentDataMap.containsKey("cardDate")) {
								IndexCardPojo pojoData = new IndexCardPojo(currentChildPageResource, desktop);
								unsortedCardList.add(pojoData);
							}
						}
					}
				}
			}
			Collections.sort(unsortedCardList);
			cardList = unsortedCardList;

			if (unsortedCardList.size() > getLimitOfList()) {
				setViewMoreCTA(true);
			} else {
				setViewMoreCTA(false);
			}
			updateHiddenCards(cardList);
			// Getting sort list from generic list
			PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
			if (pageManager != null) {
				Page listPage = pageManager.getPage("/etc/acs-commons/lists/article-sort-order");
				if (listPage != null) {
					sortList = listPage.adaptTo(GenericList.class) != null
							? listPage.adaptTo(GenericList.class).getItems()
							: null;
				}
			}

		}
	}

	private void updateHiddenCards(List<IndexCardPojo> cardList2) {
		int count = 1;
		for (IndexCardPojo currentCardPojo : cardList2) {
			if (count <= getLimitOfList()) {
				currentCardPojo.setHidden(false);
			} else {
				currentCardPojo.setHidden(true);
			}
			count = count + 1;
		}
	}

	private int getLimitOfList() {
		int rowCount = 1;
		if (displayRows.equalsIgnoreCase("1")) {
			rowCount = 1;
		} else if (displayRows.equalsIgnoreCase("2")) {
			rowCount = 2;
		} else if (displayRows.equalsIgnoreCase("3")) {
			rowCount = 3;
		}
		return getColumnCount() * rowCount;
	}

	private int getColumnCount() {
		return this.columnCount;
	}

	private void setColumnCount() {
		columnCount = 1;
		if (displayColumns.equalsIgnoreCase("large-up-1")) {
			columnCount = 1;
		} else if (displayColumns.equalsIgnoreCase("large-up-2")) {
			columnCount = 2;
		} else if (displayColumns.equalsIgnoreCase("large-up-3")) {
			columnCount = 3;
		} else if (displayColumns.equalsIgnoreCase("large-up-4")) {
			columnCount = 4;
		}
	}

}
