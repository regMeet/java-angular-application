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
import org.springframework.stereotype.Repository;

import com.company.project.api.dao.intefaces.FacebookDAO;
import com.company.project.api.entities.facebook.FacebookUser;
import com.company.project.api.exception.HttpStatusException;
import com.company.project.api.utils.FacebookErrorUtils;
import com.google.gson.Gson;

/**
 * Handles Facebook api's endpoints
 */
@Repository
public class FacebookDAOImpl implements FacebookDAO {
	private static final String ACCESS_TOKEN_PARAMETER = "access_token";
	private static final String FIELDS_PARAMETER = "fields";
	private static final String ME_FIELDS_VALUES_PARAMETER = "id, name, first_name, last_name";
	private static final String CAPABILITIES_VALUE_PARAMETER = "capabilities";

	private static final Logger log = Logger.getLogger(FacebookDAOImpl.class);

	private static final String API_VERSION = "v2.4";
	private static final String FB_API_URL = "https://graph.facebook.com/" + API_VERSION;

	private static final String ACCESS_TOKEN_URL = FB_API_URL + "/oauth/access_token";
	private static final String USERS_ME = FB_API_URL + "/me";
	private static final String AD_ACCOUNTS = FB_API_URL + "/me/adaccounts";
	private static final String AD_ACCOUNT_CAPABILITIES = FB_API_URL + "/%s";

	public FacebookDAOImpl() {
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
			FacebookErrorUtils.checkFacebookErrors(facebookUser);

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
