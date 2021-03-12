package com.wyndham.common;


import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Wyndham utils: Utility class
 *
 * @author nareshs
 */
public class WyndhamUtils {

    private static final Logger log = LoggerFactory.getLogger(WyndhamUtils.class);

    /**
     * Gets the country root of a site.
     *
     * @param path the path
     * @return the string
     */

    public static String getCountryRootPath(String path) {
        return pathBuilder(path, 5);
    }

    /**
     * Gets the language root of a country.
     *
     * @param path the path
     * @return the string
     */
    public static String getLanguageRootPath(String path) {
        return pathBuilder(path, 6);
    }

    /**
     * Path builder.
     *
     * @param path  the path
     * @param index the index
     * @return the string
     */
    public static String pathBuilder(String path, int index) {
        return StringUtils.substring(path, 0, StringUtils.ordinalIndexOf(path, WyndhamConstants.PATH_SEPERATOR, index));
    }


    /**
     * Get title form the page
     *
     * @param page
     * @return the page title string
     */
    public static String getPageTitle(Page page) {
        String title = page.getNavigationTitle();
        if (title == null || title.isEmpty()) {
            title = page.getPageTitle();
        }
        if (title == null || title.isEmpty()) {
            title = page.getTitle();
        }
        if (title == null || title.isEmpty()) {
            title = page.getName();
        }
        return title;
    }

    public static DateFormat getDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    public static boolean isInternalLink(String linkUrl, PageManager pageManager) {
        if (linkUrl != null && !linkUrl.isEmpty()) {
            if (linkUrl.startsWith("/content") && isPageExists(linkUrl, pageManager)) {
                return true;
            }
        }

        return false;
    }

    public static String getInternalPageUrl(String linkUrl, PageManager pageManager) {
        if (linkUrl != null && !linkUrl.isEmpty()) {
            if (isInternalLink(linkUrl, pageManager)) {
                return linkUrl + ".html";
            }
        }
        return linkUrl;
    }

    public static boolean isPageExists(final String pagePath, final PageManager pageManager) {
        if (pageManager != null) {
            Page page = pageManager.getPage(pagePath);
            if (page != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the current default service resource resolver for the project.
     *
     * @param resourceResolverFactory the ResourceResolverFactory used
     *                                to get the service resourceResolver.
     * @return the service resource resolver.
     * @throws LoginException
     */
    public static ResourceResolver getDefaultServiceResourceResolver(
        final ResourceResolverFactory resourceResolverFactory, final String service) throws LoginException {
        final Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, service);
        return resourceResolverFactory.getServiceResourceResolver(param);
    }

    public static <R> R GetFromJCR(ResourceResolverFactory factory, R defaultValue, final Function<ResourceResolver, R> f) {
        ResourceResolver resourceResolver = null;
        R result = defaultValue;

        try {
            resourceResolver = getDefaultServiceResourceResolver(factory, "MinivacService");
            result = f.apply(resourceResolver);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resourceResolver != null) {
                resourceResolver.close();
            }
        }

        return result;
    }
}