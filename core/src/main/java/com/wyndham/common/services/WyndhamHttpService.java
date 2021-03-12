package com.wyndham.common.services;

import java.io.InputStream;
import java.util.Map;

import com.google.gson.JsonObject;
import org.apache.commons.httpclient.HttpStatus;

/**
 * This is http request interface about some common methods
 *
 */
public interface WyndhamHttpService {
	/**
	 * This function will return the http request response body string
	 *
	 * @param requestUrl
	 * @return String
	 */
	String getRequest(String requestUrl);

	/**
	 * This function will return the input stream about the http request response
	 *
	 * @param requestUrl
	 * @return InputStream
	 */
	InputStream getInputStreamByGetRequest(String requestUrl);

	/**
	 * This function will return the json object form a json post with custom headers
	 *
	 * @param requestUrl
	 * @param postJson
	 * @param headers
	 * @return InputStream
	 */
	JsonObject getJsonPostRequest(String requestUrl, JsonObject postJson, Map<String, String> headers);


	/**
	 * This function will return the input stream from the http request response as a response object
	 *
	 * @param requestUrl
	 * @param postJson
	 * @param headers
	 * @return json string
	 */
	WyndhamHttpResponse makePostRequest(String requestUrl, JsonObject postJson, Map<String, String> headers);


	/**
	 * This function will return the input stream from the http request response as a response object
	 *
	 * @param requestUrl
	 * @param postJson
	 * @param headers
	 * @param returnOnlySuccess The response object will contain @WyndhamHttpResponse.responseData only when the POST request returns HttpStatus.SC_OK
	 * @return json string
	 */
	WyndhamHttpResponse makePostRequest(String requestUrl, JsonObject postJson, Map<String, String> headers, boolean returnOnlySuccess);
	
	WyndhamHttpResponse makeGetRequest(String requestUrl, Map<String,String> headers, boolean returnOnlySuccess);

	/**
	 * Encapsulates the result of an HTTP request containing both the HTTP status code returned and any body data
	 */
	class WyndhamHttpResponse {
		private int statusCode;
		private String responseData;

		public WyndhamHttpResponse(int statusCode, String responseData) {
			this.statusCode = statusCode;
			this.responseData = responseData;
		}

		public static WyndhamHttpResponse FAILURE_RESPONSE() {
			return new WyndhamHttpResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, "");
		}

		public static WyndhamHttpResponse DEFAULT_RESPONSE() {
			return new WyndhamHttpResponse(HttpStatus.SC_OK, "");
		}

		public int getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}

		public String getResponseData() {
			return responseData;
		}

		public void setResponseData(String responseData) {
			this.responseData = responseData;
		}
	}
}
