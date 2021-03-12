package com.wyndham.baseapp.core.models;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.google.common.base.Strings;
import com.wyndham.common.services.KeyValuePairService;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.wyndham.baseapp.core.entity.CheckListEntity;
import com.wyndham.baseapp.core.utils.WyndhamUtil;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CheckListModel {
	@OSGiService
	protected KeyValuePairService kvService;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@SlingObject
	private ResourceResolver resourceResolver;

	@SlingObject
	private Resource resource;

	@Self
	private SlingHttpServletRequest request;

	@ScriptVariable
	private Page currentPage;

	@ScriptVariable
	private PageManager pageManager;

	private List<CheckListEntity> checkListItems = new ArrayList<CheckListEntity>();

	private String newOwnerTipsText;
	private String previousLabelText;
	private String nextLabelText;
	private String incompleteMessageHtml;
	private String completeMessageHtml;

	@ValueMapValue
	private String finalTipSource;

	@PostConstruct
	protected void init() {
		if (resource != null && currentPage != null) {
			initCheckListItems();
		}
		getKVPs();
	}

	private void initCheckListItems() {
		Resource checkListResource = resource.getChild("checklistItems");
		if (checkListResource != null) {
			Iterator<Resource> checkListChildItem = checkListResource.listChildren();
			while (checkListChildItem.hasNext()) {
				Resource checkListTypeResource = checkListChildItem.next();
				if (checkListTypeResource != null) {
					ValueMap checkListItemsValueMap = checkListTypeResource.adaptTo(ValueMap.class);
					if (checkListItemsValueMap != null) {
						CheckListEntity checkListEntity = new CheckListEntity(
								checkListItemsValueMap.get("title", String.class),
								checkListItemsValueMap.get("blueIconSource", String.class),
								checkListItemsValueMap.get("grayIconSource", String.class),
								checkListItemsValueMap.get("experienceFragmentSource", String.class));
						checkListItems.add(checkListEntity);
					}
				}
			}
		}
	}

	private void getKVPs() {
		List<String> keys = Arrays.asList("global.dashboard.newOwnerTips", "global.search.previousLabel",
				"global.search.nextLabel", "global.dashboard.incompleteNewOwnerChecklist",
				"global.dashboard.completeNewOwnerChecklist");

		Map<String, String> mapLookup = kvService.getKeyValuePairs(keys);

		for (String key : mapLookup.keySet()) {
			String value = mapLookup.get(key);
			switch (key) {
			case "global.dashboard.newOwnerTips":
				newOwnerTipsText = value;
				break;
			case "global.search.previousLabel":
				previousLabelText = value;
				break;
			case "global.search.nextLabel":
				nextLabelText = value;
				break;
			case "global.dashboard.incompleteNewOwnerChecklist":
				incompleteMessageHtml = value;
				break;
			case "global.dashboard.completeNewOwnerChecklist":
				completeMessageHtml = value;
				break;
			}
		}
	}

	public List<CheckListEntity> getCheckListItems() {
		return new ArrayList<CheckListEntity>(checkListItems);
	}

	public String getExperiences() {
		String experiences = checkListItems.stream()
				.map(entity -> WyndhamUtil.getInternalPageUrl(entity.getExperienceFragmentSource(), pageManager))
				.collect(Collectors.joining(","));
		return experiences;
	}

	public String getNewOwnerTipsText() {
		return newOwnerTipsText;
	}

	public String getPreviousLabelText() {
		return previousLabelText;
	}

	public String getNextLabelText() {
		return nextLabelText;
	}

	public String getIncompleteMessageHtml() {
		return incompleteMessageHtml;
	}

	public String getCompleteMessageHtml() {
		return completeMessageHtml;
	}

	public String getFinalTipExperience() {
		if (finalTipSource == null || finalTipSource.isEmpty())
			return "";
		return WyndhamUtil.getInternalPageUrl(finalTipSource, pageManager);
	}

}
