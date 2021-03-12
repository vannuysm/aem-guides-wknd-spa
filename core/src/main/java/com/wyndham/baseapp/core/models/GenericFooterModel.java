package com.wyndham.baseapp.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.wcm.api.PageManager;
import com.wyndham.baseapp.core.utils.WyndhamUtil;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GenericFooterModel {
	@ScriptVariable
	protected PageManager pageManager;

	@ChildResource
	private List<PrimaryLinks> primaryLinks;

	@ChildResource
	private List<PrimaryLinks> socialIcons;

	@ChildResource
	private List<PrimaryLinks> linkList;

	@ChildResource
	private List<PrimaryLinks> rightTopSecondaryLinks;

	@ChildResource
	private List<Resource> brandIconList;

	@ChildResource
	private List<Resource> secondbrandIconList;

	@ChildResource
	private List<Resource> clubIconList;

	@ValueMapValue
	private String rightTopPrimaryLabel;

	@ValueMapValue
	private String rightTopPrimaryPath;

	@ValueMapValue
	private String rightTopPrimaryTarget;

	@ValueMapValue
	private String disclaimerText;

	@ValueMapValue
	private String fileReference;

	@ValueMapValue
	private String logoLinkUrl;

	@ValueMapValue
	private String copyrightText;

	@ValueMapValue
	private String rightsText;

	@ValueMapValue
	private String personalInfoText;

	@ValueMapValue
	private String showLegalDisclaimer;

	@ValueMapValue
	private String showSocial;

	@ValueMapValue
	private String showFooterSubMenu;

	@ChildResource
	private List<PrimaryLinks> rightBottomSecondaryLinks;

	@PostConstruct
	protected void initModel() {
		normalizeLinks();
	}

	private void normalizeLinks() {
		logoLinkUrl = WyndhamUtil.getInternalPageUrl(logoLinkUrl, pageManager);
		rightTopPrimaryPath = WyndhamUtil.getInternalPageUrl(rightTopPrimaryPath, pageManager);
		WyndhamUtil.normalizePrimaryLinkTree(primaryLinks, pageManager);
		WyndhamUtil.normalizePrimaryLinkTree(socialIcons, pageManager);
		WyndhamUtil.normalizePrimaryLinkTree(linkList, pageManager);
		WyndhamUtil.normalizePrimaryLinkTree(rightTopSecondaryLinks, pageManager);
		WyndhamUtil.normalizePrimaryLinkTree(rightBottomSecondaryLinks, pageManager);
	}

	public List<PrimaryLinks> getPrimaryLinks() {
		return new ArrayList<PrimaryLinks>(primaryLinks);
	}

	public List<PrimaryLinks> getSocialIcons() {
		return new ArrayList<PrimaryLinks>(socialIcons);
	}

	public List<PrimaryLinks> getRightTopSecondaryLinks() {
		return new ArrayList<PrimaryLinks>(rightTopSecondaryLinks);
	}

	public List<PrimaryLinks> getRightBottomSecondaryLinks() {
		return new ArrayList<PrimaryLinks>(rightBottomSecondaryLinks);
	}

	public List<Resource> getBrandIconList() {
		return brandIconList;
	}

	public String getRightTopPrimaryLabel() {
		return rightTopPrimaryLabel;
	}

	public String getRightTopPrimaryPath() {
		return rightTopPrimaryPath;
	}

	public String getRightTopPrimaryTarget() {
		return rightTopPrimaryTarget;
	}

	public String getDisclaimerText() {
		return disclaimerText;
	}

	public List<PrimaryLinks> getLinkList() {
		return new ArrayList<PrimaryLinks>(linkList);
	}

	public String getFileReference() {
		return fileReference;
	}

	public String getLogoLinkUrl() {
		return logoLinkUrl;
	}

	public String getCopyrightText() {
		return copyrightText;
	}

	public String getRightsText() {
		return rightsText;
	}

	public String getPersonalInfoText() {
		return personalInfoText;
	}

	public String getShowLegalDisclaimer() {
		return showLegalDisclaimer;
	}

	public String getShowSocial() {
		return showSocial;
	}

	public String getShowFooterSubMenu() {
		return showFooterSubMenu;
	}

	public List<Resource> getClubIconList() {
		return clubIconList;
	}

	public List<Resource> getSecondbrandIconList() {
		return secondbrandIconList;
	}
}
