package com.wyndham.baseapp.core.models;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PointCalculator {

	@ChildResource
	private List<Resource> priceRange;

	private String priceRangeArr;

	public List<Resource> getPriceRange() {
		return priceRange;
	}

	public String getPriceRangeArr() {
		return priceRangeArr;
	}

	@PostConstruct
	public void init() {
		if (priceRange != null && priceRange.size() > 0) {
			JsonArray rangeList = new JsonArray();
			priceRange.forEach(item -> {
				String minRange = item.getValueMap().get("minRange", String.class);
				String maxRange = item.getValueMap().get("maxRange", String.class);
				String savings = item.getValueMap().get("savings", String.class);
				JsonObject rangeObj = new JsonObject();
				rangeObj.addProperty("minRange", minRange != null ? minRange : "");
				rangeObj.addProperty("maxRange", maxRange != null ? maxRange : "");
				rangeObj.addProperty("savings", savings != null ? savings : "");
				rangeList.add(rangeObj);
			});
			priceRangeArr = new Gson().toJson(rangeList);
		}
	}
}
