package com.wyndham.common.services.impl;

import com.wyndham.common.WyndhamConstants;
import com.wyndham.common.services.WyndhamHttpService;
import com.wyndham.redesign.core.exception.BaseException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.servlets.HttpConstants;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @Type Interface Impl Class
 * This is a http request impl class include some common functions
 *
 */
@Component(service = WyndhamHttpService.class,immediate=true)
public class WyndhamHttpServiceImpl implements WyndhamHttpService{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/* (non-Javadoc)
	 * @see com.wyndham.redesign.core.services.WyndhamHttpService#getRequest(java.lang.String)
	 */
	@Override
	public String getRequest(String requestUrl) {
		String responseStr = "";
		InputStream stream = getInputStreamByGetRequest(requestUrl);
		if(stream!=null){
			BufferedReader br = new BufferedReader(new InputStreamReader((stream)));
	        String output;
	        try {
				while ((output = br.readLine()) != null) {
					responseStr+=output;
				}
				br.close();
			} catch (IOException e) {
				BaseException.logException(e, logger, e.toString());
			}
		}
		return responseStr;
	}
	
	/* (non-Javadoc)
	 * @see com.wyndham.redesign.core.services.WyndhamHttpService#getJsonPostRequest(java.lang.String)
	 */
	@Override
	public JsonObject getJsonPostRequest(String requestUrl, JsonObject postJson, Map<String,String> headers) {
		WyndhamHttpResponse response = makePostRequest(requestUrl, postJson, headers);
		JsonObject jsonObject = new JsonObject();
		
		if(StringUtils.isNotBlank(response.getResponseData())) {
			jsonObject = new JsonParser().parse(response.getResponseData()).getAsJsonObject();
		}
		
		return jsonObject;
	}
	
	/* (non-Javadoc)
	 * @see com.wyndham.redesign.core.services.WyndhamHttpService#getInputStreamByGetRequest(java.lang.String)
	 */
	@Override
	public InputStream getInputStreamByGetRequest(String requestUrl) {
		InputStream stream = null;
		logger.info("Http request about jobs XML data. Request URL: "+requestUrl);
		logger.debug("Http request about jobs XML data. Request URL: "+requestUrl);
		HttpURLConnection connection = null;

        try {
        	final URL url = new URL(requestUrl);
        	connection = (HttpURLConnection) url.openConnection();

			// Set timeout of 50s
			connection.setConnectTimeout(50000);
			connection.setReadTimeout(50000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod(HttpConstants.METHOD_GET);

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				stream = connection.getInputStream();
			}

		} catch (IOException e) {
			BaseException.logException(e, logger, e.getMessage());
		} finally {
        	if (connection != null) {
				connection.disconnect();
			}
		}
		return stream;
	}
	
	/* (non-Javadoc)
	 * @see com.wyndham.redesign.core.services.WyndhamHttpService#getInputStreamByPostRequest(java.lang.String)
	 */
	@Override
	public WyndhamHttpResponse makePostRequest(String requestUrl, JsonObject postJson, Map<String,String> headers) {
		return makePostRequest(requestUrl, postJson, headers, true);
	}

