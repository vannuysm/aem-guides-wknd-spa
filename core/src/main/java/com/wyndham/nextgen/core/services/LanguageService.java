package com.wyndham.nextgen.core.services;


import com.day.cq.wcm.api.PageManager;
import com.wyndham.nextgen.core.pojos.NameValuePairPojo;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.List;

public interface LanguageService {

    List<NameValuePairPojo> getLanguagesLocale(ResourceResolver resolver, String location, PageManager pageManager);
	List<NameValuePairPojo> getLanguagesPage(ResourceResolver resolver, SlingHttpServletRequest request, String location, PageManager pageManager);
}
