package com.wyndham.owner.core.authentication;

public interface AuthHandlerConstants {

    static final String REQUEST_METHOD_POST = "POST";

    static final String REQUEST_HEADER_CLIENT_IP = "True-Client-IP";
    static final String REQUEST_HEADER_BROWSER_PROVIDED_IP = "X-Wyn-Client-IP";

    static final String TIMEOUT = "Session timeout.";
    static final String RESOURCE_REQUIRES_AUTHENTICATION = "Resource requires authentication.";

    static final String PAR_LOOP_PROTECT = "$$login$$";

    static final String OWNER_CUSTOM_AUTH_PID = "com.wyndham.owner.core.authentication.OwnerCustomAuthenticationHandler";
    static final String OWNER_CONFIG_PATH = "path";
    
    static final String OWNER_CONFIG_API_URL = "formAuthApiUrl";
    static final String OWNER_CONFIG_USER_CONFIG_JSON = "userConfigurationsJson";
    static final String OWNER_CONFIG_REDIRECT_LOGIN_PATH = "redirectLoginPath";

    static final String AUTH_INFO_API_RESPONSE_JSON = "wyndham.api.response";
    static final String AUTH_INFO_API_RESPONSE_STATUS = "wyndham.api.response.status";
    static final String AUTH_INFO_COOKIE_DOMAIN_KEY = "wyndham.domain";
    static final String AUTH_INFO_SESSION_TIMEOUT_KEY = "wyndham.session.timeout";
    static final String AUTH_INFO_COOKIE_CONTENTS = "wyndham.session.cookie";

    static final String API_USERNAME_FIELD_NAME = "username";
    static final String API_STATUS_FIELD_NAME = "status";
    static final String API_DESCRIPTION_FIELD_NAME = "description";
    static final String API_AUDIENCETYPES_FIELD_NAME = "audienceTypes";
    static final String API_AUDIENCETYPE_FIELD_NAME = "audienceType";
    static final String API_PARTY_ID_FIELD_NAME = "id";

    static final String API_STATUS_OK = "OK";

    static final String API_UNKNOWN_FAILURE_MESSAGE = "Unknown failure.";

    static final String REQUEST_URL_SUFFIX = "/j_security_check";
    static final String SUCCESS = "SUCCESS";
    static final String FAILURE = "FAILURE";
}

