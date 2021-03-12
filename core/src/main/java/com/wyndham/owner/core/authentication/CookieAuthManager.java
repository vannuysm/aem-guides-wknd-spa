package com.wyndham.owner.core.authentication;

import org.apache.sling.auth.core.spi.AuthenticationInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CookieAuthManager {
    OwnerCookieData extractCredentials(HttpServletRequest request);
    void create(HttpServletRequest request, HttpServletResponse response, AuthenticationInfo authInfo);
    void clear(HttpServletRequest request, HttpServletResponse response, String cookieDomain);
}
