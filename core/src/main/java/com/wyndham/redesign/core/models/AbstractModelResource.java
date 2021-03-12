package com.wyndham.redesign.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(
		adaptables = {Resource.class})
public class AbstractModelResource {
	 @SlingObject
	 ResourceResolver resolver;

	 @SlingObject
	 Resource resource;
}
