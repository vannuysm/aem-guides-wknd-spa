package com.wyndham.common.services.impl;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.VariationDef;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.wyndham.baseapp.core.configuration.SiteConfiguration;
import com.wyndham.common.services.ContentVariationService;
import com.wyndham.common.services.WyndhamHttpService;
import com.wyndham.owner.core.authentication.CookieAuthManager;
import com.wyndham.owner.core.authentication.CookieAuthManagerImpl;
import com.wyndham.owner.core.authentication.OwnerCookieData;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.wyndham.common.WyndhamConstants.HTTP_SESSION_MEMBERSHIP_NAME;

@Component(service = ContentVariationService.class)
public class ContentVariationServiceImpl implements ContentVariationService {
    private static final Map<String, String> MEMBERSHIP_TIER_MAP = new ImmutableMap.Builder<String, String>()
        .put("CLUB WYNDHAM PLUS BRONZE VIP", "cw-bronze")
        .put("CLUB WYNDHAM PLUS SILVER VIP", "cw-silver")
        .put("CLUB WYNDHAM PLUS GOLD VIP", "cw-gold")
        .put("CLUB WYNDHAM PLUS PLATINUM VIP", "cw-platinum")
        .put("CLUB WYNDHAM PLUS FOUNDERS VIP", "cw-founders")
        .build();

    private static final Map<String, String> MEMBERSHIP_TYPE_MAP = new ImmutableMap.Builder<String, String>()
        .put("Margaritaville Vacation Club", "margaritaville")
        .put("CLUB WYNDHAM Presidential Reserve", "presidentialReserve")
        .put("CLUB WYNDHAM Discovery VIP", "discoveryVIP")
        .put("CLUB WYNDHAM Discovery Plus", "discoveryPlus")
        .put("CLUB WYNDHAM Discovery", "discovery")
        .put("CLUB WYNDHAM Access", "access")
        .put("CLUB WYNDHAM Select", "select")
        .build();

    @Reference
    private WyndhamHttpService httpService;

    @Reference
    private SiteConfiguration siteConfiguration;

    private final CookieAuthManager cookieManager = new CookieAuthManagerImpl();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Resource getVariation(SlingHttpServletRequest httpRequest, Resource resource) {
        if (resource == null) {
            logger.info("Cannot determine content variation, resource is null.");
            return null;
        }

        if (httpRequest == null) {
            logger.info("Cannot determine content variation, httpRequest is null.");
            return resource;
        }

        logger.info("Finding variation for resource: " + resource.getPath());

        OwnerCookieData credentials = cookieManager.extractCredentials(httpRequest);
        ResourceResolver resourceResolver = httpRequest.getResourceResolver();

        if (credentials == null) {
            logger.info("Credentials not found, returning the master variant.");
            return findVariation(resourceResolver, "master", null, resource);
        }

        logger.info("Credentials found for user: " + credentials.getUsername());

        Gson gson = new GsonBuilder().create();
        LoginApiResponse loginApiResponse = gson.fromJson(
            credentials.getApiResponse(),
            LoginApiResponse.class
        );

        logger.info("Found audience types: " + loginApiResponse.getAudienceTypes());

        HttpSession httpSession = httpRequest.getSession();
        String membershipTier;
        if (httpSession.getAttribute(HTTP_SESSION_MEMBERSHIP_NAME) == null) {
            logger.info("Membership information not found in session, calling Membership API.");
            membershipTier = getMembershipTier(loginApiResponse);

            if (membershipTier == null) {
                logger.info("Membership information from API is null, returning master variant.");
                return findVariation(resourceResolver, "master", null, resource);
            }

            logger.info("Found matching membership tier: " + membershipTier);
            httpSession.setAttribute(HTTP_SESSION_MEMBERSHIP_NAME, membershipTier);
        } else {
            membershipTier = httpSession.getAttribute(HTTP_SESSION_MEMBERSHIP_NAME).toString();
            logger.info("Membership information found in session: " + membershipTier);
        }

        String membershipType = getMembershipType(loginApiResponse);
        return findVariation(resourceResolver, membershipTier, membershipType, resource);
    }

