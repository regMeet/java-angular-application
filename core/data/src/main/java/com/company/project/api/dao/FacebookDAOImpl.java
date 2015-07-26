package com.company.project.api.dao;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.company.project.api.dao.intefaces.FacebookDAO;
import com.company.project.api.entities.facebook.FacebookUser;
import com.company.project.api.entities.facebook.FacebookAccessToken;
import com.company.project.api.exception.HttpStatusException;
import com.company.project.api.utils.FacebookErrorUtils;
import com.google.gson.Gson;

/**
 * Handles Facebook api's endpoints
 */
@Repository
public class FacebookDAOImpl implements FacebookDAO {
	private static final Logger log = Logger.getLogger(FacebookDAOImpl.class);
	private static final String API_VERSION = "v2.4";
	private static final String FB_API_URL = "https://graph.facebook.com/" + API_VERSION;

	private static final String LONG_ACCESS_TOKEN = FB_API_URL + "/oauth/access_token";
	public static final String CLIENT_ID_KEY = "client_id";
	public static final String REDIRECT_URI_KEY = "redirect_uri";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String CODE_KEY = "code";

	private static final String USERS_ME = FB_API_URL + "/me";
	private static final String ACCESS_TOKEN_PARAMETER = "access_token";
	private static final String FIELDS_PARAMETER = "fields";
	private static final String ME_FIELDS_VALUES_PARAMETER = "id, name, first_name, last_name, email";

	@Value("${facebook.secret.key}")
	private String facebookAppSecret;

	public FacebookDAOImpl() {
	}

	@Override
	public FacebookAccessToken getAccessTokenFromCode(String code, String clientId, String redirectUri) throws HttpStatusException {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		CloseableHttpClient client = clientBuilder.build();

		FacebookAccessToken accessToken = null;
		try {
			URIBuilder builder = new URIBuilder(LONG_ACCESS_TOKEN).addParameter(CLIENT_ID_KEY, clientId)
					.addParameter(CLIENT_SECRET, facebookAppSecret).addParameter(REDIRECT_URI_KEY, redirectUri).addParameter(CODE_KEY, code);

			HttpGet request = new HttpGet(builder.build());
			HttpResponse response = client.execute(request);

			String responseBody = EntityUtils.toString(response.getEntity());
			log.info("User retrieved from Facebook api " + responseBody);

			accessToken = new Gson().fromJson(responseBody, FacebookAccessToken.class);
			FacebookErrorUtils.checkErrors(accessToken);

		} catch (IOException e) {
			log.error("An IOException has been thrown when getting Facebook AccessTokenFromCode. " + e.getMessage());
		} catch (URISyntaxException e) {
			log.error("An URISyntaxException has been thrown when creating url for Facebook access token. " + e.getMessage());
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
	public FacebookUser getMe(String accessToken) throws HttpStatusException {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		CloseableHttpClient client = clientBuilder.build();

		FacebookUser facebookUser = null;
		try {
			URIBuilder builder = new URIBuilder(USERS_ME).addParameter(ACCESS_TOKEN_PARAMETER, accessToken).addParameter(FIELDS_PARAMETER,
					ME_FIELDS_VALUES_PARAMETER);

			HttpGet request = new HttpGet(builder.build());
			HttpResponse response = client.execute(request);

			String responseBody = EntityUtils.toString(response.getEntity());
			log.info("User retrieved from Facebook api " + responseBody);

			facebookUser = new Gson().fromJson(responseBody, FacebookUser.class);
			FacebookErrorUtils.checkErrors(facebookUser);

		} catch (IOException e) {
			log.error("An IOException has been thrown when getting publications. " + e.getMessage());
		} catch (URISyntaxException e) {
			log.error("An URISyntaxException has been thrown when creating url for facebook me. " + e.getMessage());
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				log.error("An IOException has been thrown when closing the HttpClient object. " + e.getMessage());
			}
		}
		return facebookUser;
	}

}
