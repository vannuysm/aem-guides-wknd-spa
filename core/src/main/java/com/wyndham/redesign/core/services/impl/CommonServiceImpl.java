package com.wyndham.redesign.core.services.impl;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.wyndham.common.WyndhamConstants;
import com.wyndham.common.WyndhamUtils;
import com.wyndham.redesign.core.entity.NavigationEntity;
import com.wyndham.redesign.core.exception.BaseException;
import com.wyndham.redesign.core.models.ArticleModel;
import com.wyndham.redesign.core.models.CardModel;
import com.wyndham.redesign.core.models.DynamicDataModel;
import com.wyndham.redesign.core.models.LinkModel;
import com.wyndham.redesign.core.services.CommonService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Type Interface Impl Class
 * This is a common service impl class include some common functions about AEM
 *
 */
@Component(service = CommonService.class,immediate=true)
public class CommonServiceImpl implements CommonService{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Reference
	ResourceResolverFactory resourceResolverFactory;

	@Reference
    private SlingSettingsService slingSettingsService;

	@Reference
	private Replicator replicator;

	@Reference
	SlingSettingsService settingsService;

	/**
	 * subservice name in AEM
	 */
	private final String ADMINUSER_NAME = "adminuser";
	/**
	 * jcr:title rel path under the page
	 */
	private String relPath = JcrConstants.JCR_CONTENT+WyndhamConstants.PATH_SEPERATOR+JcrConstants.JCR_TITLE;
	/**
	 * hideInNav rel path under the page
	 */
	private String hideNavPath = JcrConstants.JCR_CONTENT+WyndhamConstants.PATH_SEPERATOR+WyndhamConstants.HIDE_IN_NAV;

	private Boolean isPublish = true;

	@Activate
	protected void activate(){
		logger.debug("## Inside the activation ");
		isPublish = this.slingSettingsService.getRunModes().contains("publish");
		logger.debug("## WYN100 env is publish? "+isPublish);
	}

	@Override
	public ResourceResolver getAdminResourceResolver(){
		ResourceResolver resourceResolver = null;
		try {
			  resourceResolver = WyndhamUtils
	            		.getDefaultServiceResourceResolver(resourceResolverFactory, ADMINUSER_NAME);
		} catch (LoginException e) {
			BaseException.logException(e, logger, e.getMessage());
		}
		return resourceResolver;
	}


	@Override
	public Session getAdminSession(ResourceResolver resourceResolver){
		Session session = null;
		if(resourceResolver != null){
			session = resourceResolver.adaptTo(Session.class);
		}
		if(session == null){
			logger.info("Session is null getting by system user.");
		}
		return session;
	}

	/* (non-Javadoc)
	 * @see com.wyndham.redesign.core.services.CommonService#getNavigation(org.apache.sling.api.resource.ResourceResolver, java.lang.String, boolean, boolean, int)
	 */
	@Override
	public NavigationEntity getNavigation(ResourceResolver resolver, HttpServletRequest request, String navigationRoot,boolean skipNavigationRoot, boolean collectAllPages,int structureDepth){
		NavigationEntity navigation = new NavigationEntity();
		//get navigation using root path,depth and other limits
		Session session = resolver.adaptTo(Session.class);
		if(session==null){
			return navigation;
		}
		try {
			int currentDepth = 0;
			Node node = session.getNode(navigationRoot);
			navigation = getNavigationItems(resolver, request, node,skipNavigationRoot,collectAllPages,structureDepth,currentDepth,navigation);
		} catch (RepositoryException e) {
			BaseException.logException(e, logger, e.getMessage());
		}
		return navigation;
	}

	/**
	 * This function will get the global navigation list
	 * @param node
	 * @param skipNavigationRoot
	 * @param collectAllPages
	 * @param structureDepth
	 * @param currentDepth
	 * @param entity
	 * @return NavigationEntity
	 */
	private NavigationEntity getNavigationItems(ResourceResolver resolver, HttpServletRequest request, Node node,boolean skipNavigationRoot, boolean collectAllPages,int structureDepth,int currentDepth,NavigationEntity entity){
		if(currentDepth>structureDepth){
			return entity;
		}
		currentDepth++;
		try{
			if(!node.hasProperty(hideNavPath)){
				if(currentDepth == 1 && skipNavigationRoot){
					if(collectAllPages){
						if(node.hasNodes()){
							List<NavigationEntity> childEntityList = new ArrayList<>();
							addNavigationItem(resolver, request, node, skipNavigationRoot, collectAllPages, structureDepth, currentDepth,
									entity, childEntityList);
						}
					}else{
						logger.info("skipNavigationRoot: "+skipNavigationRoot+" collectAllPages:"+collectAllPages);
						return entity;
					}
				}else if(!skipNavigationRoot && !collectAllPages){
					String link = node.getPath();
					String title = node.getName();
					if(node.hasProperty(relPath)){
						title = node.getProperty(relPath).getString();
					}
					entity.setLink(processLinkPath(resolver, request, link));
					entity.setTitle(title);
				}else{
					String link = node.getPath();
					String title = node.getName();
					if(node.hasNodes()){
						List<NavigationEntity> childEntityList = new ArrayList<>();
						if(node.hasProperty(relPath)){
							title = node.getProperty(relPath).getString();
						}
						addNavigationItem(resolver, request, node, skipNavigationRoot, collectAllPages, structureDepth, currentDepth, entity,
								childEntityList);
					}
					entity.setLink(processLinkPath(resolver, request, link));
					entity.setTitle(title);
				}
			}
		} catch (RepositoryException e) {
			BaseException.logException(e, logger, e.getMessage());
		}
		return entity;
	}

