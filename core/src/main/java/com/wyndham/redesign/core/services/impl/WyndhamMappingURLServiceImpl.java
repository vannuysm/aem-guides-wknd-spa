package com.wyndham.redesign.core.services.impl;

import com.day.cq.wcm.api.PageManager;
import com.wyndham.baseapp.core.utils.WyndhamUtil;
import com.wyndham.common.WyndhamConstants;
import com.wyndham.redesign.core.services.CommonService;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@Designate(ocd=WyndhamMappingURLServiceImpl.Config.class)
@Component(service=WyndhamMappingURLServiceImpl.class,immediate=true)
public class WyndhamMappingURLServiceImpl {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Reference
	private CommonService commonService;

	@ObjectClassDefinition(name="Wyndham Resource Mapping Service",
			description = "OSGI configuration service for Wyndham.")
	public static @interface Config {
		@AttributeDefinition(name = "Resource Mapping Urls",
				description = "List of mappings to apply to paths. Incoming mappings are applied to request paths to map to resource paths, "
						+ "outgoing mappings are applied to map resource paths to paths used on subsequent requests. "
						+ "Form is <internalPathPrefix><op><externalPathPrefix> where <op> is '>' for incoming mappings, "
						+ "'<' for outgoing mappings and ':' for mappings applied in both directions. Mappings are applied in configuration order by comparing and replacing URL prefixes. "
						+ "Note: The use of '-' as the <op> value indicating a mapping in both directions is deprecated.")
		String[] resource_maps() default "";
	}

	private String[] urls;

	@Activate
	protected void activate(final Config config) {
		this.urls = config.resource_maps();
	}

	public String getShortenURL( String pagePath){
		String shortenURL = StringUtils.EMPTY;
		String path = pagePath;
		List<String> urlList = Arrays.asList(urls);
		while (path.contains(WyndhamConstants.PATH_SEPERATOR)) {
			path = path.substring(0, path.lastIndexOf(WyndhamConstants.PATH_SEPERATOR));
			for (String map: urlList) {
				if (map.startsWith(path)) {
					String[] mapReplacement = map.split(":");
					return pagePath.replaceAll(mapReplacement[0], mapReplacement[1]);
				}
			}
		}
		return shortenURL;
	}

	public String getShortenURL(String pagePath, PageManager pageManager) {
        // Do not shorten URLs on AUTHOR instance
        if (! commonService.isPublishInstance()) {
            return WyndhamUtil.getInternalPageUrl(pagePath, pageManager);
        }
        // Defer to original implementation
        return getShortenURL(pagePath);
    }

	public String[] getUrls() {
		return urls.clone();
	}

}
