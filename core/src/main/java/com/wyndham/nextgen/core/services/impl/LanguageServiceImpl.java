package com.wyndham.nextgen.core.services.impl;

import com.day.cq.commons.Language;
import com.day.cq.wcm.api.LanguageManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.wyndham.nextgen.core.pojos.NameValuePairPojo;
import com.wyndham.nextgen.core.services.LanguageService;
import com.wyndham.nextgen.core.utils.WyndhamUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * The type Language service.
 */
@Component(service = LanguageService.class, immediate = true)
public class LanguageServiceImpl implements LanguageService {

    /**
     * The Log.
     */
    Logger log = LoggerFactory.getLogger(LanguageServiceImpl.class);

    @Reference
    LanguageManager languageManager;

    @Override
    public List<NameValuePairPojo> getLanguagesLocale(ResourceResolver resolver, String location, PageManager pageManager) {
        List<NameValuePairPojo> languageList = new ArrayList<NameValuePairPojo>();
        if (location != null) {
            String path = StringUtils.substring(location, 0, StringUtils.indexOf(location, "."));
            Resource resource = resolver.getResource(path);
            if (resource != null) {
                Page rootPage = languageManager.getLanguageRoot(resource);
                if (rootPage != null) {
                    String rootLanguage = rootPage.getName();
                    Locale currentLocale = new Locale(rootLanguage);
                    Collection<Page> languageRoots = languageManager.getLanguageRoots(resolver, path);
                    Iterator<Page> iterator = languageRoots.iterator();
                    while (iterator.hasNext()) {
                    	Page page = iterator.next();
                    	NameValuePairPojo pojo = new NameValuePairPojo();
                    	String title = StringUtils.EMPTY;
                    	String country = page.getLanguage().getDisplayCountry(currentLocale);
                    	title = page.getLanguage().getDisplayLanguage(currentLocale);
                    	if(country!=null&&!country.isEmpty()){
                    		title = title+"("+country+")";
                    	}
                    	pojo.setName(title);
                    	pojo.setValue(page.getName());
                    	if (page.getName().equals(rootLanguage)) {
                    		pojo.setDefault(true);
                    	} else {
                    		pojo.setDefault(false);
                    	}
                    	languageList.add(pojo);
                    }
                }
            }
        }
        return languageList;
    }

    /**
     * Method to get list of Languages available under country page.
     *
     * @param resolver
     * @param location
     * @return
     */
    @Override
    public List<NameValuePairPojo> getLanguagesPage(ResourceResolver resolver, SlingHttpServletRequest request, String location, PageManager pageManager) {
        List<NameValuePairPojo> pageList = new ArrayList<NameValuePairPojo>();
        if (location != null) {
            String path = StringUtils.substring(location, 0, StringUtils.indexOf(location, "."));
            Resource resource = resolver.getResource(path);
            if (resource != null) {
                Collection<Page> languageRoots = languageManager.getLanguageRoots(resolver, path);
                Iterator<Page> iterator = languageRoots.iterator();
                Map<String, String> map = new HashMap<String, String>();
                while (iterator.hasNext()) {
                    Page page = iterator.next();
                    map.put(page.getName(), page.getPath());
                }
                for (Map.Entry<Language, LanguageManager.Info> entry : languageManager.getAdjacentLanguageInfo(resolver, path).entrySet()) {
                    NameValuePairPojo page = new NameValuePairPojo();
                    LanguageManager.Info info = entry.getValue();
                    page.setName(entry.getKey().getLanguageCode());
                    if (info.exists()) {
                        page.setValue(WyndhamUtil.getInternalPageUrl(info.getPath(), pageManager));
                    } else {
                        page.setValue(WyndhamUtil.getInternalPageUrl(map.get(entry.getKey().getLanguageCode()), pageManager));
                    }
                    pageList.add(page);
                }
            }
        }
        return pageList;
    }
}
