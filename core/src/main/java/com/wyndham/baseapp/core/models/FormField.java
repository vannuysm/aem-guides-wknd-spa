package com.wyndham.baseapp.core.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.acs.commons.genericlists.GenericList;
import com.adobe.acs.commons.genericlists.GenericList.Item;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.wyndham.baseapp.core.constants.WyndhamConstants;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FormField {
	@SlingObject
	ResourceResolver resourceResolver;
	@ValueMapValue
	private String fieldName;
	@ValueMapValue
	private String required;
	@ValueMapValue
	private String disabled;
	@ValueMapValue
	private String smallLength;
	@ValueMapValue
	private String mediumLength;
	@ValueMapValue
	private String largeLength;
	@ValueMapValue
	private String fieldType;
	@ValueMapValue
	private String placeholderText;
	@ValueMapValue
	private String text;
	@ValueMapValue
	private String errorMessage;
	@ValueMapValue
	private String regX;
	@ValueMapValue
	private String genericList;
	@ValueMapValue
	private String sectionDivider;
	@ValueMapValue
	private String height;
	@ValueMapValue
	private String color;
	@ValueMapValue
	private Date datePickerMinDate;
	@ValueMapValue
	private Date datePickerMaxDate;
	private List<Item> dropdownList;

	@ChildResource
	private List<Resource> radioButtons;

	private String minDateVal;
	private String maxDateVal;

	public String getFieldName() {
		return fieldName;
	}

	public String getRequired() {
		return required;
	}

	public String getDisabled() {
		return disabled;
	}

	public String getSmallLength() {
		return smallLength;
	}

	public String getMediumLength() {
		return mediumLength;
	}

	public String getLargeLength() {
		return largeLength;
	}

	public String getFieldType() {
		return fieldType;
	}

	public String getPlaceholderText() {
		return placeholderText;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getRegX() {
		return regX;
	}

	public String getGenericList() {
		return genericList;
	}

	public String getSectionDivider() {
		return sectionDivider;
	}

	public String getHeight() {
		return height;
	}

	public String getColor() {
		return color;
	}

	public List<Resource> getRadioButtons() {
		return new ArrayList<Resource>(radioButtons);
	}

	public List<Item> getDropdownList() {
		return dropdownList;
	}

	public String getText() {
		return text;
	}

	public String getMinDateVal() {
		return minDateVal;
	}

	public String getMaxDateVal() {
		return maxDateVal;
	}

	public static String getDifferenceDays(Date d1, Date d2) {
		long diff = d2.getTime() - d1.getTime();
		long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		String dateDiffString = "";
		if(days > 0) {
			dateDiffString = "+" + (days + 1);
		} else if(days < 0) {
			dateDiffString = "" + days;
		} else {
			dateDiffString = "0";
		}
		return dateDiffString;
	}

	@PostConstruct
	public void init() {
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		if (pageManager != null && genericList != null) {
			Page listPage = pageManager.getPage(WyndhamConstants.GENERIC_LIST_PATH + genericList);
			if (listPage != null) {
				dropdownList = listPage.adaptTo(GenericList.class) != null ? listPage.adaptTo(GenericList.class).getItems():null;
			}
		}

		if (datePickerMinDate != null && datePickerMaxDate != null) {
			minDateVal = getDifferenceDays(new Date(), datePickerMinDate);
			maxDateVal = getDifferenceDays(new Date(), datePickerMaxDate);
		}
	}
}
