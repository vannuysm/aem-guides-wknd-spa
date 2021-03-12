package com.wyndham.redesign.core.models;

import static com.day.cq.commons.jcr.JcrConstants.JCR_DESCRIPTION;
import static com.wyndham.common.WyndhamConstants.JCR_TITLE;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.google.gson.annotations.Expose;

/**
 * The type Dynamic data model. Stores dynamic content (page properties of content pages)
 */
@Model(adaptables = Resource.class)
public class DynamicDataModel {

    /**
     * The Title.
     */
    @Inject
    @Optional
    @Named(JCR_TITLE)
    @Expose
    String title;

    /**
     * The Sub title.
     */
    @Inject
    @Optional
    @Named("subtitle")
    @Expose
    String subTitle;

    /**
     * The Published date.
     */
    @Inject
    @Optional
    @Named("publishedTime")
    Calendar publishedTime;

    /**
     * The Published date string.
     */
    @Expose
    String publishedTimeString;

    /**
     * The Img path.
     */
    @Inject
    @Optional
    @Named("imgPath")
    @Expose
    String imgPath;

    /**
     * The Short desc.
     */
    @Inject
    @Optional
    @Named(JCR_DESCRIPTION)
    @Expose
    String shortDesc;

    /**
     * The Alt text.
     */
    @Inject
    @Optional
    @Named("altText")
    @Expose
    String altText;

    /**
     * The Link path.
     */
    @Inject
    @Optional
    @Expose
    String linkPath;

    @Inject
    @Optional
    @Named("eventTime")
    @Expose
    String eventTime;

    @Inject
    @Optional
    @Named("eventAddress")
    @Expose
    String eventAddress;

    @Inject
    @Optional
    @Named("contactDetails")
    @Expose
    String contactDetails;

    @SlingObject
    ResourceResolver resolver;

    /**
     * Init.
     */
    @PostConstruct
    void init(){
        if (publishedTime!=null ){
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
            publishedTimeString = formatter.format(publishedTime.getTime());
        }
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets sub title.
     *
     * @return the sub title
     */
    public String getSubTitle() {
        return subTitle;
    }

    /**
     * Sets sub title.
     *
     * @param subTitle the sub title
     */
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    /**
     * Gets published date.
     *
     * @return the published date
     */
    public Calendar getPublishedDate() {
        return publishedTime;
    }

    /**
     * Sets published date.
     *
     * @param publishedDate the published date
     */
    public void setPublishedDate(Calendar publishedDate) {
        this.publishedTime = publishedDate;
    }

    /**
     * Gets published date string.
     *
     * @return the published date string
     */
    public String getPublishedTimeString() {
        return publishedTimeString;
    }

    /**
     * Sets published date string.
     *
     * @param publishedTimeString the published date string
     */
    public void setPublishedTimeString(String publishedTimeString) {
        this.publishedTimeString = publishedTimeString;
    }

    /**
     * Gets img path.
     *
     * @return the img path
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * Sets img path.
     *
     * @param imgPath the img path
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * Gets short desc.
     *
     * @return the short desc
     */
    public String getShortDesc() {
        return shortDesc;
    }

    /**
     * Sets short desc.
     *
     * @param shortDesc the short desc
     */
    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    /**
     * Gets alt text.
     *
     * @return the alt text
     */
    public String getAltText() {
        return altText;
    }

    /**
     * Sets alt text.
     *
     * @param altText the alt text
     */
    public void setAltText(String altText) {
        this.altText = altText;
    }

    /**
     * Gets link path.
     *
     * @return the link path
     */
    public String getLinkPath() {
        return linkPath;
    }

    /**
     * Sets link path.
     *
     * @param linkPath the link path
     */
    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath;
    }

    public String getEventTime() {
        return eventTime;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public String getContactDetails() {
        return contactDetails;
    }

}
