package com.wyndham.redesign.core.services;

import java.util.List;

import javax.jcr.Session;
import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.replication.ReplicationActionType;
import com.wyndham.redesign.core.entity.NavigationEntity;
import com.wyndham.redesign.core.models.ArticleModel;
import com.wyndham.redesign.core.models.CardModel;
import com.wyndham.redesign.core.models.DynamicDataModel;
import com.wyndham.redesign.core.models.LinkModel;

/**
 * This is a common interface about some common methods
 * @Type Interface
 *
 */
public interface CommonService {

	/**
	 * This function will return a NavigationEntity object by the following parameters
	 * @param resolver
	 * @param navigationRoot
	 * @param skipNavigationRoot
	 * @param collectAllPages
	 * @param structureDepth
	 * @return NavigationEntity
	 */
	NavigationEntity getNavigation(ResourceResolver resolver, HttpServletRequest request, String  navigationRoot, boolean skipNavigationRoot, boolean collectAllPages,
								   int structureDepth);
	
	/**
	 * This function will return a boolean value about the current instance is publish or not
	 * @return boolean
	 */
	Boolean isPublishInstance();
	
	/**
	 * This function will return a ResourceResolver with the system service user
	 * @return ResourceResolver
	 */
	ResourceResolver getAdminResourceResolver();
	
	/**
	 * This function will return a session by the resourceResolver
	 * @param resourceResolver
	 * @return session
	 */
	Session getAdminSession(ResourceResolver resourceResolver);

	/**
	 * This function will activate or deactivate some content in AEM using a session
	 * @param session
	 * @param path
	 * @param type
	 */
	void replicateContentNode(Session session, String path, ReplicationActionType type);

	public String processLinkPath(ResourceResolver resolver, HttpServletRequest request, String path);

	public List<ArticleModel> processArticleModelLinksPath(ResourceResolver resolver, HttpServletRequest request, List<ArticleModel> models);

	public void processCardModelLinksPath(ResourceResolver resolver, HttpServletRequest request, List<CardModel> models);

	public List<LinkModel> processLinkModelListLinkPath(ResourceResolver resolver, HttpServletRequest request, List<LinkModel> models);

	public List<DynamicDataModel> processDynamicDataModelLinksPath(ResourceResolver resolver, HttpServletRequest request, List<DynamicDataModel> models);

}
