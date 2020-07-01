package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.dao.AccountDAO;

public class ApiAccountDAO implements AccountDAO {

	private static String authToken = "";
	private String baseUrl;
	private RestTemplate restTemplate;
	
	public ApiAccountDAO(String baseUrl) {
		this.baseUrl = baseUrl;
		restTemplate = new RestTemplate();
	}
	
	@Override
	public double getAccountBalanceByUserId(Integer userId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(authToken);
		HttpEntity entity = new HttpEntity<>(headers);
		
		return restTemplate.exchange(baseUrl + "/accounts/" + userId, HttpMethod.GET, entity, double.class).getBody();
	}

}