    private Resource findVariation(ResourceResolver resourceResolver, String membershipTier, String membershipType, Resource parent) {
        String contentPath = parent.getPath();
        logger.info("Finding variations for content path: " + contentPath);
        String variationPrefix = membershipTier;

        if (membershipType != null) {
            variationPrefix += "-" + membershipType;
        }

        ContentFragment contentFragment = parent.adaptTo(ContentFragment.class);
        List<VariationDef> contentFragmentVariations = new ArrayList<>();
        if (contentFragment != null) {
            contentFragment.listAllVariations().forEachRemaining(contentFragmentVariations::add);
            contentPath += "/jcr:content/data";
        }

        logger.info("Initial variation prefix: " + variationPrefix);
        String[] variationPathElements = variationPrefix.split("-");
        for (int i = variationPathElements.length; i > 0; i--) {
            Stream<String> variationsStream = Arrays.stream(variationPathElements);
            String currentVariationPath = variationsStream.limit(i).collect(Collectors.joining("-"));
            Resource resource;

            if (contentFragment != null && !contentFragmentVariations.isEmpty()) {
                Optional<VariationDef> variation = getContentFragmentVariation(contentFragmentVariations, currentVariationPath);
                if (variation.isPresent()) {
                    currentVariationPath = variation.get().getName();
                }
            }

            try {
                String variationPath = contentPath + "/" + currentVariationPath;
                logger.info("Looking for variation at path: " + variationPath);
                resource = resourceResolver.getResource(variationPath);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }

            if (resource != null) {
                logger.info("Found variation at path: " + resource.getPath());
                return resource;
            }
        }

        logger.info("No variations found, returning master");
        return resourceResolver.getResource(contentPath + "/master");
    }

    private Optional<VariationDef> getContentFragmentVariation(List<VariationDef> contentFragmentVariations, String currentVariationPath) {
        return contentFragmentVariations.stream()
            .filter(variation -> variation.getTitle().equalsIgnoreCase(currentVariationPath))
            .findFirst();
    }

    private String getMembershipType(LoginApiResponse loginApiResponse) {
        for (Map.Entry<String, String> membershipType : MEMBERSHIP_TYPE_MAP.entrySet()) {
            boolean membershipTypeMatches = loginApiResponse.getAudienceTypes().stream().anyMatch(
                audienceType -> audienceType.getAudienceType().equalsIgnoreCase(membershipType.getKey())
            );

            if (membershipTypeMatches) {
                logger.info("Membership type match found: " + membershipType.getValue());
                return membershipType.getValue();
            }
        }

        logger.info("Membership type match not found, returning null.");
        return null;
    }

    private String getMembershipTier(LoginApiResponse loginApiResponse) {
        String membershipApiUrl;

        try {
            String encodedSessionId = URLEncoder.encode(loginApiResponse.getSessionId(), "UTF-8")
                .replaceAll("\\+", "%20")
                .replaceAll("%21", "!")
                .replaceAll("%27", "'")
                .replaceAll("%28", "(")
                .replaceAll("%29", ")")
                .replaceAll("%7E", "~");

            membershipApiUrl = String.format(
                "https://%s/api/customer/%s/memberships?excludeInactive=true&partyId=%s&sessionId=%s",
                siteConfiguration.getApiHostname(),
                loginApiResponse.getId(),
                loginApiResponse.getId(),
                encodedSessionId
            );

            logger.info("Calling Membership API at URL: " + membershipApiUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        WyndhamHttpService.WyndhamHttpResponse apiResponse = httpService.makeGetRequest(
            membershipApiUrl,
            ImmutableMap.of("X-UUID", loginApiResponse.getUid()),
            false
        );

        if (apiResponse.getStatusCode() < 200 || apiResponse.getStatusCode() > 299) {
            logger.info("Membership API call failed: " + apiResponse.getStatusCode());
            return null;
        }

        try {
            String membershipName = new GsonBuilder().create()
                .fromJson(apiResponse.getResponseData(), JsonObject.class)
                .getAsJsonArray("memberships").get(0).getAsJsonObject()
                .get("clubMembershipTier").getAsJsonObject()
                .get("name").getAsString();

            logger.info("Membership API returned member name: " + membershipName);
            return MEMBERSHIP_TIER_MAP.getOrDefault(membershipName, "cw");
        } catch (Exception ex) {
            return "cw";
        }
    }

    private static class LoginApiResponse {
        private String id;
        private String sessionId;
        private String uid;
        private List<AudienceType> audienceTypes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public List<AudienceType> getAudienceTypes() {
            return audienceTypes;
        }

        public void setAudienceTypes(List<AudienceType> audienceTypes) {
            this.audienceTypes = audienceTypes;
        }

        public String getUid() {
            return uid;
        }

        private static class AudienceType {
            private String audienceType;

            public String getAudienceType() {
                return audienceType;
            }

            public void setAudienceType(String audienceType) {
                this.audienceType = audienceType;
            }
        }
    }
}
