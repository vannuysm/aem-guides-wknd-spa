package com.wyndham.baseapp.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BaseModel {

	@SlingObject
	private ResourceResolver resourceResolver;

	@SlingObject
	private Resource resource;

	@ValueMapValue
	private String contentSource;
	

	@ValueMapValue
	private String typeCarousel;
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	private List<List<ContentFragmentSourceModel>> choppedList;

	public List<List<ContentFragmentSourceModel>> getChoppedList() {
		return new ArrayList<List<ContentFragmentSourceModel>>(choppedList);
	}

	@PostConstruct
	private void initModel() {
		
		
		if (contentSource == null)
			return;

		if (contentSource.equalsIgnoreCase("inline")) {
			setInlineModels();
		} else if (contentSource.equalsIgnoreCase("contentFragment")) {
			setContentFragmentModels();
		}
		
		if(typeCarousel != null && typeCarousel.equalsIgnoreCase("card-slice"))
		   setChoppedListOfCFModels();
	}

	private void setChoppedListOfCFModels() {
		choppedList = new ArrayList<List<ContentFragmentSourceModel>>();
		int inputListSize = contentFragmentSourceModels.size();
		if (inputListSize > 0) {
			ContentFragmentSourceModel lastElement = contentFragmentSourceModels.get(inputListSize - 1);
			boolean isLastSet = false;
			for (int i = 0; i < inputListSize; i += 4) {
				int endIndex = i + 4 >= inputListSize ? (contentFragmentSourceModels.size() - 1) : (i + 4);
				if (i + 4 >= inputListSize) {
					isLastSet = true;
				}
				List<ContentFragmentSourceModel> listToAdd = null;
				if (isLastSet) {
					listToAdd = contentFragmentSourceModels.subList(i, endIndex + 1);
				} else {
					listToAdd = contentFragmentSourceModels.subList(i, endIndex);
				}

				choppedList.add(listToAdd);
			}
		}
	}

	private void setInlineModels() {
		
		contentFragmentSourceModels = new ArrayList<>();
		
		
		if(resource.getResourceType().equals("base-app/components/content/dynamic-ctaSlice")) {
			ContentFragmentSourceModel model = new ContentFragmentSourceModel(resource);
			contentFragmentSourceModels.add(model);
		} else {
			Resource resourceItems = resource.getChild("inlineItems");
			
			if (resourceItems != null) {
				for (Resource r : resourceItems.getChildren()) {
					
					ContentFragmentSourceModel model = new ContentFragmentSourceModel(r);
					
	 				contentFragmentSourceModels.add(model);
				}
			}
		}
		
	}

	private void setContentFragmentModels() {
		
		contentFragmentSourceModels = new ArrayList<>();
		Resource resourceItems = resource.getChild("cfmItems");
	
		if (resourceItems != null) {
			for (Resource r : resourceItems.getChildren()) {

				ContentFragmentSourceModel contentFragmentSourceModel = r.adaptTo(ContentFragmentSourceModel.class);
				contentFragmentSourceModels.add(contentFragmentSourceModel);
			}			
		}
	}

	private List<ContentFragmentSourceModel> contentFragmentSourceModels;

	public List<ContentFragmentSourceModel> getContentFragmentSourceModels() {
		return new ArrayList<ContentFragmentSourceModel>(contentFragmentSourceModels);
	}
	private int cfsSize;
    public int getCfsSize() {
        return contentFragmentSourceModels.size();
    }
	public String getContentSource() {
		return contentSource;
	}
}
