package com.wyndham.nextgen.core.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;


import com.day.cq.commons.LanguageUtil;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.designer.Style;

import com.wyndham.nextgen.core.constants.*;
import com.wyndham.nextgen.core.utils.UrlUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeaderModel implements WyndhamConstants {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@SlingObject
    private ResourceResolver resourceResolver;

	@SlingObject
    private Resource resource;

    @ScriptVariable
	private Style currentStyle;
    
    @ScriptVariable
    private PageManager pageManager;
    
    @ScriptVariable
    private Page currentPage;
	
	private String logoLinkUrl;
	
	private String desktopLogoSrc;
	
	private String mobileLogoSrc;
	
	private String logoAltText;
	
	private boolean logoOpenInNewWindow;
	
	private String searchUrl;
	
	private String searchPlaceholderText;
	
	private String searchButtonText;
	
	private List<NavigationItemModel> siteNavigationItems;
	
	private String loginButtonLabel;
	
	private String loginColumn1Title;
	
	private String loginColumn1Text;
	
	private String loginColumn1LinkUrl;
	
	private String loginColumn1LinkText;
	
	private boolean loginColumn1LinkOpenInNewTab;
	
	private String loginColumn2Title;
	
	private String loginColumn2Text;
	
	private String loginColumn2LinkUrl;
	
	private String loginColumn2LinkText;
	
	private boolean loginColumn2LinkOpenInNewTab;
	
	private boolean redirectInNewWindow;
	
	@PostConstruct
	private void initModel() {

		
		String languageRootPath = LanguageUtil.getLanguageRoot(currentPage.getPath()); 
		log.debug("currentPage.getPath()--"+currentPage.getPath());
		log.debug("languageRootPath--"+languageRootPath);

		if (languageRootPath == null) {
			log.debug("languageRootPath--null");
			languageRootPath = WyndhamConstants.DEFAULT_LANGUAGE_ROOT;
		}
		
		if(languageRootPath.equalsIgnoreCase("/content/club/clubwyndham/us/en")) {
			log.debug("languageRootPath--"+languageRootPath);
			languageRootPath = WyndhamConstants.DEFAULT_LANGUAGE_ROOT;
		}
		log.debug("languageRootPath--"+languageRootPath);
		
		if (languageRootPath != null) {
			Resource headerResource = resourceResolver.getResource(languageRootPath + "/jcr:content/root/header");

			if (headerResource != null) {
				log.debug("headerResource is not null");
				ValueMap headerValueMap = headerResource.adaptTo(ValueMap.class);
				logoLinkUrl = headerValueMap.get(PROP_LOGO_URL, String.class);
				desktopLogoSrc = headerValueMap.get(PROP_DESKTOP_LOGO_SRC, String.class);
				mobileLogoSrc = headerValueMap.get(PROP_MOBILE_LOGO_SRC, String.class);
				logoAltText = headerValueMap.get(PROP_LOGO_ALT_TEXT, String.class);
				logoOpenInNewWindow = headerValueMap.get(PROP_LOGO_OPEN_IN_NEW_WINDOW,
						PROP_OPEN_LINK_IN_NEW_WINDOW_DEFAULT);

				// Get Navigation Data
				String navigationRootPath = headerValueMap.get(PROP_NAVIGATION_ROOTPATH,
						PROP_NAVIGATION_ROOT_PATH_DEFAULT);
				int navigationDepth = headerValueMap.get(PROP_NAVIGATION_DEPTH, PROP_NAVIGATION_DEPTH_DEFAULT);
				
				log.debug("PROP_NAVIGATION_ROOTPATH--"+PROP_NAVIGATION_ROOTPATH);
				log.debug("PROP_NAVIGATION_ROOT_PATH_DEFAULT--"+PROP_NAVIGATION_ROOT_PATH_DEFAULT);
				
				log.debug("PROP_NAVIGATION_DEPTH--"+PROP_NAVIGATION_DEPTH);
				log.debug("PROP_NAVIGATION_DEPTH_DEFAULT--"+PROP_NAVIGATION_DEPTH_DEFAULT);
				
				log.debug("navigationDepth--"+navigationDepth);
				log.debug("navigationRootPath--"+navigationRootPath);
				// Create site main navigation
				Resource rootResource = resourceResolver.getResource(navigationRootPath);
				
				if (rootResource != null) {
					Page navigationRootPage = rootResource.adaptTo(Page.class);
					siteNavigationItems = getSiteNavigation(navigationRootPage, navigationDepth);
					log.debug("navigationRootPath--"+navigationRootPath);
					log.debug("navigationDepth--"+navigationDepth);
					log.debug("siteNavigationItems--"+siteNavigationItems.size());
				}else {
					log.debug("resource is null");
				}

				// Login button
				loginButtonLabel = headerValueMap.get("loginButtonLabel", String.class);
				loginColumn1Title = headerValueMap.get("loginColumn1Title", String.class);
				loginColumn1Text = headerValueMap.get("loginColumn1Text", String.class);
				loginColumn1LinkUrl = headerValueMap.get("loginColumn1LinkUrl", String.class);
				loginColumn1LinkText = headerValueMap.get("loginColumn1LinkText", String.class);
				loginColumn1LinkOpenInNewTab = headerValueMap.get("loginColumn1LinkOpenInNewTab", false);
				loginColumn2Title = headerValueMap.get("loginColumn2Title", String.class);
				loginColumn2Text = headerValueMap.get("loginColumn2Text", String.class);
				loginColumn2LinkUrl = headerValueMap.get("loginColumn2LinkUrl", String.class);
				loginColumn2LinkText = headerValueMap.get("loginColumn2LinkText", String.class);
				loginColumn2LinkOpenInNewTab = headerValueMap.get("loginColumn2LinkOpenInNewTab", false);
				redirectInNewWindow = headerValueMap.get("redirectInNewWindow", false);

				// Search
				searchUrl = headerValueMap.get(PROP_SEARCH_LINK, String.class);
				searchPlaceholderText = headerValueMap.get(PROP_SEARCH_PLACEHOLDER_TEXT, String.class);
				searchButtonText = headerValueMap.get(PROP_SEARCH_BUTTON_TEXT, String.class);
			}
		}
	}
	
	/**
     * Returns all the pages of a sub-tree
     * root - root node to start listing from
     * level - how deep to get into the tree
     */
    private List<NavigationItemModel> getSiteNavigation(Page root, int level) {
        if (root == null || level == 0) {
            return null;
        }
        List<NavigationItemModel> navigationItems = new ArrayList<NavigationItemModel>();
        Iterator<Page> it = root.listChildren(new PageFilter());

        while (it.hasNext()) {
            Page page = it.next();
            
            if(page.isHideInNav()) continue;
            
            if (UrlUtil.isRedirectPage(page)) {
                page = UrlUtil.resolveRedirectPage(page, pageManager);
            }
            List<NavigationItemModel> children = getSiteNavigation(page, level - 1);
                    
            navigationItems.add(new NavigationItemModel(currentPage, page, children));
      }
        return navigationItems;
    }
    
	public String getLogoLinkUrl() {
		return logoLinkUrl;
	}
	
	public String getDesktopLogoSrc() {
		return desktopLogoSrc;
	}
	
	public String getLogoAltText() {
		return logoAltText;
	}

	public boolean isLogoOpenInNewWindow() {
		return logoOpenInNewWindow;
	}

	public List<NavigationItemModel> getSiteNavigationItems() {
		if(siteNavigationItems == null)
			return new ArrayList<NavigationItemModel>();
		
		return new ArrayList<NavigationItemModel>(siteNavigationItems);
	}
	
	public String getMobileLogoSrc() {
		return mobileLogoSrc;
	}
	
	/**
	 * @return the searchUrl
	 */
	public String getSearchUrl() {
		return searchUrl;
	}
	/**
	 * @return the searchPlaceholderText
	 */
	public String getSearchPlaceholderText() {
		return searchPlaceholderText;
	}
	/**
	 * @return the searchButtonText
	 */
	public String getSearchButtonText() {
		return searchButtonText;
	}

	/**
	 * @return the loginButtonLabel
	 */
	public String getLoginButtonLabel() {
		return loginButtonLabel;
	}

	/**
	 * @return the loginColumn1Title
	 */
	public String getLoginColumn1Title() {
		return loginColumn1Title;
	}

	/**
	 * @return the loginColumn1Text
	 */
	public String getLoginColumn1Text() {
		return loginColumn1Text;
	}

	/**
	 * @return the loginColumn1LinkUrl
	 */
	public String getLoginColumn1LinkUrl() {
		return loginColumn1LinkUrl;
	}

	/**
	 * @return the loginColumn1LinkText
	 */
	public String getLoginColumn1LinkText() {
		return loginColumn1LinkText;
	}

	/**
	 * @return the loginColumn1LinkOpenInNewTab
	 */
	public boolean isLoginColumn1LinkOpenInNewTab() {
		return loginColumn1LinkOpenInNewTab;
	}

	/**
	 * @return the loginColumn2Title
	 */
	public String getLoginColumn2Title() {
		return loginColumn2Title;
	}

	/**
	 * @return the loginColumn2Text
	 */
	public String getLoginColumn2Text() {
		return loginColumn2Text;
	}

	/**
	 * @return the loginColumn2LinkUrl
	 */
	public String getLoginColumn2LinkUrl() {
		return loginColumn2LinkUrl;
	}

	/**
	 * @return the loginColumn2LinkText
	 */
	public String getLoginColumn2LinkText() {
		return loginColumn2LinkText;
	}

	/**
	 * @return the loginColumn2LinkOpenInNewTab
	 */
	public boolean isLoginColumn2LinkOpenInNewTab() {
		return loginColumn2LinkOpenInNewTab;
	}
	
	public boolean isRedirectInNewWindow() {
		return redirectInNewWindow;
	}
}