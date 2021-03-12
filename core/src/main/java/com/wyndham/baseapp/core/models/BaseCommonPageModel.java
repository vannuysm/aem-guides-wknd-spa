package com.wyndham.baseapp.core.models;

import com.day.cq.wcm.api.LanguageManager;
import com.day.cq.wcm.api.Page;
import com.google.gson.GsonBuilder;
import com.wyndham.baseapp.core.configuration.SiteConfiguration;
import com.wyndham.redesign.core.services.impl.WyndhamMappingURLServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;

/**
 * This is the common page model for all the pages
 *
 */
@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BaseCommonPageModel {

	@OSGiService
	private LanguageManager languageManager;
	
	@OSGiService
	private SiteConfiguration siteConfiguration;

	@SlingObject
	private ResourceResolver resolver;

	@OSGiService
    private transient WyndhamMappingURLServiceImpl mappingURLService;

	@Inject
	@Via("request")
	private SlingHttpServletRequest request;

	@ScriptVariable
	private Page currentPage;

	private PageMetaData pageMetaData;

	private String ldJson;
	
	private String launchScript;

	@PostConstruct
	public void init() {
		launchScript = siteConfiguration.getLaunchScript();
		Resource pageResource = request.getResourceResolver().getResource(currentPage.getContentResource().getPath());
		if (pageResource != null) {
			pageMetaData = pageResource.adaptTo(PageMetaData.class);
			if (pageMetaData != null) {
				String domain = getDomain();
				// Updating image path to full path
				pageMetaData.setLogo(domain + pageMetaData.getLogo());
				pageMetaData.setPrimaryImage(domain + pageMetaData.getPrimaryImage());
				pageMetaData.setTwitterImage(domain + pageMetaData.getTwitterImage());
				pageMetaData.setOgUrl(domain + mappingURLService.getShortenURL(currentPage.getPath())); 
				if (pageMetaData.getOgTitle().isEmpty()) {
					pageMetaData.setOgTitle(currentPage.getTitle());
				}
				if (pageMetaData.getOgDesc().isEmpty()) {
					pageMetaData.setOgDesc(currentPage.getDescription());
				}

				String location = request.getPathInfo();
				//if (location != null && (location.startsWith(WyndhamConstants.ROOT) || location.startsWith("/content/club") )) {
					String path = StringUtils.substring(location, 0, StringUtils.indexOf(location, "."));
					Resource resource = resolver.getResource(path);
					Page homePage = languageManager.getLanguageRoot(resource);
					if (homePage != null) {
						ldJson = generateLDJson(pageMetaData);
					}
				//}
			}
		}

	}

	public String getDomain() {
		String servername = request.getServerName();
		if (servername.equalsIgnoreCase("wyn-destinations-prod.adobecqms.net")) {
			servername = "www.clubwyndham.wyndhamdestinations.com";
		}
		if (servername.equalsIgnoreCase("wyn-destinations-stage.adobecqms.net")) {
			servername = "stage-clubwyndham.wyndhamdestinations.com/";
		}
		if (servername.equalsIgnoreCase("wyn-destinations-qa.adobecqms.net")) {
			servername = "qa-clubwyndham.wyndhamdestinations.com";
		}
		return request.getScheme() + "://" + servername;
	}

	private String generateLDJson(PageMetaData pageMetaData) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		// Main Map
		map.put("@context", pageMetaData.getContext());
		map.put("@type", pageMetaData.getLdJsonType());
		map.put("name", pageMetaData.getName());
		map.put("telephone", pageMetaData.getTelephone());
		map.put("description", pageMetaData.getDescription());
		map.put("url", getDomain() + mappingURLService.getShortenURL(currentPage.getPath()));
		map.put("logo", pageMetaData.getLogo());
		map.put("foundingDate", pageMetaData.getFoundingDate());

		// address map
		HashMap<String, Object> addressMap = new HashMap<String, Object>();
		addressMap.put("@type", pageMetaData.getAddressType());
		addressMap.put("streetAddress", pageMetaData.getStreetAddress());
		addressMap.put("addressLocality", pageMetaData.getAddressLocality());
		addressMap.put("addressRegion", pageMetaData.getAddressRegion());
		addressMap.put("postalCode", pageMetaData.getPostalCode());
		addressMap.put("addressCountry", pageMetaData.getAddressCountry());
		map.put("address", addressMap);

		// contactPoint map
		HashMap<String, Object> contactPointMap = new HashMap<String, Object>();
		contactPointMap.put("@type", pageMetaData.getContactPointType());
		contactPointMap.put("contactPointTel", pageMetaData.getContactPointTel());
		contactPointMap.put("contactType", pageMetaData.getContactType());
		map.put("contactPoint", contactPointMap);

		return new GsonBuilder().create().toJson(map);
	}

	public PageMetaData getPageMetaData() {
		return pageMetaData;
	}

	public String getLdJson() {
		return ldJson;
	}

	public String getLaunchScript() {
		return launchScript;
	}
	public WyndhamMappingURLServiceImpl getMappingURLService() {
		return mappingURLService;
	}

	
}