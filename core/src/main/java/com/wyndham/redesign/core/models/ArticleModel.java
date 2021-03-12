package com.wyndham.redesign.core.models;

import static com.day.cq.commons.jcr.JcrConstants.JCR_CREATED;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.day.cq.wcm.api.PageManager;
import com.wyndham.common.WyndhamConstants;
import com.wyndham.common.WyndhamUtils;

@Model(adaptables = Resource.class)
public class ArticleModel {

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
    @Named(JCR_CREATED)
    Calendar publishedDate;

    String publishedDateString;

    /**
     * The Img path.
     */
    @Inject
    @Optional
    String imgPath;

    /**
     * The Short desc.
     */
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

    @Inject
    @Optional
    String extTitle;

    @Inject
    @Optional
    String extSubTitle;

    /**
     * The Short desc.
     */
    @Inject
    @Optional
    String extShortDesc;

    @Inject
    @Optional
    String extLinkLabel;

    @Inject
    @Optional
    String extLinkPath;

    @Inject
    @Optional
    String exOpenInNewTab;

    @Inject
    @Optional
    String altText;

    @Inject
    @Optional
    String isActive;

    @Inject
    @Optional
    List<LinkModel> links;
    

    @Inject
    @Optional
	Date date;
    
    String dateStr;
    String checkedLinkPath;

	@SlingObject
    ResourceResolver resolver;
	
    private PageManager pageManager;

    @PostConstruct
    void init(){
        if (publishedDate!=null ){
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
            publishedDateString = formatter.format(publishedDate.getTime());
        }
        if(date!=null){
        	SimpleDateFormat formatter = new SimpleDateFormat(WyndhamConstants.DATE_FORMAT_7);
        	dateStr = formatter.format(date.getTime());
        }
        if(linkPath!=null && !linkPath.isEmpty()) {
          if(pageManager == null) {
        	 pageManager = resolver.adaptTo(PageManager.class);
           }	
     	 if(pageManager!=null) {
     		setCheckedLinkPath(WyndhamUtils.getInternalPageUrl(linkPath, pageManager));
     	  }
        }
    }

    public String getPublishedDateString() {
        return publishedDateString;
    }

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

    public Calendar getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Calendar publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
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

    public void setPublishedDateString(String publishedDateString) {
        this.publishedDateString = publishedDateString;
    }

    public String getExtTitle() {
        return extTitle;
    }

    public void setExtTitle(String extTitle) {
        this.extTitle = extTitle;
    }

    public String getExtSubTitle() {
        return extSubTitle;
    }

    public void setExtSubTitle(String extSubTitle) {
        this.extSubTitle = extSubTitle;
    }

    public String getExtShortDesc() {
        return extShortDesc;
    }

    public void setExtShortDesc(String extShortDesc) {
        this.extShortDesc = extShortDesc;
    }

    public String getExtLinkLabel() {
        return extLinkLabel;
    }

    public void setExtLinkLabel(String extLinkLabel) {
        this.extLinkLabel = extLinkLabel;
    }

    public String getExtLinkPath() {
        return extLinkPath;
    }

    public void setExtLinkPath(String extLinkPath) {
        this.extLinkPath = extLinkPath;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getIsActive() {
        return isActive;
    }

    public List<LinkModel> getLinks() {
        return links;
    }

    public void setLinks(List<LinkModel> links) {
        this.links = links;
    }

    public String getOpenInNewTab() {
        return openInNewTab;
    }

    public String getExOpenInNewTab() {
        return exOpenInNewTab;
    }

	public Date getDate() {
		return date;
	}

	public String getDateStr() {
		return dateStr;
	}

    public String getCheckedLinkPath() {
		return checkedLinkPath;
	}

	public void setCheckedLinkPath(String checkedLinkPath) {
		this.checkedLinkPath = checkedLinkPath;
	}


}
