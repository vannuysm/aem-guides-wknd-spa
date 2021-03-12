package com.wyndham.baseapp.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FormBuilder {

	@ValueMapValue
	@Default(booleanValues = false)
	private boolean calculatorHide;
	
	@ValueMapValue
	@Default(booleanValues = false)
	private boolean hasDynamicContent;
	
	@ValueMapValue
	private String primarySource;
	
	@ChildResource
	private List<Resource> hideSectionID;

	private String hideSectionIDs;

	@ChildResource
	private List<FormField> formBuilderFields;

	public List<FormField> getFormBuilderFields() {
		return new ArrayList<FormField>(formBuilderFields);
	}

	public boolean isCalculatorHide() {
		return calculatorHide;
	}
	
	public boolean isHasDynamicContent() {
		return hasDynamicContent;
	}
	
	public String getPrimarySource() {
		return primarySource;
	}

	public String getHideSectionIDs() {
		return hideSectionIDs;
	}

	@PostConstruct
	public void init() {
		if (hideSectionID != null) {
			hideSectionIDs = hideSectionID.stream().map(r -> r.getValueMap().get("sectionId", String.class) != null ? r.getValueMap().get("sectionId", String.class):"")
					.collect(Collectors.joining(", "));
		}
	} 
}
