package com.company.project.api.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.company.project.api.dao.intefaces.GoogleDAO;
import com.company.project.api.entities.google.GoogleAccessToken;
import com.company.project.api.entities.google.GoogleUser;
import com.company.project.api.exception.HttpStatusException;
import com.google.gson.Gson;

@Repository
public class GoogleDAOImpl implements GoogleDAO {
	private static final Logger log = Logger.getLogger(GoogleDAOImpl.class);

	private static final String LONG_ACCESS_TOKEN = "https://accounts.google.com/o/oauth2/token";
	public static final String CLIENT_ID_KEY = "client_id";
	public static final String REDIRECT_URI_KEY = "redirect_uri";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String CODE_KEY = "code";
	public static final String GRANT_TYPE_KEY = "grant_type";
	public static final String AUTH_CODE = "authorization_code";

	private static final String USERS_ME = "https://www.googleapis.com/plus/v1/people/me/openIdConnect";
	private static final String AUTH_HEADER_KEY = "Authorization";

	@Value("${google.secret.key}")
	private String googleAppSecret;

	@Override
	public GoogleAccessToken getAccessTokenFromCode(String code, String clientId, String redirectUri) throws HttpStatusException {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		CloseableHttpClient client = clientBuilder.build();

		GoogleAccessToken accessToken = null;
		try {
			HttpPost request = new HttpPost(LONG_ACCESS_TOKEN);

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair(CLIENT_ID_KEY, clientId));
			urlParameters.add(new BasicNameValuePair(CLIENT_SECRET, googleAppSecret));
			urlParameters.add(new BasicNameValuePair(REDIRECT_URI_KEY, redirectUri));
			urlParameters.add(new BasicNameValuePair(CODE_KEY, code));
			urlParameters.add(new BasicNameValuePair(GRANT_TYPE_KEY, AUTH_CODE));
			request.setEntity(new UrlEncodedFormEntity(urlParameters));

			HttpResponse response = client.execute(request);

			String responseBody = EntityUtils.toString(response.getEntity());
			log.info("User retrieved from Facebook api " + responseBody);

			accessToken = new Gson().fromJson(responseBody, GoogleAccessToken.class);
			// create GoogleErrorUtils.checkErrors(accessToken);

		} catch (IOException e) {
			log.error("An IOException has been thrown when getting Google AccessTokenFromCode. " + e.getMessage());
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				log.error("An IOException has been thrown when closing the HttpClient object. " + e.getMessage());
			}
		}
		return accessToken;
	}

	@Override
	public GoogleUser getMe(String accessToken) throws HttpStatusException {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		CloseableHttpClient client = clientBuilder.build();

		GoogleUser googleUser = null;
		try {
			HttpGet request = new HttpGet(USERS_ME);
			request.addHeader(AUTH_HEADER_KEY, String.format("Bearer %s", accessToken));

			HttpResponse response = client.execute(request);

			String responseBody = EntityUtils.toString(response.getEntity());
			log.info("User retrieved from Facebook api " + responseBody);

			googleUser = new Gson().fromJson(responseBody, GoogleUser.class);
			// create GoogleErrorUtils.checkErrors(accessToken);

		} catch (IOException e) {
			log.error("An IOException has been thrown when getting Google User information. " + e.getMessage());
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				log.error("An IOException has been thrown when closing the HttpClient object. " + e.getMessage());
			}
		}
		return googleUser;
	}
}
