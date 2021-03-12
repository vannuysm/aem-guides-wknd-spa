package com.wyndham.redesign.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(
        adaptables = Resource.class)
public class CardModel {

    Logger log = LoggerFactory.getLogger(CardModel.class);

    /**
     * The Resolver.
     */
    @SlingObject
    ResourceResolver resolver;

    /**
     * The Title.
     */
    @Inject
    @Optional
    String title;

    @Inject
    @Optional
    String subTitle;

    @Inject
    @Optional
    String shortDesc;

    @Inject
    @Optional
    String linkLabel;

    @Inject
    @Optional
    String linkPath;

    @Inject
    @Optional
    String openInNewTab;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLinkLabel() {
        return linkLabel;
    }

    public void setLinkLabel(String linkLabel) {
        this.linkLabel = linkLabel;
    }

    public String getLinkPath() {
        return linkPath;
    }

    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath;
    }

    public String getOpenInNewTab() {
        return openInNewTab;
    }

    public void setOpenInNewTab(String openInNewTab) {
        this.openInNewTab = openInNewTab;
    }

}
