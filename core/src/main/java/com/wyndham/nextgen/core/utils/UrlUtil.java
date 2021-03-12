package com.wyndham.nextgen.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

public class UrlUtil {

    private static final Logger log = LoggerFactory.getLogger(UrlUtil.class);
    private static final String RT_REDIRECT = "foundation/components/redirect";
    private static final String PN_REDIRECT_TARGET = "redirectTarget";

    /**
     * Check if the current page is a redirect page
     * @param page to check
     * @return true if current page is of type redirect, otherwise false
     */
    public static boolean isRedirectPage(Page page) {
        boolean isRedirect = false;
        Resource contentResource = page.getContentResource();
        if (contentResource != null) {
            isRedirect = contentResource.isResourceType(RT_REDIRECT);
        } else {
            log.error("Can't get content resource of page {}", page.getPath());
        }
        return isRedirect;
    }

    /**
     * Resolve the page to the redirect page
     * 
     * @param page
     *            to resolve
     * @param pageManager
     *            the page manager
     * @return the redirect target or the given page
     */
    public static Page resolveRedirectPage(Page page, PageManager pageManager) {
        Page redirectTarget = page;
        if (isRedirectPage(page)) {
            Resource contentResource = page.getContentResource();
            ValueMap valueMap = contentResource.adaptTo(ValueMap.class);
			if (valueMap != null) {
				String redirectPagePath = valueMap.get(PN_REDIRECT_TARGET, StringUtils.EMPTY);			
				Page resolvedPage = pageManager.getPage(redirectPagePath);
				if (resolvedPage != null) {
					redirectTarget = resolvedPage;
				}
			}
        }
        return redirectTarget;
    }
}

