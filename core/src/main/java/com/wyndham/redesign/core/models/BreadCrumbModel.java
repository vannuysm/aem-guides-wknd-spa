package com.wyndham.redesign.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.LanguageManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.wyndham.common.WyndhamConstants;
import com.wyndham.common.WyndhamUtils;
import com.wyndham.redesign.core.entity.NavigationEntity;

/**
 * 
 * This is the sling model for bread crumb component
 *
 */
@Model(adaptables = {Resource.class,SlingHttpServletRequest.class},defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BreadCrumbModel extends AbstractModel {

	private List<NavigationEntity> breadCrumbNav;
	Logger log = LoggerFactory.getLogger(BreadCrumbModel.class);

	@OSGiService
	LanguageManager languageManager;

	@PostConstruct
	protected void init() {
		String location = request.getPathInfo();
		location = StringUtils.substring(location, 0, StringUtils.indexOf(location, "."));
		List<NavigationEntity> entityList = new ArrayList<NavigationEntity>();
		Resource resource = resolver.getResource(location);
		if(resource != null) {
			PageManager manager = resolver.adaptTo(PageManager.class);
			if (manager != null) {
				Page currentPage = manager.getContainingPage(location);
				if (currentPage != null) {
					Page languageRootPage = languageManager.getLanguageRoot(resource);
					List<Page> pageList = new ArrayList<Page>();
					String path = currentPage.getPath();
					if (languageRootPage != null) {
						while (!languageRootPage.getPath().equals(path)) {
							pageList.add(currentPage);
							currentPage = currentPage.getParent();
							path = currentPage.getPath();
						}
						pageList.add(languageRootPage);
						for (int i = pageList.size() - 1; i >= 0; i--) {
							NavigationEntity entity = new NavigationEntity();
							Page childPage = pageList.get(i);
							if (childPage.getContentResource() != null) {
								ValueMap valueMap = childPage.getContentResource().adaptTo(ValueMap.class);
								if (valueMap != null) {
									String hideBreadcrum = null;
									if (valueMap.containsKey(WyndhamConstants.BC_HIDE_PROPERTY)) {
										hideBreadcrum = valueMap.get(WyndhamConstants.BC_HIDE_PROPERTY, String.class);
									}
									if (hideBreadcrum == null || hideBreadcrum.equals("false")) {
									/*if (!(valueMap.containsKey(WyndhamConstants.BC_HIDE_PROPERTY) &&
											valueMap.get(WyndhamConstants.BC_HIDE_PROPERTY, String.class) != null &&
											valueMap.get(WyndhamConstants.BC_HIDE_PROPERTY, String.class).equals("true"))) {*/
										entity.setTitle(WyndhamUtils.getPageTitle(childPage));
										entity.setLink(commonService.processLinkPath(resolver, request, childPage.getPath()));
										entityList.add(entity);
									}
								}
							}
						}
					}
				}
			}
		}
		breadCrumbNav = entityList;

	}

	public List<NavigationEntity> getBreadCrumbNav() {
		return new ArrayList<>(breadCrumbNav);
	}
}
