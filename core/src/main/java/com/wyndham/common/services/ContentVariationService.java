package com.wyndham.common.services;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

public interface ContentVariationService {
    Resource getVariation(SlingHttpServletRequest httpRequest, Resource resource);
}
