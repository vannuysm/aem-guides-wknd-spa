package com.wyndham.baseapp.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { Resource.class,
        SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RMContentFragmentData {
    @SlingObject
    private ResourceResolver resourceResolver;
    @ValueMapValue
    private String rmSectionTitle;
    @ValueMapValue
    private String cfSource;
    private List<Resource> cfResourceList;
    private List<Resource> leftCalendarList;
    private List<Resource> rightCalendarList;

    public List<Resource> getLeftCalendarList() {
        return leftCalendarList;
    }

    public List<Resource> getRightCalendarList() {
        return rightCalendarList;
    }

    public List<Resource> getCfResourceList() {
        return cfResourceList;
    }

    public String getRmSectionTitle() {
        return rmSectionTitle;
    }

    @PostConstruct
	public void init() {
		cfResourceList = new ArrayList<>();
		leftCalendarList = new ArrayList<>();
		rightCalendarList = new ArrayList<>();

		List<Resource> cfList = null;
		if (cfSource != null) {
			Resource cfData = resourceResolver.getResource(cfSource);
			if (cfData != null) {
				cfList = IteratorUtils.toList(cfData.listChildren());
			}
		}
		if (cfList != null && cfList.size() > 0) {
			cfList.forEach(item -> {
				Resource cfMasterData = item.getChild("jcr:content/data/master");
				if (cfMasterData != null) {
					cfResourceList.add(cfMasterData);
					String startTime = cfMasterData.getValueMap().get("startTime", String.class);
					String startTimeMeridiem = cfMasterData.getValueMap().get("startTimeMeridiem", String.class);
					if (startTime != null && startTimeMeridiem != null) {
						Integer startTimeVal = Integer.parseInt(startTime.split(":")[0]);
						if (startTimeMeridiem.equals("AM") && startTimeVal >= 1) {
							leftCalendarList.add(cfMasterData);
						} else if(startTimeMeridiem.equals("PM") && startTimeVal == 12){
							leftCalendarList.add(cfMasterData);
						} else {
							rightCalendarList.add(cfMasterData);
						}
					}
				}
			});
		}
		Collections.sort(leftCalendarList, (s1, s2) -> {
			return s2.getValueMap().get("startTime", String.class)
					.compareTo(s1.getValueMap().get("startTime", String.class));
		});
		Collections.sort(rightCalendarList, (s1, s2) -> {
			return s1.getValueMap().get("startTime", String.class)
					.compareTo(s2.getValueMap().get("startTime", String.class));
		});
	}
}
