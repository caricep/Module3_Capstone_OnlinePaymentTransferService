package com.techelevator.tenmo.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.models.Account;

public class ApiAccountService implements AccountDAO {

	private static String authToken = "";
	private String baseUrl;
	private RestTemplate restTemplate;
	
	public ApiAccountService(String baseUrl) {
		this.baseUrl = baseUrl;
		restTemplate = new RestTemplate();
	}
	
	@Override
	public double getAccountBalanceByUserId(Integer userId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(authToken);
		HttpEntity entity = new HttpEntity<>(headers);
		
		return restTemplate.exchange(baseUrl + "/users/" + userId + "/accounts", HttpMethod.GET, entity, double.class).getBody();
	}

	@Override
	public List<Account> getListOfUserAccounts() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(authToken);
		HttpEntity entity = new HttpEntity<>(headers);
		
		Account[] userAccountArray = restTemplate.exchange(baseUrl + "/accounts", HttpMethod.GET, entity, Account[].class).getBody();
		List<Account> listOfUserAccounts = Arrays.asList(userAccountArray);
		return listOfUserAccounts;
	}
	
}
