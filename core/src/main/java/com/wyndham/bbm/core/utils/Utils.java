package com.wyndham.bbm.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

public final class Utils {
	private Utils() {
		
	}
	
	public static ArrayList<Resource> executeQueryMap(
			QueryBuilder queryBuilder,
			ResourceResolver resourceResolver,
			String pathToQuery,
			String jcrPrimaryType) 
	{
		return executeQueryMap(queryBuilder, resourceResolver, pathToQuery, jcrPrimaryType, true);
	}
	
	
	public static ArrayList<Resource> executeQueryMap(
			QueryBuilder queryBuilder,
			ResourceResolver resourceResolver,
			String pathToQuery,
			String jcrPrimaryType, 
			boolean includeDescendents) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("path", pathToQuery);
		map.put("1_property","jcr:primaryType");
		map.put("1_property.value", jcrPrimaryType);
		map.put("p.guessTotal", "true"); //Suggested always use
		map.put("p.limit","-1"); //Don't limit to 10 results.
		
		if (!includeDescendents) {
			map.put("path.exact", "true");
		}
		
		return executeQueryMap(resourceResolver, queryBuilder, map);		
	}

	public static ArrayList<Resource> executeQueryMap(
			ResourceResolver resourceResolver,
			QueryBuilder queryBuilder,
			Map<String,Object> queryMap) 
	{		
		// ResourceResolver leaks, here's the workaround
		// https://helpx.adobe.com/experience-manager/kb/Unclosed-ResourceResolver-warnng-at-com-day-cq-search-impl-builder-QueryBuilderImpl.html
		// https://github.com/Adobe-Consulting-Services/acs-aem-samples/blob/master/bundle/src/main/java/com/adobe/acs/samples/search/querybuilder/impl/SampleQueryBuilder.java#L195
		
		ResourceResolver leakingResourceResolver = null;
		final ArrayList<Resource> resources = new ArrayList<Resource>();
		
		try {					
			Query query = queryBuilder.createQuery(PredicateGroup.create(queryMap), resourceResolver.adaptTo(Session.class));
			SearchResult result = query.getResult();
			List<Hit> hits = result.getHits();
	       
			for (Hit hit : hits) {
				
				if (leakingResourceResolver == null) {
                   // Get a reference to QB's leaking ResourceResolver
                   leakingResourceResolver = hit.getResource().getResourceResolver();
                }
				
				try {
					resources.add(resourceResolver.getResource(hit.getPath()));					
				} catch (Exception e) {
					e.printStackTrace();
				}
		   	}		
		   
		} catch (Exception e ) {
			e.printStackTrace();
		} finally {
			if (leakingResourceResolver != null) {
                // Always Close the leaking QueryBuilder resourceResolver.
                leakingResourceResolver.close();    
            }     
		}
		
		return resources;
   }
}