	/* (non-Javadoc)
	 * @see com.wyndham.redesign.core.services.WyndhamHttpService#getInputStreamByPostRequest(java.lang.String)
	 */
	@Override
	public WyndhamHttpResponse makePostRequest(String requestUrl, JsonObject postJson, Map<String,String> headers, boolean returnOnlySuccess) {
		WyndhamHttpResponse response = WyndhamHttpResponse.FAILURE_RESPONSE();
		InputStream stream = null;
		HttpURLConnection connection = null;
		String responseStr = "";

        try {
        	final URL url = new URL(requestUrl);
        	connection = (HttpURLConnection) url.openConnection();

			// Set timeout of 50s
			connection.setConnectTimeout(50000);
			connection.setReadTimeout(50000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", WyndhamConstants.HTTP_CONTENT_TYPE_JSON);
			connection.setRequestProperty("Accept", WyndhamConstants.HTTP_CONTENT_TYPE_JSON);
			connection.setRequestMethod(HttpConstants.METHOD_POST);
			
			//add headers if available
			if(headers != null && !headers.isEmpty()){
				for (Map.Entry<String,String> headersEntry : headers.entrySet()) {
					connection.setRequestProperty( headersEntry.getKey(), headersEntry.getValue());
				}
			}
			
			OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
			wr.write(postJson.toString());
			wr.flush();
			String uuidHeader = "";
				
			if (!returnOnlySuccess || connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				if (connection.getResponseCode() >= HttpURLConnection.HTTP_OK && connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
					stream = connection.getInputStream();
					uuidHeader = connection.getHeaderField("X-UUID");
					logger.debug("X-UUID = uuidHeader :: " + uuidHeader);
				} else {
					stream = connection.getErrorStream();
				}
				if (stream != null) {
					BufferedReader br = new BufferedReader(new InputStreamReader((stream)));
					String output;
					try {
						while ((output = br.readLine()) != null) {
							responseStr += output;
						}
						br.close();
					} catch (IOException e) {
						logger.error("Error reading stream from :: "+ requestUrl + " :: ", e);
					}
				}
				
				if(StringUtils.isNotBlank(uuidHeader) && StringUtils.isNotBlank(responseStr)) {
					try {
						JSONObject responseJson = new JSONObject(responseStr);
						responseJson.put("uid", uuidHeader);
						responseStr = responseJson.toString();
						logger.debug("responseStr w/ uuidHeader :: " + responseStr);
					} catch (JSONException e) {
						//Do nothing if JSON isnt returned from login
					}
				}
			}

			response = new WyndhamHttpResponse(connection.getResponseCode(), responseStr);

		} catch (IOException e) {
			logger.error("Error connecting to :: "+ requestUrl + " :: ", e);
		} finally {
        	if (connection != null) {
				connection.disconnect();
			}
		}
		return response;
	}

	/* (non-Javadoc)
	 * @see com.wyndham.redesign.core.services.WyndhamHttpService#getInputStreamByPostRequest(java.lang.String)
	 */
	@Override
	public WyndhamHttpResponse makeGetRequest(String requestUrl, Map<String,String> headers, boolean returnOnlySuccess) {
		WyndhamHttpResponse response = WyndhamHttpResponse.FAILURE_RESPONSE();
		InputStream stream = null;
		HttpURLConnection connection = null;
		String responseStr = "";

        try {
        	final URL url = new URL(requestUrl);
        	connection = (HttpURLConnection) url.openConnection();

			// Set timeout of 50s
			connection.setConnectTimeout(50000);
			connection.setReadTimeout(50000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", WyndhamConstants.HTTP_CONTENT_TYPE_JSON);
			connection.setRequestProperty("Accept", WyndhamConstants.HTTP_CONTENT_TYPE_JSON);
			connection.setRequestMethod(HttpConstants.METHOD_GET);
			
			//add headers if available
			if(headers != null && !headers.isEmpty()){
				for (Map.Entry<String,String> headersEntry : headers.entrySet()) {
					connection.setRequestProperty( headersEntry.getKey(), headersEntry.getValue());
				}
			}
			
			//OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
		//	wr.write(postJson.toString());
		//	wr.flush();

			if (!returnOnlySuccess || connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				if (connection.getResponseCode() >= HttpURLConnection.HTTP_OK && connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
					stream = connection.getInputStream();
				} else {
					stream = connection.getErrorStream();
				}
				if (stream != null) {
					BufferedReader br = new BufferedReader(new InputStreamReader((stream)));
					String output;
					try {
						while ((output = br.readLine()) != null) {
							responseStr += output;
						}
						br.close();
					} catch (IOException e) {
						logger.error("Error reading stream from :: "+ requestUrl + " :: ", e);
					}
				}
			}

			response = new WyndhamHttpResponse(connection.getResponseCode(), responseStr);

		} catch (IOException e) {
			logger.error("Error connecting to :: "+ requestUrl + " :: ", e);
		} finally {
        	if (connection != null) {
				connection.disconnect();
			}
		}
		return response;
	}

}
