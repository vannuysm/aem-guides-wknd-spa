package com.wyndham.owner.core.authentication;

import org.apache.commons.codec.binary.Base64;
import org.apache.sling.auth.core.spi.AuthenticationInfo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class CookieAuthManagerImpl implements CookieAuthManager {
    private static final String WYNDHAM_AUTH_COOKIE = "owner-login";
    private static final String FIELD_DELIMITER = "^!";
    private static final String FIELD_DELIMITER_REGEX = "\\^\\!";
    private static final String BASE64_CHARSET = "UTF-8";

    @Override
    public OwnerCookieData extractCredentials(HttpServletRequest request) {
        Cookie credCookie = getWyndhamAuthCookie(request);
        if (credCookie != null) {
            String encodedCreds = credCookie.getValue();
            if (encodedCreds.length() > 0) {
                try {
                    String creds = new String(Base64.decodeBase64(encodedCreds), BASE64_CHARSET);
                    String[] pieces = creds.split(FIELD_DELIMITER_REGEX);
                    if (pieces != null && pieces.length == 3) {
                        long cookieCreationTime;
                        try {
                            cookieCreationTime = Long.parseLong(pieces[2]);
                        } catch (NumberFormatException e) {
                            cookieCreationTime = System.currentTimeMillis();
                        }
                        return new OwnerCookieData(pieces[0], pieces[1], cookieCreationTime);
                    }
                } catch (UnsupportedEncodingException e) {
                    throw new CookieRuntimeException(e);
                }
            }
        }
        return null;
    }

    @Override
    public void create(HttpServletRequest request, HttpServletResponse response, AuthenticationInfo authInfo) {
        List<String> values = Arrays.asList(
                authInfo.getUser(),
                (String) authInfo.get(AuthHandlerConstants.AUTH_INFO_API_RESPONSE_JSON),
                Long.toString(System.currentTimeMillis()) // creation date of the cookie
        );
        String cookieValue = String.join(FIELD_DELIMITER, values);
        String encodedCookieValue;
        try {
            encodedCookieValue = Base64.encodeBase64URLSafeString(cookieValue.getBytes(BASE64_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new CookieRuntimeException(e);
        }
        setCookie(request, response, WYNDHAM_AUTH_COOKIE, encodedCookieValue, (int) authInfo.get(AuthHandlerConstants.AUTH_INFO_SESSION_TIMEOUT_KEY),
                (String) authInfo.get(AuthHandlerConstants.AUTH_INFO_COOKIE_DOMAIN_KEY));
    }

    @Override
    public void clear(HttpServletRequest request, HttpServletResponse response, String cookieDomain) {
        Cookie clearMe = getWyndhamAuthCookie(request);
        if (clearMe != null) {
            setCookie(request, response, WYNDHAM_AUTH_COOKIE, "", 0, cookieDomain);
        }
    }

    private Cookie getWyndhamAuthCookie(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(CookieAuthManagerImpl.WYNDHAM_AUTH_COOKIE)) {
                return cookie;
            }
        }

        return null;
    }

    private void setCookie(final HttpServletRequest request, final HttpServletResponse response, final String name,
                           final String value, final int age, final String domain) {

        final String ctxPath = request.getContextPath();
        final String cookiePath = (ctxPath == null || ctxPath.length() == 0) ? "/" : ctxPath;

        Cookie cookie = new Cookie(name, String.valueOf(value));
        cookie.setMaxAge(age);
        cookie.setPath(cookiePath);
        cookie.setSecure(request.isSecure());
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }

    private class CookieRuntimeException extends RuntimeException {
        static final long serialVersionUID = -101L;
        public CookieRuntimeException(UnsupportedEncodingException e) {
            super("Unsupported UTF-8 encoding", e);
        }
    }
}

