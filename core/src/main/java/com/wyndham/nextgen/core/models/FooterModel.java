package com.wyndham.nextgen.core.models;

import com.day.cq.commons.LanguageUtil;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.wyndham.nextgen.core.constants.WyndhamConstants;
import com.wyndham.nextgen.core.pojos.NameValuePairPojo;
import com.wyndham.nextgen.core.services.LanguageService;
import com.wyndham.nextgen.core.utils.WyndhamUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = {Resource.class,
    SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FooterModel implements WyndhamConstants {

    /**
     * The Language service.
     */
    @OSGiService
    LanguageService languageService;

    @ScriptVariable
    private Page currentPage;

    @ScriptVariable
    private PageManager pageManager;

    @SlingObject
    private ResourceResolver resourceResolver;

    @SlingObject
    private Resource resource;

    @SlingObject
    SlingHttpServletRequest request;

    private List<FooterColumnModel> footerColumns = new ArrayList<FooterColumnModel>();

    private List<NavigationItemModel> quickLinks = new ArrayList<NavigationItemModel>();

    private List<LinkModel> socialMediaLinks = new ArrayList<LinkModel>();

    private List<NameValuePairPojo> languages = new ArrayList<NameValuePairPojo>();

    private String logoLinkUrl;

    private String logoSrc;

    private String logoAltText;

    private boolean logoOpenInNewWindow;

    private String copyrightText1;

    private String copyrightText2;

    private String timeShareLegalCopy;

    @PostConstruct
    private void initModel() {

        languages = languageService.getLanguagesLocale(resourceResolver, request.getPathInfo(), pageManager);

        String languageRootPath = LanguageUtil.getLanguageRoot(currentPage.getPath());

        if (languageRootPath == null) {
            languageRootPath = WyndhamConstants.DEFAULT_LANGUAGE_ROOT;
        }

        if (languageRootPath.equalsIgnoreCase("/content/club/clubwyndham/us/en")) {
            //log.info("languageRootPath--"+languageRootPath);
            languageRootPath = WyndhamConstants.DEFAULT_LANGUAGE_ROOT;
        }

        if (languageRootPath != null) {
            Resource footerResource = resourceResolver.getResource(languageRootPath + "/jcr:content/root/footer");

            if (footerResource != null) {
                ValueMap footerValueMap = footerResource.adaptTo(ValueMap.class);

                // Footer columns
                String footerColumn1Heading = footerValueMap.get(PROP_FOOTERCOLUMN1_HEADING, String.class);
                String footerColumn2Heading = footerValueMap.get(PROP_FOOTERCOLUMN2_HEADING, String.class);
                String footerColumn3Heading = footerValueMap.get(PROP_FOOTERCOLUMN3_HEADING, String.class);
                String footerColumn4Heading = footerValueMap.get(PROP_FOOTERCOLUMN4_HEADING, String.class);
                String footerColumn5Heading = footerValueMap.get(PROP_FOOTERCOLUMN5_HEADING, String.class);

                String footerColumn1HeadingLink = WyndhamUtil.getInternalPageUrl(
                    footerValueMap.get(PROP_FOOTERCOLUMN1_HEADING_LINK, String.class), pageManager);
                String footerColumn2HeadingLink = WyndhamUtil.getInternalPageUrl(
                    footerValueMap.get(PROP_FOOTERCOLUMN2_HEADING_LINK, String.class), pageManager);
                String footerColumn3HeadingLink = WyndhamUtil.getInternalPageUrl(
                    footerValueMap.get(PROP_FOOTERCOLUMN3_HEADING_LINK, String.class), pageManager);
                String footerColumn4HeadingLink = WyndhamUtil.getInternalPageUrl(
                    footerValueMap.get(PROP_FOOTERCOLUMN4_HEADING_LINK, String.class), pageManager);
                String footerColumn5HeadingLink = WyndhamUtil.getInternalPageUrl(
                    footerValueMap.get(PROP_FOOTERCOLUMN5_HEADING_LINK, String.class), pageManager);

                footerColumns.add(new FooterColumnModel(footerColumn1Heading, footerColumn1HeadingLink,
                    createNavigationLinks(footerResource.getChild(CHILD_FOOTERCOLUMN1_LINKS))));
                footerColumns.add(new FooterColumnModel(footerColumn2Heading, footerColumn2HeadingLink,
                    createNavigationLinks(footerResource.getChild(CHILD_FOOTERCOLUMN2_LINKS))));
                footerColumns.add(new FooterColumnModel(footerColumn3Heading, footerColumn3HeadingLink,
                    createNavigationLinks(footerResource.getChild(CHILD_FOOTERCOLUMN3_LINKS))));
                footerColumns.add(new FooterColumnModel(footerColumn4Heading, footerColumn4HeadingLink,
                    createNavigationLinks(footerResource.getChild(CHILD_FOOTERCOLUMN4_LINKS))));
                footerColumns.add(new FooterColumnModel(footerColumn5Heading, footerColumn5HeadingLink,
                    createNavigationLinks(footerResource.getChild(CHILD_FOOTERCOLUMN5_LINKS))));

                // Quick, Social and Spaced links
                socialMediaLinks.addAll(createLinks(footerResource.getChild(CHILD_SOCIALMEDIA_LINKS)));
                quickLinks.addAll(createNavigationLinks(footerResource.getChild(CHILD_QUICK_LINKS)));

                // Get Logo
                logoLinkUrl = WyndhamUtil.getInternalPageUrl(footerValueMap.get(PROP_LOGO_URL_FOOTER, String.class),
                    pageManager);
                logoSrc = footerValueMap.get(PROP_DESKTOP_LOGO_SRC_FOOTER, String.class);
                logoAltText = footerValueMap.get(PROP_LOGO_ALT_TEXT_FOOTER, String.class);
                logoOpenInNewWindow = footerValueMap.get(PROP_LOGO_OPEN_IN_NEW_WINDOW_FOOTER,
                    PROP_OPEN_LINK_IN_NEW_WINDOW_DEFAULT);

                timeShareLegalCopy = footerValueMap.get(PROP_TIMESHARE_LEGAL_COPY, String.class);
                // Get copyright text
                copyrightText1 = footerValueMap.get(PROP_COPYRIGHT_TEXT_1, String.class);
                String year = String.valueOf(getYear());
                copyrightText1 = copyrightText1.replace("{YY}", year);
                copyrightText2 = footerValueMap.get(PROP_COPYRIGHT_TEXT_2, String.class);
            }
        }
    }

    private List<LinkModel> createLinks(Resource linksResource) {
        List<LinkModel> links = new ArrayList<LinkModel>();
        if (linksResource != null) {
            Iterator<Resource> childResources = linksResource.listChildren();
            while (childResources.hasNext()) {
                LinkModel link = childResources.next().adaptTo(LinkModel.class);
                if (link != null) {
                    links.add(link);
                }
            }
        }
        return links;
    }

    private List<NavigationItemModel> createNavigationLinks(Resource linksResource) {
        List<NavigationItemModel> navigationLinks = new ArrayList<NavigationItemModel>();
        if (linksResource != null) {
            Iterator<Resource> childResources = linksResource.listChildren();
            while (childResources.hasNext()) {
                NavigationItemModel navigationItem = getNavigationItem(childResources.next());
                if (navigationItem != null) {
                    navigationLinks.add(navigationItem);
                }
            }
        }
        return navigationLinks;
    }

    private NavigationItemModel getNavigationItem(Resource resource) {
        LinkModel link = resource.adaptTo(LinkModel.class);
        if (link != null) {
            if (StringUtils.isNotBlank(link.getLinkUrl())) {
                return new NavigationItemModel(currentPage, link);
            }
        }
        return null;
    }

    /**
     * @return the footerColumns
     */
    public List<FooterColumnModel> getFooterColumns() {
        return new ArrayList<FooterColumnModel>(footerColumns);
    }

    /**
     * @return the quickLinks
     */
    public List<NavigationItemModel> getQuickLinks() {
        return new ArrayList<NavigationItemModel>(quickLinks);
    }

    /**
     * @return the socialMediaLinks
     */
    public List<LinkModel> getSocialMediaLinks() {
        return new ArrayList<LinkModel>(socialMediaLinks);
    }

    /**
     * @return the languages
     */
    public List<NameValuePairPojo> getLanguages() {
        return new ArrayList<NameValuePairPojo>(languages);
    }

    /**
     * @return the logoLinkUrl
     */
    public String getLogoLinkUrl() {
        return logoLinkUrl;
    }

    /**
     * @return the logoSrc
     */
    public String getLogoSrc() {
        return logoSrc;
    }

    /**
     * @return the logoAltText
     */
    public String getLogoAltText() {
        return logoAltText;
    }

    /**
     * @return the logoOpenInNewWindow
     */
    public boolean isLogoOpenInNewWindow() {
        return logoOpenInNewWindow;
    }

    /**
     * @return the copyrightText1
     */
    public String getCopyrightText1() {
        return copyrightText1;
    }

    /**
     * @return the copyrightText2
     */
    public String getCopyrightText2() {
        return copyrightText2;
    }

    /**
     * @return the timeShareLegalCopy
     */
    public String getTimeShareLegalCopy() {
        return timeShareLegalCopy;
    }

    private int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
}