	/**
	 * This function will add the navigation item
	 * @param node
	 * @param skipNavigationRoot
	 * @param collectAllPages
	 * @param structureDepth
	 * @param currentDepth
	 * @param entity
	 * @param childEntityList
	 * @throws RepositoryException
	 */
	private void addNavigationItem(ResourceResolver resolver, HttpServletRequest request, Node node, boolean skipNavigationRoot, boolean collectAllPages, int structureDepth,
			int currentDepth, NavigationEntity entity, List<NavigationEntity> childEntityList)
					throws RepositoryException {
		NodeIterator iterator = node.getNodes();
		while(iterator.hasNext()){
			Node childNode = iterator.nextNode();
			if(childNode.hasNode(JcrConstants.JCR_CONTENT)&&!childNode.hasProperty(hideNavPath)){
				NavigationEntity childEntity = new NavigationEntity();
				childEntity = getNavigationItems(resolver, request, childNode,skipNavigationRoot,collectAllPages,structureDepth,currentDepth,childEntity);
				childEntityList.add(childEntity);
			}
		}
		entity.setNavList(childEntityList);
	}


	/* (non-Javadoc)
	 * @see com.wyndham.redesign.core.services.CommonService#isPublishInstance()
	 */
	@Override
	public Boolean isPublishInstance() {
		return isPublish;
	}

	/* (non-Javadoc)
	 * @see com.wyndham.redesign.core.services.CommonService#replicateContentNode(javax.jcr.Session, java.lang.String, com.day.cq.replication.ReplicationActionType)
	 */
	@Override
	public void replicateContentNode(Session session, String path, ReplicationActionType type) {
		try{
			replicator.replicate(session, type, path);
			logger.debug(type.toString()+" with the path: "+path);
		} catch (ReplicationException e) {
			BaseException.logException(e, logger, e.getMessage());
		}
	}

	public String processLinkPath(ResourceResolver resolver, HttpServletRequest request, String path){
		if (resolver != null && StringUtils.isNotEmpty(path) && !path.startsWith(WyndhamConstants.HTTP) && !path.equals("#")){
			if(!path.endsWith(WyndhamConstants.URL_SUFFIX)){
				path = StringUtils.stripEnd(path, WyndhamConstants.PATH_SEPERATOR);
				if(path.startsWith(WyndhamConstants.PATH_SEPERATOR)){
					path = resolver.map(request, path);
				}
				if (!isPublish) {
					path = path + WyndhamConstants.URL_SUFFIX;
				}
			}
		}
		return path;
	}

	public List<ArticleModel> processArticleModelLinksPath(ResourceResolver resolver, HttpServletRequest request, List<ArticleModel> models){
		List<ArticleModel> modelList = new ArrayList<ArticleModel>();
		if (models != null){
			for (ArticleModel model : models){
				if (model.getLinkPath() != null ){
					model.setLinkPath(processLinkPath(resolver, request, model.getLinkPath()));
				}
				if (model.getExtLinkPath() != null){
					model.setExtLinkPath(processLinkPath(resolver, request, model.getExtLinkPath()));
				}
				if (model.getLinks() != null){
					model.setLinks(processLinkModelListLinkPath(resolver, request, model.getLinks()));
				}
				modelList.add(model);
			}
		}
		return modelList;
	}

	@Override
	public void processCardModelLinksPath(ResourceResolver resolver, HttpServletRequest request, List<CardModel> models) {
		if (models != null) {
			for (CardModel model : models) {
				if (model.getLinkPath() != null) {
					model.setLinkPath(processLinkPath(resolver, request, model.getLinkPath()));
				}
			}
		}
	}

	public List<DynamicDataModel> processDynamicDataModelLinksPath(ResourceResolver resolver, HttpServletRequest request, List<DynamicDataModel> models){
		List<DynamicDataModel> modelList = new ArrayList<DynamicDataModel>();
		if (models != null){
			for (DynamicDataModel model : models){
				if (model.getLinkPath() != null ){
					model.setLinkPath(processLinkPath(resolver, request, model.getLinkPath()));
				}
				modelList.add(model);
			}
		}
		return modelList;
	}

	public List<LinkModel> processLinkModelListLinkPath(ResourceResolver resolver, HttpServletRequest request, List<LinkModel> models){

		List<LinkModel> linkModelList = new ArrayList<LinkModel>();
		for (LinkModel linkModel : models){
			if(linkModel.getLinkPath() != null){
				linkModel.setLinkPath(processLinkPath(resolver, request, linkModel.getLinkPath()));
			}
			linkModelList.add(linkModel);
		}

		return linkModelList;
	}
}
