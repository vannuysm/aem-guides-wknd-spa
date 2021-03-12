package com.wyndham.redesign.core.models;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

/**
 * The type Link model. Reusable model for links.
 */
@Model(adaptables = Resource.class)
public class LinkModel {

    /**
     * The Link label.
     */
    @Inject
    @Named("linkLabel")
    @Optional
    String linkLabel;

    /**
     * The Link path.
     */
    @Inject
    @Optional
    @Named("linkPath")
    String linkPath;

    /**
     * The Open in new tab.
     */
    @Inject
    @Optional
    String openInNewTab;

    /**
     * The Is activate.
     */
    @Inject
    @Optional
    String isActive;

    @SlingObject
    ResourceResolver resolver;

    /**
     * Gets link label.
     *
     * @return the link label
     */
    public String getLinkLabel() {
        return linkLabel;
    }

    /**
     * Gets link path.
     *
     * @return the link path
     */
    public String getLinkPath() {
       return linkPath;
    }

    public String getOpenInNewTab() {
        return openInNewTab;
    }

    /**
     * Gets open in new tab.
     *
     * @return the open in new tab
     */


    public String getIsActive() {
        return isActive;
    }

    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath;
    }
}
