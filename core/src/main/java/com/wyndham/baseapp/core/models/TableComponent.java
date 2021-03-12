package com.wyndham.baseapp.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * This class contains data for table component
 *
 */
@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TableComponent {
	@ValueMapValue
	private String title;
	@ValueMapValue
	@Default(values = "0")
	private String numRows;
	@ValueMapValue
	private String hoverLogic;
	@ChildResource
	private List<Resource> tableConfig;
	@ValueMapValue
	private String keyTitle;
	@ValueMapValue
	private String keyBackgroundColor;
	@ValueMapValue
	private String keyTextColor;
	@ChildResource
	private List<Resource> tableKey;
	private List<List<Resource>> tableList;

	public String getTitle() {
		return title;
	}

	public String getNumRows() {
		return numRows;
	}

	public String getHoverLogic() {
		return hoverLogic;
	}

	public List<Resource> getTableConfig() {
		return tableConfig;
	}

	public String getKeyTitle() {
		return keyTitle;
	}

	public String getKeyBackgroundColor() {
		return keyBackgroundColor;
	}

	public String getKeyTextColor() {
		return keyTextColor;
	}

	public List<Resource> getTableKey() {
		return tableKey;
	}

	public List<List<Resource>> getTableList() {
		return tableList;
	}

	@PostConstruct
	public void init() {
		tableList = new ArrayList<>();
		if(tableConfig != null && tableConfig.size() > 0) {
			for (int i = 0; i < Integer.parseInt(numRows); i++) {
				tableList.add(tableConfig);
			}
		}
	}
}