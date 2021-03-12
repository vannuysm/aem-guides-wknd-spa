package com.wyndham.baseapp.core.models;

import com.wyndham.baseapp.core.configuration.SiteConfiguration;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class EUMScriptModel {
    private static final Logger log = LoggerFactory.getLogger(EUMScriptModel.class);

    @OSGiService
    SiteConfiguration siteConfiguration;

    private String eumScript;

     @PostConstruct
     private void initModel() {
        eumScript = siteConfiguration.getEumScriptThunk();
     }

    public String getEumScript() {
        return eumScript;
    }
}
