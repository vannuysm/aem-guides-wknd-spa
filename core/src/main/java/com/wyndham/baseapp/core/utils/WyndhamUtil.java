package com.wyndham.baseapp.core.utils;


import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.wyndham.baseapp.core.constants.WyndhamConstants;
import com.wyndham.baseapp.core.models.CFMResortModel;
import com.wyndham.baseapp.core.models.PrimaryLinks;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class WyndhamUtil.
 */
public class WyndhamUtil {

    /**
     * The Constant log.
     */
    private static final Logger log = LoggerFactory.getLogger(WyndhamUtil.class);

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
     * Gets the date format.
     *
     * @param format the format
     * @return the date format
     */
    public static DateFormat getDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * Returns the root page of the site
     * E.g.: /content/wyndham/en
     *
     * @param resourcePage the current Page
     * @return root page or null if a root cannot be found
     */
    public static Page findRoot(Page resourcePage) {
        Page rootPage = resourcePage;
        while (rootPage != null && !isRoot(rootPage)) {
            rootPage = rootPage.getParent();
        }
        return rootPage;
    }

    /**
     * Checks if a page is the root page of the site.
     *
     * @param page current page to check
     * @return <code>true</code> if the page is the root page of the site, <code>false</code> otherwise.
     */
    public static boolean isRoot(Page page) {
        Resource res = page.getContentResource();
        if (res == null) {
            return false;
        }
        ValueMap vm = res.adaptTo(ValueMap.class);
        if (vm == null) {
            return false;
        }
        return vm.get(WyndhamConstants.PROP_NAV_ROOT, false);
    }

    /**
     * Returns the title of the given resource. If the title is empty it will fallback to the page title, title,
     * or name of the given page.
     *
     * @param resource The resource.
     * @param page     The page to fallback to.
     * @return The best suited title found (or <code>null</code> if resource is <code>null</code>).
     */
    public static String getTitle(final Resource resource, final Page page) {
        if (resource != null) {
            final ValueMap properties = resource.adaptTo(ValueMap.class);
            if (properties != null) {
                final String title = properties.get(NameConstants.PN_TITLE, String.class);
                if (StringUtils.isNotBlank(title)) {
                    return title;
                } else {
                    return getPageTitle(page);
                }
            }
        } else {
            log.debug("Provided resource argument is null");
        }
        return null;
    }

    /**
     * Returns the page title of the given page. If the page title is empty it will fallback to the title and to the
     * name of the page.
     *
     * @param page The page.
     * @return The best suited title found (or <code>null</code> if page is <code>null</code>).
     */
    public static String getPageTitle(final Page page) {
        if (page != null) {
            final String title = page.getPageTitle();
            if (StringUtils.isBlank(title)) {
                return getTitle(page);
            }
            return title;
        } else {
            log.debug("Provided page argument is null");
            return null;
        }
    }

    /**
     * Returns the title of the given page. If the title is empty it will fallback to the name of the page.
     *
     * @param page The page.
     * @return The best suited title found (or <code>null</code> if page is <code>null</code>).
     */
    public static String getTitle(final Page page) {
        if (page != null) {
            final String title = page.getTitle();
            if (StringUtils.isBlank(title)) {
                return page.getName();
            }
            return title;
        } else {
            log.debug("Provided page argument is null");
            return null;
        }
    }

    /**
     * Checks if is page exists.
     *
     * @param pagePath    the page path
     * @param pageManager the page manager
     * @return true, if is page exists
     */
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
     * Gets the page.
     *
     * @param pagePath    the page path
     * @param pageManager the page manager
     * @return the page
     */
    public static Page getPage(final String pagePath, final PageManager pageManager) {
        if (pageManager != null) {
            Page page = pageManager.getPage(pagePath);
            if (page != null) {
                return page;
            }
        }
        return null;
    }

