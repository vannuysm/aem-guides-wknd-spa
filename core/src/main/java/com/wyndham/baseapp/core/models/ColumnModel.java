package com.wyndham.baseapp.core.models;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ColumnModel 
{
	@SlingObject
    private Resource resource;
	
	@PostConstruct
	private void initModel() {	
		
		if (resource == null) 
			return;
		
		columns = new ArrayList<>();		
		Resource columnsNode = resource.getChild("columns");
		
		if (columnsNode != null) {			
			for (Resource r : columnsNode.getChildren()) {
				ColumnChildModel col = r.adaptTo(ColumnChildModel.class);
				columns.add(col);
			}
		}
	}
	
	private List<ColumnChildModel> columns;
	
	public List<ColumnChildModel> getColumns() {
		return new ArrayList<ColumnChildModel>(columns);
	}
}


