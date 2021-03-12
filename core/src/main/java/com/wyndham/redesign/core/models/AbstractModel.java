package com.wyndham.redesign.core.models;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.wyndham.redesign.core.services.CommonService;

/**
 * @Type Abstract Class
 * This is an abstract sling model class for some models if need reference some common interfaces
 *
 */
@Model(
		adaptables = {Resource.class, SlingHttpServletRequest.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public abstract class AbstractModel extends AbstractModelResource{
	
	 @SlingObject
	 ResourceResolver resolver;

	 @Inject
	 @Via("resource")
	 Resource resource;

	 @Inject
	 @Via("request")
	 SlingHttpServletRequest request;

	 @OSGiService
	 CommonService commonService;

}