    /**
     * Checks if is internal link.
     *
     * @param linkUrl     the link url
     * @param pageManager the page manager
     * @return true, if is internal link
     */
    public static boolean isInternalLink(String linkUrl, PageManager pageManager) {
        if (linkUrl != null && !linkUrl.isEmpty()) {
            if (linkUrl.startsWith("/content") && isPageExists(linkUrl, pageManager)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets the internal page url.
     *
     * @param linkUrl     the link url
     * @param pageManager the page manager
     * @return the internal page url
     */
    public static String getInternalPageUrl(String linkUrl, PageManager pageManager) {
        if (pageManager != null && linkUrl != null && !linkUrl.isEmpty()) {
            if (isInternalLink(linkUrl, pageManager)) {
                return linkUrl + ".html";
            }
        }
        return linkUrl;
    }

    /**
     * Gets the read resolver.
     *
     * @param resolverFactory the resolver factory
     * @return the read resolver
     */
    public static ResourceResolver getReadResolver(ResourceResolverFactory resolverFactory) {
        ResourceResolver resolver = null;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put(ResourceResolverFactory.SUBSERVICE, WyndhamConstants.READ_RESOLVER);
        try {
            resolver = resolverFactory.getServiceResourceResolver(param);
        } catch (LoginException e) {
            log.error("LoginException for readService service resolver " + e);
        }
        return resolver;
    }

    /**
     * Gets the read write resolver.
     *
     * @param resolverFactory the resolver factory
     * @return the read write resolver
     */
    public static ResourceResolver getReadWriteResolver(ResourceResolverFactory resolverFactory) {
        ResourceResolver resolver = null;
        Map<String, Object> param = new ConcurrentHashMap<String, Object>();
        param.put(ResourceResolverFactory.SUBSERVICE, WyndhamConstants.READ_WRITE_RESOLVER);

        try {
            resolver = resolverFactory.getServiceResourceResolver(param);
        } catch (LoginException e) {
            log.error("LoginException for readWriteService resolver " + e);
        }
        return resolver;
    }

    /**
     * Gets the admin resolver.
     *
     * @param resolverFactory the resolver factory
     * @return the admin resolver
     */
    public static ResourceResolver getAdminResolver(ResourceResolverFactory resolverFactory) {
        ResourceResolver resolver = null;
        Map<String, Object> param = new ConcurrentHashMap<String, Object>();
        param.put(ResourceResolverFactory.SUBSERVICE, WyndhamConstants.ADMINUSER_RESOLVER);

        try {
            resolver = resolverFactory.getServiceResourceResolver(param);
        } catch (LoginException e) {
            log.error("LoginException for readWriteService resolver " + e);
        }
        return resolver;
    }

    /**
     * Gets the yext id.
     *
     * @param resource the resource
     * @param resolver the resolver
     * @return the yext id
     */
    public static String getYextId(Resource resource, ResourceResolver resolver) {

        if (resource != null) {
            if (resource.getPath().contains("review")) {
                Resource resortDetailResource = resolver.getResource(resource.getPath().replace("review_listing", "resort_content_detai"));
                if (resortDetailResource != null) {

                    ValueMap valueMap = resortDetailResource.getValueMap();

                    if (valueMap != null) {
                        String contentSource = valueMap.get("contentSource").toString();

                        Resource r = resolver.getResource(contentSource + WyndhamConstants.CFM_MASTER_VARIATION_PATH);
                        if (r != null) {
                            CFMResortModel resortModel = r.adaptTo(CFMResortModel.class);

                            if (resortModel != null) {
                                return resortModel.getYextId() == null ? "" : resortModel.getYextId();
                            }
                        }
                    }
                }
            }
        }

        return "";
    }

    public static void writeResponse(SlingHttpServletResponse response, String message)
        throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(message);
    }

    public static void normalizePrimaryLinkTree(List<PrimaryLinks> primaryLinks, PageManager pageManager) {
        if (primaryLinks != null) {
            primaryLinks.forEach(primaryLink -> {
                String normalizedLinkPath = WyndhamUtil.getInternalPageUrl(primaryLink.getLinkPath(), pageManager);
                primaryLink.setLinkPath(normalizedLinkPath);
                List<PrimaryLinks> secondaryLinks = primaryLink.getPrimaryLinks();
                if (secondaryLinks != null && secondaryLinks.size() > 0) {
                    normalizePrimaryLinkTree(secondaryLinks, pageManager);
                }
            });
        }
    }

}