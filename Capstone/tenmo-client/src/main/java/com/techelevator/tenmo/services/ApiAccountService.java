package com.techelevator.tenmo.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;

public class ApiAccountService implements AccountDAO {

	private static String authToken = "";
	private String baseUrl;
	private RestTemplate restTemplate;
	
	public ApiAccountService(String baseUrl) {
		this.baseUrl = baseUrl;
		restTemplate = new RestTemplate();
	}
	
	
	@Override
	public double getAccountBalanceByUserId(int userId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(authToken);
		HttpEntity entity = new HttpEntity<>(headers);
		
		return restTemplate.exchange(baseUrl + "/users/" + userId + "/accounts", HttpMethod.GET, entity, double.class).getBody();
	}
	
	@Override
	public int getAccountIdByUserId(int userId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(authToken);
		HttpEntity entity = new HttpEntity<>(headers);
		
		return restTemplate.exchange(baseUrl + "/accounts/" + userId, HttpMethod.GET, entity, int.class).getBody();
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


	@Override
	public Account withdrawMoneyForTransfer(Account account) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(authToken);
		HttpEntity<Account> entity = new HttpEntity<Account>(account, headers);
		
		return restTemplate.exchange(baseUrl + "/accounts", HttpMethod.PUT, entity, Account.class).getBody();
	}


	@Override
	public Account depositMoneyForTransfer(Account account, int accountToId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(authToken);
		HttpEntity<Account> entity = new HttpEntity<Account>(account, headers);
		
		return restTemplate.exchange(baseUrl + "/accounts/" + accountToId, HttpMethod.PUT, entity, Account.class).getBody();
	}

	


	
	
}
